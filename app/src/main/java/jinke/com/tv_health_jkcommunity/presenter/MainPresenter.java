package jinke.com.tv_health_jkcommunity.presenter;

import android.content.Context;

import jinke.com.tv_health_jkcommunity.view.MainView;

/**
 * Created by Administrator on 2018/1/5.
 */

public class MainPresenter extends BasePresenter<MainView> {
    public Context context;

    public MainPresenter(Context context) {
        this.context = context;
    }
}
