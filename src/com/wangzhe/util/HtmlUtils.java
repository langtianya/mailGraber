package com.wangzhe.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author ocq
 */
public class HtmlUtils {
//    GBK_ZH_FONT
//            BIG5_ZH_FONT

    private static final Logger log = Logger.getLogger(HtmlUtils.class.getName());

    public static final Pattern P_SCRIPT = Pattern.compile("<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>", Pattern.CASE_INSENSITIVE);
    public static final Pattern P_STYLE = Pattern.compile("<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>", Pattern.CASE_INSENSITIVE);
    public static final Pattern P_HTML = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
    public static final Pattern patCSS = Pattern.compile("<style[^>]*>([\\s\\S](?!<style))*?</style>", Pattern.CASE_INSENSITIVE);
    public static final Pattern patJS = Pattern.compile("<script[^>]*>([\\s\\S](?!<script))*?</script>", Pattern.CASE_INSENSITIVE);
    public static final Pattern patHTML = Pattern.compile("<([^>]*)>", Pattern.DOTALL);
    public static final Pattern patLINK = Pattern.compile("<link.*?>", Pattern.DOTALL);
    public static final Pattern patSentence = Pattern.compile("[,，。;；]+", Pattern.DOTALL);

    /**
     * Java过滤任意(script,html,style)标签符,返回纯文本
     *
     * @param inputString
     * @return
     */
    public static String htmlFormat2Txt(String inputString) {

        if (StringUtils.isAllEmpty(inputString)) {
            return null;
        }
        Matcher mat;

        try {
            mat = P_SCRIPT.matcher(inputString);
            inputString = mat.replaceAll(""); // 过滤script标签

            mat = P_STYLE.matcher(inputString);
            inputString = mat.replaceAll(""); // 过滤style标签

            mat = P_HTML.matcher(inputString);
            inputString = mat.replaceAll(""); // 过滤html标签
            return inputString;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Html2Text: " + e.getMessage());
        }

        return inputString;// 返回文本字符串
    }

    public static String ubbToHtml(String htmlContent) {
        //http://t.55.la/html_ubb/
        htmlContent = htmlContent.replace("<", "&lt;");
        htmlContent = htmlContent.replaceAll(">", "&gt;");
        htmlContent = htmlContent.replaceAll("\n/ig", "<br />");
//		htmlContent = htmlContent.replaceAll("\\[\\](.+?)\\[\\/code\\]", "function($1, $2) {return phpcode($2);}");

        htmlContent = htmlContent.replaceAll("\\[hr\\]", "<hr />");
        htmlContent = htmlContent.replaceAll("\\[\\/(size|color|font|backcolor)\\]", "</font>");
        htmlContent = htmlContent.replaceAll("\\[(sub|sup|u|i|strike|b|blockquote|li)\\]", "<$1>");
        htmlContent = htmlContent.replaceAll("\\[\\/(sub|sup|u|i|strike|b|blockquote|li)\\]", "</$1>");
        htmlContent = htmlContent.replaceAll("\\[\\/align\\]", "</p>");
        htmlContent = htmlContent.replaceAll("\\[(\\/)?h([1-6])\\]", "<$1h$2>");

        htmlContent = htmlContent.replaceAll("\\[align=(left|center|right|justify)\\]", "<p align=\"$1\">");
        htmlContent = htmlContent.replaceAll("\\[size=(\\d+?)\\]", "<font size=\"$1\">");
        htmlContent = htmlContent.replaceAll("\\[color=([^\\[\\<]+?)\\]", "<font color=\"$1\">");
        htmlContent = htmlContent.replaceAll("\\[backcolor=([^\\[\\<]+?)\\]", "<font style=\"background-color:$1\">");
        htmlContent = htmlContent.replaceAll("\\[font", "<font face=\"$1\">");
        htmlContent = htmlContent.replaceAll("\\[list=(a|A|1)\\](.+?)\\[\\/list\\]", "<ol type=\"$1\">$2</ol>");
        htmlContent = htmlContent.replaceAll("\\[(\\/)?list\\]", "<$1ul>");

//		htmlContent = htmlContent.replaceAll("[\\s:(\\d+)\\]","function($1,$2){ return smilepath($2);}");
        htmlContent = htmlContent.replaceAll("[img\\]([^\\[]*)\\[\\/img\\]", "<img src=\"$1\" border=\"0\" />");
        htmlContent = htmlContent.replaceAll("[url=([^\\]]+)\\]([^\\[]+)\\[\\/url\\]", "<a href=\"$1\">$2</a>");
        htmlContent = htmlContent.replaceAll("[url\\]([^\\[]+)\\[\\/url\\]", "<a href=\"$1\">$1</a>");
        return htmlContent;
    }

    public static String htmlFormat2Ubb(String htmlContent) {
        if (StringUtils.isEmpty(htmlContent)) {
            return htmlContent;
        }
        htmlContent = htmlContent.replaceAll("<a[^>]*href=[\"\"\\s]*([^\\s\"\"]*)[^>]*>(.+?)<\\/a>", "[url=$1]$2[/url]");
        htmlContent = htmlContent.replaceAll("<img[^>]*smile=\"(\\d+)\"[^>]*>", "[s:$1]");
        htmlContent = htmlContent.replaceAll("<img[^>]*src=[\"\"\\s]*([^\\s\"\"]+)[^>]*>", "[img]" + "$1" + "[/img]");

        htmlContent = htmlContent.replaceAll("<br[^>]*>", "\r\n");
        htmlContent = htmlContent.replaceAll("<p[^>\\/]*\\/>", "\r\n");
        htmlContent = htmlContent.replaceAll("</p>", "\r\n");

        htmlContent = htmlContent.replaceAll("\\son[\\w]{3,16}\\s?=\\s*(['\"]).+?\\1/ig", "");

        htmlContent = htmlContent.replaceAll("<hr[^>]*>", "[hr]");
        htmlContent = htmlContent.replaceAll("<(sub|sup|u|strike|b|i|pre)>", "[$1]");
        htmlContent = htmlContent.replaceAll("<\\/(sub|sup|u|strike|b|i|pre)>", "[/$1]");
        htmlContent = htmlContent.replaceAll("<(\\/)?strong>", "[$1b]");
        htmlContent = htmlContent.replaceAll("<(\\/)?em>", "[$1i]");
        htmlContent = htmlContent.replaceAll("<(\\/)?blockquote([^>]*)>", "[$1blockquote]");

        htmlContent = htmlContent.replaceAll("<[^>]*?>", "");
        htmlContent = htmlContent.replaceAll("&amp;", "&");
        htmlContent = htmlContent.replaceAll("&lt;", "<");
        htmlContent = htmlContent.replaceAll("&gt;", ">");
        
        htmlContent = htmlContent.replaceAll("\\s+", "\r\n");
        
        return htmlFormat2Txt(htmlContent);


    }

//    public static void main(String[] args) {
//        String aaString = "";
////        aaString = aaString.replaceAll("[\r\n]+", "\r\n");
////        System.out.println("去除后"+aaString);
//        String ssaString = htmlFormat2Ubb(aaString);
//        System.out.println("转化后" + ssaString);
//    }
    public static String html2TxtKeepLink(String inputString) {

        String[][] links = RegexUtil.getArray2("<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*)[\"']{0,1}[^>]*?>([^<]+)<", inputString);
        inputString = HtmlUtils.htmlFormat2Txt(inputString);

        if (links == null) {
            return inputString;
        }

        return inputString;

    }

    //去掉HTML标签代码
    public static String clearHtmlTag(String s, boolean combineBlank) {
        if (s == null) {
            return null;
        }

        try {
            Matcher m = null;

            m = patCSS.matcher(s);
            while (m.find()) {
                s = s.replace(m.group(), "");
            }

            m = patJS.matcher(s);
            while (m.find()) {
                s = s.replace(m.group(), "");
            }

            m = patHTML.matcher(s);
            while (m.find()) {
                s = s.replace(m.group(), "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (combineBlank) {
            s = s.trim().replaceAll("\\s{2,}", " ");
        }
        return s.trim();
    }

    /**
     * 清除注释
     *
     * @param src String
     * @return String
     */
    public static String clearComments(String src) {
        if (StringUtils.isAllEmpty(src)) {
            return src;
        }

        return src.replaceAll("<!--[\\s\\S]*?-->", "");
    }

    /**
     * 清空所有文本输入框中的文本
     *
     * @param src
     * @return
     */
    public static String clearFiledValue(String src) {
        if (src == null) {
            return null;
        }
        Document doc = Jsoup.parse(src);
        doc.select("input[type=text]").val("");
        return doc.html();
    }

    /**
     * 把双引号替换成单引号
     */
    public static String quoteDoubleToSingle(String src) {
        if (src == null) {
            return null;
        }
        return src = src.replace('"', '\'');
    }

    /**
     *
     * @param linkFeature String 需要获取的URl中的特征，即herf=""引号内必须有的特征
     * @param displayName String a标签显示给用户的内容中有的特征，不是必须的
     */
    public String getATagLinkByName(String websiteContent, String linkFeature, String displayName) // , String webContent
    {
        String[] formpat
                = {"<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*" + linkFeature + "[^\\s\"'>]*)[\"']{0,1}[^>]*?>" + displayName + "<", "<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*" + linkFeature + "[^\\s\"'>]*)[\"']{0,1}[^>]*?>" + displayName + "[^<]*?[^<]*?<",
                    "<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*" + linkFeature + "[^\\s\"'>]*)[\"']{0,1}[^>]*?>[^<]*?[^<]*?" + displayName + "[^<]*?<", "<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*)[\"']{0,1}[^>]*?>[^<]*?" + displayName + "[^<]*?<",
                    "<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*" + linkFeature + "[^\\s\"'>]*)[\"']{0,1}[^>]*?>[^<]*?<"};

        String res = null;
        if (linkFeature.equals("")) {
            return res;
        }
        for (int i = 0; i < formpat.length; i++) {
            res = RegexUtil.getFirstString(websiteContent, formpat[i], 1);
            if (res != null && res.length() > 0) {
                break;
            }
        }
        if (res != null) {
            res = replaceTagAmp(res);
        }
        return res;
    }

    /**
     * 转换&amp;成为&
     *
     * @param htmlContent
     * @return
     */
    public static String replaceTagAmp(String htmlContent) {
        if (htmlContent != null && htmlContent.contains("&amp;")) {
            htmlContent = htmlContent.replace("&amp;", "&");
        }
        return htmlContent;
    }

    /**
     * 去掉HTML标签代码（css标签、js标签、html标签、两个空格换成一个）
     *
     * @param srcStr 要处理的字符串
     * @return 返回没有HTML标签代码的字符串
     */
    public static String removeHtmlTags(String srcStr) {
        if (srcStr == null) {
            return null;
        }
        // 去除css标签
        srcStr = srcStr.replaceAll("<style[^>]*>([\\s\\S](?!<style))*?</style>", "");
        // 去除js标签
        srcStr = srcStr.replaceAll("<script[^>]*>([\\s\\S](?!<script))*?</script>", "");
        // 去除html标签
        srcStr = srcStr.replaceAll("<([^>]*)>", "");
        //去空格
        srcStr = srcStr.trim().replaceAll("\\s{2,}", " ");

        return srcStr.trim();
    }

    //HTML反编码/解码html标签 方法1
    public static String unescapeHtml(String srcContent) {
        if (srcContent == null) {
            return null;
        }
        srcContent = srcContent.replace("%3f", "?");
        srcContent = srcContent.replace("%26", "&");
        srcContent = srcContent.replace("&rdquo;", "\"");
        srcContent = srcContent.replace("&hellip;", "..");
        srcContent = srcContent.replace("%3D", "=");
        srcContent = srcContent.replace("%2F", "/");
        srcContent = srcContent.replace("&lt;", "<");
        srcContent = srcContent.replace("&gt;", ">");
        srcContent = srcContent.replace("&nbsp;", " ");
        srcContent = srcContent.replace("&nbsp", " ");
        srcContent = srcContent.replace("%3a", ":");
        srcContent = srcContent.replaceAll("\\&[a-z]{2,5};", "");
        srcContent = srcContent.replace("&ldquo;", "\"");
        srcContent = srcContent.replace("%3d", "=");
        srcContent = srcContent.replace("&mdash;", "—");
        srcContent = srcContent.replace("%3A", ":");
        srcContent = srcContent.replace("&amp;", "&");
        srcContent = srcContent.replace("&quot;", "\"");
        srcContent = srcContent.replace("&quot", "\"");
        srcContent = srcContent.replace("%2f", "/");
        return srcContent;
    }

    /**
     *  //HTML反编码/解码html标签方法2
     */
//    public String unescapeHtml(String src) {
//        if(src==null){
//            return null;
//        }
//      return StringEscapeUtils.unescapeHtml(src);
//    }
    public static String clearLink(String html) {
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        html = html.replaceAll("<(a|A)[^>]*?>(.*?)</(a|A)>", "");
        html = html.replaceAll("(http[^<\\s]*?)", "");
        html = html.replaceAll("(www[^<\\s]*?)", "");
        html = html.replaceAll("([img].*?[/img])", "");

        return html;
    }
   public static String decodeUTFChar(String srcHtml) {
        if (srcHtml == null) {
            return null;
        }
        String result = srcHtml;
        Pattern pattern = Pattern.compile("\\\\u([0-9a-f]{4,4})", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(srcHtml);
        while (matcher.find()) {
            String tmp = matcher.group(0);
            String code = matcher.group(1);
            char c = (char) Integer.parseInt(code, 16);
            result = result.replace(tmp, c + "");
        }
        return result;
    }
}
