package jinke.com.tv_health_jkcommunity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import jinke.com.tv_health_jkcommunity.presenter.BasePresenter;

/**
 * Created by Administrator on 2018/1/2.
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends Activity {
    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, childSetContentView(), null));
        ButterKnife.bind(this);
        try {
            childInitView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        presenter = initPresenter();
        if (presenter != null) {
            presenter.setView((V) this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.setView((V) this);
    }

    protected abstract int childSetContentView();

    protected abstract T initPresenter();

    protected abstract void childInitView() throws Exception;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.destroyView();
    }
}
