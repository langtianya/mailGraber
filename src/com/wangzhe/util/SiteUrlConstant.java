package com.wangzhe.util;

import com.wangzhe.service.impl.ZgbfwCom;
import com.wangzhe.service.impl.FubuEmailCatch;
import com.wangzhe.service.impl.LedWangImpl;
import com.wangzhe.service.impl.QiyeguCom;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ocq
 */
public class SiteUrlConstant {

    public final static Map<String, String> SITES = new HashMap<String, String>();

    static {
        SITES.put("http://www.cnledw.com/", LedWangImpl.class.getSimpleName());
        SITES.put("http://www.fubu.com/", FubuEmailCatch.class.getSimpleName());
        SITES.put("http://www.qiyegu.com/", QiyeguCom.class.getSimpleName());
        SITES.put("http://www.zgbfw.com/", ZgbfwCom.class.getSimpleName());
    }

}
