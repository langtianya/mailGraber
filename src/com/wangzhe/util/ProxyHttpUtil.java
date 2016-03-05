package com.wangzhe.util;

import com.wangzhe.beans.ProxyBean;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class ProxyHttpUtil {

    // 连接超时 10s
    private static final int connectTimeout = 1000 * 10;
    // 读取超时 20s
    private static final int socketTimeout = 1000 * 20;
    // 请求超时 10s
    private static final int requestTimeout = 1000 * 10;
    // 是否启用代理
    private static boolean isProxy;
    //搜索引擎

//    private static String SE="baidu";
    private static String SE = null;
    // 是否重定向
    private static boolean isRedirect;

    private static final Logger log = Logger.getLogger(ProxyHttpUtil.class.getName());
    public static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36";

    public static Map<String, Object> doHttpGet(String reqURL, boolean isProxy, int proxyId) {

//        SE=str;
        Map<String, Object> map = new HashMap<>();
        // 代理主机ip
        String proxyHost = null;
        // 代理端口
        int proxyPort = 0;
        // 代理ip存放id
        //int proxyId = 0;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        boolean flag = ConfigManager.getProperties("engineset").equalsIgnoreCase("google");
        if (flag) {
            SE = "google";
        } else {
            SE = "baidu";
        }

        try {
            //PrintWriter pw = new PrintWriter(new File("d:/1234.txt"));
            HttpGet httpGet = new HttpGet(reqURL);
            httpGet.setHeader("User-Agent", USERAGENT);
            if (SE.equalsIgnoreCase("baidu")) {
                httpGet.setHeader("Referer", "http://www.baidu.com/");
            } else {
                httpGet.setHeader("Referer", "https://www.google.com/");
            }

            RequestConfig requestConfig;
            //是否代理
            if (isProxy) {
                ProxyBean proxyBean =null;
                if (proxyBean != null) {
                    proxyId = proxyBean.getId();
                    proxyHost = proxyBean.getHost();
                    proxyPort = proxyBean.getPort();
                    log.info("Thread" + Thread.currentThread().getId() + "：取到的代理是:" + proxyHost + ":" + proxyPort);
//                    System.out.println("代理的相关信息："+proxyBean.getHost()+"----------"+proxyBean.getPort());
                } else {
                    log.info("Thread" + Thread.currentThread().getId() + "：取到的代理是:null");
                    map.put("result", "proxyipisnull");
                    map.put("proxyId", proxyId);
                    return map;
                }
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                requestConfig = RequestConfig.custom()
                        .setProxy(proxy)
                        .setConnectTimeout(connectTimeout)
                        .setConnectionRequestTimeout(requestTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setRedirectsEnabled(false)
                        .build();
            } else {
                requestConfig = RequestConfig.custom()
                        .setConnectTimeout(connectTimeout)
                        .setConnectionRequestTimeout(requestTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setRedirectsEnabled(false)
                        .build();
            }
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("Thread" + Thread.currentThread().getId() + "：【状态码】" + statusCode);
            try {
                if (statusCode >= 300 && statusCode <= 309) {
                    log.info("Thread" + Thread.currentThread().getId() + "：【Location】" + response.getFirstHeader("Location").getValue());
                    map.put("result", response.getFirstHeader("Location").getValue());
                    map.put("proxyId", proxyId);
                    return map;
                }
                if (statusCode >= 400 && statusCode <= 409) {
                    map.put("result", "proxyipis404");
                    map.put("proxyId", proxyId);
                    return map;
                }
                if (statusCode >= 200 && statusCode <= 209) {
                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity, "utf-8");
                    map.put("result", result);
                    map.put("proxyId", proxyId);
                    //pw.append(result + "\r\n");
                    //pw.flush();
                    EntityUtils.consume(entity);
                } else {
                    map.put("result", "proxyipis404");
                    map.put("proxyId", proxyId);
                    return map;
                }

            } catch (IOException | ParseException pex) {
                log.error("Thread" + Thread.currentThread().getId() + "：【" + reqURL + "】uex:" + pex);
                map.put("result", "proxyipisexception");
                map.put("proxyId", proxyId);
                return map;
            } finally {
                response.close();
            }
        } catch (UnknownHostException uex) {//主机名解析时通常出现的暂时错误，它意味着本地服务器没有从权威服务器上收到响应。
            log.error("Thread" + Thread.currentThread().getId() + "：【" + reqURL + "】uex:" + uex);
            map.put("result", "proxyipisexception");
            map.put("proxyId", proxyId);
            return map;
        } catch (SocketException sex) {//连接超时
            log.error("Thread" + Thread.currentThread().getId() + "：【" + reqURL + "】sex:" + sex);
            map.put("result", "proxyipisexception");
            map.put("proxyId", proxyId);
            return map;
        } catch (IllegalStateException ilse) {//缺少http
            log.error("Thread" + Thread.currentThread().getId() + "：【" + reqURL + "】ilse" + ilse);
            map.put("result", "proxyipisexception");
            map.put("proxyId", proxyId);
            return map;
        } catch (IOException iex) {
            log.error("Thread" + Thread.currentThread().getId() + "：【" + reqURL + "】iex:" + iex);
            map.put("result", "proxyipisexception");
            map.put("proxyId", proxyId);
            return map;
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                log.error("Thread" + Thread.currentThread().getId() + "：关闭失败：" + ex);
            }
        }
        return map;
    }

    public static String doHttpGet(String reqURL, String referUrl,boolean  isAutoDoLocation,ProxyBean proxyBean) {

        if (reqURL == null || proxyBean == null) {//现在的需求是必须要用代理，日后可改造成下面判断
            return null;
        }

//        SE=str;
        Map<String, Object> map = new HashMap<>();
        // 代理主机ip
        String proxyHost = proxyBean.getHost();
        // 代理端口
        int proxyPort = proxyBean.getPort();

        CloseableHttpClient httpclient = HttpClients.createDefault();

        SE = proxyBean.getServerType();
        CloseableHttpResponse response = null;
        try {
            //PrintWriter pw = new PrintWriter(new File("d:/1234.txt"));
            HttpGet httpGet = new HttpGet(reqURL);
            httpGet.setHeader("User-Agent", USERAGENT);

            if (SE == null) {
                httpGet.setHeader("Referer", referUrl);
            } else if (SE.equalsIgnoreCase("baidu")) {
                httpGet.setHeader("Referer", "http://www.baidu.com/");
            } else {
                httpGet.setHeader("Referer", "https://www.google.com/");
            }

            RequestConfig requestConfig;
            //是否代理
            if (proxyBean != null) {
                log.info("Thread" + Thread.currentThread().getId() + "：取到的代理是:" + proxyHost + ":" + proxyPort);
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                requestConfig = RequestConfig.custom()
                        .setProxy(proxy)
                        .setConnectTimeout(connectTimeout)
                        .setConnectionRequestTimeout(requestTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setRedirectsEnabled(isAutoDoLocation)
                        .build();
            } else {
                requestConfig = RequestConfig.custom()
                        .setConnectTimeout(connectTimeout)
                        .setConnectionRequestTimeout(requestTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setRedirectsEnabled(isAutoDoLocation)
                        .build();
            }

            httpGet.setConfig(requestConfig);
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("Thread" + Thread.currentThread().getId() + "：【状态码】" + statusCode);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            return result;

        } catch (UnknownHostException uex) {//主机名解析时通常出现的暂时错误，它意味着本地服务器没有从权威服务器上收到响应。
            return null;
        } catch (SocketException sex) {//连接超时
            return null;
        } catch (IllegalStateException ilse) {//缺少http
            return null;
        } catch (IOException iex) {
            return null;
        } finally {
            try {
                if (response!=null) {
                     response.close();
                }
               
                httpclient.close();
            } catch (IOException ex) {
                log.error("Thread" + Thread.currentThread().getId() + "：关闭失败：" + ex);
            }
        }
//        return null;
    }
}
