package jinke.com.tv_health_jkcommunity.presenter;

/**
 * Created by Administrator on 2018/1/5.
 */

public abstract class BasePresenter<T> {
    private T view;

    public void setView(T view) {
        this.view = view;
    }

    public T destroyView(){
        return view = null;
    }
}
