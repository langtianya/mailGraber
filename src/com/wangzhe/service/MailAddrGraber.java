/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.service;

import com.wangzhe.beans.ConfigParam;
import com.wangzhe.beans.ProxyBean;
import com.wangzhe.util.ArrayUtils;
import com.wangzhe.util.Commons;
import com.wangzhe.util.FileUtils;
import com.wangzhe.ui.HomeController;
import com.wangzhe.util.Constants;
import com.wangzhe.util.ContainerUtils;
import static com.wangzhe.util.HttpUtil.USERAGENT_KEY;
import com.wangzhe.util.NumberUtils;
import com.wangzhe.util.ProxyHttpUtil;
import com.wangzhe.util.RegexUtil;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;

/**
 *
 * @author ocq
 */
public abstract class MailAddrGraber extends AbstractWebSiteOperater {

    protected String mailAddrRelatedPath = "log/" + classSimpleName + "邮箱对应公司信息";
    protected String progressFilePath = "log/" + classSimpleName + "进度";

    public static boolean isStop = false;
//    private static MailAddrGraber instance;
    private static final int CLICK_SUB_URL_DEEP = 3;
    /**
     * 报错邮箱的文件名
     */
    protected String fileName = getClass().getSimpleName() + "的邮箱列表";

    public MailAddrGraber() {
    }

//    public static MailAddrGraber getInstance() {
//        if (instance == null) {
//            instance = new MailAddrGraber();
//        }
//        return instance;
//    }
    /**
     * 开始挖掘入口
     *
     * @param cp.getGrabIds()
     */
    public void startGrab(ConfigParam cp) {

        if (cp.getGrabIds() == null || cp.getGrabIds().size() < 1) {
            return;
        }
        if (cp.getGrabUrls() == null || cp.getGrabUrls().length == 0) {
            cp.setGrabUrls(new String[]{"http://www.baidu.com/"});
        }

        log.info("挖掘中....1");
        int successSiteNum = 0;

//        getProxyFromUrl();
//        getProxyFromFile();
        List<String> urlList = new ArrayList<>();
        ///下面的所有请求都是代理请求，可以把遍历做得简单点，但是为了日后考虑，现在分开
        //遍历每个代理，每个代理去打开一批网址
//        for (String domainUrl : urlList) {
        tryTime = 0;//成功次数
        int validProxyNum = 0;
        long time1 = System.currentTimeMillis();
        List<ProxyBean> successProxyList = new ArrayList<>();
        log.info("挖掘中...." + proxyList.size());
         appendLog("开始挖掘.....");
        String[] mailAddrs = getMailAddr(cp);

        //遍历代理
      /*  for (int i = 0; i < proxyList.size(); i++) {
         if (isStop) {
         break;
         }
         if (tryTime > cp.getMaxNum()) {
         break;
         }
         proxyBean = proxyList.get(i);
         log.info("使用第" + (i + 1) + "个代理ip");
         appendLog("使用第" + (i + 1) + "个代理ip");
         doHttpGet2("https://www.alipay.com/");
         if (webpageContent != null) {
         saveProxyToFile("重新检验有效代理");
         }
         if (validProxyNum != 0) {
         successSiteNum++;
         log.info(Arrays.toString(cp.getGrabUrls()) + "域名点击任务完成执行完成,使用了的有效代理数量为" + validProxyNum);
         }
         //        }//网站 for end
         log.info("所有网站执行完成，成功数量为" + successSiteNum);
         return;
         }*/
    }
    /**
     * 获取并保存邮箱地址
     * @param i 循环的下标
     * @param requestUrl 请求地址
     * @param cp 
     */
    protected void getAndSaveMailAddr(int i, final String requestUrl, ConfigParam cp) {
        appendLog("开始分析用户：" + i + "的邮箱地址");
        final List<String> emails = getEmailFromWebpageContent();
        if (emails != null && emails.size() > 0) {
            writeToTxtFile(emails);
            writeToTxtFile(emails.toString() + "---" + requestUrl, mailAddrRelatedPath);
        }
        final int sleepTime = NumberUtils.getRandomNum(cp.getMinSpeed(), cp.getMaxSpeed());
        Commons.sleep(sleepTime);
        appendLog("休眠" + sleepTime + "毫秒秒后继续挖掘.....");
    }
    /**
     *
     * @param cp
     * @return mail address
     */
    protected abstract String[] getMailAddr(ConfigParam cp);

    /**
     * 获取并保存邮箱
     *
     * @return
     */
    protected List<String> getEmailFromWebpageContent() {
        //([^:：@，,\s>\]\"']{2,}@.*.com)//email\\]([^\\[]*)\\[/emai
      //(<[^>(?:email)]*?>)
        return RegexUtil.getList(Constants.MAIL_ADDR_REGEX, webpageContent);
    }
    

    protected void writeToTxtFile(List<String> emails) {
        writeToTxtFile(emails, fileName);
    }

    protected void writeToTxtFile(List<String> emails, String fileName) {
        if (emails != null) {
            final List<String> repeatEmail = ContainerUtils.removeRepeat(emails);
            final String mailAddrMsg = "即将保存的邮箱数量" + repeatEmail.size() + "，邮箱是：" + repeatEmail;
            log.info(mailAddrMsg);
            appendLog(mailAddrMsg);
            FileUtils.writeToTxtFile(fileName, repeatEmail);
        }
    }

    protected void writeToTxtFile(String content, String fileName) {
        if (content != null) {
            FileUtils.writeToTxtFile(fileName, content);
        }
    }

    @Override
    protected boolean addPublishResult(String publishResultUrl, int status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ///////////////////////////////////////////////////////////////////代理点击器实现
    @Override
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

    int tryTime = 0;//成功次数

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
        Platform.runLater(() -> {
            HomeController.instance.fx_txtareaLog.appendText(msg + "\r\n");
        });

    }

    public void clearLog() {
        if (HomeController.instance.fx_txtareaLog.getText().length() > 1000) {
            Platform.runLater(() -> {
                HomeController.instance.fx_txtareaLog.setText("");
            });
        }
    }

    @Override
    public void setSiteUrl(String siteUrl) {
        super.setSiteUrl(siteUrl);
    }

}
