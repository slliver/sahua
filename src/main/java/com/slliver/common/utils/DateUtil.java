package com.slliver.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/9 10:46
 * @version: 1.0
 */
public class DateUtil {

    public static void main(String[] args) {
        Date date = new Date();
        Date expireDate = DateUtils.addMinutes(date, 10);
        System.out.println(DateFormatUtils.format(expireDate, "yyyy-MM-dd HH:mm:ss"));

        String dateFormat = getDate2(getCurrentDate());
        System.out.println(dateFormat);
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    // 使用ThreadLocal强制使每个线程的SimpleDateFormat变量唯一，实现线程安全
    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<>();

    private static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat sdf = simpleDateFormatThreadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            simpleDateFormatThreadLocal.set(sdf);
        }
        return sdf;
    }

    private static String getDate(Date date) {
        SimpleDateFormat sd = getSimpleDateFormat();
        return sd.format(date);
    }

    // 使用apache-commons-lang包下的FastDateFormat
    private static String getDate2(Date date){
        FastDateFormat fdf = FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS);
        return fdf.format(date);
    }

    public static String format(Date date){
        FastDateFormat fdf = FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS);
        return fdf.format(date);
    }
}
