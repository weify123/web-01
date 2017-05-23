package com.wfy.server.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by weifeiyu on 2017/5/17.
 */
public class LocalDateUtils {

    private static ZoneId zone = ZoneId.systemDefault();

    /**
     * 字符串转Date
     * @param date
     * @return
     * @throws Exception
     */
    public static Date convertToDate(String date) throws Exception{
        LocalDate localDate = null;
        if(null == date){
            throw new NullPointerException("date isn't null");
        } else {
            localDate = LocalDate.parse(date);
            return convertToDate(localDate);
        }
    }

    /**
     * 字符串转LocalDateTime
     * @param date
     * @return localDateTime
     */
    public static LocalDateTime convertToLocalDateTime(String date){
        LocalDateTime localDateTime = null;
        if(null == date){
            throw new NullPointerException("date isn't null");
        } else {
            localDateTime = LocalDateTime.parse(date);
            return localDateTime;
        }
    }

    /**
     * LocalDate转Date
     * @param localDate
     * @return Date
     */
    public static Date convertToDate(LocalDate localDate){
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate转Date
     * @param localDateTime
     * @return Date
     */
    public static Date convertToDate(LocalDateTime localDateTime){
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * Date转LocalDate
     * @param date
     * @return localDate
     */
    public static LocalDate convertToLocalDate(Date date){
        Instant instant = date.toInstant();
        return convertToLocalDateTime(instant).toLocalDate();
    }

    /**
     * Date转LocalTime
     * @param date
     * @return localDate
     */
    public static LocalTime convertToLocalTime(Date date){
        Instant instant = date.toInstant();
        return convertToLocalDateTime(instant).toLocalTime();
    }

    /**
     * Date转LocalDatetime
     * @param date
     * @return localDate
     */
    public static LocalDateTime convertToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        return convertToLocalDateTime(instant);
    }


    /**
     * Instant转LocalDateTime
     * @param instant
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(Instant instant){
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime转Instant
     * @param localDateTime
     * @return
     */
    public static Instant convertToInstant(LocalDateTime localDateTime){
        return localDateTime.atZone(zone).toInstant();
    }

    /**
     * LocalDate转Instant
     * @param localDate
     * @return
     */
    public static Instant convertToInstant(LocalDate localDate){
        return localDate.atStartOfDay(zone).toInstant();
    }

    /**
     * LocalDate转LocalDateTime
     * @param localDate
     * @return LocalDateTime
     */
    public static LocalDateTime convertToLocalDateTime(LocalDate localDate){
        return localDate.atStartOfDay();
    }

    /**
     * 日周期格式化
     * @param localDateTime
     * @param formatStyle
     * @return
     */
    public static String formatter(LocalDateTime localDateTime, String formatStyle){
        return DateTimeFormatter.ofPattern(formatStyle).format(localDateTime);
    }

    /**
     * 设置年
     * @param sourceDate
     * @param year
     * @return LocalDateTime
     */
    public static LocalDateTime setYear(LocalDateTime sourceDate, Integer year){
        return sourceDate.withYear(year);
    }

    /**
     * 设置月
     * @param sourceDate
     * @param month
     * @return LocalDateTime
     */
    public static LocalDateTime setMonth(LocalDateTime sourceDate, Integer month){
        return sourceDate.withMonth(month);
    }

    /**
     * 设置天
     * @param sourceDate
     * @param dayOfMonth
     * @return LocalDateTime
     */
    public static LocalDateTime setDayOfMonth(LocalDateTime sourceDate, Integer dayOfMonth){
        return sourceDate.withDayOfMonth(dayOfMonth);
    }

    /**
     * 设置小时
     * @param sourceDate
     * @param hour
     * @return
     */
    public static LocalDateTime setHour(LocalDateTime sourceDate,Integer hour){
        return sourceDate.withHour(hour);

    }

    /**
     * 设置分钟
     * @param sourceDate
     * @param minute
     * @return
     */
    public static LocalDateTime setMinute(LocalDateTime sourceDate,Integer minute){
        return sourceDate.withMinute(minute);
    }

    /**
     * 设置秒
     * @param sourceDate
     * @param second
     * @return
     */
    public static LocalDateTime setSecond(LocalDateTime sourceDate,Integer second){
        return sourceDate.withSecond(second);
    }

    /**
     * 修改年月日
     * @param sourceDate
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static LocalDateTime setYMD(LocalDateTime sourceDate, Integer year, Integer month, Integer dayOfMonth) {
        return sourceDate.withYear(year).withMonth(month).withDayOfMonth(dayOfMonth);
    }

    /**
     * 修改时分秒
     * @param sourceDate
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static LocalDateTime setHMS(LocalDateTime sourceDate,Integer hour, Integer minute, Integer second) {
        return sourceDate.withHour(hour).withMinute(minute).withSecond(second);
    }

    /**
     * 计算相差的天数
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getInteverDays(LocalDate beginDate,LocalDate endDate){
        Period period = Period.between(beginDate, endDate);
        return period.getDays();
    }

    /**
     * 日期加减
     * @param  num  数量
     * @param  unit  单位
     * @param  localDate 原日期
     * @return LocalDate 增加后的日期
     */
    @SuppressWarnings("static-access")
    public static LocalDate addLocalDate(long num,ChronoUnit unit,final LocalDate localDate){
        LocalDate resultDate;
        if(num > 0){
            resultDate = localDate.now().plus(num, unit);
        } else {
            resultDate = localDate.now().minus(Math.abs(num), unit);
        }
        return resultDate;
    }

    /**
     * 日期时分秒加
     * @param  num  数量
     * @param  unit 单位
     * @param  localDateTime 原日期
     * @return LocalDateTime 增加后的日期
     */
    @SuppressWarnings("static-access")
    public static LocalDateTime addLocalDateTime(long num,ChronoUnit unit,LocalDateTime localDateTime){
        LocalDateTime resultDateTime;
        if(num > 0){
            resultDateTime = localDateTime.now().plus(num, unit);
        } else {
            resultDateTime = localDateTime.now().minus(Math.abs(num),unit);
        }
        return resultDateTime;
    }

    /**
     * 时分秒加减
     * @param  num  数量
     * @param  unit 单位
     * @param  localTime 原日期
     * @return LocalDateTime 增加后的日期
     */
    @SuppressWarnings("static-access")
    public static LocalTime addLocalTime(long num, ChronoUnit unit, LocalTime localTime){
        LocalTime resultTime;
        if(num > 0){
            resultTime = localTime.now().plus(num, unit);
        } else {
            resultTime = localTime.now().minus(Math.abs(num), unit);
        }
        return resultTime;
    }

    public static void main(String[] args){

        LocalDateTime time  = LocalDateTime.now(zone);
        String rr = formatter(time, "yyyy-MM-dd HH:mm:ss");
        System.out.println(rr);
        LocalDateTime time2  = addLocalDateTime(-2, ChronoUnit.HOURS, time);
        String yy = formatter(time2, "yyyy-MM-dd HH:mm:ss");
        System.out.println(yy);

        try {
            Date stringtod = convertToDate("2017-05-17");
            System.out.println(stringtod);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocalDateTime s = convertToLocalDateTime(new Date());
        System.out.println("s ->>>" + formatter(s,"yyyy-MM-dd HH:mm:ss"));
    }
}
