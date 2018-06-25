package jinke.com.tv_health_jkcommunity.application;

import android.app.Application;


/**
 * Created by Administrator on 2018/1/4.
 */

public class MyApplication extends Application {
    private MyApplication application;

    public MyApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.application = this;

    }
}
