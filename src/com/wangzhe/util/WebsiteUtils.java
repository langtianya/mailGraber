/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import com.wangzhe.beans.SiteBean;

import com.wangzhe.beans.Forms;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author ocq
 */
public class WebsiteUtils {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(WebsiteUtils.class.getName());

    /**
     * 获取一个随机的可用分类
     *
     * @param siteBean 网站对象
     * @return
     */
    public static String getRandomCategory(SiteBean siteBean) {

       return null;
    }

    /**
     * 获取一个随机的可用分类
     *
     * @param useableCategory 用逗号分隔的可发分类字符串
     * @return
     */
    public static String getRandomCategory(String useableCategory) {
        if (useableCategory == null) {
            return null;
        }
        String[] ceArr = useableCategory.split(",|，");
        if (!ArrayUtils.isEmpty(ceArr)) {
            return ceArr[NumberUtils.getRandomNumByMax(ceArr.length)];
        }
        return null;
    }

    /**
     *  //把获取的表单信息写进表单
     *
     * @param forms String[][] 表单信息
     * @param form Forms 表单拼接载体
     * @throws Exception
     */
    public static void setFrom(String[][] forms, Forms form) {
        if (forms == null) {
            return;
        }
        Assert.notNull(form);
        if (forms.length > 0) {
            for (int i = 0; i < forms.length; i++) {
                if (StringUtils.isOneEmpty(forms[i][0])) {
                    continue;
                }
                if (!form.contains(forms[i][0])) {
                    if (forms[i][1] == null || forms[i][1].equals("")) {
                        forms[i][1] = "hongkong";
                    }
                    form.add(forms[i][0], StringEscapeUtils.unescapeHtml(forms[i][1]));
                }
            }
        }
    }

    /**
     *
     * 获取表单信息（包括了隐藏表单）
     */
    public static String[][] getFormsNameAndValue(String webpageContent) {
        if (webpageContent == null) {
            return null;
        }
        String tempString = null;
        if (webpageContent.toLowerCase().contains("<form")) {
            tempString = RegexUtil.getFirstString("(<form[^>]*?>[\\s\\S]*?</form>)", webpageContent, 1);
        }
        if (StringUtils.allNotEmpty(tempString)) {
            webpageContent = tempString;
        }

        Map<String, String> resMap = null;
        String[] regexs = new String[]{
            "name=[\"']{0,1}([^\\s>\"']*]*)[\"']{0,1}()",
            "name=[\"']{0,1}([^\\s>\"']*]*)[\"']{0,1}[^<>]*value=['\"]{0,1}([^>\"']*]*)['\"]{0,1}",
            "value=['\"]{0,1}([^>\"']*]*)['\"]{0,1}[^>]*?name=[\"']{0,1}([^\\s>\"']*]*)[\"']{0,1}",
            "name=[\"']{0,1}([^\\s>\"']*]*)[\"']{0,1}[^<>]*?>\\s*?<option[^<]*?value=['\"]{0,1}(\\d+]*)['\"]{0,1}",
            "name=[\"']{0,1}([^\\s>\"']*]*)[\"']{0,1}[^<>]*?>\\s*?<option[^<]*?</option>\\s*?<option[^<]*?value=['\"]{0,1}([^\\s>\"']*]*)['\"]{0,1}",
            "<textarea[^>]*?name=[\"']{0,1}([^\\s>\"']*]*)[\"']{0,1}[^<>]*['\"]{0,1}>([^>\"']*]*)['\"]{0,1}</textarea>"

        };
        for (int i = 0, len = regexs.length; i < len; i++) {
            Map<String, String> tempArr = RegexUtil.getMap(regexs[i], webpageContent);
            if (!ContainerUtils.isEmpty(tempArr)) {

                if (regexs[i].startsWith("value")) {
                    tempArr = ContainerUtils.reversalKeyValue(tempArr);
                }

                if (resMap == null) {
                    resMap = tempArr;
                } else {
                    resMap.putAll(tempArr);
                }

                continue;
            }
        }
        return ContainerUtils.mapToStrArr2(resMap);
    }

    public static String getActionByIdOrName(String webpageContent, String[] formNames) {
        return getActionByIdOrNameAndUrltag(webpageContent, formNames, null);
    }

    public static String getActionByUrltag(String webpageContent, String[] actionUrls) {
        return getActionByIdOrNameAndUrltag(webpageContent, null, actionUrls);
    }

    /**
     * 根据标签id和name获取表单action的URl，找不到再用url中的标志查找
     *
     * @param formNames String 正则中name或者id的标志
     * @param actionUrls String[] url中标志
     * @return String
     */
    public static String getActionByIdOrNameAndUrltag(String webpageContent, String[] formNames, String[] actionUrls) {
        String res = null;
        if (StringUtils.isOneEmpty(webpageContent)) {
            return null;
        }
        for (int i = 0; formNames != null && i < formNames.length; i++) {
            String[] formpat = {
                new StringBuffer("(?:name|id|class)+=[\"']{0,1}").append(formNames[i]).append("[\"']{0,1}[^>]*?action=[\"']{0,1}([^\\s\"'>]*)[\"']{0,1}").toString(),
                "action=[\"']{0,1}([^\\s\"'>]*)[\"']{0,1}[^>]*?(?:name|id|class)+=[\"']{0,1}" + formNames[i] + "[\"']{0,1}",//有空再改成StringBuffer
            };

            //先用正则查找目标
            for (int j = 0; j < formpat.length; j++) {
                res = RegexUtil.getFirstString(formpat[j], webpageContent, 1);
                if (res != null && res.length() > 0) {
                    return HtmlUtils.replaceTagAmp(res);
                }
            }
        }

        //正则查找不到再用url中的标志查找
        if (res == null && actionUrls != null && actionUrls.length > 0) {
            List actions = RegexUtil.getList("action=[\"']{0,1}([^\"'\\s>]*)[\"']{0,1}", webpageContent, 1);

            if (actions != null && actions.size() > 0) {
                for (int i = 0; i < actions.size(); i++) {
                    for (int j = 0; j < actionUrls.length; j++) {
                        if (((String) actions.get(i)).contains(actionUrls[j])) {
                            return HtmlUtils.replaceTagAmp((String) actions.get(i));
                        }
                    }
                }
                //根据url找不到，而actions集合只有一个元素时
                if (actions.size() == 1 && (res == null || res.length() < 1) && !((String) actions.get(0)).contains("search")) {
                    return HtmlUtils.replaceTagAmp((String) actions.get(0));
                }
            }
        }
        return null;
    }

    public static String getFormContentByFlag(String destContent, String[] formNames) {
        return getFormContentByFlag(destContent, formNames, null, null, null);
    }

    public static String getFormContentByFlag(String destContent, String[] formNames, String[] actionUrls) {
        return getFormContentByFlag(destContent, formNames, actionUrls, null, null);
    }

    public static String getFormContentByFlag(String destContent, String[] formNames, String[] actionUrls, String[] formAttributes) {
        return getFormContentByFlag(destContent, formNames, actionUrls, formAttributes, null);
    }

    /**
     * 根据表单的名称或者id获取表单内的所有内容（如果匹配不到，转向范匹配），如果formNames为null代表忽略特征进行范匹配
     * 获取给定表单标签名称的表单内容，
     *
     * @param destContent
     * @param formNames String[] 表单标签名称集合，如果为null代表忽略特征进行范匹配 比如<form
     * name="postForm" 中的postForm @param actionUrls 表单中请求地 址列表比如
     * action="http://xxxx/xx"的http://xxxx/xx @param formAttributes 表单中属性名称列表
     * 比如name=“username”中的username @param taskType 比如是注册还是发布还是登录 @ret u r n
     * String 表单内容
     */
    public static String getFormContentByFlag(String destContent, String[] formNames, String[] actionUrls, String[] formAttributes, String taskType) {
        String formContent = null;
        if (StringUtils.isOneEmpty(destContent)) {
            return null;
        }
        ///////////////利用正则列表
        for (String formName : formNames) {

            String[] hiddenpats = new String[]{
                new StringBuffer("(<form[^>]*?\\s*?=\\s*[\"']{0,1}").append(formName).append("[\"']{0,1}.*?)</form>").toString(),
                new StringBuffer("(<form[^>]*?\\s*?=\\s*[\"']{0,1}[^\"']*?").append(formName).append("[^\"']*?[\"']{0,1}.*?)</form>").toString()
            };
            for (String regex : hiddenpats) {
                formContent = RegexUtil.getMatcher(regex, destContent, 1, true);
                if (formContent != null) {
                    return formContent;
                }
            }

        }
        ///////////////范抓取

        List forms = RegexUtil.getMatcherList("(<form[^>]*?>.*?</form>)", destContent, 1);

        //查找表单内容是否有actionurl标识
        for (int i = 0, len = forms.size(); forms != null && i < len; i++) {

            String formflag = RegexUtil.getMatcher("(<form[^>]*>)", (String) forms.get(0), 1, true);
            if (formflag != null && StringUtils.isContainsOne(formflag, "search", "navigation", "pageform")) {
                forms.remove(0);
                continue;
            }
            
            for (int j = 0; actionUrls != null && j < actionUrls.length; j++) {
                if (formflag != null && formflag.contains(actionUrls[j])) {
                    formContent = (String) forms.get(0);
                    return formContent;
                }
            }

        }

        //查找表单内容是否有表单属性标识标识
        for (int i = 0; forms != null && i < forms.size(); i++) {

//            String formflag = RegexUtil.getFirstString("(<form[^>]*>)", (String) forms.get(i), 1);
//            if (formflag != null && StringUtils.containsOne(formflag, "search","navigation")) {
//                continue;
//            }
            for (int j = 0; formAttributes != null && j < formAttributes.length; j++) {
                if (((String) forms.get(i)) != null && ((String) forms.get(i)).contains(formAttributes[j])) {
                    formContent = (String) forms.get(i);
                    return formContent;
                }
            }

        }
        //只有一个符合条件时
        if (forms.size() > 0) {
            return (String) forms.get(0);
        }
        return formContent;
    }

    /**
     * 根据表单属性名称获取表单属性中的值
     *
     * @param formNames String
     * @return String
     */
    public static String getFormValueByName(String srcContent, String[] formNames) {
        if (formNames == null) {
            return "";
        }
        String res = null;
        for (String formName : formNames) {
            res = getFormValueByName(srcContent, formName);
            if (res != null) {
                break;
            }
        }
        return res;
    }

    /**
     * 根据表单属性名称获取表单属性中的值
     *
     * @param formNames String
     * @return String
     */
    public static String getFormValueByName(String srcContent, String formName) {
        if (formName == null || srcContent == null || srcContent.length() == 0) {
            return "";
        }
        String res = null;
        String[] getRegex = new String[]{new StringBuffer("name=['\"]{0,1}").append(formName).append("['\"]{0,1}[^>]*?value=['\"]{0,1}([^'\"]*)['\"]{0,1}").toString(),
            new StringBuffer("value=['\"]{0,1}([^'\"]*)['\"]{0,1}[^>]*?name=['\"]{0,1}").append(formName).append("['\"]{0,1}").toString(),};
        for (String regex : getRegex) {

            res = RegexUtil.getFirstString(regex, srcContent, 1);
            if (res != null && res.length() > 0) {
                return res;
            }
        }
        return res;
    }

    /**
     * 给定url中的标识或者显示的连接名称，获取连接的url
     *
     * @param desString 目标内容
     * @param urlFalgs 需要获取的URl中的特征，即herf=""引号内必须有的特征
     * @param displayNames a标签显示给用户的内容中有的特征，不是必须的
     * @return
     */
    public static String getALinkValueTagByName(String desString, String[] urlFalgs, String[] displayNames) {
        for (String displayName : displayNames) {
            for (String urlFalg : urlFalgs) {
                String reString = getALinkValueTagByName(desString, desString, desString);
                if (reString != null && reString.length() > 0) {
                    return reString;
                }
            }
        }
        return null;
    }

    /**
     * 给定url中的标识或者显示的连接名称，获取连接的url
     *
     * @param desString 目标内容
     * @param urlFalg String 需要获取的URl中的特征，即herf=""引号内必须有的特征
     * @param displayName String a标签显示给用户的内容中有的特征，不是必须的
     */
    public static String getALinkValueTagByName(String desString, String urlFalg, String displayName) {
        if (StringUtils.isAllEmpty(urlFalg)) {
            return null;
        }
        String[] regexs
                = {new StringBuffer("<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*").append(urlFalg).append("[^\\s\"'>]*)[\"']{0,1}[^>]*?>").append(displayName).append("<").toString(),
                    new StringBuffer("<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*").append(urlFalg).append("[^\\s\"'>]*)[\"']{0,1}[^>]*?>").append(displayName).append("[^<]*?[^<]*?<").toString(),
                    new StringBuffer("<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*").append(urlFalg).append("[^\\s\"'>]*)[\"']{0,1}[^>]*?>[^<]*?[^<]*?").append(displayName).append("[^<]*?<").toString(),
                    new StringBuffer("<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*)[\"']{0,1}[^>]*?>").append(displayName).append("[^<us]*?<").toString(),
                    new StringBuffer("<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*").append(urlFalg).append("[^\\s\"'>]*)[\"']{0,1}[^>]*?>[^<]*?<").toString()
                };

        String res = null;
        for (int i = 0; i < regexs.length; i++) {
            res = RegexUtil.getFirstString(regexs[i], desString, 1);
            if (res != null && res.length() > 0) {
                res = HtmlUtils.replaceTagAmp(res);
                return res;
            }
        }
        return res;

    }

    /**
     * 从给定内容中获取内容中包含的跳转信息即location
     *
     * @param desString
     * @return
     */
    public static String getLocationFromHtml(String desString) {

        if (desString == null) {
            return null;
        }
        String location = null;
        if (location == null) {
            location = RegexUtil.getFirstString("location\\s*?=\\s*?[\"']([^\"']*)[\"']", desString);
        }
        if (location == null) {
            location = RegexUtil.getFirstString("location.href\\s*?=\\s*?[\"']([^\"']*)[\"']", desString);
        }
        return location;
    }

    public static void siteStatusStatistics(List<SiteBean> sitesAll) {
        //        Set<String> site=new HashSet<>();
        Map<String, Integer> siteMap = new HashMap<>();
        for (SiteBean siteBean : sitesAll) {
//            site.add(siteBean.getStatus());
            if (siteMap.containsKey(siteBean.getStatus())) {
                Integer sum = (Integer) siteMap.get(siteBean.getStatus());
                sum++;
                siteMap.put(siteBean.getStatus(), sum);
            } else {
                siteMap.put(siteBean.getStatus(), 1);
            }

        }

        String[][] siteArray = new String[siteMap.size()][2];
        int i = 0;
        for (String key : siteMap.keySet()) {
            siteArray[i][0] = key;
            siteArray[i][1] = String.valueOf(siteMap.get(key));
            i++;
        }

        for (int a = 0; a < siteArray.length; a++) {
            for (int j = 0; j < siteArray.length - 1; j++) {
                if (Integer.valueOf(siteArray[a][1]) > Integer.valueOf(siteArray[j][1])) {
                    String[] temp = siteArray[a];
                    siteArray[a] = siteArray[j];
                    siteArray[j] = temp;
                }
            }
        }
        int aaaI = 1;
        System.out.println("一共【" + sitesAll.size() + "】个网站，状态统计如下：");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("序号\t该状态下的网站数量\t占百分比\t\t错误状态");
        System.out.println("-------------------------------------------------------------------------------------");
        for (String[] strings : siteArray) {
            float ssF = new BigDecimal(Float.valueOf(strings[1]) * 100 / sitesAll.size()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//                  String ttString=null;
//                  if(strings[0].length()>26){
//                      ttString="\t";
//                  }else  if(strings[0].length()>20){
//                        ttString="\t\t";
//                  }else  if(strings[0].length()>12){
//                        ttString="\t\t\t";
//                  } if(strings[0].length()>8){
//                        ttString="\t\t\t\t";
//                  }
//

            System.out.println(aaaI++ + "\t\t" + strings[1] + "\t\t" + ssF + "%" + "\t\t" + strings[0]);
//            System.out.println("序号：【" + aaaI++ + "】:错误状态【" + strings[0] + "】:该状态下的网站数量【" + strings[1] + "】占百分比："+ssF+"%");
        }

//        for (String key : siteMap.keySet()) {
//            System.out.println(key+":"+siteMap.get(key));
//        }
    }

    /**
     * 获取用冒号分隔的键值对的值，获取形如：”iid: 202730125“中的202730125
     *
     * @param desString
     * @return
     */
    public static String getValueByKeySeparateByColon(String desString, String key) {

        if (desString == null || key == null) {
            return null;
        }
        return RegexUtil.getFirstString("[\"']{0,1}".concat(key).concat("[\"']{0,1}\\s*?:\\s*?[\"']{0,1}([^\"'\\s,]+)[\"']{0,1}"), desString);

    }

    private static ScriptEngineManager jsEngineManager = null;
    private static ScriptEngine jsEngine = null;

    /**
     * 调用给定js代码中指定的js函数
     *
     * @param jsCode js代码
     * @param jsFunctionName 要调用的js函数名称
     * @param args js函数需要传人的参数
     * @return
     */
    public static String invokeJsFunction(String jsCode, String jsFunctionName, Object... args) {
        if (StringUtils.isOneEmpty(jsCode, jsFunctionName)) {
            return null;
        }
        try {
            if (jsEngineManager == null) {
                jsEngineManager = new ScriptEngineManager();
            }
            if (jsEngine == null) {
                jsEngine = jsEngineManager.getEngineByName("JavaScript");
            }
            jsEngine.eval(jsCode);
            Invocable invoca = (Invocable) jsEngine;
            return (String) invoca.invokeFunction(jsFunctionName, args);
        } catch (ScriptException ex) {
            log.error(ex);
        } catch (NoSuchMethodException ex) {
            log.error("没有找到该js方法" + ex);
        }
        return null;
    }

}
