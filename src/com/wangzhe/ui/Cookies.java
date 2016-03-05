/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.ui;

import com.wangzhe.util.ContainerUtils;
import com.wangzhe.util.CookieUtil;
import com.wangzhe.util.StringUtils;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.http.cookie.Cookie;

/**
 *
 * @author ocq
 */
public class Cookies {

    private Map<String, String> cookiesMap = new HashMap<>();

    public Cookies() {
    }

    public void add(List<Cookie> cookies) {
        if (ContainerUtils.isEmpty(cookies)) {
            return;
        }
        for (Cookie cookie : cookies) {
            cookiesMap.put(cookie.getName(), cookie.getValue());
        }

    }

    public void add(String cookies) {
        if (StringUtils.isAllEmpty(cookies)) {
            return;
        }
        this.cookiesMap = CookieUtil.strCookiesToMap(cookies);

    }

    public void add(String key, String value) {
        cookiesMap.put(key, value);

    }

    public String getValue(String key) {
        return cookiesMap.get(key);

    }

    public boolean containsKey(String key) {
       return cookiesMap!=null&& cookiesMap.containsKey(key);
    }

    public boolean containsValue(String value) {
      return cookiesMap!=null&&cookiesMap.containsValue(value);
    }

    public void clear() {
        cookiesMap.clear();
    }

    @Override
    public String toString() {
        if (ContainerUtils.isEmpty(cookiesMap)) {
            return "";
        }
        return CookieUtil.toString(cookiesMap);
//        StringBuilder sb = new StringBuilder();
//        for (String key : cookiesMap.keySet()) {
//            sb.append(key).append("=").append(cookiesMap.get(key)).append(";");
//        }
////         return sb.toString()==null?"":sb.toString();
//        return sb.toString();
    }

    public Map<String, String> getCookiesMap() {
        return cookiesMap;
    }

    public void setCookiesMap(Map<String, String> cookiesMap) {
        this.cookiesMap = cookiesMap;
    }

    public int size() {
        return this.cookiesMap.size();
    }

}
