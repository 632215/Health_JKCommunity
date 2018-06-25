package jinke.com.tv_health_jkcommunity.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import jinke.com.tv_health_jkcommunity.presenter.BasePresenter;

/**
 * Created by Administrator on 2018/1/5.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    private T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        if (presenter != null) {
            presenter.setView((V) this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.setView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroyView();
        }
    }

    protected abstract T initPresenter();
}
