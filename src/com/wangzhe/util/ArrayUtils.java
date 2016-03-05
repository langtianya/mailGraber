/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;

/**
 *
 * @author ocq
 */
public class ArrayUtils {

    public static String[] listToStrArray(List<?> list) {
        Object[] objsObject = list.toArray();
        String[] strArr = new String[objsObject.length];
        for (int i = 0; i < objsObject.length; i++) {
            strArr[i] = (String) objsObject[i];

        }
        return strArr;

    }

    public static Header[] formMapVaules(Map<String, Header> map) {
        Object[] objsObject = map.values().toArray();
        Header[] strArr = new Header[objsObject.length];
        for (int i = 0; i < objsObject.length; i++) {
            strArr[i] = (Header) objsObject[i];

        }
        return strArr;
    }

    public static long[] listToLongArray(List list) {
        Object[] objsObject = list.toArray();
        long[] strArr = new long[objsObject.length];
        for (int i = 0; i < objsObject.length; i++) {
            strArr[i] = (Long) objsObject[i];

        }
        return strArr;

    }

    public static boolean isEmpty(Object[] array) {
        return null == array || array.length == 0;
    }

    public static boolean notEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean isEmpty(Object[][] array) {
        return null == array || array.length == 0;
    }

    public static boolean notEmpty(Object[][] array) {
        return !isEmpty(array);
    }

    public static Object getRamdomElement(Object[] array) {
        if (isEmpty(array)) {
            return null;
        }
        return array[NumberUtils.getRandomNumByMax(array.length)];
    }

    
    public static String getRamdomElement(String[] array) {
        if (isEmpty(array)) {
            return null;
        }
        return array[NumberUtils.getRandomNumByMax(array.length)];
    }

    public static <T> Set<T> asSet(T... element) {
        HashSet<T> elements = new HashSet<T>(element.length);
        Collections.addAll(elements, element);
        return elements;
    }

    //    public static boolean isEmpty(String[] array1) {
//        return array1 == null || array1.length < 1;
//    }
//    //二维数组
//
//    public static boolean isEmpty(String[][] array2) {
//        return array2 == null || array2.length < 1;
//    }
//    //一维数组
//
//    public static boolean isEmpty(Object[] objArr) {
//        return objArr == null || objArr.length < 1;
//    }
//    //二维数组
//
//    public static boolean isEmpty(Object[][] objArr) {
//        return objArr == null || objArr.length < 1;
//    }
}
