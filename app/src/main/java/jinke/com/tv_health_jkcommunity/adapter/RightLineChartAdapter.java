package jinke.com.tv_health_jkcommunity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

import jinke.com.tv_health_jkcommunity.R;


/**
 * Created by Administrator on 2018/1/2.
 */

public class RightLineChartAdapter extends BaseCommonAdapter<String, RightLineChartAdapter.ViewHolder> {
    public LineChartAdapterListener listener;

    public RightLineChartAdapter(List<String> list, Context context) {
        super(list, context);
    }

    public void setListener(LineChartAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public RightLineChartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_right_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final RightLineChartAdapter.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position, list.get(position));
            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LineChart lineChart;

        public ViewHolder(View itemView) {
            super(itemView);
            lineChart = itemView.findViewById(R.id.line_chart);
        }
    }

    public interface LineChartAdapterListener {
        void onItemClick(int position, String title);
    }
}



