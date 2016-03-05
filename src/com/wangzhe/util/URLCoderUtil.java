/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class URLCoderUtil {

    private static final Logger log = Logger.getLogger(HttpUtil.class.getName());

    /**
     * URL编码
     *
     * @param str 描述
     * @param encode 描述
     * @return 描述
     */
    public static String encode(String str, String encode) {
        try {
            return URLEncoder.encode(str, encode);
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }
        return null;
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }
        return null;
    }

    /**
     * URL解码
     *
     * @param str 描述
     * @param encode 描述
     * @return 描述
     */
    public static String decode(String str, String encode) {
        try {
            return URLDecoder.decode(str, encode);
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        }
        return null;
    }
}
