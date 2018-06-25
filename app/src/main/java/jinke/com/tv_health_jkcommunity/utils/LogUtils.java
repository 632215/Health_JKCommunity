package jinke.com.tv_health_jkcommunity.utils;

import android.util.Log;

import jinke.com.tv_health_jkcommunity.config.Config;

/**
 * Created by Administrator on 2018/1/5.
 */

public class LogUtils {
    public static void e(String mag) {
        Log.e(Config.LOG_TAG, mag);
    }

    public static void i(String mag) {
        Log.i(Config.LOG_TAG, mag);
    }
}
