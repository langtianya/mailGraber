package com.wangzhe.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author ocq
 */
public class StringUtils {

    private static final String EN_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 把空格回车什么的清除掉
     *
     * @param src
     * @return
     */
    public static String replaceSpace(String src) {
        if (src == null) {
            return src;
        }
        src = HtmlUtils.unescapeHtml(src);
        src = src.replace("\t", "");
        src = src.replace("\r", "");
        src = src.replace("\n", "");
        src = src.replaceAll("\\s", "");
        src = src.trim();
        return src;
    }

    /**
     * 随机字符串
     *
     * @param len 指定长度
     * @return
     */
    public static String getRandomEnAndNumString(int len) {
        return RandomStringUtils.random(len, EN_LETTERS.concat(NumberUtils.ONE_TO_NINE));
    }

    public static String getRandomEnString(int len) {
        return RandomStringUtils.random(len, EN_LETTERS);
    }

    public static String getRandomEnString(int min, int max) {
        return RandomStringUtils.random(NumberUtils.getRandomNum(min, max), EN_LETTERS);
    }

    /**
     * 随机用户名，用户名必须以字母开头
     *
     * @param len
     * @return
     */
    public static String getRandomUsername(int len) {
        return RandomStringUtils.random(len, EN_LETTERS);
    }

    public static String getRandomUsername() {
        return getRandomPinyin().concat(RandomStringUtils.random(6, EN_LETTERS));
    }

    public static String getRandomPassword() {
        StringBuilder sb = new StringBuilder(10);
        sb.append(StringUtils.getRandomEnAndNumString(4).toLowerCase()).append(NumberUtils.getRandomNum(1)).append(StringUtils.getRandomEnAndNumString(5).toUpperCase());
        return sb.toString();
    }

    /**
     * 随机拼音字母
     *
     * @return
     */
    public static String getRandomPinyin() {
        String[] sm = {"B", "P", "M", "F", "D", "T", "N", "L", "G", "K", "H", "J", "Q", "X", "ZH", "CH", "SH", "R", "Z", "C", "S", "Y", "W"};
        String[] ym = {"ai", "ei", "ui", "ao", "ou", "iu", "ie", "er", "an", "en", "in", "un", "ang", "eng", "ing", "ong"};
        return sm[NumberUtils.getRandomNumByMax(sm.length)].concat(ym[NumberUtils.getRandomNumByMax(ym.length)]);
    }

    /**
     * 获得随机简体中文
     *
     * @param len
     * @return
     */
    public static String getRandomJianHan(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBk"); //转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    /**
     * 其中之一个字符串为空或者没值
     */
    public static boolean isOneEmpty(String... str) {
//        for (String s : str) {
//            if (s == null || s.length() == 0) {
//                return true;
//            }
//        }
//        return false;
        return !allNotEmpty(str);
    }

    /**
     * 所有字符串为空或者没值
     */
    public static boolean isAllEmpty(String... str) {
//        for (String s : str) {
//          if (s != null&& s.length()> 0) {
//                return false;
//            }
//        }
//        return true;
        return !oneNotEmpty(str);
    }

    /**
     * 其中之一不为空并且有值
     */
    public static boolean oneNotEmpty(String... str) {
        for (String s : str) {
            if (s != null && s.length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 所有不为空并且有值
     */
    public static boolean allNotEmpty(String... str) {
        for (String s : str) {
            if (s == null || s.length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 所有不为空并且有值
     */
    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 所有不为空并且有值
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean haveZh(String desString) {
        return RegexUtil.ifMatcher("[\u4e00-\u9fa5]", desString);
    }

    /**
     * public static boolean isContainsOne(String desString, String... subStr) {
     * 如果desString包含subStr数组中其中一个元素，则返回true
     *
     * @param desString
     * @param subStr
     * @return
     */
    public static boolean isContainsOne(String desString, String... subStr) {
        if (StringUtils.isEmpty(desString)) {
            return false;
        }
        for (String s : subStr) {
            if (StringUtils.notEmpty(s) && desString.toLowerCase().indexOf(s.toLowerCase()) > -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean startWithOne(String desString, String... subStr) {
        if (StringUtils.isEmpty(desString)) {
            return false;
        }
        for (String s : subStr) {
            if (StringUtils.notEmpty(s) && desString.toLowerCase().startsWith(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 一个都不能包含
     *
     * @param desString
     * @param subStr
     * @return
     */
    public static boolean notContainsOne(String desString, String... subStr) {
        return !isContainsOne(desString, subStr);
    }

    /**
     * 如果desString包含subStr数组中所有元素，则返回true
     *
     * @param desString
     * @param des
     * @return
     */
    public static boolean isContainsAll(String desString, String... des) {
        for (String s : des) {
            if (!StringUtils.isOneEmpty(s) && !StringUtils.isOneEmpty(desString) && desString.indexOf(s) < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果desString包含subStr数组中其中一个元素，则返回true
     *
     * @param desString
     * @param subStr
     * @return
     */
    public static boolean equalsOne(String desString, String... subStr) {
        if (StringUtils.isOneEmpty(desString)) {
            return false;
        }
        for (String s : subStr) {
            if (!StringUtils.isOneEmpty(s) && desString.toLowerCase().equals(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String removeLastChar(String str, String char_) {
        if (str.endsWith(char_)) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 简体转繁体
     *
     * @param simpStr 简体字符串
     * @return 繁体字符串
     */
    public static String gbkToBig5(String simpStr) {
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
        String traditionalStr = converter.convert(simpStr);
        return traditionalStr;
    }

    /**
     * 繁体转简体
     *
     * @param tradStr 繁体字符串
     * @return 简体字符串
     */
    public static String big5ToGbk(String tradStr) {
        ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
        String simplifiedStr = converter.convert(tradStr);
        return simplifiedStr;
    }

//    public static void main(String[] args) {
//        //System.out.println("'简单'的繁体是：" + SimToTra("简单"));
//        //System.out.println("'簡單'的简体是：" + TraToSim("簡單"));
//        // System.out.println(StringUtils.removeLastChar("http://www.baidu.com", "/"));
//    }
    /**
     * 反编码char16，即把类似&#x9891;&#x7e41;&#x5237;&#x65b0;&#x9650;&#x5236;的反编码成正常的文字
     * 「&#x」开头的后接十六进制数字。
     *
     * @param str
     * @return
     */
    public static String char16Decode(String str) {
        if (str == null || str.length() < 0) {
            return str;
        }

        String result = str;
        Matcher matcher = Pattern.compile("&#x([0-9a-fA-F]{2,5});", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(str);
        while (matcher.find()) {
            result = result.replace(matcher.group(0), (char) Integer.parseInt(matcher.group(1), 16) + "");
        }
        return result;
    }

    /**
     * &#\\d+;){2,} 「&#」开头的后接十进制数字
     *
     * @param str
     * @return
     */
    public static String char10Decode(String str) {
        if (str == null || str.length() < 0) {
            return str;
        }
        return StringEscapeUtils.unescapeHtml(str);
    }

    /**
     * 把utf16解码成正常字符
     *
     * @param utfString
     * @return
     */
    public static String utf16Decode(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }

        return sb.toString();
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0;) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    //版本2
//    public static boolean isNumeric(String str) {
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(str);
//        return isNum.matches();
//    }
//    public static void main(String[] args) {
//        System.out.println(StringUtils.convert("&#23558;&#19979;&#26041;&#39564;&#35777;&#30721;&#22797;&#21046;&#21040;&#19978;&#26041;&#30340;&#26694;&#20869;"));
//    }

    public static String getUrlId(String url) {
        String id = RegexUtil.getFirstString("&id=(\\d+)", url);
        if (id == null || id.length() == 0) {
            id = RegexUtil.getFirstString("tid=(\\d+)", url);
        }
        if (id == null || id.length() == 0) {
            id = RegexUtil.getFirstString("fid=(\\d+)", url);
        }
        if (id == null || id.length() == 0) {
            id = RegexUtil.getFirstString("mid=(\\d+)", url);
        }
        if (id == null || id.length() == 0) {
            id = RegexUtil.getFirstString("id=(\\d+)", url);
        }
        if (id == null || id.length() == 0) {
            id = RegexUtil.getFirstString("=(\\d+)", url);
        }
        //"replyto=(\\d+)"
//         "articleid=(\\d+)"
        return id;
    }
}
