package jinke.com.tv_health_jkcommunity.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/4.
 */

public class ToastUtils {
    public static void showLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
