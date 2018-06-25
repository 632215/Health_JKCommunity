package jinke.com.tv_health_jkcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public abstract class BaseCommonAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    public List<T> list = new ArrayList<>();
    public Context context;

    public BaseCommonAdapter(List<T> list, Context context) {
        if (list != null) {
            this.list.addAll(list);
        }
        this.context = context;
    }

    public void refreshAllData(List<T> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public abstract V onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(V holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }
}
