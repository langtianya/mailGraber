/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import com.wangzhe.util.RegexUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;

/**
 * 规范
 *
 * @author ocq
 */
public class NormUtil {

    /**
     * 是否Url
     *
     * @param url 描述
     * @return 描述
     */
    public static boolean isUrl(String url) {
        if (RegexUtil.ifMatcher("http[s]?://", url)) {
            return true;
        }
        return false;
    }

    /**
     * 处理baseUrl
     *
     * @param url 描述
     * @return 描述
     */
    public static String toBaseUrl(String url) {
        if (RegexUtil.ifMatcher("\\.(?:php|html)$", url)) {//先找是否带.php或者.html
            url = RegexUtil.getString(".*/", url, 0);//有的话取最后一条斜杠之前的
        } else {//没有带.php或者.html话
            if (!RegexUtil.ifMatcher("/$", url)) {//处理url最后加上斜杠
                url += "/";
            }
        }
        return url;
    }

    /**
     * 去掉带.php的url 描述
     *
     * @param url 描述
     * @return 描述
     */
    public static String toRemovePhp(String url) {
        return url.replaceAll("/[^/]*\\.php$", "/");
    }

    public static String getDiscuzMainURL(String url) {
        if (RegexUtil.ifMatcher("\\.php[^/\\.]*/", url)) {
            return url.replaceAll("/[^/]*\\.php[^/\\.]*/.*$", "/");
        }
        if (RegexUtil.ifMatcher("\\.html[^/]*/", url)) {
            return url.replaceAll("/[^/]*\\.html[^/]*/.*$", "/");
        }
//        if (RegexUtil.ifMatcher("\\.shtml/", url)) {
//            return url.replaceAll("/[^/]*\\.shtml/.*$", "/");
//        }
        return url.replaceAll("/[^/]*$", "/");
    }

    public static boolean isDiscuz(String homePage) {
        if (RegexUtil.ifMatcher("Powered by.*?Discuz", homePage)) {
            return true;
        }
        if (RegexUtil.ifMatcher("class\\s*=\\s*\"[^\"]*(pns y|y pns)", homePage)) {
            return true;
        }
        if (RegexUtil.ifMatcher("<meta[^>]*name=\"generator\"[^>]*content=\"[^\"]*Discuz", homePage)) {
            return true;
        }
        if (RegexUtil.ifMatcher("<meta[^>]*name=\"author\"[^>]*content=\"[^\"]*Discuz", homePage)) {
            return true;
        }
        if (RegexUtil.ifMatcher("id=\"qmenu\"[^>]*(onmouseover|onMouseOver)=\"(delayShow|showMenu)", homePage)) {
            return true;
        }
        if (RegexUtil.ifMatcher("<script[^>]*src=\"[^\"]*/common.js\\?", homePage)) {
            return true;
        }
        return false;
    }

    public static List<String> getBBSURL(String baseUrl, String homePage) {
        List<String> list = new ArrayList<>();
        //<a href="/forum/" target="_blank">交流天地</a>
        boolean b = true;
        if (RegexUtil.ifMatcher("href\\s*=\\s*\"(/)?forum(/)?[^\"]*\"", homePage)) {
            list.add(baseUrl.concat("/forum/").concat(RegexUtil.getString("href\\s*=\\s*\"(/)?forum(/)?([^\"]*?)\"", homePage, 3)));
            b = false;
        }
        //<a href="/bbs/" target="_blank">交流天地</a>
        //<area shape="rect" coords="245,14,329,32" href="bbs/">
        if (RegexUtil.ifMatcher("href\\s*=\\s*\"(/)?bbs(/)?[^\"]*\"", homePage)) {
            list.add(baseUrl.concat("/bbs/").concat(RegexUtil.getString("href\\s*=\\s*\"(/)?bbs(/)?([^\"]*?)\"", homePage, 3)));
            b = false;
        }
        //<a href="http://bbs.taisha.org/" target="_blank">
        if (RegexUtil.ifMatcher("href\\s*=\\s*\"http://bbs\\.[^\"]*\"", homePage)) {
            list.add("http://bbs.".concat(RegexUtil.getString("bbs\\.([^/]*?)/", homePage)));
            b = false;
        }

        //<a href="http://forum.taisha.org/" target="_blank">
        if (RegexUtil.ifMatcher("href\\s*=\\s*\"http://forum\\.[^\"]*\"", homePage)) {
            list.add("http://forum.".concat(RegexUtil.getString("forum\\.([^/]*?)/", homePage)));
            b = false;
        }

        //<script language='javascript'>document.location = '../viewthread.php?tid=1&extra=page%3D1'</script>
        String dlocation = RegexUtil.getString("document\\.location\\s*=\\s*[\"']([^']*?)[\"']", homePage);
        if (dlocation != null) {
            list.add(baseUrl.concat("/stats.php"));
            b = false;
        }
        //window.location="http://www.xhclub.net/forum/index.php"
        String wlocation = RegexUtil.getString("window\\.location\\s*=\\s*[\"']([^\"]*)[\"']", homePage);
        if (wlocation != null) {
            if (!RegexUtil.ifMatcher("http(s)?://", wlocation)) {
                list.add(baseUrl.concat(wlocation));
            } else {
                list.add(wlocation);
            }
            b = false;
        }

        if (b) {
            List<String> bbsUrl = RegexUtil.getList("<a[^>]*href=\\\"([^\\\"]*)\\\"[^>]*>[^<]*(?:论坛|社区|BBS|FORUM|論壇|社區)<", homePage);
            if (!bbsUrl.isEmpty()) {
                for (String s : bbsUrl) {
                    list.add(s);
                }
            }
        }

        return list;
    }

    public static List<String> getCustomUrl(String baseUrl) {
        List<String> list = new ArrayList<>();
        if (baseUrl.contains("www")) {
            list.add(baseUrl.replace("www", "bbs"));
            list.add(baseUrl.replace("www", "forum"));
        }
        list.add(baseUrl.concat("/bbs"));
        list.add(baseUrl.concat("/forum"));
        return list;
    }

    public static int getVersion(String homePage) {
        //discuz X以上
        if (RegexUtil.ifMatcher("Discuz! X", homePage)) {
            return 11;
        }
        String str = RegexUtil.getString("(?s)id=\"frt\">(.*?)</div", homePage);
        if (str != null && RegexUtil.ifMatcher("Discuz! X", str.replaceAll("<[^>]*>", ""))) {
            return 11;
        }
        if (RegexUtil.ifMatcher("member\\.php\\?mod=|class\\s*=\\s*\"(pns y|y pns)", homePage)) {
            System.out.println("version 11");
            return 11;
        }
        //discuz 7
        if (RegexUtil.ifMatcher("Discuz! 7", homePage)) {
            return 7;
        }
        str = RegexUtil.getString("(?s)id=\"rightinfo\">(.*?)</div", homePage);
        if (str != null && RegexUtil.ifMatcher("Discuz! 7", str.replaceAll("<[^>]*>", ""))) {
            return 7;
        }
        if (RegexUtil.ifMatcher("div id\\s*=\\s*\"umenu\"", homePage)) {
            System.out.println("version 7");
            return 7;
        }
        //discuz 6
        if (RegexUtil.ifMatcher("Discuz! 6", homePage)) {
            return 6;
        }
        str = RegexUtil.getString("(?s)id=\"copyright\">(.*?)</div", homePage);
        if (str != null && RegexUtil.ifMatcher("Discuz! 6", str.replaceAll("<[^>]*>", ""))) {
            return 6;
        }
        //discuz 5
        if (RegexUtil.ifMatcher("Discuz! 5", homePage)) {
            return 5;
        }
        str = RegexUtil.getString("(?s)Powered by <a[^>]*>(.*?)</td", homePage);
        if (str != null && RegexUtil.ifMatcher("Discuz! 5", str.replaceAll("<[^>]*>", ""))) {
            return 5;
        }
        if (RegexUtil.ifMatcher("class=\"maintable\"", homePage)) {
            System.out.println("version 5");
            return 5;
        }
        if (RegexUtil.ifMatcher("div id\\s*=\\s*\"menu\"", homePage)) {
            System.out.println("version 6");
            return 6;
        }
        return 0;
    }

    public static int getXVersion(int version, String source) {
        if (version == 11) {
            if (!RegexUtil.ifMatcher("name=\"sechash\"", source)) {
                version = 13;//discuz X3.1
            }
        }
        return version;
    }

    public static Map<String, String> getRegister(String homePage) {

        Map<String, String> map = new HashMap<>();

        switch (getVersion(homePage)) {
            case 5:
                //System.out.println("【Discuz版本】：5 以下");
                map.put("version", "5");
                String str5 = RegexUtil.getString("(?=游客|遊客|訪客|访客|Guest).*href=\"([^\"]*?)\"[^>]*>[^<]*(?=注册|注冊|加入论坛|註冊|<[^>]*>注册|Register)", homePage);//href=\"([^\"]*?)\"[^>]*>[^<]*(注册|注冊|加入论坛|註冊)
                if (str5 != null) {
                    map.put("regurl", str5);
                    return map;
                }
                break;
            case 6:
                //System.out.println("【Discuz版本】：6");
                map.put("version", "6");

                homePage = RegexUtil.getString("(?s)<div id=\"menu\">(.*?)</div", homePage);
                String str6 = RegexUtil.getString("href=\"([^\"]*?)\"[^>]*>[^<]*(?=注册|注冊|註冊|加入论坛)", homePage);
                if (str6 != null) {
                    map.put("regurl", str6);
                    return map;
                }
                str6 = RegexUtil.getString("href=\"([^\"]*?)\"[^>]*class\\s*=\\s*\"notabs\"", homePage);
                if (str6 != null) {
                    map.put("regurl", str6);
                    return map;
                }
                break;
            case 7:
                //System.out.println("【Discuz版本】：7");
                map.put("version", "7");
                String str7 = RegexUtil.getString("href=\"([^\"]*?)\"[^>]*class\\s*=\\s*\"noborder\"", homePage);
                if (str7 != null) {
                    map.put("regurl", str7);
                    return map;
                }
                str7 = RegexUtil.getString("href=\"([^\"]*?)\"[^>]*>[^<]*(?=注册|注冊|註冊|加入论坛)", homePage);
                if (str7 != null) {
                    map.put("regurl", str7);
                    return map;
                }
                break;
            case 11:
                //System.out.println("【Discuz版本】：X1 以上");

//            if (RegexUtil.ifMatcher("class=\"xi2\"", homePage)) {
//                System.out.println("【Discuz版本】：X1");
//            }
//
//            if (RegexUtil.ifMatcher("class=\"xi2 xw1\"", homePage)) {
//                System.out.println("【Discuz版本】：X2以上");
//            }
                map.put("version", "11");
                String str = RegexUtil.getString("member\\.php\\?mod=([^\"]*?)\"\\s*class\\s*=\\s*\"[^\"]*xi2\\s*xw1", homePage);
                if (str != null) {
                    map.put("regurl", "member.php?mod=" + str);
                    return map;
                }
                str = RegexUtil.getString("member\\.php\\?mod=([^\"]*?)\"[^>]*class\\s*=\\s*\"\\s*xi2\\s*\"[^>]*>(?!找回密码|登录)", homePage);
                if (str != null) {
                    map.put("regurl", "member.php?mod=" + str);
                    return map;
                }
                str = RegexUtil.getString("member\\.php\\?mod=([^\"]*?)\"[^>]*>[^<]*?(?=注册|注冊|加入|进站|<em>注册|新用户)[^<]*?<", homePage);
                if (str != null) {
                    if (RegexUtil.ifMatcher("'", str)) {
                        str = str.replaceAll("'", "");
                    }
                    map.put("regurl", "member.php?mod=" + str);
                    return map;
                }
                str = RegexUtil.getString("href=\"([^\"]{2,})\"[^>]*>[^<]*(?:注册|注冊|註冊)", homePage);
                if (str != null) {
                    map.put("regurl", str);
                    return map;
                }
                break;
            default:
                String str0 = RegexUtil.getString("href=\"([^\"]{2,})\"[^>]*>[^<]*(?:注册|注冊|註冊)", homePage);
                map.put("version", "7");
                map.put("regurl", str0);
        }
        return map;
    }

    public static Map<String, String> getLogin(String homePage) {
        Map<String, String> map = new HashMap<String, String>();
        switch (getVersion(homePage)) {
            case 11:
                map.put("version", "11");
                map.put("loginURL", "member.php?mod=logging&action=login");
                return map;
            case 7:
                map.put("version", "7");
                map.put("loginURL", "logging.php?action=login");
                return map;
            case 6:
                map.put("version", "6");
                map.put("loginURL", "logging.php?action=login");
                return map;
            case 5:
                map.put("version", "5");
                map.put("loginURL", "logging.php?action=login");
                return map;
        }
        return null;
    }

    public static int checkRegisterPage(String regPage) {

        //是否未定义操作
        if (RegexUtil.ifMatcher("未定义操作", regPage)) {
            return 201;
        }

        //是否QQ登录
        if (RegexUtil.ifMatcher("QQ登录服务协议<|正在前往\\s*QQ\\s*注册页面|注册请使用QQ登录系统注册", regPage)) {
            return 202;
        }

        //是否禁止注册
        if (RegexUtil.ifMatcher("禁止新用户注册|不提供帐号注册|黑客注册|关闭自由注册|暂停新会员注册|人工審核制", regPage)) {//|邀请注册
            return 203;
        }

        //是否有邀请码
        boolean isHaveInviteCode = RegexUtil.ifMatcher("name=\"invitecode\"", regPage);//for\\s*=\\s*\"invitecode|>邀请码
        //boolean isMustInviteCode = RegexUtil.ifMatcher("\\<\\s*span\\s*class\\s*=\\s*\\\"rq\\\"\\>\\s*\\*\\<\\s*/\\s*span\\s*>\\<\\s*label\\s*for\\s*=\\s*\\\"invitecode\\\"\\>邀请码", regPage);
        //if (isHaveInviteCode && isMustInviteCode) {
        if (isHaveInviteCode) {
            return 204;
        }// else if (isHaveInviteCode) {
        //    return 200;
        //setMessage("包含【可不填邀请码】可注册...");
        //} else {
        //return 200;
        //setMessage("无【邀请码】可注册...");
        //}

        //是否验证邮箱
        if (RegexUtil.ifMatcher("注册需要验证邮箱，", regPage)) {
            return 205;
        }

        //是否有短信验证码
        if (RegexUtil.ifMatcher("短信验证码<", regPage)) {
            return 206;
        }

        //是否有安全码
        if (RegexUtil.ifMatcher(">安全码", regPage)) {
            return 207;
        }
        if (RegexUtil.ifMatcher("页面没[^<]*找到<|404 Not Found", regPage)) {
            return 208;
        }
        //System.out.println("regPage" + regPage);
        //是否原生注册页面
        if (!RegexUtil.ifMatcher("type=\"hidden\"\\s*name=\"formhash\"|name=\"formhash\"\\s*type=\"hidden\"", regPage)) {
            return 210;
        }
        if (!RegexUtil.ifMatcher("type=\"hidden\"\\s*name=\"referer\"|name=\"referer\"\\s*type=\"hidden\"", regPage)) {
            return 210;
        }
        return 200;
    }

    public static Map<String, String> getAutoInputData(String source, Map<String, String> data) {

        //hidden表单数据 隐藏表单value值可能为""
        Pattern pattern = Pattern.compile("type=\"hidden\"[^>]*name=\"([^\"]*?)\"");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            if (data.get(matcher.group(1)) == null) {
                String isHaveValue = RegexUtil.getString("name=\"" + matcher.group(1) + "\"[^>]*value=\"([^\"]*?)\"", source);
                if (isHaveValue == null) {
                    isHaveValue = RegexUtil.getString("value=\"([^\"]*?)\"[^>]*name=\"" + matcher.group(1) + "\"", source);
                }
                if (isHaveValue != null) {
                    data.put(matcher.group(1), isHaveValue);
                }
            }
        }

        pattern = Pattern.compile("name=\"([^\"]*?)\"[^>]*type=\"hidden\"");
        matcher = pattern.matcher(source);
        while (matcher.find()) {
            if (data.get(matcher.group(1)) == null) {
                String isHaveValue = RegexUtil.getString("name=\"" + matcher.group(1) + "\"[^>]*value=\"([^\"]*?)\"", source);
                if (isHaveValue == null) {
                    isHaveValue = RegexUtil.getString("value=\"([^\"]*?)\"[^>]*name=\"" + matcher.group(1) + "\"", source);
                }
                if (isHaveValue != null) {
                    data.put(matcher.group(1), isHaveValue);
                }
            }
        }

//        pattern = Pattern.compile("(?:input|textarea)[^>]*\\s(?:id|name)=\"([^\"]*?)\"[^>]*value=\"([^\"]+?)\"");
//        matcher = pattern.matcher(source);
//        while (matcher.find()) {
//            if (formData.get(matcher.group(1)) == null) {
//                formData.put(matcher.group(1), matcher.group(2));
//            }
//        }
//        pattern = Pattern.compile("(?:input|textarea)[^>]*value=\"([^\"]+?)\"[^>]*(?:id|name)=\"([^\"]*?)\"");
//        matcher = pattern.matcher(source);
//        while (matcher.find()) {
//            if (formData.get(matcher.group(2)) == null) {
//                formData.put(matcher.group(2), matcher.group(1));
//            }
//        }
        //input|textarea表单数据 value必需要有值不为""
        pattern = Pattern.compile("(?:<textarea|<input)[^>]*name=\"([^\"]+?)\"");
        matcher = pattern.matcher(source);
        while (matcher.find()) {
            if (data.get(matcher.group(1)) == null) {
                //System.out.println(matcher.group(1));
                String str = matcher.group(1).replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
                String isHaveValue = RegexUtil.getFirstString("name=\"" + str + "\"[^>]*value=\"([^\"]+?)\"", source);
                //System.out.println(isHaveValue);
                if (isHaveValue == null) {
                    isHaveValue = RegexUtil.getFirstString("value=\"([^\"]+?)\"[^>]*name=\"" + str + "\"", source);
                }
                if (isHaveValue != null) {
                    data.put(matcher.group(1), isHaveValue);
                }
            }

        }
        pattern = Pattern.compile("(?:<textarea|<input)[^>]*id=\"([^\"]+?)\"");
        matcher = pattern.matcher(source);
        while (matcher.find()) {
            if (data.get(matcher.group(1)) == null) {
                String isHaveName = RegexUtil.getString("id=\"" + matcher.group(1) + "\"[^>]*name=\"([^\"]+?)\"", source);
                if (isHaveName == null) {
                    isHaveName = RegexUtil.getString("name=\"([^\"]+?)\"[^>]*id=\"" + matcher.group(1) + "\"", source);
                    if (isHaveName == null) {
                        String isHaveValue = RegexUtil.getFirstString("id=\"" + matcher.group(1) + "\"[^>]*value=\"([^\"]+?)\"", source);
                        if (isHaveValue == null) {
                            isHaveValue = RegexUtil.getFirstString("value=\"([^\"]+?)\"[^>]*id=\"" + matcher.group(1) + "\"", source);
                        }
                        if (isHaveValue != null) {
                            data.put(matcher.group(1), isHaveValue);
                        }
                    }
                }
            }
        }

        //select表单数据 有些可为""或0 有些带星号必选
        pattern = Pattern.compile("<select[^>]*name=\"([^\"]*?)\"[^>]*>.*?<option[^>]*value=\"([^\"]+?)\"", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(source);
        while (matcher.find()) {
            if (data.get(matcher.group(1)) == null) {
                data.put(matcher.group(1), matcher.group(2));
            }
        }
        return data;
    }

    public static Map<String, String> getAllData(String source) {

        Map<String, String> map = new HashMap<String, String>();
        Pattern pattern = Pattern.compile("(?:<textarea|<input)[^>]*name=\"([^\"]+?)\"");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            map.put(matcher.group(1), "");
        }
        pattern = Pattern.compile("(?:<textarea|<input)[^>]*id=\"([^\"]+?)\"");
        matcher = pattern.matcher(source);
        while (matcher.find()) {
            if (map.get(matcher.group(1)) == null) {
                String isHaveName = RegexUtil.getString("id=\"" + matcher.group(1) + "\"[^>]*name=\"([^\"]+?)\"", source);
                if (isHaveName == null) {
                    isHaveName = RegexUtil.getString("name=\"([^\"]+?)\"[^>]*id=\"" + matcher.group(1) + "\"", source);
                    if (isHaveName == null) {
                        map.put(matcher.group(1), "");
                    }
                }
            }
        }
        return map;
    }

    public static Map<String, Map<String, String>> getFromData(String fromPage, Map<String, String> map, Map<String, String> formData) {

        Map<String, Map<String, String>> data = new HashMap<>();

        Map<String, String> autoInputData = NormUtil.getAutoInputData(fromPage, formData);
        autoInputData.remove("email");
        autoInputData.remove("seccodeverify");
        System.out.println("【autoInputData】：" + autoInputData);

        Map<String, String> allData = NormUtil.getAllData(fromPage);
        System.out.println("【allData】:" + allData);

        Map<String, String> a = new HashMap<>();
        Map<String, String> b = new HashMap<>();

        for (String key : allData.keySet()) {
            if (autoInputData.get(key) == null) {
                if (map.get(key) == null) {
                    if (!key.contains("seccodeverify_") && !key.contains("secqaaverify_")) {
                        if (!key.contains("secanswer") && !key.contains("seccodeverify")) {
                            System.out.println("【检测到自定义字段】：" + key);
                            b.put(key, "");
                            //params_b.add(key);
                            //mcspvalue 防止暴力注册
                        }
                    }
                } else {
                    a.put((String) key, map.get(key));
                }
            }
        }

        for (String key : autoInputData.keySet()) {
            a.put(key, autoInputData.get(key));
        }
        data.put("a", a);
        data.put("b", b);
        return data;
    }

    public static List<NameValuePair> mapToList(Map<String, String> a) {
        List<NameValuePair> params = new ArrayList<>();
        for (String key : a.keySet()) {
            params.add(new BasicNameValuePair(key, a.get(key)));
        }
        return params;
    }

    public static void removeMapData(Map map, String[] str) {
        for (String s : str) {
            map.remove(s);
        }
    }

    public static List removeListData(List list, String[] str) {
        for (String s : str) {
            list.remove(s);
        }
        return list;
    }

    public static String removeBlanklines(String source) {
        return source.replaceAll("\\s*", "");
    }

    public static String removeHtmlSign(String source) {
        return source.replaceAll("<[^>]*>", "").replaceAll("\\s*", "");
    }

    public static StringBuilder getCookie(List<Cookie> lcookie, StringBuilder cookie) {
        StringBuilder cookie_str = new StringBuilder();
        cookie_str.append(cookie);

        for (Cookie c : lcookie) {
            if (RegexUtil.ifMatcher(c.getName(), cookie_str.toString())) {
                cookie_str.replace(0, cookie_str.length(), cookie_str.toString().replaceAll(c.getName().concat("=[^;]*;"), c.getName().concat("=").concat(c.getValue()).concat(";")));
            } else {
                cookie_str.append(c.getName()).append("=").append(c.getValue()).append(";");
            }
        }
        return cookie_str;
    }

  //  public static void main(String[] args) {
//        try {
//            URL url = new URL("sss");
//            System.out.println("getPath:" + url.getPath());
//            System.out.println("getRef:" + url.getRef());
//            System.out.println("getUserInfo:" + url.getUserInfo());
//            System.out.println("getHost:" + url.getHost());
//            System.out.println("getFile:" + url.getFile());
//            System.out.println("getPort:" + url.getPort());
//            System.out.println("getContent:" + url.getContent());
//            System.out.println("getProtocol:" + url.getProtocol());
//            System.out.println("getAuthority:" + url.getAuthority());
//            System.out.println("getQuery:" + url.getQuery());
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }

//    }
}
