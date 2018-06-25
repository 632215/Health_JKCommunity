package jinke.com.tv_health_jkcommunity.socket;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import jinke.com.tv_health_jkcommunity.bean.ConnectBean;
import jinke.com.tv_health_jkcommunity.bean.ConnectDataBean;
import jinke.com.tv_health_jkcommunity.bean.ConnectSuccessBean;
import jinke.com.tv_health_jkcommunity.utils.JsonUtil;
import jinke.com.tv_health_jkcommunity.utils.LogUtils;
import jinke.com.tv_health_jkcommunity.utils.StringUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SocketHelper {
    private WebSocket webSocket;
    private boolean isFirst = true;
    private SocketListener listener;
    private int messageRec = 1;
    private int SUCCESS = 1;
    private int FAILED = 2;
    private int SENT_HEART = 3;

    private String BaseSocketAddress = "http://health.tq-service.com/healthService/webSocketServer"; //正式
    private String socketRegisterAddress = "http://health.tq-service.com/healthService/message/sendData"; //正式

//    private String BaseSocketAddress = "ws://dev.tq-service.com:18087/healthService/webSocketServer"; //测试
//    private String socketRegisterAddress = "http://dev.tq-service.com:18087/healthService/message/sendData"; //测试

    public SocketHelper(SocketListener listener) {
        this.listener = listener;
        initSocket();
    }

    private void initSocket() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
                .build();
        Request request = new Request.Builder()
                .url(BaseSocketAddress)
                .build();
        webSocket = client.newWebSocket(request, new
                WebSocketListener() {
                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        super.onOpen(webSocket, response);
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, String text) {
                        super.onMessage(webSocket, text);
                        if (messageRec == 1) {
                            ConnectSuccessBean bean = JsonUtil.GsonToBean(text, ConnectSuccessBean.class);
                            ConnectBean connectBean = new ConnectBean();
                            connectBean.setSignType("isRegister");
                            connectBean.setData(new ConnectBean.DataBean("M0000002011090800011", bean.getData()));
                            webSocket.send(JsonUtil.GsonString(connectBean));
                            LogUtils.e("socket：第" + messageRec + "次连接：" + text);
                            //触发信息发送
                            Request request = new Request
                                    .Builder()
                                    .url(socketRegisterAddress)//正式
                                    .build();

                            try {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                Response response = okHttpClient.newCall(request)
                                        .execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (messageRec == 2) {
                            ConnectBean connectBean = JsonUtil.GsonToBean(text, ConnectBean.class);
                            LogUtils.e("socket：第" + messageRec + "次连接：" + text);
                        } else if (messageRec > 2) {
                            LogUtils.e("socket：第" + messageRec + "次连接：" + text);
                            Message message = new Message();
                            message.obj = text;
                            message.what = SUCCESS;
                            handler.sendMessage(message);
                        }
                        messageRec++;
                    }

                    @Override
                    public void onMessage(WebSocket webSocket, ByteString bytes) {
                        super.onMessage(webSocket, bytes);
                    }

                    @Override
                    public void onClosing(WebSocket webSocket, int code, String reason) {
                        super.onClosing(webSocket, code, reason);
                        LogUtils.e("socket：连接准备关闭");
                        webSocket.close(1, "");
                    }

                    @Override
                    public void onClosed(WebSocket webSocket, int code, String reason) {
                        super.onClosed(webSocket, code, reason);
                        if (!StringUtils.equals("destory", reason)) {
                            failed();
                            LogUtils.e("socket：连接关闭");
                        }
                    }

                    @Override
                    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                        super.onFailure(webSocket, t, response);
                        failed();
                        LogUtils.e("socket：连接失败");
                    }
                });
    }

    public void destory() {
        webSocket.close(3200, "destory");
    }

    private void failed() {
        isFirst = true;
        messageRec = 1;
        Message message = new Message();
        message.what = FAILED;
        handler.sendMessage(message);
    }

    public interface SocketListener {
        void onMessage(ConnectDataBean bean);

        void onError();

        void onHeart();

        void connect();

        void sentHeart();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        listener.connect();
                        ConnectDataBean bean = JsonUtil.GsonToBean(String.valueOf(msg.obj), ConnectDataBean.class);
                        if (bean.getData().getUserInfo() != null)
                            listener.onMessage(JsonUtil.GsonToBean(String.valueOf(msg.obj), ConnectDataBean.class));
                        else {
                            LogUtils.e("socket：心跳");
                            listener.onHeart();
                            LogUtils.e("ssss:收到心跳回复");
                            heartIsGet = true;
                        }
                    } catch (Exception e) {
                        return;
                    }
                    //设置心跳
                    if (timer == null) {
                        timer = new Timer();
                        timer.schedule(task, 0, 5000);
                    }
                    break;
                case 2:
                    if (task != null)
                        task.cancel();
                    if (timer != null)
                        timer.cancel();
                    if (webSocket != null)
                        webSocket.cancel();
                    listener.onError();
                    break;
                case 3:
                    listener.sentHeart();
                    break;
            }
        }
    };

    Timer timer;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = SENT_HEART;
            handler.sendMessage(message);
            ConnectBean connectBean = new ConnectBean();
            connectBean.setSignType("isHeartbeat");
            webSocket.send(JsonUtil.GsonString(connectBean));
            mHandler.postDelayed(runnable, 7000);
            heartIsGet = false;
            LogUtils.e("ssss:开启定时");
        }
    };

    private boolean heartIsGet = false;

    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!heartIsGet) {
                LogUtils.e("ssss:心跳停止");
                failed();
            }
        }
    };
}
