/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

/**
 *
 * @author ocq
 */
public class UrlUtils {

    public static boolean startWithHttp(String src) {
        if (StringUtils.isOneEmpty(src)) {
            return false;
        }
        return src.startsWith("http");
    }

    /**
     * 获取网址的域名，此方法获取的域名有可能有二级目录，而且大多以http、https开头
     */
    public static String getSiteDomain(String srcUrl) {
        if (srcUrl == null) {
            return null;
        }
        if (srcUrl.endsWith("/blog/") || srcUrl.endsWith("/wiki/") || srcUrl.endsWith("/forums/") || srcUrl.endsWith("/bbs/") || srcUrl.endsWith("/forum/")) {
            return srcUrl;
        }
        int idx = srcUrl.indexOf("//");
        if (idx > 0) {
            int idx2 = srcUrl.indexOf("/", idx + 2);
            if (idx2 > 0) {
                srcUrl = srcUrl.substring(0, idx2 + 1);
            }
        }
        if (srcUrl.endsWith("?") || srcUrl.endsWith(".") || srcUrl.endsWith("&") || srcUrl.endsWith("-")) {
            srcUrl = srcUrl.substring(0, srcUrl.length() - 1);
        }

        return srcUrl;
    }
    /**
     * 获取网址的根域名 此方法获取的域名不会有二级目录  不会有http、https开头的网址返回
     */
    public static String getSiteRootDomain(String srcUrl) {
        if (srcUrl == null) {
            return null;
        }
      
        int idx = srcUrl.indexOf("//");
        if (idx > 0) {
            int idx2 = srcUrl.indexOf("/", idx + 2);
            if (idx2 > 0) {
                srcUrl = srcUrl.substring(idx + 2, idx2 + 1);
            }
        }
        return srcUrl;
    }
    /**
     * 拼接两个url,比如http://blog.csdn.net/和reg.php
     */
    public static String connectUrl(String hostUrl, String sonUrl) {
        if (hostUrl == null) {
            return sonUrl;
        }
        if (sonUrl == null) {
            return hostUrl;
        }
        hostUrl=hostUrl.trim();
         sonUrl=sonUrl.trim();
         
        if (sonUrl.indexOf("&amp;") > -1) {
            sonUrl = HtmlUtils.replaceTagAmp(sonUrl);
        }
        
        int i=sonUrl.indexOf(" ");
        if (i > -1) {
            String temp=sonUrl.substring(i);

            temp = URLCoderUtil.encode(temp, "utf-8");
            sonUrl = sonUrl.substring(0, i);
            sonUrl += temp;
        }
        if (RegexUtil.ifMatcher("[\\u4e00-\\u9fa5]", sonUrl)) {
            String temp=RegexUtil.getFirstString("([\\u4e00-\\u9fa5])", sonUrl);
            String temp2 = URLCoderUtil.encode(temp, "utf-8");
            sonUrl=sonUrl.replace(temp, temp2);
        }
        
        if (sonUrl.startsWith("http")) {
             if(sonUrl.startsWith("http%")||sonUrl.startsWith("https%")){
            sonUrl=URLCoderUtil.decode(sonUrl, "utf-8");
        }
            return sonUrl;
        }
        if (sonUrl.startsWith("./")) {
            sonUrl = sonUrl.substring(2);
        }else
        if (sonUrl.startsWith("../")) {
            sonUrl = sonUrl.substring(3);
        }
        if (!hostUrl.endsWith("/") && ((sonUrl.startsWith("/") || sonUrl.startsWith("?")))) {
            return hostUrl.concat(sonUrl) ;
        }
        if (hostUrl.endsWith("/") && ((sonUrl.startsWith("/") || sonUrl.startsWith("?")))) {
            return hostUrl.substring(0,hostUrl.lastIndexOf("/")).concat(sonUrl);
        }
        if (hostUrl.endsWith("/") && !sonUrl.startsWith("/")) {
            return hostUrl.concat(sonUrl) ;
        }
       if (hostUrl.endsWith("=") && !sonUrl.startsWith("/")) {
            return hostUrl.concat(sonUrl);
        }
        if (!hostUrl.endsWith("/") && !sonUrl.startsWith("/")) {
            return hostUrl.concat("/").concat(sonUrl);
        }
      
        return hostUrl;
    }
//    public static void main(String[] args) {
//       String aString= UrlUtils.getSiteRootDomain("http://i.cnblogs.com/EditPosts.aspx?postid=2838344");
//        System.out.println(aString);
//    }

    public static String getRootDomain(String domainName) {
        if ((domainName != null) && (domainName.indexOf("http") > -1 || domainName.indexOf("www") > -1)) {
            domainName = domainName.replace("http://", "");
            domainName = domainName.replace("https://", "");
            domainName = domainName.replace("www.", "");
        }
        return domainName;
    }
}
