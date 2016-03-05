/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class Kit {

    static Random random = new Random();

    public static String getRandomStr(int length, int type) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (type > 0) {
            base += "!@$#%^&*()_-':;.,<>/?\\|[]{}";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomStr(int length) {
        return getRandomStr(length, 0);
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz";   //生成字符串从此序列中取
        Random random = new Random();
        String n = "0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomUpperStr(int length) {

        StringBuffer buffer = new StringBuffer();
        int index = 0;
        for (int i = 0; i < length; i++) {
            // char c = 'a';
            int random = (int) (Math.random() * 1000);
            if (i >= 3) {
                index = random % 3;
            } else {
                index = i;
            }

            switch (index) {
                case 0:
                    buffer.append((char) (97 + random % 26));
                    break;
                case 1:
                    buffer.append((char) (65 + random % 26));
                    break;
                case 2:
                    buffer.append((char) (48 + random % 10));
                    break;
            }

        }
        return buffer.toString();
    }

    public static String getMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    public static String match(String str, String pat, int p) {
        if (str == null) {
            return null;
        }

        Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(p);
        }

        return null;
    }

    public static String[] match(String str, String pat) {
        if (str == null) {
            return null;
        }

        Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {

            String[] ret = new String[matcher.groupCount()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = matcher.group(i + 1);
            }

            return ret;
        }

        return null;
    }
}
