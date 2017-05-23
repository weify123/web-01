package com.wfy.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 推荐使用
 * @author wfy  2017年2月10日 下午4:26:51
 * @since JDK 1.8	
 */
public class ThreadLocalDateUtil {

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalDateUtil.class);
    private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    private static final String dateTimeFormat = "yyyyMMddHHmmss";
    private static final String dateTimeFormatWithoutMills = "yyyy/MM/dd HH:mm";
    private static final String dateTimeFormatWithSecond = "yyyyMMdd HH:mm:ss";
    private static final String dateFormatWithSlash = "yyyy/MM/dd";
    private static final String dateFormat = "yyyyMMdd";
    private static final String dateWithChineseFormat = "yyyy年MM月dd日";

	private static final String date_format = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(); 
 
    public static DateFormat getDateFormat()   
    {  
        DateFormat df = threadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(date_format);  
            threadLocal.set(df);  
        }  
        return df;  
    }  

    public static String formatDate(Date date) throws ParseException {
        return getDateFormat().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }   
}
