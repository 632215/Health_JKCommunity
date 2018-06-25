package jinke.com.tv_health_jkcommunity.ui.weight;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class MyXFormatter implements IAxisValueFormatter {
    List<String> mValues;
    int x = 0;

    public MyXFormatter(List<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float v, AxisBase axis) {
//        String returnValue = "";
//        switch (String.valueOf(v)) {
//            case "0.0":
//                returnValue = mValues.get(0);
//                break;
//            case "0.8":
//                returnValue = mValues.get(1);
//                break;
//            case "1.6":
//                returnValue = mValues.get(2);
//                break;
//            case "2.4":
//                returnValue = mValues.get(3);
//                break;
//            case "3.2":
//                returnValue = mValues.get(4);
//                break;
//            case "4.0":
//                returnValue = mValues.get(5);
//                break;
//            default:
//                returnValue = "";
//                break;
//        }
        if (v == 0 || v == -1) {
            x = 0;
            return mValues.get(0);
        } else if(v > 0) {
            x++;
            return mValues.get(x - 1);
        }else {
            return String.valueOf(v);
        }

//        return getDate(mValues.get(0), x);
//        return getDate(mValues.get(0), (int) (v / 0.8));
    }

    private String getDate(String s, int i) {
        if (s.contains("/")) {  //月/日
            String month = s.substring(0, s.indexOf("/"));
            String year = s.substring(s.indexOf("/") + 1);
            if (Integer.parseInt(month) + i > 12) {
                month = String.valueOf(Integer.parseInt(month) + i - 12);
//                year = String.valueOf(Integer.parseInt(year) + 1);
            } else {
                month = String.valueOf(Integer.parseInt(month) + i);
            }
            String result = Integer.parseInt(month) + "/" + year;
            return result;
        } else {
            String month = s;
            if (Integer.parseInt(month) + i > 12) {
                month = String.valueOf(Integer.parseInt(month) + i - 12);
            } else {
                month = String.valueOf(Integer.parseInt(month) + i);
            }
            String result = month;
            return result;
        }
    }
}