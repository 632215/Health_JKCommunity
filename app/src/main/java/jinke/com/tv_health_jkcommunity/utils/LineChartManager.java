package jinke.com.tv_health_jkcommunity.utils;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import jinke.com.tv_health_jkcommunity.R;
import jinke.com.tv_health_jkcommunity.bean.LineBean;
import jinke.com.tv_health_jkcommunity.ui.weight.MyXFormatter;

/**
 * Created by Administrator on 2018/1/9.
 */

public class LineChartManager {
    public LineChartManager() {
        CircleVisiable = true;
        LineCubicMode = LineDataSet.Mode.CUBIC_BEZIER;
    }

    private static String lineName = null;
    private static String lineName1 = null;
    //Y轴的坐标范围
    private static float yMax = 30f;
    private static float yMin = 0f;

    //圆点
    private static boolean CircleVisiable = true;

    //线
    private static int LineCubicFilledColor = R.color.background_color;
    private static LineDataSet.Mode LineCubicMode = LineDataSet.Mode.CUBIC_BEZIER;

    /**
     * @param context    上下文
     * @param mLineChart 折线图控件
     * @param yValue     折线在y轴的值
     * @param yKedu
     * @Description:创建两条折线
     */
    public void initSingleLineChart(Context context, LineChart mLineChart, ArrayList<Entry> yValue, ArrayList<String> yKedu) {
        initDataStyle(context, mLineChart, yKedu, yKedu.size());
        //获取一条line实例
        LineDataSet lineDataSet = new LineDataSet(yValue, "");
        //设置线的属性
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleSize(2f);
        lineDataSet.setCircleColors(context.getResources().getColor(R.color.line_chart_circle_color1), context.getResources().getColor(R.color.line_chart_circle_color2));
        lineDataSet.setLineWidth(1f);
        lineDataSet.setColor(context.getResources().getColor(R.color.line_chart_line_color1));
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(LineCubicFilledColor);
        lineDataSet.setMode(LineCubicMode);//设置为光滑曲线
        lineDataSet.setDrawCircles(CircleVisiable);//设置是否绘制圆点
//        if (Build.VERSION.SDK_INT >= 18) {
//            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape_fill_color);
//            lineDataSet.setFillDrawable(drawable);
//        } else {
//            lineDataSet.setFillColor(context.getResources().getColor(R.color.background_color));
//        }
        lineDataSet.setFillColor(context.getResources().getColor(R.color.line_chart_bg_color));

        //将数据插入
        List<ILineDataSet> lines = new ArrayList<>();
        lines.add(lineDataSet);
        LineData lineData = new LineData(lines);
        mLineChart.setData(lineData);
        //设置动画效果
        mLineChart.animateY(1000, Easing.EasingOption.Linear);
        mLineChart.animateX(1000, Easing.EasingOption.Linear);
        mLineChart.invalidate();
    }

    /**
     * @param context    上下文
     * @param mLineChart 折线图控件
     * @param yKedu
     * @Description:创建多条折线
     */
    public void initLineSChart(Context context, LineChart mLineChart, ArrayList<LineBean> lineList, ArrayList<String> yKedu) {
        int max = 0;
        for (LineBean bean : lineList) {
            max = bean.getEntries().size() > max ? bean.getEntries().size() : max;
        }
        initDataStyle(context, mLineChart, yKedu, max);
        List<ILineDataSet> lines = new ArrayList<>();
        //获取一条line实例
        for (int x = 0; x < lineList.size(); x++) {
            LineDataSet lineDataSet = new LineDataSet(lineList.get(x).getEntries(), "");
            //设置线的属性
            lineDataSet.setDrawValues(false);
            lineDataSet.setCircleSize(2f);
            lineDataSet.setCircleColors(context.getResources().getColor(R.color.line_chart_circle_color1), context.getResources().getColor(R.color.line_chart_circle_color2));
            lineDataSet.setLineWidth(1f);
            lineDataSet.setColor(context.getResources().getColor(lineList.get(x).getLineColor()));
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillColor(LineCubicFilledColor);
            lineDataSet.setMode(LineCubicMode);//设置为光滑曲线
            lineDataSet.setDrawCircles(CircleVisiable);//设置是否绘制圆点
//            for (int y = 0; y < lineList.get(x).getEntries().size(); y++) {
//                if (lineList.get(x).getEntries().get(y).getY() == max) {
//                    if (Build.VERSION.SDK_INT >= 18) {
//                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.shape_fill_color);
//                        lineDataSet.setFillDrawable(drawable);
//                    } else {
//                        lineDataSet.setFillColor(context.getResources().getColor(R.color.background_color));
//                    }
//                }
//            }
            lineDataSet.setFillColor(context.getResources().getColor(R.color.line_chart_bg_color));
            //将数据插入
            lines.add(lineDataSet);
            LineData lineData = new LineData(lines);
            mLineChart.setData(lineData);
        }
        //设置动画效果
        mLineChart.animateY(1000, Easing.EasingOption.Linear);
        mLineChart.animateX(1000, Easing.EasingOption.Linear);
        mLineChart.invalidate();
    }

    /**
     * @param context
     * @param mLineChart
     * @param yValue
     * @param value
     * @Description:初始化图表的样式
     */
    private void initDataStyle(Context context, LineChart mLineChart, ArrayList<String> yValue, int value) {
        //设置图表是否支持触控操作
        Description description = new Description();
        description.setText("");
        mLineChart.setDescription(description);
        mLineChart.setTouchEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.setBackgroundColor(context.getResources().getColor(R.color.background_color));
        mLineChart.setDrawGridBackground(false);
//        设置折线 图例（默认在图表的左下角）
        Legend title = mLineChart.getLegend();
        title.setEnabled(false);//设置不显示图例

        //设置x轴的样式
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#186AE7"));
        xAxis.setAxisLineWidth(1f);
        xAxis.setTextSize(0.4f);
        xAxis.setTextColor(context.getResources().getColor(R.color.white));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(value,true);
//        xAxis.setAxisMinValue(0);
//        xAxis.setAxisMaxValue((float)5);
//        xAxis.setAxisMaxValue((float) value * 10 / 8);
        xAxis.setValueFormatter(new MyXFormatter(yValue));
        //设置是否显示x轴
        xAxis.setDrawLabels(true);
        xAxis.setEnabled(true);

        //设置左边y轴的样式
        YAxis yAxisLeft = mLineChart.getAxisLeft();
        yAxisLeft.setAxisLineColor(Color.parseColor("#186AE7"));
        yAxisLeft.setTextColor(context.getResources().getColor(R.color.white));
        yAxisLeft.setAxisLineWidth(1f);
        yAxisLeft.setTextSize(0.2f);
        yAxisLeft.setGridColor(context.getResources().getColor(R.color.line_chart_grid_line_color));
        yAxisLeft.setDrawGridLines(true);//显示网格
        yAxisLeft.setAxisMaximum(yMax);
        yAxisLeft.setAxisMinimum(yMin);
        yAxisLeft.setDrawZeroLine(false);
        //设置右边y轴的样式
        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);
    }

    /**
     * @param name
     * @Description:设置折线的名称
     */

    public void setLineName(String name) {
        lineName = name;
    }

    /**
     * @param name
     * @Description:设置另一条折线的名称
     */
    public void setLineName1(String name) {
        lineName1 = name;
    }

    /**
     * 设置纵坐标的最大最小值
     *
     * @param yMax
     * @param yMin
     */
    public void sety(float yMax, float yMin) {
        LineChartManager.yMax = yMax;
        LineChartManager.yMin = yMin;
    }

    /**
     * 设置曲线的直角/圆润（默认圆润）
     *
     * @param lineCubicMode
     */
    public void setLineCubicMode(LineDataSet.Mode lineCubicMode) {
        LineCubicMode = lineCubicMode;
    }

    /**
     * 设置是否绘制圆点（默认绘制）
     *
     * @param circleVisiable
     */
    public void setCircleVisiable(boolean circleVisiable) {
        CircleVisiable = circleVisiable;
    }
}
