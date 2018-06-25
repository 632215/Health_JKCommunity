package jinke.com.tv_health_jkcommunity.jpserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
import jinke.com.tv_health_jkcommunity.bean.ConnectDataBean;
import jinke.com.tv_health_jkcommunity.bean.JPushBean;
import jinke.com.tv_health_jkcommunity.ui.activity.MainActivity;
import jinke.com.tv_health_jkcommunity.utils.LogUtils;

/**
 * 一般消息的接收者
 * <p>
 * Created by Administrator on 2018/1/17.
 */

public class CommunityMsgReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private Context mContext;

    private JPushBean bean;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Bundle bundle = intent.getExtras();
        //获取内容
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogUtils.e("[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.e("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            // TODO: 2018/5/10
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            bean = new Gson().fromJson(json, JPushBean.class);
//            ConnectDataBean connectDataBean = new Gson().fromJson(bean.getData(), ConnectDataBean.class);
            ConnectDataBean connectDataBean = new ConnectDataBean();
            MainActivity mainActivity = new MainActivity();
            mainActivity.onMessage(connectDataBean);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.e("[MyReceiver] 用户点击打开了通知");
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogUtils.e("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogUtils.e("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogUtils.e("[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据

    private static String printBundle(Bundle bundle) {
        return String.valueOf(bundle);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
    }
}
