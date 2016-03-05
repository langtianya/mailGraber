package com.wangzhe.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author ocq
 */
public class NumberUtils {

    public static final String ONE_TO_NINE = "0123456789";
    public static Random random = new Random();

    /**
     * 获取给定长度的随机数字字符串
     *
     * @param len
     * @return
     */
    public static String getRandomNumString(int len) {

        String randomLengthStr = RandomStringUtils.random(len, ONE_TO_NINE);//获得随机长度的String
        return randomLengthStr;
    }

    /**
     * 获取给定长度的随机数字字符串
     *
     * @param len
     * @return
     */
    public static String getMathRadomString() {
        return String.valueOf(random.nextDouble());
    }

    /**
     * 获取给定长度的数字
     *
     * @param len
     * @return
     */
    public static int getRandomNum(int len) {

        return Integer.parseInt(RandomStringUtils.random(len, "123456789"));
    }

    /**
     * 随机月份
     *
     * @param len
     * @return
     */
    public static String getRandomMon() {
        return String.valueOf(getRandomNum(1, 12));
    }

    /**
     * 随机日
     *
     * @return
     */
    public static String getRandomDay() {
        return String.valueOf(getRandomNum(1, 28));
    }

    public static String getRandomYear() {
        return String.valueOf(getRandomNum(1950, 1994));
    }

    /**
     * 随机手机号码
     *
     * @return
     */
    public static String getRandomCellPhone() {
        return String.valueOf(getRandomNum(13, 18)) + String.valueOf(getRandomNum(9));
    }

    /**
     * 随机的qq号码
     *
     * @return
     */
    public static String getRandomQQ() {

        return getRandomNumString(10);
    }

    /**
     * 获取0到maxNum之间的随机一个数字
     *
     * @param maxNum
     * @return
     */
    public static int getRandomNumByMax(int maxNum) {
        if (maxNum == 0) {
            return 0;
        }
        return random.nextInt(maxNum);
    }

    /**
     * 获取minNum和maxNum间的一个随机数
     *
     * @param minNum
     * @param maxNum
     * @return
     */
    public static int getRandomNum(int minNum, int maxNum) {
        if (maxNum <= minNum) {
            return 0;
        }
        return random.nextInt(maxNum - minNum + 1) + minNum;
    }

    public static String getRandomNumString(int minNum, int maxNum) {
        return String.valueOf(getRandomNum(minNum, maxNum));
    }

    public static List<Integer> getRandomNums(int count, int num) {
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                array.add(getRandomNumByMax(num));
            } else {
                while (true) {
                    int rdNum = getRandomNumByMax(num);
                    if (!array.contains(rdNum)) {
                        array.add(rdNum);
                        break;
                    }
                }
            }
        }
        return array;
    }

    public static boolean equalsOne(int src, int... des) {

        for (int i = 0; i < des.length; i++) {
            if (des[i] == src) {
                return true;
            }
        }
        return false;
    }

    public static boolean notEqualsOne(int src, int... des) {
        return !equalsOne(src, des);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
//            System.out.println(getRandomCellPhone());
            System.out.println(getRandomNum(9));
        }
    }
}
