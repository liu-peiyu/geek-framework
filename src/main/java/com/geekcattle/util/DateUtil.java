package com.geekcattle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 日期时间工具类
 * @author geekcattle
 */
public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);


    private final static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private final static String Y_M_D_H_M = "yyyy-MM-dd HH:mm";
    private final static String Y_M_D = "yyyy-MM-dd";
    private final static String YMD = "yyyyMMdd";
    private final static String YEAR = "yyyy";
    private final static String MONTH = "MM";
    private final static String DAY = "dd";
    private final static DateTimeFormatter YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern(YMDHMS);
    private final static DateTimeFormatter YEARSDF = DateTimeFormatter.ofPattern(YEAR);
    private final static DateTimeFormatter MONTHSDF = DateTimeFormatter.ofPattern(MONTH);
    private final static DateTimeFormatter DAYSDF = DateTimeFormatter.ofPattern(DAY);
    private final static DateTimeFormatter YYYYMMDDHHMM = DateTimeFormatter.ofPattern(Y_M_D_H_M);
    private final static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern(Y_M_D);
    private final static DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern(YMD);
    private final static long ONE_DAY = 86400L;

    private final static LocalDateTime LOCAL_CURRENT_DATE_TIME = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

    /**
     * 获得当前时间(字符串类型)
     * 格式：2014-12-02 10:38:53
     * @return String
     */
    public static String getCurrentTime() {
        return LOCAL_CURRENT_DATE_TIME.format(YYYYMMDDHHMMSS);
    }

    /**
     * 获取年月日(字符串类型)
     * 格式：2014-12-02
     * @return String
     */
    public static String getCurrentDate() {
        return LOCAL_CURRENT_DATE_TIME.format(YYYY_MM_DD);
    }

    /**
     * 获取今天的日期
     * 格式：20141202
     * @return String
     */
    public static String getTodateString() {
        return LOCAL_CURRENT_DATE_TIME.format(YYYYMMDD);
    }

    /**
     * 获得当前时间(日期类型)
     * 格式：2014-12-02 10:38:53
     * @return String
     */
    public static Date getSysTime() {        ;
        return Date.from(LOCAL_CURRENT_DATE_TIME.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取年月日(日期类型)
     * 格式：2014-12-02
     * @return String
     */
    public static Date getSysDate() {
        LocalDate localDate = LocalDate.now();
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前日期
     * @return
     */
    public static Instant getInstantNow(){
        return Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));
    }

    /**
     * 获取当前秒数 10位
     * @return
     */
    public static Long getInstantNowSecond(){
        return getInstantNow().getEpochSecond();
    }

    /**
     * 获取当前毫秒数 13位
     * @return
     */
    public static Long getInstantNowMillis(){
        return getInstantNow().toEpochMilli();
    }

    /**
     * 获取前某几天的日期
     * @param day
     * @return
     */
    public static LocalDateTime getYesterDay(int day){
        Long timestamp = getInstantNow().getEpochSecond() - ONE_DAY * day;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),ZoneId.systemDefault());
        return localDateTime;
    }

    /**
     * 可以获取昨天的日期
     * 格式：2014-12-01
     * @return String
     */
    public static Date getYesterDayDate(int day) {
        return Date.from(getYesterDay(day).atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取昨天的日期
     * 格式：20141201
     * @return String
     */
    public static String getYesterDayString(int day) {
        return getYesterDay(day).format(YYYYMMDD);
    }

    /**
     * 获取当前的年
     * @return String
     */
    public static String getCurrentYear() {
        return LOCAL_CURRENT_DATE_TIME.format(YEARSDF);
    }

    /**
     * 获取当前的月
     * @return String
     */
    public static String getCurrentMonth() {
        return LOCAL_CURRENT_DATE_TIME.format(MONTHSDF);
    }

    /**
     * 获取当前的日
     * @return String
     */
    public static String getCurrentDay() {
        return LOCAL_CURRENT_DATE_TIME.format(DAYSDF);
    }

    /**
     * 获取今天0点开始的秒数
     * @return long
     */
    public static long getTimeNumberToday() {
        //获取当前时间戳
        Long nowTimestamp = getInstantNow().getEpochSecond();
        Long startTimestamp = LocalDate.now().toEpochDay();
        return nowTimestamp-startTimestamp;
    }



    /**
     * 获得指定日期所在的自然周的第一天，即周日
     * @param date 日期
     * @return 自然周的第一天
     */
    public static Date getStartDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在的自然周的最后一天，即周六
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, 7);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在当月第一天
     * @param date
     * @return
     */
    public static Date getStartDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在当月最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期的下一个月的第一天
     * @param date
     * @return
     */
    public static Date getStartDayOfNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期的下一个月的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfNextMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONTH, 2);
        c.add(Calendar.DATE, -1);
        date = c.getTime();
        return date;
    }

    public static void main(String[] args) {
        logger.info("获取当前日期时间：{}", DateUtil.getCurrentTime());
        logger.info("获取当前日期：{}" , DateUtil.getCurrentDate());
        logger.info("获取当前日期时间：{}", DateUtil.getSysTime());
        logger.info("获取当前日期：{}", DateUtil.getSysDate());
        logger.info("获取昨天日期：{}",  DateUtil.getYesterDayDate(1));
        logger.info("获取前两天日期：{}", DateUtil.getYesterDayString(2));
        logger.info("获取当前年：{}", DateUtil.getCurrentYear());
        logger.info("获取当前月：{}", DateUtil.getCurrentMonth());
        logger.info("获取当前日：{}", DateUtil.getCurrentDay());
    }

}
