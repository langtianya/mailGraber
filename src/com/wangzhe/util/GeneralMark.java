/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import com.wangzhe.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 通配符的支持(通配符多选一，用“,”隔开) 
 * /# 数字 
 * /$ 小写字母
 * /* 任意数组或字母
 * /@ 大写字母 
 * /@author ocq
 */
public class GeneralMark {

//    public static void main(String[] args) {
//        System.out.println(randomString("tom$$$$$"));
//    }

    /**
     * 支持$和#通配符,多个通配符用英文逗号隔开，随机其一
     * @param general 描述
     * @return  描述
     */
    public static String randomString(String general) {
        if(StringUtils.isAllEmpty(general)){
            return null;
        }
        String val = "";
        Random random = new Random();

        String[] strArr = general.split(",");//获得分割后的通配符数组
        //System.out.println("" + strArr[strArr.length - 1]);
        List<String> generalList = new ArrayList();
        for (int i = 0; i < strArr.length; i++) {
            //System.out.println("转之前通配符" + (i + 1) + ":" + strArr[i]);
            String str = strArr[i];
            char[] strCharArr = str.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (int j = 0;j<strCharArr.length;j++) {
                if (strCharArr[j] == "$".charAt(0)) {
                    strCharArr[j] = getRandomLowerChar();//找到每个$，随机转为小写字母
                }
                if (strCharArr[j] == "#".charAt(0)) {
                    strCharArr[j] = getRandomNumChar();//找到每个#，随机转为数字
                }
                sb.append(strCharArr[j]);
            }
            str = sb.toString();
            generalList.add(str);
            //System.out.println("转之后通配符" + (i + 1) + ":" + str);
        }
        int randomNum = random.nextInt(strArr.length);
        val = generalList.get(randomNum);
        
        return val;
    }

    /**
     * 产生一个随机的小写字符
     *
     * @return 描述
     */
    public static char getRandomLowerChar() {
        String base = "abcdefghijklmnopqrstuvwxyz";

        Random random = new Random();
        int number = random.nextInt(base.length());
        return base.charAt(number);
    }

    /**
     * 产生一个随机的0-9之间的数字
     *
     * @return 描述
     */
    public static char getRandomNumChar() {
        String base = "0123456789";

        Random random = new Random();
        int number = random.nextInt(base.length());
        return base.charAt(number);
    }
}
