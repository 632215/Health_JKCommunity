package jinke.com.tv_health_jkcommunity.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuzhibing
 */
public class DateUtil {
    /**
     * 把毫秒转化成日期
     *
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数)
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
}