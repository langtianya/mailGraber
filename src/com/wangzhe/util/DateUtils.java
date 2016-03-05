/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wangzhe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ocq
 */
public class DateUtils {
private static SimpleDateFormat sdf;
    /**
     * 获得当前时间
     *
     * @return String
     */
    public static String getTime() {
        sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date()).toString();
    }

    /**
     * 获得当前日期
     *
     * @return String
     */
    public static String getDate() {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date()).toString();
    }

    /**
     * 获得当前日期时间
     *
     * @return String
     */
    public static String getDateTime() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date()).toString();
    } 
    /**
     * 获得当前日期时间
     *
     * @return String
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获得当前日期时间缩写成随机名字
     *
     * @return String
     */
    public static String getDateTimeForRandomName() {
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()).toString();
    }

    /**
     * 根据日期字符串格式化日期
     *
     * @param date String 日期字符串
     * @return String
     */
    public static String timeStampToDate(String date) {
        String str_date = "";
        if (!"".equals(date) && date != null) {
            try {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                str_date = sdf.format(sdf.parse(date));
            } catch (ParseException ex) {
                System.out.println(ex);
            }
        }
        return str_date;
    }
        /**
     * 时间戳转换成年月日，如果需要多种格式，日后改善
     * @param timeStr 时间戳，比如1412848117508
     * @return 
     */
    public static String timeStampToDate(long time) {

            Date date = new Date();
//            System.out.println(new Date(time * 1000).getTime());
//            System.out.println(new Date().getTime());       
            Date today = new Date();
              int year =  Calendar.getInstance().get(Calendar.YEAR);
            if (date.getYear() == today.getYear() && date.getMonth() == today.getMonth() && date.getDate() == today.getDate()) {
                StringBuilder sb=new StringBuilder();
                sb.append(year).append("年").append(today.getMonth()+1).append("月").append(date.getHours() < 10 ? "0" : "").append(date.getDate()).append( "日").append(date.getHours()).append( ":").append(date.getMinutes() < 10 ? "0" : "").append(date.getMinutes());
                return sb.toString();
            } else {
             
                  StringBuilder sb=new StringBuilder();
                sb.append(year).append("年").append(date.getMonth() < 9 ? "0" : "").append(date.getMonth() + 1).append("月").append(date.getDate() < 10 ? "0" : "").append(date.getDate()).append( "日");
                return sb.toString();
            }
    }
    
    /**
     * 获取当前年份
     * @return 
     */
     public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

     /**
      * 获取当前月份
      * @return 
      */
    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }
//
//    public static void main(String[] args) {
//        System.out.println(DateUtils.getCurrentMonth());
//    }
    

}
