package jinke.com.tv_health_jkcommunity.bean;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17.
 */

public class LineBean {
    private ArrayList<Entry> entries;
    private int lineColor;

    public LineBean(ArrayList<Entry> entries, int lineColor) {
        this.entries = entries;
        this.lineColor = lineColor;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }
}
