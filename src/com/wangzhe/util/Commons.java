package com.wangzhe.util;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import java.util.function.Consumer;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author ocq
 */
public class Commons {

    private static Logger log = Logger.getLogger(Commons.class.getName());
    private static Random random = new Random();

    public static String getRandomIdCard() {
        return IdCardGenerator.generate();
    }

    public static String randomString(int length) {
        return randomString(length, true);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
        }
    }

    public static void sleepSecond(long second) {
        sleep(second * 1000);
    }

    public static String randomString(int length, boolean isFirstChar) {
        String val = "";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = "";
            if (isFirstChar) {
                if (i == 0) {
                    charOrNum = "char";
                } else {
                    charOrNum = (random.nextInt(2) % 2 == 0 || i == 0) ? "char" : "num"; // 输出字母还是数字
                }
            } else {
                charOrNum = (random.nextInt(2) % 2 == 0 || i == 0) ? "char" : "num"; // 输出字母还是数字
            }

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串     
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字     
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }

    public static String getRandomStr(int length) {
        return getRandomStr(length, 0);
    }

    /**
     * 产生一个随机的字符串
     *
     * @param length 描述
     * @param type 描述
     * @return 描述
     */
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

    public static String getStartChar(int len) {
        if (len < 0 || len - 1 < 0) {
            return null;
        }

        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int number = random.nextInt(base.length());
        return base.charAt(number) + getRandomStr(len - 1);

    }

    public static boolean isNaN(String str) {

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDouble(String str) {
        for (char c : str.toCharArray()) {
            if (c == '.') {
                continue;
            }
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (!list.isEmpty()) {
            list.stream().forEach(new Consumer<String>() {

                @Override
                public void accept(String str) {
                    sb.append(str);
                }
            });
        }
        return sb.toString();
    }

    /**
     * 获取随机省份
     *
     * @return getRandomCity()
     */
    public static String getRandomProvince() {
        return provinces[NumberUtils.getRandomNumByMax(provinces.length)][1];
    }
    public static String cityByIp;

    /**
     * 根据本地ip获取本地城市，格式为：xx省xx市 暂时请求网络去获取，后面考虑自己写代码实现，提高效率
     *
     * @return
     */
    public static String getCityByIp() {
        try {
            if (StringUtils.notEmpty(cityByIp)) {
                return cityByIp;
            }
            cityByIp = RegexUtil.getFirstString("来自：([^\\s]*)", HttpUtil.doGet("http://www.ip138.com/ips1388.asp"));

            if (StringUtils.notEmpty(cityByIp)) {
                return cityByIp;
            }
        } catch (UnknownHostException ex) {
            log.error(ex);
        } catch (Exception ex) {
            log.error(ex);
        }

        return "广东深圳市";
    }
    private static String[] signature = new String[]{
        "只要相信就有可能",
        "青春有得有失",
        "退一步并不象征认输",
        "命脉乐观面对人生",
        "生命比盖房更需要蓝图",};

    public static String getRandomSignature() {
        return signature[NumberUtils.getRandomNumByMax(signature.length)];
    }

    private static String[] regReason = new String[]{
        "喜欢本站提供内容，想在此多多交流",
        "兴趣所致重在分享",
        "交流经验，获取所需",
        "喜欢本站 分享生活，交流生活",
        "被本站内容所吸引，希望能获取更多信息，希望交流沟通",};

    public static String getRandomRegReason() {
        return regReason[NumberUtils.getRandomNumByMax(regReason.length)];
    }

    private static String[] selfIntroduction = new String[]{
        "热爱生活，热爱网络，遵纪守法，热爱祖国",
        "我能够生活团结 互助 友爱 充满热情，让自己更有生命力",
        "希望有兴在日后与各位成为同仁共同努力 共同进步",
        "平身何惧鬼神怒，不遭天妒是庸才！",
        "一直在努力 从未放弃过",};

    public static String getRandomSelfIntroduction() {
        return selfIntroduction[NumberUtils.getRandomNumByMax(selfIntroduction.length)];
    }

    private static String[][] provinces = new String[][]{
        {"4", "成都"},
        {"7", "杭州"},
        {"8", "北京"},
        {"9", "上海"},
        {"10", "重庆"},
        {"39", "天津"},
        {"51", "江苏"},
        {"50", "浙江"},
        {"201", "安徽"},
        {"339", "江西"},
        {"55", "福建"},
        {"52", "广东"},
        {"57", "广西"},
        {"213", "海南"},
        {"48", "湖北"},
        {"116", "湖南"},
        {"153", "山东"},
        {"198", "山西"},
        {"195", "河北"},
        {"200", "河南"},
        {"47", "四川"},
        {"214", "贵州"},
        {"215", "云南"},
        {"216", "西藏"},
        {"149", "辽宁"},
        {"211", "吉林"},
        {"226", "吉林"},
        {"212", "黑龙江"},
        {"150", "陕西"},
        {"192", "甘肃"},
        {"219", "新疆"},
        {"217", "青海"},
        {"218", "宁夏"},
        {"210", "内蒙古"},
        {"151", "港澳台"},
        {"391", "海外"}
    };

    /**
     * unicode 转字符串
     *
     * @param unicode
     * @return
     */
    public static String Unicode2String(String unicode) {
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }
        return string.toString();
    }

    /**
     * 字符串转换unicode
     *
     * @param string
     * @return
     */
    public static String String2Unicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
//            if (c >= 33 && c <= 126) {
//                unicode.append(c);
//            } else {
            unicode.append("\\u").append(Integer.toHexString(c));
//            }
        }
        return unicode.toString();
    }

    public static String getImageMimeType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (fileName.endsWith(".png")) {
            return "image/png";
        }
        if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        if (fileName.endsWith(".tif")) {
            return "image/tiff";
        }
        return "image/jpeg";
    }

    public static String toBase64(String str) {
        String result = "";
        try {
            result = Base64.encodeBase64String(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }
        return result;
    }
     /**
     * 判断指定值是否为空
     * 
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        boolean result = null == obj;
        result = (obj instanceof String) ? ((String) obj).isEmpty() : result;
        result = (obj instanceof Collection) ? ((Collection<?>) obj).isEmpty() : result;
        return result;
    }
     public static int getProgress(String progressFilePath) {
        final String progressStr = FileUtils.readTxtFile(progressFilePath);
        return Commons.isEmpty(progressStr) ? 1 : Integer.valueOf(progressStr.trim());
                 
    }
}
