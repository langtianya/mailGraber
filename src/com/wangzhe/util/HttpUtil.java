package com.wangzhe.util;

import com.wangzhe.beans.CaptchaBean;
import com.wangzhe.beans.ProxyBean;
import com.wangzhe.beans.ResponseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;

/**
 * HttpClint 工具类 最后修改时间：2014-07-23 15:40
 *
 * @author ocq
 */
public class HttpUtil {

    private static final Logger log = Logger.getLogger(HttpUtil.class.getName());
    //请求头常量
    public static final String CACHECONTROL = "max-age=0";
    public static final String CONNECTION = "keep-alive";
    public static final String USERAGENT_KEY = "User-Agent";
    public static final String REFERER_KEY = "Referer";
    public static final String COOKIE_KEY = "Cookie";

    public static final String ACCEPT_ENCODING_KEY = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE_KEY = "Accept-Language";
    public static final String ACCEPT_KEY = "Accept";
    public static final String ACCEPT_VALUE = "*/*";
    public static final String ACCEPT_ENCODING_VALUE = "gzip";
    public static final String ACCEPT_LANGUAGE_VALUE = "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3";

    //用户代理
    public static final String USERAGENT_GOOGLE = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";
    //public static final String USERAGENT_GOOGLE = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36";//谷歌
    public static final String USERAGENT_IE = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)";//IE
    public static final String USERAGENT_FIREFOX = "Mozilla/5.0 (Windows NT 6.1; rv:26.0) Gecko/20100101 Firefox/26.0";//火狐
    public static final String USERAGENT_OPERA = "Opera/9.27 (Windows NT 5.2; U; zh-cn) ";//Opera
    public static final String USERAGENT_SAFARI = "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Version/3.1 Safari/525.13";//Safari
    //是否重定向
    private static final boolean ISREDIRECT = true;
    //是否代理
    private static final boolean ISPROXY = false;
    //超时设置
//    private static final int CONNECTTIMEOUT = 1000 * 10;
//    private static final int REQUESTTIMEOUT = 1000 * 10;
//    private static final int SOCKETTIMEOUT = 1000 * 20;
    //ocq 改长点时间，参考xx
    public static final int CONNECT_TIME_OUT = 1000 * 65;
    public static final int REQUEST_TIME_OUT = 1000 * 90;
    public static final int SOCKETT_IME_OUT = 1000 * 155;

    //更短时间的请求，只用于特殊情况
    private static final int FAST_CONNECTTIMEOUT = 4 * 1000;
    private static final int FAST_REQUESTTIMEOUT = FAST_CONNECTTIMEOUT;
    private static final int FAST_SOCKETTIMEOUT = 8 * 1000;

    //默认头信息
    final static BasicHeader[] defaultHeaders = new BasicHeader[]{
        new BasicHeader(HttpUtil.ACCEPT_KEY, ACCEPT_VALUE),
        new BasicHeader(ACCEPT_LANGUAGE_KEY, HttpUtil.ACCEPT_LANGUAGE_VALUE),
        new BasicHeader(HttpUtil.ACCEPT_ENCODING_KEY, HttpUtil.ACCEPT_ENCODING_VALUE),
        new BasicHeader(HttpUtil.USERAGENT_KEY, USERAGENT_FIREFOX), //            
    };

    public static CloseableHttpClient createHttpClient(String reqURL) {
        CloseableHttpClient httpclient = null;
        if (reqURL.toLowerCase().startsWith("https://")) {
            httpclient = createSSLClientDefault();
        } else {
            httpclient = HttpClients.createDefault();
        }
        return httpclient;
    }

    public static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error(e);
        }
        return HttpClients.createDefault();
    }

    public static HttpClientContext createHttpClientContext(CookieStore cookieStore) {
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setCookieStore(cookieStore);
        return localContext;
    }

    public static HttpGet httpGet(String reqURL, Header... headers) {
        return httpGet(reqURL, true, headers);
    }

//    public static HttpGet httpGet(String reqURL, boolean isRedirect, Header... headers) {
//        return httpGet(reqURL, isRedirect, null, headers);
//    }
    public static HttpGet httpGet(String reqURL, boolean isRedirect, Header... headers) {
        HttpGet httpGet = new HttpGet(reqURL);

        if (headers != null && headers.length > 0) {
            //        boolean ishaveAcceptKey=false;
            for (Header header : headers) {
                httpGet.setHeader(header.getName(), header.getValue());
            }
        } else {
            //设置默认头
            for (Header header : defaultHeaders) {
                httpGet.setHeader(header.getName(), header.getValue());
            }
        }
//        RequestConfig.Builder builder = RequestConfig.custom()
//                .setCookieSpec(CookieSpecs.BEST_MATCH)
//                .setConnectTimeout(CONNECT_TIME_OUT)
//                .setConnectionRequestTimeout(REQUEST_TIME_OUT)
//                .setSocketTimeout(SOCKETT_IME_OUT)
//                .setRedirectsEnabled(isRedirect);
//        if (proxyBean != null) {
//            builder.setProxy(new HttpHost(proxyBean.getHost(), proxyBean.getPort()));
//        }
//        RequestConfig requestConfig = builder.build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setConnectTimeout(CONNECT_TIME_OUT)
                .setConnectionRequestTimeout(REQUEST_TIME_OUT)
                .setSocketTimeout(SOCKETT_IME_OUT)
                .setRedirectsEnabled(isRedirect)
                .build();
        httpGet.setConfig(requestConfig);
        return httpGet;
    }

    public static HttpPost httpPost(String reqURL, Header... headers) {
        HttpPost httpPost = new HttpPost(reqURL);
        //httpPost.setHeader("Cache-Control", CACHECONTROL);
        //httpPost.setHeader("Connection", CONNECTION);
        for (Header header : headers) {
            httpPost.setHeader(header.getName(), header.getValue());
        }
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setConnectTimeout(CONNECT_TIME_OUT)
                .setConnectionRequestTimeout(REQUEST_TIME_OUT)
                .setSocketTimeout(SOCKETT_IME_OUT)
                .build();
        httpPost.setConfig(requestConfig);
        return httpPost;
    }

    public static String getCharset(HttpEntity entity) {
        ContentType contentType = ContentType.getOrDefault(entity);
        if (contentType != null) {
            Charset charset = contentType.getCharset();
            if (charset != null) {
                return charset.toString();
            }
        }
        return null;
    }

    public static String getPageCharset(String str) {
        String charset = RegexUtil.getFirstString("content=\"[^\"]*charset=([^\"]*?)\"", str);
        if (StringUtils.isAllEmpty(charset)) {
            charset = RegexUtil.getFirstString("charset=\"([^\"]*?)\"", str);
        }
        if (charset == null) {
            charset = "utf-8";
        }
        return charset;
    }

    public static String getMimeType(HttpEntity entity) {
        ContentType contentType = ContentType.getOrDefault(entity);
        if (contentType != null) {
            return contentType.getMimeType();
        }
        return null;
    }

    public static CloseableHttpResponse httpExecute(CloseableHttpClient httpclient, ProxyBean proxyBean, HttpUriRequest hur) throws IOException {
        return httpExecute(httpclient, proxyBean, hur, null);
    }

    public static CloseableHttpResponse httpExecute(CloseableHttpClient httpclient, ProxyBean proxyBean, HttpUriRequest hur, CookieStore cookieStore) throws IOException {
        CloseableHttpResponse response = null;
        if (proxyBean == null) {
            if (cookieStore == null) {
                response = httpclient.execute(hur);
            } else {
                response = httpclient.execute(hur, createHttpClientContext(cookieStore));
            }
        } else {
            HttpHost httpHost = new HttpHost(proxyBean.getHost(), proxyBean.getPort());
            if (cookieStore == null) {
                response = httpclient.execute(httpHost, hur);
            } else {
                response = httpclient.execute(httpHost, hur, createHttpClientContext(cookieStore));
            }
        }
        return response;
    }

    /**
     * 快速请求，总共只需6秒 没有重构的方法，日后重构
     */
    public static HttpGet fastHttpGet(String reqURL, boolean isRedirect, Header... headers) {
        HttpGet httpGet = new HttpGet(reqURL);
        //httpGet.setHeader("Cache-Control", CACHECONTROL);
        //httpGet.setHeader("Connection", CONNECTION);
        httpGet.setHeader(ACCEPT_KEY, ACCEPT_VALUE);
        //httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        for (Header header : headers) {
            httpGet.setHeader(header.getName(), header.getValue());
        }
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setConnectTimeout(FAST_CONNECTTIMEOUT)
                .setConnectionRequestTimeout(FAST_REQUESTTIMEOUT)
                .setSocketTimeout(FAST_SOCKETTIMEOUT)
                .setRedirectsEnabled(isRedirect)
                .build();
        httpGet.setConfig(requestConfig);
        return httpGet;
    }

    /**
     * 没有重构的方法，日后重构
     */
    public static ResponseBean doFastGetBean(String reqURL, boolean isRedirect, Header... headers) throws Exception {
        ResponseBean rb = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = fastHttpGet(reqURL, isRedirect, headers);
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpResponse response = httpclient.execute(httpGet, createHttpClientContext(cookieStore));
            int status = response.getStatusLine().getStatusCode();
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                //String charset = getCharset(entity);
                //if (charset == null) {
                String charset = getPageCharset(EntityUtils.toString(entity));
                //}
                rb = new ResponseBean();
                rb.setStatusCode(status);
                rb.setContent(EntityUtils.toString(entity, charset));
                rb.setCharset(charset);
                rb.setMimeType(getMimeType(entity));
                rb.setCookie(cookieStore.getCookies());
                Header header = response.getFirstHeader("Location");
                if (header != null) {
                    String location = header.getValue();
                    if (location != null) {
                        rb.setRedirectionUrl(location);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpGet.abort();
            }
        } finally {
            httpclient.close();
        }
        return rb;
    }

    public static String doGet(String reqURL) throws Exception {
        return doGet(reqURL, ISREDIRECT);
    }

    public static String doGet(String reqURL, boolean isRedirect) throws Exception {
        return doGet(reqURL, isRedirect, defaultHeaders);
    }

    public static String doGet(String reqURL, Header... headers) throws Exception {
        return doGet(reqURL, ISREDIRECT, headers);
    }

    public static String doGet(String reqURL, boolean isRedirect, Header... headers) throws Exception {
        return doGet(reqURL, isRedirect, null, headers);
    }

    public static String doGet(String reqURL, ProxyBean proxyBean) throws Exception {
        return doGet(reqURL, ISREDIRECT, proxyBean, defaultHeaders);
    }

    public static String doGet(String reqURL, boolean isRedirect, ProxyBean proxyBean, Header... headers) throws Exception {
        String result = null;
        CloseableHttpClient httpclient = createHttpClient(reqURL);
        try {
            HttpGet httpGet = httpGet(reqURL, isRedirect, headers);
            CloseableHttpResponse response = httpExecute(httpclient, proxyBean, httpGet);//httpclient.execute(httpGet);
            //int status = response.getStatusLine().getStatusCode();
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                //String charset = getCharset(entity);
                //if (charset == null) {
                String charset = getPageCharset(EntityUtils.toString(entity));
                if (charset != null && charset.equals("gbk2312")) {
                    charset = "gb2312";
                }
                //}
                result = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpGet.abort();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

    public static ResponseBean doGetBean(String reqURL) throws Exception {
        return doGetBean(reqURL, defaultHeaders);
    }

    public static ResponseBean doGetBean(String reqURL, Header... headers) throws Exception {
        return doGetBean(reqURL, ISREDIRECT, headers);
    }

    public static ResponseBean doGetBean(String reqURL, boolean isRedirect, Header... headers) throws Exception {
        return doGetBean(reqURL, isRedirect, null, headers);
    }

    public static ResponseBean doGetBean(String reqURL, String hostname, int port) throws Exception {
        return doGetBean(reqURL, ISREDIRECT, null, defaultHeaders);
    }

    public static ResponseBean doGetBean(String reqURL, boolean isRedirect, ProxyBean proxyBean, Header... headers) throws Exception {
        ResponseBean rb = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = httpGet(reqURL, isRedirect, headers);
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpResponse response = httpExecute(httpclient, proxyBean, httpGet, cookieStore);// httpclient.execute(httpGet, createHttpClientContext(cookieStore));
            int status = response.getStatusLine().getStatusCode();
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                //String charset = getCharset(entity);
                //if (charset == null) {
                String charset = getPageCharset(EntityUtils.toString(entity));
                //}
                rb = new ResponseBean();
                rb.setStatusCode(status);
                rb.setContent(EntityUtils.toString(entity, charset));
                rb.setCharset(charset);
                rb.setMimeType(getMimeType(entity));
                rb.setCookie(cookieStore.getCookies());
                Header header = response.getFirstHeader("Location");
                if (header != null) {
                    String location = header.getValue();
                    if (location != null) {
                        rb.setRedirectionUrl(location);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpGet.abort();
            }
        } finally {
            httpclient.close();
        }
        return rb;
    }

    public static String doPost(String reqURL, List<NameValuePair> params, String charset, Header... headers) throws Exception {
        return doPost(reqURL, params, charset, null, headers);
    }

    public static String doPost(String reqURL, List<NameValuePair> params, String charset, ProxyBean proxyBean, Header... headers) throws Exception {
        String result = null;
        CloseableHttpClient httpclient = createHttpClient(reqURL);
        try {
            HttpPost httpPost = httpPost(reqURL, headers);
            httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
            CloseableHttpResponse response = httpExecute(httpclient, proxyBean, httpPost);
            //System.out.println(response.getStatusLine().getStatusCode());
            try {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpPost.abort();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

    public static ResponseBean doPostBean(String reqURL, List<NameValuePair> params, String charset, Header... headers) throws Exception {
        return doPostBean(reqURL, params, charset, null, headers);
    }

    public static ResponseBean doPostBean(String reqURL, List<NameValuePair> params, String charset, ProxyBean proxyBean, Header... headers) throws Exception {
        ResponseBean rb = null;
        CloseableHttpClient httpclient = createHttpClient(reqURL);
        try {
            HttpPost httpPost = httpPost(reqURL, headers);
            CookieStore cookieStore = new BasicCookieStore();
            httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
            CloseableHttpResponse response = httpExecute(httpclient, proxyBean, httpPost, cookieStore);
            int statusCode = response.getStatusLine().getStatusCode();
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    entity = new BufferedHttpEntity(entity);
                }
                charset = getPageCharset(EntityUtils.toString(entity));

                rb = new ResponseBean();
                rb.setStatusCode(statusCode);
                rb.setContent(EntityUtils.toString(entity, charset));
                rb.setCharset(charset);
                rb.setMimeType(getMimeType(entity));
                rb.setCookie(cookieStore.getCookies());
                Header header = response.getFirstHeader("Location");
                if (header != null) {
                    String location = header.getValue();
                    if (location != null) {
                        rb.setRedirectionUrl(location);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpPost.abort();
            }
        } finally {
            httpclient.close();
        }
        return rb;
    }

    public static ResponseBean doPostFileBean(String reqURL, Map<String, ContentBody> params, String charset, Header... headers) throws Exception {
        ResponseBean rb = null;
        CloseableHttpClient httpclient = createHttpClient(reqURL);
        try {
            HttpPost httpPost = httpPost(reqURL, headers);
            CookieStore cookieStore = new BasicCookieStore();
            MultipartEntityBuilder meb = MultipartEntityBuilder.create();
            meb.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for (String key : params.keySet()) {
                meb.addPart(key, params.get(key));
            }
            httpPost.setEntity(meb.build());

            CloseableHttpResponse response = httpclient.execute(httpPost, createHttpClientContext(cookieStore));
            int statusCode = response.getStatusLine().getStatusCode();
            //System.out.println(statusCode);
            try {
                HttpEntity entity = response.getEntity();
                rb = new ResponseBean();
                rb.setStatusCode(statusCode);
                rb.setContent(EntityUtils.toString(entity, charset));
                rb.setCharset(charset);
                rb.setMimeType(getMimeType(entity));
                rb.setCookie(cookieStore.getCookies());
                Header header = response.getFirstHeader("Location");
                if (header != null) {
                    String location = header.getValue();
                    if (location != null) {
                        rb.setRedirectionUrl(location);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpPost.abort();
            }
        } finally {
            httpclient.close();
        }
        return rb;
    }

    public static byte[] doGetImage(String reqURL, Header... headers) throws Exception {
        byte[] b = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = httpGet(reqURL, headers);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                HttpEntity entity = response.getEntity();
                b = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpGet.abort();
            }
        } finally {
            httpclient.close();
        }
        return b;
    }

    public static CaptchaBean doGetImageBean(String reqURL, Header... headers) throws Exception {
        CaptchaBean ib = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = httpGet(reqURL, headers);
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpResponse response = httpclient.execute(httpGet, createHttpClientContext(cookieStore));
            try {
                HttpEntity entity = response.getEntity();
                ib = new CaptchaBean();
                ib.setImageByte(EntityUtils.toByteArray(entity));
                //ib.setImageInputStream(entity.getContent());
                ib.setMimeType(getMimeType(entity));
                ib.setCookies(cookieStore.getCookies());
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpGet.abort();
            }
        } finally {
            httpclient.close();
        }
        return ib;
    }

    public static byte[] doPostImage(String reqURL, List<NameValuePair> params, String charset, Header... headers) throws Exception {
        byte[] b = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = httpPost(reqURL, headers);
            httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                b = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpPost.abort();
            }
        } finally {
            httpclient.close();
        }
        return b;
    }

    public static CaptchaBean doPostImageBean(String reqURL, List<NameValuePair> params, String charset, Header... headers) throws Exception {
        CaptchaBean ib = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = httpPost(reqURL, headers);
            CookieStore cookieStore = new BasicCookieStore();
            httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
            CloseableHttpResponse response = httpclient.execute(httpPost, createHttpClientContext(cookieStore));
            try {
                HttpEntity entity = response.getEntity();
                ib = new CaptchaBean();
                ib.setImageByte(EntityUtils.toByteArray(entity));
                ib.setMimeType(getMimeType(entity));
                ib.setCookies(cookieStore.getCookies());
                //如果不注释有些验证码无法获取，日后其他验证码有问题再说
//              String str = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpPost.abort();
            }
        } finally {
            httpclient.close();
        }
        return ib;
    }

    public static void doSaveImage(String reqURL, String outURL) throws Exception {
        doSaveImage(reqURL, outURL, null);
    }

    public static void doSaveImage(String reqURL, String outURL, String referer) throws Exception {
        doSaveImage(reqURL, outURL, referer, null);
    }

    public static void doSaveImage(String reqURL, String outURL, String referer, String cookie) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(reqURL);
            if (StringUtils.allNotEmpty(referer)) {
                httpGet.setHeader("Referer", referer);
            }
            if (StringUtils.allNotEmpty(cookie)) {
                httpGet.setHeader(HttpUtil.COOKIE_KEY, cookie);
            }
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpResponse response = httpclient.execute(httpGet, createHttpClientContext(cookieStore));
            try {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                OutputStream out = new FileOutputStream(new File(outURL));
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                inputStream.close();
                EntityUtils.consume(entity);
            } finally {
                response.close();
                httpGet.abort();
            }
        } finally {
            httpclient.close();
        }
    }

    public static boolean isillegalPostParameter(Object forms, String actionURL) {
        if (actionURL == null || actionURL.length() == 0 || forms == null) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
//        //使用例子1
//        String gets = HttpUtil.doGet("http://www.baidu.com");
//        //使用例子2
//        ResponseBean rb = HttpUtil.doGetBean("http://www.baidu.com",
//                new BasicHeader(HttpUtil.USERAGENT_KEY, HttpUtil.USERAGENT_GOOGLE),
//                new BasicHeader(HttpUtil.REFERER_KEY, "http://www.baidu.com"),
//                new BasicHeader(HttpUtil.COOKIE_KEY, "BAIDUID=EB8F3434C7ACBA9C75C40F609DBA6B91:FG=1;"));
//        //使用例子3
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));
//        String posts = HttpUtil.doPost("http://www.baidu.com", nvps, "utf-8",
//                new BasicHeader(HttpUtil.USERAGENT_KEY, HttpUtil.USERAGENT_GOOGLE),
//                new BasicHeader(HttpUtil.REFERER_KEY, "http://www.baidu.com"));
//        //使用例子4
//        HttpUtil.doPostBean("http://www.baidu.com", nvps, "utf-8",
//                new BasicHeader(HttpUtil.USERAGENT_KEY, HttpUtil.USERAGENT_GOOGLE),
//                new BasicHeader(HttpUtil.REFERER_KEY, "http://www.baidu.com"));
        //System.out.println(HttpUtil.doGetBean("http://www.baidu.com", true, "120.198.243.111", 84));
    }
}
//    public static String getCharsetXXXX(CloseableHttpResponse response) {
//        String charset = "ISO_8859-1";
//        Header header = response.getEntity().getContentType();
//        if (header != null) {
//            String s = header.getValue();
//            if (RegexUtil.ifMatcher("(charset)\\s?=\\s?(utf-?8)", s)) {
//                charset = "utf-8";
//            } else if (RegexUtil.ifMatcher("(charset)\\s?=\\s?(gbk)", s)) {
//                charset = "gbk";
//            } else if (RegexUtil.ifMatcher("(charset)\\s?=\\s?(gb2312)", s)) {
//                charset = "gb2312";
//            }
//        }
//        return charset;
//    }
//    public static String getLastRedirectUrl(String reqURL, CloseableHttpClient httpclient, String referer) {
//        String result = "";
//        HttpGet httpGet = null;
//        CloseableHttpResponse response = null;
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setRedirectsEnabled(false)
//                .build();
//        int i = 0;
//        while (true) {
//            httpGet = new HttpGet(reqURL);
//
//            httpGet.setHeader("Referer", referer);
//            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
//            httpGet.setConfig(requestConfig);
//
//            try {
//                response = httpclient.execute(httpGet);
//                if (response.getStatusLine().getStatusCode() >= 300 && response.getStatusLine().getStatusCode() <= 307) {
//                    reqURL = response.getFirstHeader("Location").getValue();
//                    try {
//                        URI u = new URI(reqURL);
//                        reqURL = u.getHost() == null ? httpGet.getURI().getScheme() + "://" + httpGet.getURI().getHost() + "/" + reqURL : reqURL;
//                    } catch (URISyntaxException ex) {
//                        log.error(ex);
//                        break;
//                    }
//                } else {
//                    result = i == 0 ? "" : reqURL;
//                    response.close();
//                    break;
//                }
//            } catch (IOException ex) {
//                log.error(ex);
//                break;
//            } finally {
//                httpGet.abort();
//            }
//            i++;
//        }
//        return result;
//    }
//
//    public static String getRedirectUrl(String reqURL, CloseableHttpClient httpclient, String referer) {
//        String result = "";
//        HttpGet httpGet = new HttpGet(reqURL);
//        httpGet.setHeader("Referer", referer);
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
//
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setRedirectsEnabled(false)
//                .build();
//        httpGet.setConfig(requestConfig);
//        try {
//            CloseableHttpResponse response = httpclient.execute(httpGet);
//            if (response.getStatusLine().getStatusCode() >= 300 && response.getStatusLine().getStatusCode() <= 307) {
//                result = response.getFirstHeader("Location").getValue();
//            }
//            response.close();
//        } catch (IOException ex) {
//            System.out.println(ex);
//        } finally {
//            httpGet.abort();
//        }
//
//        return result;
//    }
//
//    public static boolean isRedirect(String reqURL, CloseableHttpClient httpclient, String referer) {
//        HttpGet httpGet = new HttpGet(reqURL);
//        httpGet.setHeader("Referer", referer);
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
//
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setRedirectsEnabled(false)
//                .build();
//        httpGet.setConfig(requestConfig);
//        try {
//            CloseableHttpResponse response = httpclient.execute(httpGet);
//            if (response.getStatusLine().getStatusCode() >= 300 && response.getStatusLine().getStatusCode() <= 307) {
//                return true;
//            }
//            response.close();
//        } catch (IOException ex) {
//            System.out.println(ex);
//        } finally {
//            httpGet.abort();
//        }
//
//        return false;
//    }

