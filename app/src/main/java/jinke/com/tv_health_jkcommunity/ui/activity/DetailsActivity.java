package jinke.com.tv_health_jkcommunity.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jinke.com.tv_health_jkcommunity.R;
import jinke.com.tv_health_jkcommunity.base.BaseActivity;
import jinke.com.tv_health_jkcommunity.presenter.BasePresenter;
import jinke.com.tv_health_jkcommunity.ui.weight.ServiceScaleView;
import jinke.com.tv_health_jkcommunity.utils.LineChartManager;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class DetailsActivity extends BaseActivity {
    @BindView(R.id.tx_title)
    TextView txTitle;
    @BindView(R.id.line_chart)
    LineChartView lineChart;
    @BindView(R.id.mp_line_chart)
    LineChart mpLineChart;
    @BindView(R.id.scale_view)
    ServiceScaleView scaleView;

    private List<Line> lines;
    private List<PointValue> values;

    private LineData lineData;
    private List<Integer> arrowList = new ArrayList<>();
    //    {BitmapFactory.decodeResource(getResources(), R.mipmap.icon_green_arrow), BitmapFactory.decodeResource(getResources(), R.mipmap.icon_red_arrow)};
    private List<Integer> indexList = new ArrayList<>();
//    {BitmapFactory.decodeResource(getResources(), R.mipmap.icon_blood_oxygen1), BitmapFactory.decodeResource(getResources(), R.mipmap.icon_blood_oxygen2)};

    @Override
    protected void childInitView() {
        txTitle.setText(getIntent().getStringExtra("title"));
        initChart();
        initMpChat();
        arrowList.add(R.mipmap.icon_green_arrow);
        arrowList.add(R.mipmap.icon_red_arrow);
        indexList.add(R.mipmap.icon_blood_oxygen1);
        indexList.add(R.mipmap.icon_blood_oxygen2);
        scaleView = new ServiceScaleView(this);
        scaleView.setList(R.mipmap.icon_red_arrow, indexList,1);
    }

    private void initMpChat() {
//        //设置x轴的数据
//        ArrayList<String> xValues = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            xValues.add("sada" + i);
//        }
//        //设置y轴的数据
//        ArrayList<Entry> yValue = new ArrayList<>();
//        yValue.add(new Entry(5, 0));
//        yValue.add(new Entry(13, 1));
//        yValue.add(new Entry(6, 2));
//        yValue.add(new Entry(3, 3));
//        yValue.add(new Entry(7, 4));
//        yValue.add(new Entry(2, 5));
//        yValue.add(new Entry(5, 6));
//        //设置折线的名称
////        LineChartManager.setLineName("当月值");
//        //创建一条折线的图表
//        mpLineChart.setBackgroundColor(getResources().getColor(R.color.background_color));
//        mpLineChart.setDrawGridBackground(false);
//        LineChartManager.initSingleLineChart(this, mpLineChart, xValues, yValue, ys);
    }

    private void initChart() {
        Line line = new Line();
        line.setCubic(true);
        line.setPointRadius(3);
        line.setStrokeWidth(2);
        line.setColor(getResources().getColor(R.color.A106));
        lines = new ArrayList<>();
        lines.add(line);
        values = new ArrayList<>();
        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 4));
        values.add(new PointValue(2, 16));
        values.add(new PointValue(3, 4));
        lineChart.setInteractive(false);
        LineChartData data = new LineChartData();
        Axis axisX = new Axis();//x
        Axis axisY = new Axis();//y
        axisX.setTextColor(getResources().getColor(R.color.A106));
        axisX.setLineColor(getResources().getColor(R.color.white));
        axisY.setTextColor(getResources().getColor(R.color.white));
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setLines(lines);
        line.setValues(values);
        line.setFilled(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setLineChartData(data);
    }

    @Override
    protected int childSetContentView() {
        return R.layout.activity_details;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
