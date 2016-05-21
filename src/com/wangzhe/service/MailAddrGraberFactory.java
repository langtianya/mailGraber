package com.wangzhe.service;

import static com.wangzhe.util.SiteUrlConstant.SITES;
import org.apache.log4j.Logger;

/**
 * 反射+工厂模式解决创建子类的创建
 *
 * @author ocq
 */
public class MailAddrGraberFactory {

    protected static Logger log = Logger.getLogger(MailAddrGraberFactory.class.getName());

    public static MailAddrGraber getInstanceByUrl(String siteUrl) {
        MailAddrGraber mdGraber = getInstanceByType(SITES.get(siteUrl));
        mdGraber.setSiteUrl(siteUrl);
        return mdGraber;
    }

    public static MailAddrGraber getInstanceByType(String type) {
        try {
            return (MailAddrGraber) Class.forName("com.wangzhe.service.impl." + type).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            log.error("site not support yet", ex);
        }
        return null;
    }

}
