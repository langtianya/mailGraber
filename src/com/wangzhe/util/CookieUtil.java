/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import java.net.HttpCookie;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.ParseException;
import org.apache.http.cookie.Cookie;

/**
 *
 * @author ocq
 */
public class CookieUtil {

    public static String toString(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : map.keySet()) {
            sb.append(s).append("=").append(map.get(s)).append(";");
        }
        return sb.toString();
    }

    public static String toString(List<Cookie> cookies) {
        if (cookies == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookies) {
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        Map<String, String> map = null;
//
//        for (String s : map.keySet()) {
//            System.out.println(s);
//        }
////        StringBuilder sb = new StringBuilder();
////        System.out.println(sb.toString());
//
//    }
    public static Map<String, String> strCookiesToMap(String strCookies) {
        if (StringUtils.isOneEmpty(strCookies)) {
            return null;
        }
        return RegexUtil.getMap("([^=]*?)=([^;]*);", strCookies);
    }

    static Pattern httponlyRegex1 = Pattern.compile("httponly.*?;", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    static Pattern httponlyRegex2 = Pattern.compile(" HttpOnly", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    static Pattern getCookeisRegex1 = Pattern.compile(";[^=]*?;", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    static Pattern getCookeisRegex2 = Pattern.compile(";[^=]*?$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    /**
     *
     * @param cookie
     * @param cookies
     * @throws java.text.ParseException
     */
    public static void getCookiesFromConnent(URLConnection connent, Map<String, String> cookies) {

        Map map = connent.getHeaderFields();
        Collection cookiesCollect = (Collection) (map.get("Set-Cookie"));
        if (cookiesCollect != null && cookiesCollect.size() > 0) {
            String[] cookes = new String[cookiesCollect.size()];
            cookiesCollect.toArray(cookes);
            for (String cookie : cookes) {
                if (!cookie.startsWith("LSID=EXPIRED")) {
                    try {
                        if (cookie.startsWith("=")) {
                            return;
                        }

                        Matcher m = httponlyRegex1.matcher(cookie);
                        if (m.find()) {
                            cookie = cookie.replace(m.group(), "");
                        }

                        m = httponlyRegex2.matcher(cookie);
                        if (m.find()) {
                            cookie = cookie.replace(m.group(), "");
                        }

                        while (true) {
                            m = getCookeisRegex1.matcher(cookie);
                            if (m.find()) {
                                cookie = cookie.replace(m.group(), ";");
                                continue;
                            }
                            break;
                        }

                        m = getCookeisRegex2.matcher(cookie);
                        if (m.find()) {
                            String g = m.group();
                            if (g.length() > 1) {
                                cookie = cookie.replace(g, ";");
                            }
                        }

                        List tmpList = null;
                        try {
                            if (cookie==null||cookie.length()==0) {
                                continue;
                            }
                            try {
                                 tmpList = HttpCookie.parse(cookie);
                            } catch (Exception e) {
                                 tmpList = HttpCookie.parse(cookie);
                            }
                           
                        } catch (Exception ex) {
                            System.err.println("cookie=" + cookie);
                            ex.printStackTrace();
                            return;
                        }
                        int size = tmpList.size();
                        for (int j = 0; j < size; j++) {
                            HttpCookie hc = (HttpCookie) tmpList.get(j);

                            String expires = RegexUtil.getFirstString("expires=(.*?GMT)", cookie, 1);
                            if (expires != null) {
                                hc.setMaxAge(getCookieExpire(expires));
                            } else {
                                hc.setMaxAge(3600);
                            }

                            if (hc.hasExpired()) {
                                //System.out.println("过期了的："+cookie);
                                return;
                            }

                            if (hc.getValue().length() < 1) {
                                return;
                            }

                            cookies.put(hc.getName(), hc.getValue());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    final static String NETSCAPE_COOKIE_DATE_FORMAT1 = "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'";
    final static String NETSCAPE_COOKIE_DATE_FORMAT2 = "EEE',' dd MMM yyyy HH:mm:ss 'GMT'";
    final static String NETSCAPE_COOKIE_DATE_FORMAT3 = "EEE','dd-MMM-yyyy HH:mm:ss 'GMT'";
    final static String NETSCAPE_COOKIE_DATE_FORMAT4 = "EEE',' dd-EEE-yyyy HH:mm:ss 'GMT'";
    final static String NETSCAPE_COOKIE_DATE_FORMAT5 = "EEE dd-MMM-yyyy HH:mm:ss 'GMT'";
    final static Locale locale = new Locale("en", "US");
    final static SimpleDateFormat cookieDateFormat1 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT1, locale);
    final static SimpleDateFormat cookieDateFormat2 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT2, locale);
    final static SimpleDateFormat cookieDateFormat3 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT3, locale);
    final static SimpleDateFormat cookieDateFormat4 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT4, locale);
    final static SimpleDateFormat cookieDateFormat5 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT5, locale);

    static {
        cookieDateFormat1.setTimeZone(TimeZone.getTimeZone("GMT"));
        cookieDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
        cookieDateFormat3.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    /**
     * 获取cookies的过期日期
     *
     * @param dateString String
     * @return long
     */
    public static long getCookieExpire(String dateString) throws java.text.ParseException {
        dateString = dateString.replaceAll("(\\d{2})-(\\w{3})-(\\d{2}) ", "$1-$2-20$3 ");
        Date date = null;
        try {
            date = cookieDateFormat1.parse(dateString);
            return (date.getTime() - System.currentTimeMillis()) / 1000;
        } catch (Exception e) {
            try {
                date = cookieDateFormat2.parse(dateString);
                return (date.getTime() - System.currentTimeMillis()) / 1000;
            } catch (Exception e2) {
                try {
                    date = cookieDateFormat3.parse(dateString);
                    return (date.getTime() - System.currentTimeMillis()) / 1000;
                } catch (Exception e3) {

                    try {
                        date = cookieDateFormat4.parse(dateString);
                        return (date.getTime() - System.currentTimeMillis()) / 1000;
                    } catch (ParseException ex) {
                        try {
                            date = cookieDateFormat5.parse(dateString);
                            return (date.getTime() - System.currentTimeMillis()) / 1000;
                        } catch (ParseException ex1) {
                            ex1.printStackTrace();
                        }
                    }
                    return 0;
                }
            }
        }
    }

}
