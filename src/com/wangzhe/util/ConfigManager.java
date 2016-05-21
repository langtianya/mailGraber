package com.wangzhe.util;

import com.wangzhe.beans.ProxyBean;
import java.io.File;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

/**
 * 实现对Java配置文件Properties的读取、写入与更新操作
 *
 * @author ocq
 */
public class ConfigManager {

    private static final Logger log = Logger.getLogger(ConfigManager.class.getName());

    /**
     * 采用静态方法
     */
    private static XMLConfiguration config = null;

    static {
        try {
            config = new XMLConfiguration(System.getProperty("user.dir") + "\\config.xml");
            config.setAutoSave(true);//设置配置文件自动保存
            config.setEncoding("utf-8");
        } catch (ConfigurationException ex) {
            log.error(ex);
        }
    }

    public ConfigManager() {
    }

    /**
     * 以文件形式返回setting.properties文件
     *
     * @return
     */
    public static File getPropertiesFile() {
        return config.getFile();
    }

    /**
     * 写入配置内容到配置文件（软件根目录下的setting.properties文件中）
     *
     * @param key
     * @param value
     */
    public static void setProperties(String key, String value) {
        config.setProperty(key, value);
    }

    /**
     * 读取配置文件（默认都是返回String类型）
     *
     * @param key 常量调用：例如：ConfigManager.proxy_type是获得代理类型！
     * @return
     */
    public static String getProperties(String key) {
        String[] values=config.getStringArray(key);
        StringBuilder valueSb=new StringBuilder();
        for (String value : values) {
            valueSb.append(",").append(value);
        }

        return valueSb.toString().substring(1);
    }

    public static String getVersion() {
        String ver = getProperties("app.ver");
        return ver == null ? "1.0.0.0" : ver;
    }

    public static boolean isDevelop() {
        String ver = getProperties("app.develop");
        if (ver == null) {
            return false;
        } else {
            return ver.equals("1");
        }
    }

    public static int emailIndex = 0;

    public static int proxyIndex = 0;

    //public static final String APIPROXY = "";
    public static ProxyBean getApiProxy() {
        if (ContainerUtils.notEmpty(Constants.ApiProxyList)) {
            if (proxyIndex == Constants.ApiProxyList.size()) {
                proxyIndex = 0;
            }
            ProxyBean proxyBean = Constants.ApiProxyList.get(proxyIndex++);
            return proxyBean;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static String getGrabUrl() {
        return getProperties("setting_GrabUrl");
    }

    public static void setGrabUrl(String message) {
        setProperties("setting_GrabUrl", message);
    }

    //////////抓取作品信息
    /**
     *
     * @return
     */
    public static String getProductionFlag() {
        return getProperties("setting_productionFlag");
    }

    public static void setProductionFlag(String message) {
        setProperties("setting_productionFlag", message);
    }

    //////////网络拨号
    /**
     *
     * @return
     */
    public static String getNetAccount() {
        return getProperties("setting_NetAccount");
    }

    public static void setNetAccount(String message) {
        setProperties("setting_NetAccount", message);
    }

    /**
     *
     * @return
     */
    public static String getNetPassword() {
        return getProperties("setting_NetPassword");
    }

    public static void setNetPassword(String message) {
        setProperties("setting_NetPassword", message);
    }
    /**
     *
     * @return
     */
    public static String getReDialTimes() {
        return getProperties("setting_reDialTimes");
    }
    public static void setReDialTimes(String message) {
        setProperties("setting_reDialTimes", message);
    }
    /**
     *
     * @return
     */
    public static String getDisnetTime() {
        return getProperties("setting_disnetTime");
    }

    public static void setDisnetTime(String message) {
        setProperties("setting_disnetTime", message);
    }
}
