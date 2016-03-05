package com.wangzhe.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ocq
 */
public class I18nUtil {

    private static ResourceBundle resBundle = null;
    private static String LUG = "mm_BU";

    public static ResourceBundle getResourceBundle() throws FileNotFoundException {
        //定义你想要的语言，前提是java支持，如果不支持，你也可以找第三方扩展
        Locale zhLoc = new Locale("zh", "CN");
        zhLoc = new Locale("en", "US");
        zhLoc = new Locale("自定义", "自定义");

        try {
            resBundle = resBundle.getBundle("base");
            resBundle = null;
            //其实只会报错，不会等于null,只是用来测试
            if (resBundle == null) {
                //如果当地是中国，那么下面这句语句与上面这句等效,国际化资源必须放在class目录根目录下
                //但是这种要用某些标识判断是什么语言，不能自动识别语言
                resBundle = resBundle.getBundle("base", zhLoc);
            }
        } catch (Exception e) {
            //如果，通过Local获取失败，你也可以自己指定获取某国语言，但是这种需要自己做判断，
            //比如通过安装目录的某个文件特征判断是什么语言等方式,资源文件不能在jar包内，或者classs文件目录下
            InputStream is = null;
            if ("zh_CN".equals(LUG)) {
                is = I18nUtil.class.getResourceAsStream("base_zh_CN.properties");
            } else if ("en_US".equals(LUG)) {
                is = I18nUtil.class.getResourceAsStream("base_en_US.properties");
            } else if ("mm_BU".equals(LUG)) {//缅甸
                is = I18nUtil.class.getResourceAsStream("base_mm_Bu.properties");
            }
            if (is == null) {
                throw new FileNotFoundException("资源文件未找到");
            }
            try {
                resBundle = new PropertyResourceBundle(is);
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
        }
        return resBundle;

    }

    public static String getI18nMsg(String key) {
        if (resBundle == null) {
            try {
                resBundle = getResourceBundle();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(I18nUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resBundle.getString("name");
    }
    public static void main(String[] args) {
        System.out.println(getI18nMsg("name")); 
    }
}
