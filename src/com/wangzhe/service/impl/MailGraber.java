/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wangzhe.beans.ProxyBean;
import com.wangzhe.service.AbstractWebSiteOperater;
import com.wangzhe.ui.HomeController;
import com.wangzhe.util.ArrayUtils;
import com.wangzhe.util.Commons;
import com.wangzhe.util.FileUtils;
import com.wangzhe.beans.Forms;
import static com.wangzhe.util.HttpUtil.USERAGENT_KEY;
import com.wangzhe.util.NumberUtils;
import com.wangzhe.util.ProxyHttpUtil;
import com.wangzhe.util.RegexUtil;
import com.wangzhe.util.StringUtils;
import com.wangzhe.util.UrlUtils;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class MailGraber extends AbstractWebSiteOperater {

    private Logger log = Logger.getLogger(MailGraber.class.getName());
    public static boolean isStop = false;

    public MailGraber() {

    }
/**
 * 开始挖掘入口
 * @param grabIds
 * @param maxNum
 * @param homeController
 * @param minS
 * @param maxS
 * @param grabUrl
 * @return 
 */
 public boolean startGrab(List<String> grabIds, int maxNum, HomeController homeController, int minS, int maxS, String grabUrl) {

        this.homeController = homeController;

        if (grabIds == null || grabIds.size() < 1) {
            return false;
        }
        if (grabUrl == null || grabUrl.length() == 0) { 
           grabUrl = "http://www.baidu.com/";
        }

        final int needClickSubUrlNum = 3;
        log.error("挖掘中....1");
        int successSiteNum = 0;

//        getProxyFromUrl();
//        getProxyFromFile();
        //"http://www.wangdaizhijia.com/daohang.html"
//        doHttpGet(myReferUrl, myReferUrl, true);

//        List<String> urlList = RegexUtil.getList("<a[^>]*?href=[\"']{0,1}([^\\s\"'>#]{3,})[\"']{0,1}[^>]*?rel=[\"']{0,1}nofollow[\"']{0,1}[^>]*?>", webpageContent);
        List<String> urlList = new ArrayList<>();
//        urlList.add("http://www.ijitapu.com/");
//        urlList.add("http://localhost:8084/cloudclickServer/");
//        urlList.add("http://182.254.198.103/api/123.jsp");
//        urlList.add("http://182.254.198.103/");
//        urlList.add("http://www.wangdaituiguangbao.com/index.htm");
//        urlList.add("http://www.xingzuotao.com/");

        ///下面的所有请求都是代理请求
        //可以把遍历做得简单点，但是为了日后考虑，现在分开
        //遍历每个代理，每个代理去打开一批网址
//        String grabUrl = "http://www.ctflife.com/sxwl2014/grab.aspx";
        //遍历网址
//        for (String domainUrl : urlList) {
        tryTime = 0;//成功次数
        int validProxyNum = 0;
        long time1 = System.currentTimeMillis();
        List<ProxyBean> successProxyList = new ArrayList<>();
        log.error("挖掘中....2" + proxyList.size());
        //遍历代理
        for (int i = 0; i < proxyList.size(); i++) {

            if (isStop) {
                break;
            }
            if (tryTime > maxNum) {
                break;
            }
            proxyBean = proxyList.get(i);

//            String requestUrl = "http://sz.bendibao.com/ip/ip.asp";
//            doHttpGet2(requestUrl);
//            if (true) {
//                final String ip = RegexUtil.getString("你的电脑的公网IP地址：\\s*([^\\s]*)", webpageContent);
//                if (ip != null && !ip.isEmpty()) {
//                    log.info("您的IP:" + ip);
//                    saveProxyToFile("重新检验有效代理");
//                    webpageContent = null;
//                }
//                continue;
//            }
//            proxyBean = new ProxyBean("192.80.169.13", 9013, "user1", "vVYm70R0oXEf", "添加");
//            proxyBean.setType(Proxy.Type.SOCKS);
            log.error("使用第" + (i + 1) + "个代理ip");
            appendLog("使用第" + (i + 1) + "个代理ip");
//            doHttpGet2("https://www.alipay.com/");
//            if (webpageContent != null) {
//                saveProxyToFile("重新检验有效代理");
//            }
//            if (true) {
//                continue;
//            }
//              doHttpGet2("http://www.aashw.com/html/201443/1296.html#0-qzone-1-13501-d020d2d2a4e8d1a374a433f596ad144", null, true);
//              Commons.sleepSecond(5);
            //action=post&id=10&grabitem=3&postbt=%CD%B6%C6%B1
//            CustomForms form = new CustomForms(pageCharset);
//            form.addNotEncode("action", "post");
//            form.addNotEncode("id", "16");
//            form.addNotEncode("grabitem", "4");
//            form.addAndEncode("postbt", "挖掘");
//            doHttpPost(form.toStringBuilder(), "http://www.aashw.com/plus/grab.php", "http://www.aashw.com/html/201443/1296.html");
//            
            int iiii = 0;
            for (String id : grabIds) {

                if (true) {

                    BasicHeader[] headers2 = new BasicHeader[6];
                    headers2[0] = new BasicHeader("Accept", "application/json");
                    headers2[1] = new BasicHeader("Content-Type", "application/json; charset=UTF-8");
                    headers2[2] = new BasicHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; MI 4LTE MIUI/V6.5.2.0.KXDCNCD)");
                    headers2[3] = new BasicHeader("Connection", "Keep-Alive");
                    headers2[4] = new BasicHeader("Accept-Encoding", "gzip");

                    BasicHeader[] headers1 = new BasicHeader[]{
                        new BasicHeader("POST", "http://api.app.xmtv.cn/Token HTTP/1.1"),
                        new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"),
                        new BasicHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; MI 4LTE MIUI/V6.5.2.0.KXDCNCD)"),
                        new BasicHeader("Connection", "Keep-Alive"),
                        new BasicHeader("Accept-Encoding", "gzip")
                    };

                    Map<String, String> heardMap1 = new HashMap<>();
                    heardMap1.put("Accept", "application/json");
                    heardMap1.put("Content-Type", "application/json; charset=UTF-8");
                    heardMap1.put("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; MI 4LTE MIUI/V6.5.2.0.KXDCNCD)");
                    heardMap1.put("Connection", "Keep-Alive");
                    heardMap1.put("Accept-Encoding", "gzip");

                    Map<String, String> heardMap2 = new HashMap<>();
                    heardMap2.put("POST", "http://api.app.xmtv.cn/Token HTTP/1.1");
                    heardMap2.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    heardMap2.put("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; MI 4LTE MIUI/V6.5.2.0.KXDCNCD)");
                    heardMap2.put("Connection", "Keep-Alive");
                    heardMap2.put("Accept-Encoding", "gzip");

                    String username = "15007553053";
//                    username = "18577859691";
                    String password = "383462579";
//                    password = "134258";

                    Forms form = new Forms();
                    form.add("grant_type", "password");

                    form.add("password", password);
                    form.add("username", username);
                    doHttpPost(form.toStringBuilder(pageCharset), "http://api.app.xmtv.cn/Token", "http://api.app.xmtv.cn/Token", heardMap1);

                    if (webpageContent != null) {
                        JSONObject jsono = JSONObject.parseObject(webpageContent);
                        String access_token = (String) jsono.get("access_token");
                        String token_type = jsono.getString("token_type");
                        if (access_token != null) {

                            headers2[5] = new BasicHeader("Authorization", token_type + " " + access_token);
                            heardMap2.put("Authorization", token_type + " " + access_token);

                            form = new Forms();
                            String keyString = "1MDA3NTUzMDUz";
//                            keyString = "4NTc3ODU5Njkx";

                            final String grabId = "eyJVc2VyTmFtZSI6IjE" + keyString + "Iiwidm90ZUlkIjo0LCJWb3RlSXRlbUlEIjoxMjUxLCJEZXZpY2VJRCI6Ijg2NjMzMzAyMDIxMTkzMiJ9";
                            form.add("userName", username);

                            form.add("data", grabId);
                            doHttpPost(form.toStringBuilder(pageCharset), "http://api.app.xmtv.cn/api/grab?userName=" + username + "&data=" + grabId, null, heardMap2);
                            if (isWebpageContentContainsOne("status\":0,\"message\":null")) {
                                log.info("挖掘成功");
                                continue;
//                                return true;
                            } else if (isWebpageContentContainsOne("Not logged in")) {//未登陆
                                log.info("未登陆");
                                return false;
                            } else if (isWebpageContentContainsOne("status\":1")) {//已经挖掘过
                                log.info("已经挖掘过");
//                                return false;
                                continue;
                            }

                        }
                    }
                    continue;

                }
                iiii++;

//                CustomForms form = new CustomForms();
//                form.addNotEncode("type", "grab");
//                form.addNotEncode("art_id", id);
//                form.addNotEncode("r", String.valueOf(Math.random()));
//
//                doHttpPost(form.toStringBuilder(), grabUrl, grabUrl);
                cookies.clear();
                //屡知多清神奇的药引啊，龙王的胡须我来取（连载1516
                doHttpGet2("http://new.060s.com/tp/tp.php?id=48535E7F496D52375F7D42374A6D60364A4360334A43463A", "http://new.060s.com/tp/showtp.php");

//                //保存成功的代理
//                successProxyList.add(proxyBean);
                if (isWebpageContentContainsOne("挖掘成功", "res':'true")) {

                    saveProxyToFile("重新检验有效代理");
                    if (iiii == grabIds.size()) {
                        tryTime++;

                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
//                                delegate.fx_startVoteButtom.setText("成功" + tryTime + "次");
                                homeController.lb_successDis.setText("已成功" + tryTime + "票");
//                                if (tryTime == 1) {
//                                    delegate.fx_startVoteButtom.setDisable(true);
//                                }

                            }
                        });

                        //限定多少次就退出
                        if (tryTime > NumberUtils.getRandomNum(80, 150)) {
                            break;
                        }
                    }

                    log.error("挖掘成功" + tryTime + "次");
                    appendLog("挖掘成功" + tryTime + "次");

                    doHttpGet2("http://www.ctflife.com/sxwl2014/grab.aspx?o=t&w=2&pageno=6", null, true);
                    Commons.sleepSecond(NumberUtils.getRandomNum(minS, maxS));

                    log.error("时间间隔是：" + String.valueOf((System.currentTimeMillis() - time1) / (1000)) + "秒");
                    appendLog("时间间隔是：" + String.valueOf((System.currentTimeMillis() - time1) / (1000)) + "秒");

                    time1 = System.currentTimeMillis();
                }
                if (isWebpageContentContainsOne("您已经投过票了", "今天已经投过票了")) {
                    log.error("您已经投过票了");
                    appendLog("第" + (i + 1) + "个代理ip已经无效或者已经投过票");
                }
                clearLog();
            }
//            log.error(webpageContent);
            if (true) {
                continue;
            }

            //、、、、、、、、、、//////////挖掘系统结束//////////
//                Platform.runLater(futureTask);
//                DialogUtil.showWebView(null, domainUrl, null);
            //////////////////////////////////////////////////////////////////////打开首页  ////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////打开首页  ////////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////////打开首页  ////////////////////////////////////////////////////////////////////
//                doHttpGet(domainUrl, referUrl, true);
//                doHttpGet2(domainUrl, myReferUrl, true);
//               webpageContent= ProxyHttpUtil.doHttpGet(domainUrl, myReferUrl, true, proxyBean);
            if (StringUtils.isEmpty(webpageContent)) {
                tryTime++;
                if (tryTime > 5) {
//                         log.info(domainUrl+"网址被跳过");
//                         break;
                }

                //从头在来，
                i = i - 1;
                continue;
            }
            List<String> tongjiUrls = RegexUtil.getList("=[\"']{0,1}([^\\s\"'>]*?baidu.com/[^\\s\"'>]*)[\"']{0,1}[^>]*?>", webpageContent);
            log.info(webpageContent);
            for (String tongjiUrl : tongjiUrls) {
                doHttpGet2(tongjiUrl, grabUrl, true);
            }

//                log.info(HtmlUtils.htmlFormat2Txt(webpageContent.replaceAll("\\s{5,}", "")));
//                log.error(HtmlUtils.htmlFormat2Txt(webpageContent).replaceAll("\\s{5,}", ""));
            Commons.sleepSecond(sleepTime);

            doHttpGet2("http://hm.baidu.com/h.js?fc95c644693e6b042d250bd24efd2df1", grabUrl, true);
            /////////////////////////////////////////////////////////////////////随机点3个内页连接///////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////随机点3个内页连接///////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////随机点3个内页连接///////////////////////////////////////////////////////////////////
            List<String> subUrlList = RegexUtil.getList("<a[^>]*?href=[\"']{0,1}([^\\s\"'>#]{3,})[\"']{0,1}[^>]*?>", webpageContent);
            int haveClickSubUrlNum = 0;
//                for (String subUrl : subUrlList) {
            for (int j = 0; j < subUrlList.size(); j++) {

                //随机获取一个
                String subUrl = subUrlList.remove(NumberUtils.getRandomNumByMax(subUrlList.size()));

                if (subUrl.equals(grabUrl) || StringUtils.isContainsOne(subUrl, "javascript:")) {
                    continue;
                }
                if (!subUrl.startsWith("http")) {
                    subUrl = UrlUtils.connectUrl(grabUrl, subUrl);
                } else {
                    String rootDomainUrl = UrlUtils.getRootDomain(grabUrl);
                    if (StringUtils.notEmpty(rootDomainUrl) && !subUrl.contains(rootDomainUrl)) {
                        continue;
                    }
                }

                doHttpGet2(subUrl, grabUrl, true);
                if (StringUtils.isEmpty(webpageContent)) {
                    continue;
                }

                Commons.sleepSecond(sleepTime);
                haveClickSubUrlNum++;
                log.info(subUrl + "内页点击执行成功");

                //完成点击任务，点击成功
                if (haveClickSubUrlNum > needClickSubUrlNum) {
                    validProxyNum++;
                    break;
                }
            }//内页 for end

            //点击不成功的情况
            if (haveClickSubUrlNum < needClickSubUrlNum + 1) {
                //从头在来，
                i = i - 1;
                continue;
            }

            if (proxyBean != null && haveClickSubUrlNum > needClickSubUrlNum) {
                log.info(proxyBean.getHost() + ":" + proxyBean.getPort() + "代理执行成功");
            }

        }//代理for end
        if (validProxyNum != 0) {
            successSiteNum++;
            log.info(grabUrl + "域名点击任务完成执行完成,使用了的有效代理数量为" + validProxyNum);
        }

//        }//网站 for end
        log.info("所有网站执行完成，成功数量为" + successSiteNum);
        return false;
    }
    @Override
    protected boolean addPublishResult(String publishResultUrl, int status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ///////////////////////////////////////////////////////////////////代理点击器实现
    protected HttpURLConnection getFixedTimeHttpUrlConnect(String requestUrl, String referUrl) throws Exception {
        getHttpUrlConnect(requestUrl, referUrl);
        setTimeOut(10 * 1000, 10 * 1000);
        return connent;
    }

    @Override
    protected void setDefaultHeader(String referUrl) {
        super.setDefaultHeader(referUrl);
        connent.setRequestProperty(USERAGENT_KEY, ProxyHttpUtil.USERAGENT);
//        connent.setRequestProperty(USERAGENT_KEY, HttpUtil.USERAGENT_IE);
//        connent.setRequestProperty(REFERER_KEY, myReferUrl);
    }

//    @Override
//    protected HttpURLConnection getHttpUrlConnect(String requestUrl, String referUrl) throws Exception {
//
//        if (proxyBean == null) {
//            connent = (HttpURLConnection) new URL(appendUrlToDomain(requestUrl)).openConnection();
////            return null;
//        } else {
//            setProxyRequest();
//
//            log.info("Thread" + Thread.currentThread().getId() + "：取到的代理是:" + proxyBean.getHost() + ":" + proxyBean.getPort());
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyBean.getHost(), proxyBean.getPort()));
//            connent = (HttpURLConnection) new URL(appendUrlToDomain(requestUrl)).openConnection(proxy);
//        }
////        if (proxyBean == null) {
////            return null;
////        }
////        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyBean.getHost(), proxyBean.getPort()));
////        connent = (HttpURLConnection) new URL(appendUrlToDomain(requestUrl)).openConnection(proxy);
//
//        setDefaultHeader(referUrl);
//        return connent;
//    }
//    protected void setProxyRequest() {
//        System.setProperty("proxyType", "4");
//        //通知Java您要通过代理进行连接
//        System.setProperty("proxySet", "true");
//        //指定代理所在的服务器
//        System.setProperty("proxyHost", proxyBean.getHost());
//        //指定代理监听的端口
//        System.setProperty("proxyPort", Integer.toString(proxyBean.getPort()));
//    }
    private final static String myReferUrl = "http://www.wangdaituiguangbao.com/daohang.htm";
    final int sleepTime = 2;
    static String fileString;
    public static String waitObj;
    //<td>(\d+.\d+.\d+.\d+)</td><td>(\d+)</td>
    //<td>(\d+.\d+.\d+.\d+)</td>\s*<td>(\d+)</td>
    public static List<ProxyBean> proxyList = new ArrayList<>();

    //http://www.xici.net.co/
    public static String[] getProxyPageUrls = new String[]{
        "http://www.xici.net.co/nn/",
        "http://www.xici.net.co/nt/",
        "http://www.xici.net.co/wn/",
        "http://www.xici.net.co/wt/",
        "http://www.xici.net.co/qq/"};

    /**
     * 从文件读取代理ip
     */
    public void getProxyFromFile() {
        List<String> proxys = FileUtils.readTxtFileList("有效代理.txt");
//        proxys = ContainerUtils.removeRepeat(proxys);
        if (proxys.size() > 0) {
            String[] infos = proxys.get(0).split("\t");
//            if (infos.length > 4) {
            int i = 0;
            for (String proxyStr : proxys) {
                i++;
                infos = proxyStr.split("\t");
//                log.info(infos[0] + "\t" + Integer.valueOf(infos[1]) + "\t" + infos[3] + "\t" + infos[4]);
                //过滤匿名代理
                if (infos.length > 4) {
                    proxyList.add(new ProxyBean(infos[0], Integer.valueOf(infos[1]), infos[3], infos[4], Proxy.Type.SOCKS));

                } else {
                    proxyList.add(new ProxyBean(infos[0], Integer.valueOf(infos[1])));
                }

//                }
            }
            if (proxyList.size() > 0) {
                return;
            }

        }

        //从文件读取
//        webpageContent = FileUtils.readTxtFile("代理ip.txt");s
        webpageContent = FileUtils.readTxtFile("有效代理.txt");
        log.info("代理文件内容" + webpageContent);
        getProxyFromWebPageContent();
        log.info("");

    }

    /**
     * 从网络读取代理ip
     */
    public void getProxyFromUrl() {

        int maxPage = 0;
        try {
            for (String getProxyPageUrl : getProxyPageUrls) {

                //打开栏目页，计算最大分页数
                //从网页读取
                getProxyPageUrl = getProxyPageUrl.concat(String.valueOf(1));
                doHttpGet(getProxyPageUrl);
                getProxyFromWebPageContent();
                String maxPageStr = RegexUtil.getFirstString(">(\\d+)</a>[^<]*?<a[^>]*?class=[\"']{0,1}next_page", webpageContent);
                if (maxPageStr != null) {
                    maxPage = Integer.valueOf(maxPageStr);
                } else {
                    maxPage = 200;
                }

                ///////////遍历所有分页
                for (int page = 2; page <= maxPage + 2; page++) {
                    getProxyPageUrl = getProxyPageUrl.replaceAll("\\d+", String.valueOf(page));
                    doHttpGet(getProxyPageUrl);
                    getProxyFromWebPageContent();
                }
            }
        } finally {//无论发生什么异常，我都保存一下

            StringBuilder sb = new StringBuilder(2000);
            //写到本地文件
            for (ProxyBean proxyBean : proxyList) {
                sb.append(proxyBean.getHost()).append("\t").append(proxyBean.getPort()).append("\r\n");
            }
            File proxyFile = new File("代理ip.txt");
            FileUtils.saveTxtFile(proxyFile, sb.toString());
        }

    }

    protected void getProxyFromWebPageContent() throws NumberFormatException {
//        Map<String, String> proxyMap = null;
        //(\d+.\d+.\d+.\d+)\t(\d+)
//        if (webpageContent != null && !webpageContent.contains("<td>")) {
//            proxyMap = RegexUtil.getMap("(\\d+.\\d+.\\d+.\\d+)\\t(\\d+)", webpageContent);
//        } else {
//            proxyMap = RegexUtil.getMap("<td>(\\d+.\\d+.\\d+.\\d+)</td>\\s*<td>(\\d+)</td>", webpageContent);
//        }
//        for (String key : proxyMap.keySet()) {
//            proxyList.add(new ProxyBean(key, Integer.valueOf(proxyMap.get(key))));
//        }
        String[][] proxyListTemp = null;
        //由于端口不同也可以，所以用list
        if (webpageContent != null && !webpageContent.contains("<td>") && !webpageContent.contains("代理")) {
            proxyListTemp = RegexUtil.getArray2("(\\d+.\\d+.\\d+.\\d+):(\\d+)", webpageContent);
            if (proxyListTemp == null || proxyListTemp.length == 0) {
                proxyListTemp = RegexUtil.getArray2("(\\d+.\\d+.\\d+.\\d+)\\t(\\d+)", webpageContent);
            }

        } else {
            if (proxyListTemp == null || proxyListTemp.length == 0) {
                proxyListTemp = RegexUtil.getArray2("<td>(\\d+.\\d+.\\d+.\\d+)</td>\\s*<td>[\\s\\S]*?>(\\d+)</td>", webpageContent);
            }
            if (proxyListTemp == null || proxyListTemp.length == 0) {
                proxyListTemp = RegexUtil.getArray2("<td>(\\d+.\\d+.\\d+.\\d+)</td>\\s*<td>[\\s\\S]*?>(\\d+)</td>", webpageContent);
            }
            if (proxyListTemp == null || proxyListTemp.length == 0) {
                proxyListTemp = RegexUtil.getArray2("(\\d+.\\d+.\\d+.\\d+):(\\d+)", webpageContent);
            }
            //(\d+.\d+.\d+.\d+)&nbsp;(\d+)
        }
        if (proxyListTemp == null) {
            return;
        }

        for (String[] proxyText : proxyListTemp) {
//            String[][] proxyMapTemp = RegexUtil.getArray2("(\\d+.\\d+.\\d+.\\d+)\\t(\\d+)", proxyText);
            if (ArrayUtils.isEmpty(proxyText)) {
                continue;
            }
            //过滤验证代理
            if (proxyText.length > 2) {
                continue;
            }

            try {
                proxyList.add(new ProxyBean(proxyText[0], Integer.valueOf(proxyText[1])));
            } catch (Exception e) {
            }
        }

    }
    public static String webPageHtml;

    public void getProxyByNeizhiLiuLanQi() {
        while (true) {

            FutureTask<Map<String, Object>> futureTask = new FutureTask(new Callable<Map<String, Object>>() {
                @Override
                public Map<String, Object> call() throws Exception {

                    return null;
                }
            });
            Platform.runLater(futureTask);
            Commons.sleepSecond(60 * 10);
        }
    }

    public boolean login() {

        if (true) {
//            click(null, null);
            getProxyByNeizhiLiuLanQi();
            return true;
        }

        final int needClickSubUrlNum = 3;

        int successSiteNum = 0;

//        getProxyFromUrl();
        getProxyFromFile();
        //"http://www.wangdaizhijia.com/daohang.html"
        doHttpGet(myReferUrl, myReferUrl, true);
//        List<String> urlList = RegexUtil.getList("<a[^>]*?href=[\"']{0,1}([^\\s\"'>#]{3,})[\"']{0,1}[^>]*?rel=[\"']{0,1}nofollow[\"']{0,1}[^>]*?>", webpageContent);
        List<String> urlList = new ArrayList<>();
        urlList.add("http://www.ijitapu.com/");
//        urlList.add("http://localhost:8084/cloudclickServer/");
//        urlList.add("http://182.254.198.103/api/123.jsp");
//        urlList.add("http://182.254.198.103/");
//        urlList.add("http://www.wangdaituiguangbao.com/index.htm");
//        urlList.add("http://www.xingzuotao.com/");

        ///下面的所有请求都是代理请求
        //可以把遍历做得简单点，但是为了日后考虑，现在分开
        //遍历每个代理，每个代理去打开一批网址
        webpageContent = null;
        //遍历网址
        for (String domainUrl : urlList) {
            proxyBean = proxyList.get(NumberUtils.getRandomNumByMax(proxyList.size()));
//            setProxyRequest();
            FutureTask<Map<String, Object>> futureTask = new FutureTask(new Callable<Map<String, Object>>() {
                @Override
                public Map<String, Object> call() throws Exception {

                    return null;
                }
            });
            Platform.runLater(futureTask);

            if (true) {
                return false;
            }

        }//网站 for end
        log.info("所有网站执行完成，成功数量为" + successSiteNum);
        return false;
    }
    int tryTime = 0;//成功次数
    HomeController homeController = null;

   

    private void saveProxyToFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append(proxyBean.getHost()).append("\t").append(proxyBean.getPort());

        FileUtils.writeToTxtFile(fileName, sb.toString());
    }

    private void getProxyFromNeiZhiliulanqi(String webPageHtml) throws NumberFormatException {
        try {
            webpageContent = webPageHtml;
            getProxyFromWebPageContent();

        } finally {//无论发生什么异常，我都保存一下

            StringBuilder sb = new StringBuilder(2000);
            //写到本地文件
            for (ProxyBean proxyBean : proxyList) {
                sb.append(proxyBean.getHost()).append("\t").append(proxyBean.getPort()).append("\r\n");
            }
            File proxyFile = new File("代理ip.txt");

            FileUtils.writeToFile("D:\\CloudsSaaS\\自动抓取代理ip.txt", sb.toString());

            proxyList.clear();
        }
    }

    public static void main(String[] args) {
//        new UrlClicker().click(null, 100);
    }

    @Override
    protected void updateStatusToDB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void updateAnonymousStatusToDB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void updateSuccessInfoToDB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delSiteEmailRefer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void appendLog(String msg) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                homeController.fx_txtareaLog.appendText(msg + "\r\n");
            }
        });

    }

    public void clearLog() {
        if (homeController.fx_txtareaLog.getText().length() > 1000) {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {

                    homeController.fx_txtareaLog.setText("");

                }
            });
        }
    }
}
