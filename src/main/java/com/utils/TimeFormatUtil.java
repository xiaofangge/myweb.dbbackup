package com.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatUtil {

    private final static SimpleDateFormat SDF_DIR_NAME = new SimpleDateFormat("yyyy-MM-dd/HHmm");

    private final static SimpleDateFormat SDF_DIR_HM = new SimpleDateFormat("HHmm");

    private final static SimpleDateFormat SDF_DAY = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat ALL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static String dateToNormal(Date date) {
        return ALL.format(date);
    }

    /**
     * Date => HHmm
     *
     * @param date 时间
     * @return String
     * @author Fang Ruichuan
     * @date 2021/11/9 14:12
     */
    public static String dateToDirHM(Date date) {
        return SDF_DIR_HM.format(date);
    }

    /**
     * Date => yyyy-MM-dd/HHmm
     *
     * @param date 时间
     * @return String
     * @author Fang Ruichuan
     * @date 2021/11/2 17:00
     */
    public static String dateToDirectoryName(Date date) {
        return SDF_DIR_NAME.format(date);
    }


    /**
     * Date => yyyyMMdd
     *
     * @param date new Date()
     * @return String
     * @author Fang Ruichuan
     * @date 2021/11/3 10:12
     */
    public static String dateToDay(Date date) {
        return SDF_DAY.format(date);
    }


    /**
     * 将秒数转为其他形式
     * @author Fang Ruichuan
     * @date 2021/11/12 9:25
     * @param seconds 秒数
     * @return String
     */
    public static String secondsToFormat(double seconds) {
        if (seconds < 60) {
            return String.format("%.2f秒", seconds);
        } else if (seconds > 60 && seconds < 3600) {
            int m = ((int)seconds) / 60;
            double s = seconds % 60;
            return m + "分" + String.format("%.2f秒", s);
        } else {
            int h = ((int) seconds) / 3600;
            int m = ((int) seconds) % 3600 / 60;
            double s = seconds % 3600 % 60;
            return h + "小时" + m + "分" + String.format("%.2f秒", s);
        }
    }
}
