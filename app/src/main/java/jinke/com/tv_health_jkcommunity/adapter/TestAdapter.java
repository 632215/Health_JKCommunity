package jinke.com.tv_health_jkcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jinke.com.tv_health_jkcommunity.R;


/**
 * Created by Administrator on 2018/1/2.
 */

public class TestAdapter extends BaseCommonAdapter<String, TestAdapter.ViewHolder> {
    public TestAdapterListener listener;

    public TestAdapter(List<String> list, Context context) {
        super(list, context);
    }

    public void setListener(TestAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(final TestAdapter.ViewHolder holder, final int position) {
        holder.txTitle.setText(list.get(position));
        holder.txTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                if (b)
//                    holder.txTitle.setTextSize(60);
//                else
//                    holder.txTitle.setTextSize(50);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position, list.get(position));
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            txTitle = itemView.findViewById(R.id.tx_title);
        }
    }

    public interface TestAdapterListener {
        void onItemClick(int position, String title);
    }
}



