package jinke.com.tv_health_jkcommunity.socket;

import java.io.IOException;

import jinke.com.tv_health_jkcommunity.utils.LogUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/10.
 */

public class JpHelper {
//    private String BaseSocketAddress = "http://health.tq-service.com/healthService/webSocketServer"; //正式
//    private String socketRegisterAddress = "http://health.tq-service.com/healthService/message/sendData"; //正式

    private static String BaseSocketAddress = "ws://dev.tq-service.com:18087/healthService/webSocketServer"; //测试
    private static String RegisterAddress = "http://dev.tq-service.com:18087/healthService/message/sendData"; //测试

    public static void startJG() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("token", "1").build();
        Request request = new Request.Builder().url(RegisterAddress).post(body).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String string = response.body().string();
                LogUtils.e(string);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
