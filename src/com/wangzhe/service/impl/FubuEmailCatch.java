/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.service.impl;

import com.wangzhe.beans.ProxyBean;
import com.wangzhe.service.AbstractWebSiteOperater;
import com.wangzhe.ui.HomeController;
import com.wangzhe.util.ArrayUtils;
import com.wangzhe.util.Commons;
import com.wangzhe.util.ContainerUtils;
import com.wangzhe.util.FileUtils;
import com.wangzhe.util.HtmlUtils;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class FubuEmailCatch extends AbstractWebSiteOperater {

    private Logger log = Logger.getLogger(FubuEmailCatch.class.getName());
    public static boolean isStop = false;

    public FubuEmailCatch() {

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
    HomeController delegate = null;

    public boolean click() {
        String referUrlString = null;

        for (int i = 0; i < 70; i++) {
            final String requestUrl = "http://z.fobshanghai.com/cse/search?q=%40*.com&p=" + i + "&s=8343853458128089936&entry=1&cid=32667";

            doHttpGet2(requestUrl, referUrlString != null ? referUrlString : "http://z.fobshanghai.com/cse/search");
            referUrlString = requestUrl;

            if (webpageContent == null) {
                continue;
            }
            //([^:：@，,\s\]]*@.*.<em>com)
            List<String> urlList = RegexUtil.getList("title\"\\s*href=\"([^\"]*)", webpageContent);
            getAndSaveEmail();
            log.info("正则采集第" + (i + 1) + "页");
            for (int j = 1; j <= urlList.size(); j++) {

                log.info("正则采集第" + (i + 1) + "页,第" + j + "条");
                Commons.sleepSecond(1);
                doHttpGet2(urlList.get(j), referUrlString != null ? referUrlString : "http://z.fobshanghai.com/cse/search");
                if (webpageContent == null) {
                    continue;
                }
                getAndSaveEmail();
            }
        }

        return false;
    }

    protected void getAndSaveEmail() {
        //([^:：@，,\s>\]\"']{2,}@.*.com)//email\\]([^\\[]*)\\[/emai
        webpageContent = webpageContent.replaceAll("<em>", "");//(<[^>(?:email)]*?>)
        webpageContent = webpageContent.replaceAll("</em>", "");
        List<String> emails = RegexUtil.getList("([a-z0-9A-Z_]{2,}@[^>]*.com)", webpageContent);
        if (emails != null) {
            final List<String> repeatEmail = ContainerUtils.removeRepeat(emails);
            log.info("获得邮箱数量" + repeatEmail.size() + "邮箱是：" + repeatEmail);
            FileUtils.writeToTxtFile("物流邮箱", repeatEmail);
        }

    }

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
        Locale[] localeList = Locale.getAvailableLocales();  
for(int i=0; i<localeList.length; i++) {  
System.out.println(localeList[i].getDisplayCountry()+"="+localeList[i].getCountry() + " " + localeList[i].getDisplayLanguage() + "=" +localeList[i].getLanguage());  

}  
//        new FubuEmailCatch().click();
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
                delegate.fx_txtareaLog.appendText(msg + "\r\n");
            }
        });

    }

    public void clearLog() {
        if (delegate.fx_txtareaLog.getText().length() > 1000) {
            Platform.runLater(new Runnable() {

                @Override
                public void run() {

                    delegate.fx_txtareaLog.setText("");

                }
            });
        }
    }
}
