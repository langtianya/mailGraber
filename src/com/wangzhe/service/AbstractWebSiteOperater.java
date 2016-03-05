/**
 * *********************************************************************
 * Module: WebSiteAbstract.java Author: ocq Purpose: Defines the Class
 * WebSiteAbstract
 * *********************************************************************
 */
package com.wangzhe.service;

import com.wangzhe.beans.CaptchaBean;
import com.wangzhe.beans.ProxyBean;
import com.wangzhe.beans.QuestionBean;
import com.wangzhe.util.ContainerUtils;
import com.wangzhe.beans.ResponseBean;
import com.wangzhe.beans.SiteBean;
import com.wangzhe.ui.BasicAuthenticator;
import com.wangzhe.ui.Cookies;
import com.wangzhe.util.RegexUtil;
import com.wangzhe.util.HtmlUtils;
import com.wangzhe.util.StringUtils;
import com.wangzhe.util.UrlUtils;
import com.wangzhe.util.ArrayUtils;
import com.wangzhe.util.ByteUtils;
import com.wangzhe.util.Commons;
import com.wangzhe.util.Constants;
import static com.wangzhe.util.Constants.DEFAULT_ENCODE;
import com.wangzhe.util.CookieUtil;
import com.wangzhe.util.HttpUtil;
import static com.wangzhe.util.HttpUtil.ACCEPT_ENCODING_KEY;
import static com.wangzhe.util.HttpUtil.ACCEPT_ENCODING_VALUE;
import static com.wangzhe.util.HttpUtil.ACCEPT_KEY;
import static com.wangzhe.util.HttpUtil.ACCEPT_LANGUAGE_KEY;
import static com.wangzhe.util.HttpUtil.ACCEPT_LANGUAGE_VALUE;
import static com.wangzhe.util.HttpUtil.ACCEPT_VALUE;
import static com.wangzhe.util.HttpUtil.CONNECT_TIME_OUT;
import static com.wangzhe.util.HttpUtil.COOKIE_KEY;
import static com.wangzhe.util.HttpUtil.REFERER_KEY;
import static com.wangzhe.util.HttpUtil.REQUEST_TIME_OUT;
import static com.wangzhe.util.HttpUtil.USERAGENT_FIREFOX;
import static com.wangzhe.util.HttpUtil.USERAGENT_KEY;
import static com.wangzhe.util.HttpUtil.doFastGetBean;
import static com.wangzhe.util.HttpUtil.doGetBean;
import static com.wangzhe.util.HttpUtil.doPostBean;
import com.wangzhe.util.ConfigManager;
import com.wangzhe.util.URLCoderUtil;
import com.wangzhe.util.WebsiteUtils;
import com.wangzhe.beans.Forms;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

/**
 * 网站操作类根抽象类
 *
 * @author ocq
 */
public abstract class AbstractWebSiteOperater {//extends Thread

    protected Logger log = Logger.getLogger(getClass().getName());

    /**
     * 默认值，一个网站对应一个邮箱，如果一个邮箱可以用在一个网站多次，那么设置为true
     */
    protected boolean isRegReuseEmail = false;

    //默认系统会自动判断是否需要再次尝试执行多一次，但是如果特殊要求可以自己设置这个值，true代表需要再次尝试
    protected boolean tryAgain = false;
    private boolean isSuccess = false;
    //如果实现类不需要邮箱，请在初始化的时候把该值设置成false，否则如果该值为true就会把邮箱与网站的对应关系写进数据，浪费资源
    protected boolean isNeedEmail = true;
    //响应bean
    protected ResponseBean rb;
    //当前网站分组的名字
    protected String siteGroupName;
    //执行的是注册发布还是发布结果，还是回帖库的操作
    protected int operateType;
//    /**当前操作网址的域名，一般*/
//     protected String domainUrl;
    /**
     * 当前的文章实体
     */
    //表单内容
    protected String formString;

    //用于更新对应表的dao
//    protected static IBaseDao dao;
//    protected static IBaseDao sitedao;
//    protected static IBaseDao sitedao1;
//     protected static IBaseDao siteda1o1;
    /**
     * 当前的文章内容
     */
    protected String articleBody;
    /**
     * 没修改过的文章标题
     */
    protected String articleTitle;
    /**
     * 当前已经尝试了多少次了，比如注册遇到验证码，或者用户名已经使用的情况，那么就会再次尝试，这个变量就是记录当前是第几次尝试了
     */
    protected int currentTryTime;
    //临时的，以后删除
    public static int counter = 0;

    protected Cookies cookies = new Cookies();

    //需要操作的网站对象
    protected SiteBean site;
    protected ProxyBean proxyBean;
//   protected WebSiteAccount account;
    // 当前的网页页面编码
    protected String pageCharset = DEFAULT_ENCODE;
    private boolean verify_cancle = false;
    private boolean verifyQuestion_cancle = false;
    /**
     * 当前使用的邮箱地址
     */
    protected String email;
    protected String emailPassword;
//    private AbstractTaskCommand taskCommand;
    /**
     * 当前执行的任务类型，是发布还是回复还是注册...
     */
    protected String taskType;
    /**
     * 验证码
     */
    protected CaptchaBean captchaBean = new CaptchaBean();
    /**
     * 验证问题
     */
    protected QuestionBean questionBean = new QuestionBean();
//    protected IQuestionAble questionVerifyer;
//    protected ICaptchaVerifyAble captchaVerifyer;

    //map形式的分类<分类value，分类name>
    protected Map<String, String> categorysMap;
    //list形式的分类<分类value>
    protected List<String> categorysList;

//     //当前发布的关键词列表
//    protected String[][] keywords;
    //验证问题
    protected String verifyQuestion;
    //验证问题答案
    protected String verifyAnswer;

    //谷歌验证码的recaptcha_challenge_field
    protected String recaptcha_challenge_field;

//    protected String cookie_str;
    // 用于注册网站的用户名
    protected String regUsername;
    // 用于注册网站的密码
    protected String regPassword;
    // 用于注册网站的用户名
    protected String loginUsername;
    // 用于注册网站的密码
    protected String loginPassword;
    // HTTP会话cookies
//    protected Map<String, String> cookiesMap = new HashMap();
    // 打开url后加载的内容
    protected String webpageContent = null;

    //当前操作的网址地址
    protected String siteUrl = null;
    // 验证码Url
    protected String captchaUrl = null;
    //操作出现的错误信息
    protected String errorMsg = null;
    //错误状态码
//    protected int errorStatusId;
    protected String status;
    //上一次的状态
    protected String lastTimeStatus;

    // 注册页面地址
    protected String registerUrl;
    // 登录页面地址
    protected String loginUrl;
    // 文章发布记录url
    protected String publishRecordUrl;
    // 发布文章所在页面
    protected String publishUrl;
    //回复历史记录
    protected String replyRecordUrl;
    // 回复帖子所在页面
    protected String replyUrl;
    //提交表单地址 即post请求的action
    protected String formActionUrl;
    //连接重定向跳转地址可能是302也可能是301，也就是就是请求头中的location
    protected String redirectionUrl;

    protected List<String> getErrorReguExps = new ArrayList(10);
    /**
     * 获取注册是否成功的正则列表
     */
    protected List<String> isRegSuccessReguExps = new ArrayList(10);
    /**
     * 获注册后账号是否需要激活的正则列表
     */
    protected List<String> isRegWaitActiveExps = new ArrayList(10);
    /**
     * 获取登录是否成功 的正则列表
     */
    protected List<String> isLoginSuccessReguExps = new ArrayList(10);
    /**
     * 获取账号是否没有激活 的正则列表
     */
    protected List<String> isLoginWaitActiveExps = new ArrayList(10);
    /**
     * 获取 发布是否成功的正则列表
     */
    protected List<String> isPublishSuccessReguExps = new ArrayList(10);
    /**
     * 获取 判断回复是否成功的正则列表
     */
    protected List<String> isReplySuccessReguExps = new ArrayList(10);
    /**
     * 获取发布/回复成功后的文章地址的正则列表
     */
    protected List<String> isRecordedAuditReguExps = new ArrayList(10);
    /**
     * 获取验证问码地址的正则列表
     */
    protected List<String> getCaptchUrlReguExps = new ArrayList(10);
    /**
     * 获取验证问题的正则列表
     */
    protected List<String> getVerifyQuestionReguExps = new ArrayList(10);

    //服务器响应状态码
    protected int responseCode;

    /**
     * 如果上一个请求打开页面失败则为true
     */
    //protected boolean isOpenUrlFail = false;
    public SiteBean getSite() {
        return site;
    }

    protected AbstractWebSiteOperater() {
        this(null);
    }

    /**
     * @param site
     */
    protected AbstractWebSiteOperater(SiteBean site) {
        init();
        this.site = site;
    }

    /**
     * 初始化
     */
    private void init() {
        getErrorReguExps.add("=[\"']{0,1}error[\"']{0,1}[^>]*?>(.*?)</");
        getErrorReguExps.add("=[\"']{0,1}error message.*?[\"']{0,1}[^>]*?>(.*?)</");
        isRegSuccessReguExps.add("注册成功");
        isLoginSuccessReguExps.add("登录成功");
        isLoginSuccessReguExps.add("register success");
        isPublishSuccessReguExps.add("发布成功");
        reInit();
    }

    /**
     * 初始化登陆信息
     */
    public void initLoginInfo() {
        if (currentTryTime == 1) {
//        if (loginUsername == null && loginUsername.length() < 1) {
            loginUsername = site.getUsername();
            loginPassword = site.getPassword();
        }
    }

    /**
     * 重新初始化
     */
    public void reInit() {
        getRandomUsername();
        getRandomPassword();
        //操作出现的错误信息
        errorMsg = null;
        //错误状态码
//        status = null;
        // 文章发布记录url
        publishRecordUrl = null;
        //回复历史记录
        replyRecordUrl = null;
    }

    private String getZhOrEnUsername() {
        if ((status != null) && (status.equals(Constants.STATUSTIP_YHMZWZC) || status.equals(Constants.STATUSTIP_YHMFF))) {
            return regUsername = StringUtils.getRandomJianHan(6);
        } else {
            return regUsername = StringUtils.getRandomUsername(9).toLowerCase();
        }
    }

    private String changePassword() {
        if ((status != null) && (status.equals(Constants.STATUSTIP_MMTR))) {
            //加了特殊符号
            return regPassword = StringUtils.getRandomPassword().concat("$");
        } else {
            //全部小写，可能有数字
            return regPassword = StringUtils.getRandomPassword().toLowerCase();
        }
    }

    protected String getRandomUsername() {
        if (isNotTryOne()) {
            return getZhOrEnUsername();
        }
//        if (currentTryTime == 2 && (StringUtils.allNotEmpty(status))
//                && (status.equals(Constants.STATUSTIP_YHMZWZC) || status.equals(Constants.STATUSTIP_YHMFF))) {
//            return regUsername = StringUtils.getRandomJianHan(6);
//        } else if (currentTryTime == 3) {
//            return regUsername = StringUtils.getRandomUsername(9).toLowerCase();
//        }
//        regUsername = ConfigManager.getGeneralUsername();
        if (StringUtils.isOneEmpty(regUsername)) {
            regUsername = StringUtils.getRandomUsername();
        }
        return regUsername;
    }

    protected String getRandomPassword() {
        if (isNotTryOne()) {
            return changePassword();
        }
//        regPassword = ConfigManager.getGeneralPassword();
        if (StringUtils.isOneEmpty(regPassword)) {
            //肯定包含小写字母+数字+大写字母
            regPassword = StringUtils.getRandomPassword();
        }
        return regPassword;
    }

    protected boolean isNotTryOne() {
        return currentTryTime == 1 || currentTryTime == 2;
    }

    protected String getRandomEmail() {
        synchronized (this) {

        }
        //用来取邮箱服务器的邮箱，目前还不支持所以注释
//        if (StringUtils.isEmpty(email)) {
//            email = ConfigManager.getGeneralEmail();
//        }
//        if (StringUtils.isEmpty(email)) {
//            email = StringUtils.getRandomUsername(9).concat("@gmail.com");
//        }
//        System.err.println(counter+++"用户名："+regUsername);
//         captchaVerifyer = new CaptchaVerify();
//        questionVerifyer = new QuestionVerify();
        return email;
    }

//    {
//        MailBean mb = ConfigManager.getRandomEmail();
//        return mb;
//    }
    /**
     * 通过正则表达式获取到请求后的错误信息
     *
     * @return
     */
    protected String getErrorMsgByReguExps() {
        if (getErrorReguExps.size() < 1 || StringUtils.isAllEmpty(webpageContent)) {
            return null;
        }
        this.errorMsg = ContainerUtils.getMsgByReguExps(getErrorReguExps, webpageContent);
        if (StringUtils.isOneEmpty(errorMsg)) {
            errorMsg = HtmlUtils.htmlFormat2Txt(webpageContent);
//            if (!StringUtils.isOneEmpty(errorMsg) && errorMsg.length() > 500) {
//                errorMsg = errorMsg.substring(0, 500);
//            }
        }
        return errorMsg;
    }

    /**
     * 从数据库获取之前保存的可发分类，获取一个随机的可用分类
     *
     * @return
     */
    protected String getPubableCateFromDB() {
        return WebsiteUtils.getRandomCategory(site);
    }

    /**
     * 注册是否成功
     *
     * @return
     */
    protected boolean isRegSuccess() {
        if (isRegSuccessReguExps.size() < 1) {
            return false;
        }
        if (ContainerUtils.isContainsOne(isRegSuccessReguExps, webpageContent)) {
            return true;
        }
        return false;
    }

    /**
     * 账号是否需要等待激活
     *
     * @return
     */
    protected boolean isRegWaitActive() {
        if (isRegWaitActiveExps.size() < 1) {
            return false;
        }
//        if (!StringUtils.isOneEmpty(ContainerUtils.getMsgByReguExps(isRegWaitActiveExps, webpageContent))) {
        if (ContainerUtils.isContainsOne(isRegWaitActiveExps, webpageContent)) {
            status = Constants.STATUSTIP_ZHDDJH;
            return true;
        }
        return false;
    }

    /**
     * 登录是否成功
     *
     * @return
     */
    protected boolean isLoginSuccess() {
        if (isLoginSuccessReguExps.size() < 1) {
            return false;
        }
//        if (!StringUtils.isOneEmpty(ContainerUtils.getMsgByReguExps(isLoginSuccessReguExps, webpageContent))) {
        if (ContainerUtils.isContainsOne(isLoginSuccessReguExps, webpageContent)) {
            return true;
        }
        return false;
    }

    /**
     * 发布是否成功
     *
     * @return
     */
    protected boolean isPublishSuccess() {
        if (isPublishSuccessReguExps.size() < 1) {
            return false;
        }
//        if (!StringUtils.isOneEmpty(ContainerUtils.getMsgByReguExps(isPublishSuccessReguExps, webpageContent))) {
        if (ContainerUtils.isContainsOne(isPublishSuccessReguExps, webpageContent)) {
            return true;
        }
        return false;
    }

    /**
     * 回复是否成功
     *
     * @return
     */
    protected boolean isReplySuccess() {
        if (isReplySuccessReguExps.size() < 1 || StringUtils.isOneEmpty(webpageContent)) {
            return false;
        }
//        if (!StringUtils.isOneEmpty(ContainerUtils.getMsgByReguExps(isReplySuccessReguExps, webpageContent))) {
        if (ContainerUtils.isContainsOne(isReplySuccessReguExps, webpageContent)) {
            return true;
        }
        return false;
    }

    /**
     * 获取验证码地址
     *
     * @return
     */
    protected String getCaptchUrl() {
        this.captchaUrl = ContainerUtils.getMsgByReguExps(getCaptchUrlReguExps, webpageContent);
        if (StringUtils.allNotEmpty(captchaUrl)) {
            captchaUrl = HtmlUtils.replaceTagAmp(captchaUrl);
        }
        return captchaUrl;
    }

    /**
     * 获取验证问题
     *
     * @return
     */
    protected String getVerifyQuestionByRegex() {
        if (verifyQuestion != null) {
            return verifyQuestion;
        }
        this.verifyQuestion = ContainerUtils.getMsgByReguExps(getVerifyQuestionReguExps, webpageContent);
        if (!StringUtils.isOneEmpty(verifyQuestion)) {
            verifyQuestion = HtmlUtils.htmlFormat2Txt(verifyQuestion);
        }
        if (!StringUtils.isOneEmpty(verifyQuestion) && verifyQuestion.length() > 256) {
            verifyQuestion = verifyQuestion.substring(0, 256);
        }
        return verifyQuestion;
    }

    protected String getVerifyQuestion() {
        return verifyQuestion;
    }

    /**
     * 读取服务端返回的cookies
     *
     * @param rb
     * @return
     */
    protected void readCookies() {
        cookies.add(rb.getCookie());
    }
//     protected StringBuilder readCookies(ResponseBean rb) {
//        StringBuilder cookieBuilder = new StringBuilder();
//        return  NormUtil.getCookie(rb.getCookie(), cookieBuilder);
//    }

    /**
     * 上一个连接请求是否成功打开页面
     *
     * @param rb ResponseBean 请求bean
     * @return
     */
    protected boolean isOpenUrlFail() {
        return isOpenUrlFail(webpageContent);
    }

    protected boolean isOpenUrlFail(String tempWebpageContent) {
        //打开页面失败
        if ((rb == null && tempWebpageContent == null && redirectionUrl == null) || (responseCode >= HttpStatus.SC_BAD_REQUEST && responseCode < HttpStatus.SC_INTERNAL_SERVER_ERROR)) {
//            log.error("-----------我是打开失败，网址是:"+siteUrl+"responseCode是："+responseCode+"redirectionUrl是："+redirectionUrl+"rb是："+rb+"tempWebpageContent内容是："+tempWebpageContent);
            status = Constants.STATUSTIP_DKSB;
//            isOpenUrlFail = true;
            return true;
        } else if (responseCode >= HttpStatus.SC_INTERNAL_SERVER_ERROR) {
//             log.error("-----------我是服务器错误，网址是:"+siteUrl+"responseCode是："+responseCode+"redirectionUrl是："+redirectionUrl+"rb是："+rb+"tempWebpageContent内容是："+tempWebpageContent);
            this.status = Constants.STATUSTIP_FWQCW;//服务器错误
//            isOpenUrlFail = true;
            return true;
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////          doHttpGet家族
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * get方法
     *
     * @param requestUrl 请求地址
     * @return
     */
    protected String doHttpGet(String requestUrl) {
//        return doHttpGet(requestUrl, siteUrl, true);
        return doHttpGet(requestUrl, siteUrl, true, (Header[]) null);
    }

    /**
     * get方法
     *
     * @param requestUrl 请求地址
     * @param referUrl 引用地址
     * @return
     */
    protected String doHttpGet(String requestUrl, String referUrl) {
//        return doHttpGet(requestUrl, referUrl, true);
        return doHttpGet(requestUrl, referUrl, true, (Header[]) null);
    }

    /**
     * get方法
     *
     * @param requestUrl 请求地址
     * @param referUrl 引用地址
     * @param isAutoDoRedirection 遇到302或301时是否自动处理跳转，true自动处理，false不处理
     * @return
     */
    protected String doHttpGet(String requestUrl, String referUrl, boolean isAutoDoRedirection) {
        return doHttpGet(requestUrl, referUrl, isAutoDoRedirection, (Header[]) null);
    }

    /**
     * get方法
     *
     * @param requestUrl 请求地址
     * @param referUrl 引用地址
     * @param isAutoDoRedirection 遇到302或301时是否自动处理跳转，true自动处理，false不处理
     * @return
     */
    protected String doHttpGet(String requestUrl, String referUrl, boolean isAutoDoRedirection, Header... headers) {
        webpageContent = doHttpGetTempContent(requestUrl, referUrl, isAutoDoRedirection, headers);
        if (rb != null) {
            pageCharset = rb.getCharset();//获得页面编码
        }
        return webpageContent;
    }

    /**
     * get方法（包含解码和频繁刷新）未使用 待检测
     *
     * @param requestUrl 请求地址
     * @param referUrl 引用地址
     * @param isAutoDoRedirection 遇到302或301时是否自动处理跳转，true自动处理，false不处理
     * @return
     */
    protected String doAutoHttpGet(String requestUrl, String referUrl, boolean isAutoDoRedirection, Header... headers) {
        while (true) {
            webpageContent = doHttpGetTempContent(requestUrl, referUrl, isAutoDoRedirection, headers);
            //检测编码 &#36825;
            if (webpageContent != null && RegexUtil.ifMatcher("(?:&#\\d+;){2,}", webpageContent)) {
                webpageContent = StringUtils.char10Decode(webpageContent);
            }
            //检测编码 &#x60a8;
            if (webpageContent != null && webpageContent.contains("&#x")) {
                webpageContent = StringUtils.char16Decode(webpageContent);
            }
            //頻繁刷新
            if (!isWebpageContentContainsOne("频繁刷新", "頻繁刷新")) {
                //status = Constants.STATUSTIP_QQTPF;
                break;
            }
            Commons.sleep(1000);
        }

        if (rb != null) {
            pageCharset = rb.getCharset();//获得页面编码
        }
        return webpageContent;
    }

    /**
     * get方法
     *
     * @param requestUrl 请求地址
     * @param referUrl 引用地址
     * @param isAutoDoRedirection 遇到302或301时是否自动处理跳转，true自动处理，false不处理
     * @return
     */
    protected String doHttpGetTempContent(String requestUrl, String referUrl, boolean isAutoDoRedirection) {
        return doHttpGetTempContent(requestUrl, referUrl, isAutoDoRedirection, (Header[]) null);
    }

    /**
     *
     * @param requestUrl
     * @param referUrl
     * @param isAutoDoRedirection
     * @param headers
     * //如果你认为头信息错误，那么你可以自定义头信息。如果为null，系统会给予一些默认头信息。一般只你应该只给系统默认值没有的头信息，或者你认为系统的头信息的value不符合你的实现类时才会写该参数
     * @return
     */
    protected String doHttpGetTempContent(String requestUrl, String referUrl, boolean isAutoDoRedirection, Header... headers) {
        if (isillegalGetParameter(requestUrl)) {
            return null;
        }
        requestUrl = appendUrlToDomain(requestUrl);
        try {
            getProxy();
//            Commons.sleep(NumberUtils.getRandomNumByMax(500));
            headers = getHeadersGet(referUrl, headers, false);
            rb = doGetBean(requestUrl, false, proxyBean, headers);
            return getResponseInfo(isAutoDoRedirection, requestUrl);
        } catch (Exception ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("Not in GZIP format")) {
                return doHttpGet2(requestUrl, referUrl, isAutoDoRedirection);
            }
//            ex.printStackTrace();
            log.error("get出错的网址是：".concat(siteUrl) + "   " + ex);
            log.error(ex);
            dealHttpRequestException();
        }
        return null;
    }

    private void getProxy() {
        //查找代理，如果有代理就用代理连接
        if (proxyBean == null && site != null) {
            proxyBean = site.getProxyBean();
        }
    }

    private void dealHttpRequestException() {
        redirectionUrl = null;
        webpageContent = null;
    }

    /**
     * 快速请求方法，给予默认超时时间更短,用于大量循环请求的时候
     *
     * @param requestUrl 请求地址
     * @param referUrl 引用地址
     * @param isAutoDoRedirection 遇到302或301时是否自动处理跳转，true自动处理，false不处理
     * @return
     */
    protected String doFastHttpGetTempContent(String requestUrl, String referUrl, boolean isAutoDoRedirection) {
        return doFastHttpGetTempContent(requestUrl, referUrl, isAutoDoRedirection, (Header[]) null);
    }

    /**
     * 快速请求方法，给予默认超时时间更短,用于大量循环请求的时候
     *
     * @param requestUrl 请求地址
     * @param referUrl 引用地址
     * @param isAutoDoRedirection 遇到302或301时是否自动处理跳转，true自动处理，false不处理
     * @param headers
     * //如果你认为头信息错误，那么你可以自定义头信息。如果为null，系统会给予一些默认头信息。一般只你应该只给系统默认值没有的头信息，或者你认为系统的头信息的value不符合你的实现类时才会写该参数
     * @return
     */
    protected String doFastHttpGetTempContent(String requestUrl, String referUrl, boolean isAutoDoRedirection, Header... headers) {
        if (isillegalGetParameter(requestUrl)) {
            return null;
        }
        requestUrl = appendUrlToDomain(requestUrl);
        try {
            headers = getHeadersGet(referUrl, headers, false);
            rb = doFastGetBean(requestUrl, false, headers);
            return getResponseInfo(isAutoDoRedirection, requestUrl);
        } catch (Exception ex) {
//        ex.printStackTrace();
            log.error("Fastget出错的网址是：" + siteUrl + ex);
            log.error(ex);
            dealHttpRequestException();
        }
        return null;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////          doHttpPost家族
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * post方法
     *
     * @param forms 表单数据
     * @param actionURL 请求地址
     * @return
     */
    protected String doHttpPost(Forms forms, String actionURL) {
//        return doHttpPost(forms, actionURL, siteUrl);
        return webpageContent = doTempContentHttpPost(forms, actionURL, siteUrl, false, (Header[]) null);
    }

    /**
     * post方法
     *
     * @param forms 表单数据
     * @param actionURL 请求地址
     * @param referUrl 引用地址
     * @return
     */
    protected String doHttpPost(Forms forms, String actionURL, String referUrl) {
//        return doHttpPost(forms, actionURL, referUrl, false, (Header[]) null);
        return webpageContent = doTempContentHttpPost(forms, actionURL, referUrl, false, (Header[]) null);
    }

    /**
     * post方法
     *
     * @param forms 表单数据
     * @param actionURL 请求地址
     * @param referUrl 引用地址
     * @return
     */
    protected String doHttpPost(Forms forms, String actionURL, String referUrl, boolean isAutoDoRedirection) {
//        return doHttpPost(forms, actionURL, referUrl, isAutoDoRedirection, (Header[]) null);
        return webpageContent = doTempContentHttpPost(forms, actionURL, referUrl, isAutoDoRedirection, (Header[]) null);
    }

    protected String doHttpPost(Forms forms, String actionURL, String referUrl, Header... headers) {
        webpageContent = doTempContentHttpPost(forms, actionURL, referUrl, false, headers);
        return webpageContent;
    }

    /**
     * post方法
     *
     * @param forms 表单数据
     * @param actionURL 请求地址
     * @param referUrl 引用地址
     * @param headers 请求的头信息 添加头信息只需要添加需要改动部分，或者添加本方法没有部分的头信息
     * @return
     */
    protected String doHttpPost(Forms forms, String actionURL, String referUrl, boolean isAutoDoRedirection, Header... headers) {
        webpageContent = doTempContentHttpPost(forms, actionURL, referUrl, isAutoDoRedirection, headers);
        return webpageContent;
    }

    protected String doTempContentHttpPost(Forms forms, String actionURL, String referUrl, boolean isAutoDoRedirection) {
        return doTempContentHttpPost(forms, actionURL, referUrl, isAutoDoRedirection, (Header[]) null);
    }

    /**
     *
     * @param forms
     * @param actionURL
     * @param referUrl
     * @param isAutoDoRedirection
     * @param headers
     * //如果你认为头信息错误，那么你可以自定义头信息。如果为null，系统会给予一些默认头信息。一般只你应该只给系统默认值没有的头信息，或者你认为系统的头信息的value不符合你的实现类时才会写该参数
     * @return
     */
    protected String doTempContentHttpPost(Forms forms, String actionURL, String referUrl, boolean isAutoDoRedirection, Header... headers) {
        try {
            if (HttpUtil.isillegalPostParameter(forms, actionURL)) {
                return null;
            }
            actionURL = appendUrlToDomain(actionURL);
            headers = getHeadersPost(referUrl, headers, true);
            getProxy();
            rb = doPostBean(actionURL, forms.get(), pageCharset, proxyBean, headers);
            return getResponseInfo(isAutoDoRedirection, actionURL);
        } catch (Exception ex) {
            log.error("post出错的网址是：" + siteUrl + ex);
//            if (ex.getMessage().contains("zip")) {
//                log.info("出错的网址是：" + siteUrl);
//            }
            ex.printStackTrace();
            dealHttpRequestException();
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////          上传文件post方法家族
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     * @param map 表单
     * @param actionURL 请求网址
     * @param referUrl 参考网址
     * @param isAutoDoRedirection 是否自动处理跳转/重定向
     * @param headers
     * //如果你认为头信息错误，那么你可以自定义头信息。如果为null，系统会给予一些默认头信息。一般只你应该只给系统默认值没有的头信息，或者你认为系统的头信息的value不符合你的实现类时才会写该参数
     * @return
     */
    protected String doHttpPostFile(Map map, String actionURL, String referUrl, boolean isAutoDoRedirection, Header... headers) {
        try {
            if (HttpUtil.isillegalPostParameter(map, actionURL)) {
                return null;
            }
            actionURL = appendUrlToDomain(actionURL);
            headers = getHeadersPost(referUrl, headers, true);
            rb = HttpUtil.doPostFileBean(actionURL, map, pageCharset, headers);
            return webpageContent = getResponseInfo(isAutoDoRedirection, actionURL);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    private String getResponseInfo(boolean isAutoDoRedirection, String refer) {
        if (rb == null) {
            return null;
        }
        readCookies();

        this.redirectionUrl = rb.getRedirectionUrl();

        if (isAutoDoRedirection && redirectionUrl != null) {

            for (int i = 0; redirectionUrl != null && i < 5; i++) {
                try {
                    redirectionUrl = this.appendUrlToDomain(redirectionUrl);
                    rb = doGetBean(redirectionUrl, false, getHeadersGet(refer, (Header[]) null, false));
                    readCookies();
                    this.redirectionUrl = rb.getRedirectionUrl();
                } catch (Exception ex) {
                    log.error(ex);
                }

            }
        }
        if (rb == null) {
            return null;
        }
        responseCode = rb.getStatusCode();
        String tempString = rb.getContent();
        isOpenUrlFail(tempString);
        return tempString;
    }

    /**
     * 头信息集合
     */
    private final Map<String, Header> headsMapPost = new HashMap<>();
    private final Map<String, Header> headsMapGet = new HashMap<>();

    //以下两个方法为了性能，为了方便就分开了，修改的时候记得修改两处就是，有空后面想办法
    /**
     * @param String referUrl 参考网址
     * @param Header[] headers自定义头信息
     */
    public Header[] getHeadersPost(String referUrl, Header[] headers, boolean isPost) {

        setDefaultHeard(headsMapPost);
        headsMapPost.put(ACCEPT_ENCODING_KEY, new BasicHeader(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE));
        headsMapPost.put(ACCEPT_LANGUAGE_KEY, new BasicHeader(ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VALUE));

        headers = setOtherHeard(referUrl, headers, headsMapPost);
        return headers;
    }

    /**
     * @param String referUrl 参考网址
     * @param Header[] headers自定义头信息
     */
    public Header[] getHeadersGet(String referUrl, Header[] headers, boolean isPost) {
        setDefaultHeard(headsMapGet);
        headers = setOtherHeard(referUrl, headers, headsMapGet);
        return headers;
    }

    public void setDefaultHeard(Map<String, Header> map) {
        //headsMapPost.clear();
        //headsMapGet.clear();
        map.clear();
        //         ("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:26.0) Gecko/20100101 Firefox/26.0");
//        ("Accept-Encoding", "gzip");
//        ("Accept-Language", "zh-cn");
//        (ACCEPT_KEY, ACCEPT_VALUE);
//               map.put(USERAGENT_KEY, new BasicHeader(USERAGENT_KEY, USERAGENT_GOOGLE));
        map.put(USERAGENT_KEY, new BasicHeader(USERAGENT_KEY, USERAGENT_FIREFOX));
//         map.put(USERAGENT_KEY, new BasicHeader(USERAGENT_KEY, USERAGENT_IE));
//         map.put(USERAGENT_KEY, new BasicHeader(USERAGENT_KEY, USERAGENT_OPERA));

        map.put(ACCEPT_KEY, new BasicHeader(ACCEPT_KEY, ACCEPT_VALUE));
//            final String acceptEncoding = "Accept-Encoding";
//            headsMapGet.put(acceptEncoding, new BasicHeader(acceptEncoding, "gzip"));
//             final String AcceptLanguage = "Accept-Language";
//           headsMapGet.put(AcceptLanguage, new BasicHeader(AcceptLanguage, "zh-cn"));
//         headsMapPost.put(ACCEPT_ENCODING_KEY, new BasicHeader(ACCEPT_ENCODING_KEY, "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));
//            headsMapPost.put(ACCEPT_LANGUAGE_KEY, new BasicHeader(ACCEPT_LANGUAGE_KEY, "gzip, deflate"));
    }

    public Header[] setOtherHeard(String referUrl, Header[] headers, Map<String, Header> map) {
        map.put(COOKIE_KEY, new BasicHeader(COOKIE_KEY, cookies.toString()));
        referUrl = appendUrlToDomain(referUrl);
        map.put(REFERER_KEY, new BasicHeader(REFERER_KEY, referUrl));
        if (headers != null && headers.length > 0) {

            for (Header header : headers) {
                if (headers == null) {
                    continue;
                }
                map.put(header.getName(), header);
            }
        }
        headers = ArrayUtils.formMapVaules(map);
        return headers;
    }

    /**
     * post方法
     *
     * @param forms 表单数据
     * @param actionURL 请求地址
     * @param referUrl 引用地址
     * @return
     */
    protected String doHttpPost_multipartFormData(Forms forms, String actionURL, String referUrl, boolean isAutoDoRedirection) {
        doHttpPost(forms, actionURL, referUrl, isAutoDoRedirection,
                new BasicHeader("Content-Type", "multipart/form-data; boundary=---------------------------" + StringUtils.getRandomEnAndNumString(13)));
        if (isAutoDoRedirection && StringUtils.allNotEmpty(redirectionUrl)) {
            redirectionUrl = this.appendUrlToDomain(redirectionUrl);
            this.doHttpGet(redirectionUrl, actionURL, true);
        }
        return webpageContent;
    }

    protected void htmlTagDecode() {

    }

    /**
     * 处理验证码
     *
     */
    protected boolean doCaptcha() {
        if (isVerifyNoLongerPopUp()) {
            return false;
        }

        if (captchaUrl == null) {
            return false;
        }
        return doCaptcha(captchaUrl);
    }

    /**
     * 处理验证码
     *
     */
    protected boolean doCaptcha(Forms form) {
        if (isVerifyNoLongerPopUp()) {
            return false;
        }
        if (captchaUrl == null) {
            return false;
        }
        return doCaptcha(this.captchaUrl, form);
    }

//    /**
//     * 处理验证码，这个方法不需要预先获取好验证码地址，方法内会有获取验证码地址的功能
//     *
//     */
//    protected boolean doCaptchaCaptchUrl() {
//        if (isVerifyNoLongerPopUp()) {
//            return false;
//        }
//        this.captchaUrl = getCaptchUrl();
//        if (StringUtils.allNotEmpty(captchaUrl)) {
//            captchaUrl = appendUrlToDomain(captchaUrl);
//
//            if (captchaUrl == null) {
//                return false;
//            }
//            if (doCaptcha(this.captchaUrl)) {
//                return true;
//            }
//            if (isVerifyNoLongerPopUp()) {
//                return false;
//            }
//        }
//        return false;
//    }
    /**
     * 处理验证码
     *
     * @param loopCount
     * @param referUrl
     */
    protected boolean doCaptcha(String referUrl) {
        return doCaptcha(referUrl, null);
    }

    /**
     * 处理验证码
     *
     * @param loopCount
     * @param referUrl
     * @return 如果成功那么返回true
     */
    protected boolean doCaptcha(String referUrl, Forms form) {
        //用于优化验证码：如果上一次验证码输对了，那么下面n次其他错误的循环不再弹出验证码，节省打码资源
        if (notNeedVerify()) {
            return false;
        }
        //Constants.USE_VERIFY_API = true;
        if (StringUtils.isEmpty(captchaUrl)) {
            status = Constants.STATUSTIP_YZMBZC;
            return false;
        }

        //暂时这么处理
        if (captchaBean == null) {
            captchaBean = new CaptchaBean();
        }
        //非正常验证码设置为特殊验证码，第三方打码不支持
        if (captchaBean.getType() != 0) {
            captchaBean.setIsSpecial(true);
        }

        if (isVerifyCancle()) {
            return false;
        }

        captchaUrl = appendUrlToDomain(captchaUrl);
        referUrl = appendUrlToDomain(referUrl);
//        cookiesMap = WebsiteUtils.strCookiesToMap(cookie_str);
        captchaBean.setCaptchaurl(captchaUrl);
//        if (form == null) {
//            this.captchaBean = CaptchaVerify.doGetCaptcha(captchaUrl, referUrl, cookies.getCookiesMap());
//        } else {

        this.captchaBean = null;
//        }

        if (isVerifyCancle()) {
            return false;
        }
        if (isDoCaptchaFail()) {
            return false;
        }
        if (captchaBean != null) {
            cookies.add(captchaBean.getCookies());
        }
        return true;
    }

    private boolean isVerifyCancle() {
        if (notDoVerfyByHand()) {
            status = Constants.STATUSTIP_CAPTCHA_QUXIAO;
//            this.errorMsg = Constants.CANCLE_CAPTCHA_MESSAGE;
            return true;
        }
        return false;
    }

    private boolean notDoVerfyByHand() {
        return (Constants.VERIFY_NO_LONGER_POP_UP || cancleCaptcha()) && !Constants.USE_VERIFY_API;
    }

    /**
     * 处理验证码，这个方法不需要预先获取好验证码地址，方法内会有获取验证码地址的功能
     *
     */
    protected void doCaptchaByGetUrl() {
        doCaptchaByGetUrl(this.captchaUrl);
    }

    /**
     * 处理验证码，这个方法不需要预先获取好验证码地址，方法内会有获取验证码地址的功能
     *
     */
    protected void doCaptchaByGetUrl(String referUrl) {
        this.captchaUrl = getCaptchUrl();
        if (StringUtils.allNotEmpty(captchaUrl)) {
            captchaUrl = appendUrlToDomain(captchaUrl);
            if (notDoVerfyByHand()) {
                return;
            }
            if (captchaUrl == null) {
                return;
            }
            doCaptcha(referUrl);
        }
    }

    /**
     * 验证问题识别
     *
     * @param loopCount
     * @param referUrl
     * @return
     */
    protected boolean doQuestion() {
//         if (isNeedVerify()) 
//             return false;

        if (isQuestionCancle()) {
            return false;
        }

//        if (StringUtils.isAllEmpty(this.verifyQuestion)) {
//            this.getVerifyQuestion();
//        }
        if (StringUtils.isEmpty(verifyQuestion)) {
            return false;
        }
//         questionVerifyer = new QuestionVerify();

        if (isDoQuestionFail()) {
            return false;
        }

//        verifyAnswer=questionBean.getRecognitionResult();
        return true;
    }

    /**
     * 用于优化验证码：如果上一次验证码输对了，那么下面n次其他错误的循环不再弹出验证码，节省打码资源
     */
    protected boolean notNeedVerify() {
        if (!isVerifyFail() && isCurrentTryTimeGt1()) {
            return true;
        }
        return false;

    }

//    private boolean isQuestionCancle() {
//        if (cancleQuestion()) {//Constants.QUESTION_NO_LONGER_POP_UP ||
////            this.errorMsg = Constants.CANCLE_QUESTION_MESSAGE;
//            return true;
//        }
//        return false;
//    }
    protected String getSetUp() {
        return null;
    }

    /**
     * 把双引号替换成单引号
     */
    protected void quoteDoubleToSingle() {
        webpageContent = HtmlUtils.quoteDoubleToSingle(webpageContent);
    }

    /**
     * 把网址追加到网站域名之后
     *
     * @param destUrl 需要追加到网站域名后面的url
     * @return
     */
    public String appendUrlToDomain(String destUrl) {
//        return UrlUtils.connectUrl(UrlUtils.getSiteDomain(siteUrl), URLCoderUtil.decode(requestUrl, pageCharset));
        //return UrlUtils.connectUrl(getSiteDomain(), URLCoderUtil.decode(requestUrl, pageCharset));
        if (StringUtils.isEmpty(destUrl)) {
            return destUrl;
        }

        if (destUrl.indexOf("&amp;") > -1) {
            destUrl = HtmlUtils.replaceTagAmp(destUrl);
        }
        destUrl = destUrl.trim();
        int i = destUrl.indexOf(" ");
        if (i > -1) {
            String temp = destUrl.substring(i);

            temp = URLCoderUtil.encode(temp, pageCharset);
            destUrl = destUrl.substring(0, i);
            destUrl += temp;
        }
        if (RegexUtil.ifMatcher("[\\u4e00-\\u9fa5]", destUrl)) {
            String temp = RegexUtil.getFirstString("([\\u4e00-\\u9fa5])", destUrl);
            String temp2 = URLCoderUtil.encode(temp, pageCharset);
            destUrl = destUrl.replace(temp, temp2);
        }

        return UrlUtils.connectUrl(getSiteDomain(), destUrl);
    }

    /**
     * 获取网址的根域名
     */
    public String getSiteDomain() {
        if (siteUrl == null) {
            return null;
        }
//        if(siteUrl.startsWith("http%")||siteUrl.startsWith("https%")){
//            siteUrl=URLCoderUtil.decode(siteUrl, pageCharset);
//        }
        if (specifySonUrl(siteUrl)) {
            return siteUrl;
        }
        String temp = domainLen(siteUrl);
        temp = removeEndWith(temp);
        return temp;
    }

    /**
     * 本方法决定什么样字符结尾的该去除
     *
     * @param siteUrl
     * @return
     */
    protected String removeEndWith(String siteUrl) {
        if (siteUrl.endsWith("?") || siteUrl.endsWith(".") || siteUrl.endsWith("&") || siteUrl.endsWith("-")) {
            siteUrl = siteUrl.substring(0, siteUrl.length() - 1);
        }
        return siteUrl;
    }

    /**
     * 本方法会决定子域名的长度或者是否有子域名
     *
     * @param siteUrl
     * @return
     */
    protected String domainLen(String siteUrl) {
        //找"//"标识，如果有就几下位置
        int idx = siteUrl.indexOf("//");
        if (idx > 0) {
            //从上次查找到的位置出发，去找最近的"/" 找到了的话直接用本子域名作为最终结果返回
            int idx2 = siteUrl.indexOf("/", idx + 2);
            if (idx2 > 0) {
                siteUrl = siteUrl.substring(0, idx2 + 1);
            }
        }
        return siteUrl;
    }

    /**
     * 指定什么样的子域名直接返回
     *
     * @param siteUrl
     * @return
     */
    protected boolean specifySonUrl(String siteUrl) {
        if (siteUrl.endsWith("/blog/") || siteUrl.endsWith("/wiki/") || siteUrl.endsWith("/forums/") || siteUrl.endsWith("/bbs/") || siteUrl.endsWith("/forum/")) {
            return true;
        }
        return false;
    }

    /**
     * 根据域名后面即将连接的子url判断获取域名
     *
     * @param requestUrl 即将需要拼接到本方法后面的子url
     * @return
     */
    public String getSiteDomain(String requestUrl) {
        if (StringUtils.isAllEmpty(requestUrl)) {

            return null;
        }

        String[] temp = null;
        if (requestUrl.startsWith("./")) {
            temp = siteUrl.split("/");
            if (temp != null && temp.length > 4) {
                StringBuilder sb = new StringBuilder();
                sb.append(temp[0]).append("//");
                for (int i = 2; i < 4; i++) {
                    sb.append(temp[i]).append("/");
                }

                return sb.toString();
            }
        } else if (requestUrl.startsWith("../")) {
            temp = siteUrl.split("/");
            if (temp != null && temp.length > 5) {
                StringBuilder sb = new StringBuilder();
                sb.append(temp[0]).append("//");
                for (int i = 2; i < 5; i++) {
                    sb.append(temp[i]).append("/");
                }

                return sb.toString();
            }
        } else if (requestUrl.startsWith("?")) {
            if (siteUrl.contains("?")) {
                return siteUrl.substring(0, siteUrl.indexOf("?"));
            }
            return siteUrl;
        } else if (!requestUrl.startsWith(".")) {
            if (!requestUrl.startsWith("http") && siteUrl.endsWith(requestUrl)) {
                return siteUrl;
            }
            temp = siteUrl.split("/");

            StringBuilder sb = new StringBuilder();
            sb.append(temp[0]).append("//");
            for (int i = 2; i < temp.length - 1; i++) {
                sb.append(temp[i]).append("/");
            }

            return sb.toString();
        }

//        return UrlUtils.getSiteDomain(siteUrl);
        return getSiteDomain();
    }

    private boolean isillegalGetParameter(String requestUrl) {
        if (requestUrl == null || requestUrl.length() == 0) {
            return true;
        }
        return false;
    }
//    /**
//     * 该注释已过时：
//     * 享元模式的外部状态之1
//     *现在的注释：设置账号对象
//     * @param account
//     */
//    private void setAccount(WebSiteAccount account) {
//        // TODO: implement
//    }

    /**
     * 该注释已过时： 享元模式的外部状态之2，一般只有多用户程序网站才会同时设置帐号和网站，否则网站以组合方式在操作类构造方法传入
     *
     * 现在的注释：设置网址对象
     *
     * @param site
     */
    public void setSite(SiteBean site) {
        this.site = site;
        if (site != null) {
            //主页地址是否正确
            if (!UrlUtils.startWithHttp(site.getSiteUrl())) {
                siteUrl = "http://".concat(site.getSiteUrl());
            }
            siteUrl = site.getSiteUrl().trim();

        }

    }

    public void setTaskCommand(String taskType) {
        this.taskType = taskType;
    }

    /**
     * 获取一个随机分类
     *
     * @return
     */
    protected String getCategory() {
        if (!ContainerUtils.isEmpty(categorysMap)) {
            return null;
        }

//        for (String key : categorysMap.keySet()) {
//            return (String) categorysMap.getValue(key);
//        }
        return ContainerUtils.getRandomValue(categorysMap);
    }

    /**
     * 获取一个随机分类
     *
     * @return
     */
    protected String getCategoryFromList() {
        if (!ContainerUtils.isCollectionNull(categorysList)) {
            return null;
        }

        return ContainerUtils.getRandomElement(categorysList);
    }

    /**
     * 添加发布/回复等成功会的文章地址记录
     * 由于每种网站类型存的表不一样，所以这个方法需要每个类型去定制，比如说论坛类型，博客类型，总之只要表不一样就需要自己实现一个
     *
     * @param publishResultUrl 获取到的发布成功文章地址
     * @param status 该发布是否是审核状态 0代表不需要审核，1代表需要审核
     * @return
     */
    protected abstract boolean addPublishResult(String publishResultUrl, int status);

    /**
     * 保存发布历史记录
     *
     * @return
     */
    protected boolean addPublishResult() {
        if (StringUtils.isEmpty(publishRecordUrl)) {
            return false;
        }
        if (StringUtils.notEmpty(siteUrl)) {
            publishRecordUrl = appendUrlToDomain(publishRecordUrl);
        }
        if (isNeedAuth()) {
            postNeedAuth();
            addPublishResult(publishRecordUrl, Constants.PUBLISH_NEED_AUDIT);
        } else {
            getSite().setStatus(Constants.STATUS_FBCG);
            addPublishResult(publishRecordUrl, Constants.PUBLISH_NOT_AUDIT);
        }
        return true;
    }

    protected boolean isNeedAuth() {
        return status == Constants.STATUSTIP_DDSH;
    }

    protected void postNeedAuth() {
        getSite().setStatus(new StringBuilder().append(Constants.STATUS_FBCG).append("[").append(status).append("]").toString());
    }

    /**
     * 获取谷歌验证码通用方法
     *
     */
    public void getGoogleCaptcha() {
        if (StringUtils.isOneEmpty(webpageContent)) {
            return;
        }

        String regexs[]
                = {"src=[\"']{0,1}(https://api-secure.recaptcha.net/challenge\\?k=[^\"'\\s>]*)[\"']{0,1}",
                    "src=[\"']{0,1}(http://api.recaptcha.net/challenge\\?k=[^\"'\\s>]*)[\"']{0,1}",
                    "src=[\"']{0,1}(http://www.google.com/recaptcha/api/challenge\\?k=[^\"'\\s>]*)[\"']{0,1}",
                    "src=[\"']{0,1}(https://www.google.com/recaptcha/api/challenge\\?k=[^\"'\\s>]*)[\"']{0,1}"
                };

        for (int i = 0; i < regexs.length; i++) {
            captchaUrl = RegexUtil.getFirstString(regexs[i], this.webpageContent, 1);
            if (captchaUrl != null && !captchaUrl.isEmpty()) {
                break;
            }
        }

        if (captchaUrl == null) {
            return;
        }

        try {
            String rootUrl = "http://api.recaptcha.net/image?c=";
            this.doHttpGet(captchaUrl, this.registerUrl, false);

            if (this.redirectionUrl != null || (webpageContent != null && webpageContent.indexOf("302") > -1)) {
                this.doHttpGet(this.appendUrlToDomain(redirectionUrl), this.registerUrl, true);
                rootUrl = "http://www.google.com/recaptcha/api/image?c=";
            }
            recaptcha_challenge_field = RegexUtil.getFirstString("challenge\\s*:\\s*'(.*?)'", webpageContent, 1);
            if (recaptcha_challenge_field != null) {
                captchaUrl = rootUrl + "" + recaptcha_challenge_field;
            }
        } catch (Exception ex) {
//            this.site.setErrorMsg("获取谷歌验证码失败！");
        }

    }

//    /**
//     * 字符串为空
//     */
//    public boolean StringUtils.isOneEmpty(String... srcStr) {
//        return StringUtils.StringUtils.isOneEmpty(srcStr);
//    }
//    protected boolean isMapNull(Map... map) {
//        return ContainerUtils.isEmpty(map);
//    }
//
//    protected boolean isCollectionNull(Collection... coll) {
//        return ContainerUtils.isCollectionNull(coll);
//    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 当前返回错误信息中是否包含给定的其中一个子字符串
     *
     * @param des 给定的子字符串
     * @return
     */
    protected boolean isErrorMsgContainsOne(String... des) {
        if (StringUtils.isAllEmpty(errorMsg)) {
            return false;
        }
        for (String s : des) {
            if (!StringUtils.isOneEmpty(s) && !StringUtils.isOneEmpty(this.errorMsg) && this.errorMsg.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前返回的网页内容是否包含给定的其中一个子字符串
     *
     * @param des 给定的子字符串
     * @return
     */
    protected boolean isWebpageContentContainsOne(String... des) {
        if (StringUtils.isAllEmpty(webpageContent)) {
            return false;
        }
        for (String s : des) {
            if (!StringUtils.isOneEmpty(s) && !StringUtils.isOneEmpty(webpageContent) && this.webpageContent.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前返回的网页内容是否包含所有给定的子字符串
     *
     * @param des 给定的子字符串
     * @return
     */
    protected boolean isWebpageContentContainsAll(String... des) {
        if (StringUtils.isAllEmpty(webpageContent)) {
            return false;
        }
        for (String s : des) {
            if (StringUtils.allNotEmpty(s) && StringUtils.allNotEmpty(webpageContent) && webpageContent.indexOf(s) < 0) {
                return false;
            }
        }
        return true;
    }

    public String getRegUsername() {
        return regUsername;
    }

    public void setRegUsername(String regUsername) {
        this.regUsername = regUsername;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(String regPassword) {
        this.regPassword = regPassword;
    }

//    public int getErrorStatusId() {
//        return errorStatusId;
//    }
//
//    public void setErrorStatusId(int errorStatusId) {
//        this.errorStatusId = errorStatusId;
//    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 如果有的话， 随机获取一个当前使用的关键词
     *
     * @return
     */
    public String getRandomKeywordUrl() {

        return null;
//         String keywordString=ContainerUtils.getRandomKey(keywordMap);
//        return new KeyWordBean(keywordString,keywordMap.getValue(keywordString));
    }

    /**
     * 如果有的话， 根据关键词获取关键词对应的url
     *
     * @return
     */
    public String getKeyword(String keywordUrl) {

        return null;
    }

    /**
     * 如果有的话， 根据关键词获取关键词对应的url
     *
     * @return
     */
    public String getRandomKeyword() {
        return getKeyword(getRandomKeywordUrl());
    }

    /**
     * 如果取消了验证码验证则返回true
     *
     * @return
     */
    protected boolean cancleCaptcha() {

        if (captchaBean != null && captchaBean.getValidateStatus() == Constants.VERIFY_CANCLE) {
            this.status = Constants.STATUSTIP_CAPTCHA_QUXIAO;
            setVerify_cancle(true);
            return true;
        }

        return false;

    }

    /**
     * 如果取消了验证问题验证则返回true
     *
     * @return
     */
    protected boolean isQuestionCancle() {

        //QUESTION_NO_LONGER_POP_UP||
        if ((questionBean != null && questionBean.getValidateStatus() == Constants.VERIFY_CANCLE)) {

            this.status = Constants.STATUSTIP_QUESTION_QUXIAO;
            setVerifyQuestion_cancle(true);
            return true;
        }
        return false;

    }

    /**
     * 如果验证码错误则返回true
     *
     * @return
     */
    protected boolean captchaVerifyFail() {

        if (captchaBean == null || captchaBean.getValidateStatus() == Constants.VERIFY_FAIL || StringUtils.isEmpty(captchaBean.getRecognitionResult())) {
            this.status = Constants.STATUSTIP_YZMCW;
            return true;

        }
        return false;

    }

    protected void clearArticleLink() {

    }

    /**
     * 验证的循环是否执行continue命令，如果需要则返回true
     *
     * @return
     */
    protected boolean isRefreshCaptch() {
        if (captchaBean != null && captchaBean.getValidateStatus() == Constants.VERIFY_REFRESH) {
            this.status = Constants.REFRESH_CAPTCHA_MESSAGE;
            return true;

        }
        return false;
    }

    /**
     * 验证的循环是否执行continue命令，如果需要则返回true
     *
     * @return
     */
    protected boolean isRefreshQuestion() {
        if (questionBean != null && questionBean.getValidateStatus() == Constants.VERIFY_REFRESH) {
            this.status = Constants.REFRESH_QUESTION_MESSAGE;
            return true;

        }
        return false;
    }

    /**
     * 如果你验证问题识别识别返回true
     *
     * @return
     */
    protected boolean questionVerifyFail() {

        if (questionBean == null || questionBean.getValidateStatus() == Constants.VERIFY_FAIL || StringUtils.isEmpty(questionBean.getRecognitionResult())) {
            this.status = Constants.STATUSTIP_YZWDCW;
            return true;
        }

        return false;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getCookie_str() {
//        return cookie_str;
//    }
//
//    public void setCookie_str(String cookie_str) {
//        this.cookie_str = cookie_str;
//    }
    /**
     * 更新操作状态到数据库
     *
     * @param cookie_str
     */
    protected abstract void updateStatusToDB();

    /**
     * 更新操作状态到数据库 由于每种网站类型存的表不一样，所以这个方法需要每个类型去定制，比如说论坛类型，博客类型，总之只要表不一样就需要自己实现一个
     *
     * 方法暂时不需要，后面考虑去掉
     */
    protected abstract void updateAnonymousStatusToDB();

    /**
     * 更新账号密码等到数据库，只有成功的时候才调用
     *
     */
    protected abstract void updateSuccessInfoToDB();

    public int getCurrentTryTime() {
        return currentTryTime;
    }

    public void setCurrentTryTime(int currentTryTime) {
        this.currentTryTime = currentTryTime;
    }

    public boolean isVerify_cancle() {
        return verify_cancle;
    }

    public void setVerify_cancle(boolean verify_cancle) {
        this.verify_cancle = verify_cancle;
    }

    public boolean isVerifyQuestion_cancle() {
        return verifyQuestion_cancle;
    }

    public void setVerifyQuestion_cancle(boolean verifyQuestion_cancle) {
        this.verifyQuestion_cancle = verifyQuestion_cancle;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public boolean isVerifyNoLongerPopUp() {
        if (notDoVerfyByHand()) {
            status = Constants.STATUSTIP_CAPTCHA_QUXIAO;
//            this.errorMsg = Constants.CANCLE_CAPTCHA_MESSAGE;
            return true;
        }
        return false;
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void setCookies(Cookies cookies) {
        this.cookies = cookies;
    }

    public void clearCookies() {
        this.cookies.clear();
    }

    protected boolean isDoCaptchaFail() {
//        if (captchaBean == null) {
//            this.status = Constants.STATUSTIP_YZMCW;
//            return true;
//        }
        if (isRefreshCaptch()) {
            return true;
        }
        if (cancleCaptcha()) {
            return true;
        }
        if (captchaVerifyFail()) {
            return true;
        }
//        if (StringUtils.isEmpty(captchaBean.getRecognitionResult())) {
//            this.status = Constants.STATUSTIP_YZMCW;
//            return true;
//        }
        return false;
    }

    protected boolean isDoQuestionFail() {
//           if (questionBean == null) {
//            this.status = Constants.STATUSTIP_YZWDCW;
//            return true;
//        }

        if (isRefreshQuestion()) {
            return true;
        }
        if (isQuestionCancle()) {
            return true;
        }
        if (questionVerifyFail()) {
            return true;
        }

        return false;
    }

    /**
     * 是否验证失败 包括验证问题和验证码失败
     *
     * @return
     */
    protected boolean isVerifyFail() {
        return StringUtils.isContainsOne(getStatus(),
                Constants.STATUSTIP_YZMCW, Constants.STATUSTIP_YZWDCW, Constants.REFRESH_CAPTCHA_MESSAGE, Constants.REFRESH_QUESTION_MESSAGE//验证码和验证问题错误 的情况要继续
        );
    }

    protected boolean isCaptchaVerifyFail() {
        return StringUtils.isContainsOne(getStatus(),
                Constants.STATUSTIP_YZMCW, Constants.STATUSTIP_YZWDCW//验证码识别错误
        );
    }

    /**
     * 当前尝试的次数是否大于1次
     *
     * @return
     */
    protected boolean isCurrentTryTimeGt1() {
        return currentTryTime > 1;
    }

    public String getSiteGroupName() {
        return siteGroupName;
    }

    public void setSiteGroupName(String siteGroupName) {
        this.siteGroupName = siteGroupName;
    }

    public CaptchaBean getCaptchaBean() {
        return captchaBean;
    }

    public void setCaptchaBean(CaptchaBean captchaBean) {
        this.captchaBean = captchaBean;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

// private String getResponseCodeInfo() {
//        readCookies();
//        responseCode = rb.getStatusCode();
//        return rb.getContent();
//    }
//  private void acceptResponseInfo(boolean isAutoDoRedirection) {
//        if (rb != null) {
//            webpageContent = rb.getContent();
//            pageCharset = rb.getCharset();//获得页面编码
//            if (!isAutoDoRedirection) {
//                this.redirectionUrl = rb.getRedirectionUrl();
//            }
//            readCookies();
//            responseCode = rb.getStatusCode();
////            htmlTagDecode();//反编码页面 太耗性能了，实现类用的时候再调用吧
//        }
//        isOpenUrlFail();
//    }
    /**
     * 获取长度为len的文章标题
     *
     * @param len
     * @return
     */
    public String getArticleTitle(int len) {
        if (len != 0 && articleTitle != null) {
            return articleTitle.length() > len ? articleTitle.substring(0, len) : articleTitle;
        }
        return articleTitle;
    }

    /**
     * 获取长度为len的文章标题
     *
     * @param len
     * @return
     */
    public String getArticleBody(int len) {
        if (len != 0 && articleBody != null) {
            return articleBody.length() > len ? articleBody.substring(0, len) : articleBody;
        }
        return articleBody;
    }

    ////////////////////获取标签系列方法start
    /**
     * 尝试获取maxNum数量的标签，如果不足maxNum那么返回实际获取的数量
     *
     * @param len 标签的长度
     * @param maxNum 标签最大数量
     * @param separate 标签之间用什么分隔符隔开
     * @return
     */
    protected String getTag(int len, int maxNum, String separate) {

        if (maxNum == 0 || maxNum == 1) {
            return getTag(len);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxNum; i++) {
            sb.append(getTag(len)).append(separate);
        }
        return sb.toString();
    }

    protected String getTag(int len, int maxNum) {
        return getTag(len, maxNum, " ");
    }

    protected String getTag(int len) {
        String tag = getRandomKeyword() == null ? articleTitle : getRandomKeyword();
        if (tag.length() > len) {
            return tag.substring(0, len);
        }
        return tag;
    }
    ////////////////////获取标签系列方法 end

    public String getSiteUrl() {
        return siteUrl;
    }

    public QuestionBean getQuestionBean() {
        return questionBean;
    }

//    public String getVerifyAnswer() {
//        return verifyAnswer;
//    }
    // ==================================== ==================================== ==================================== ==================================== ====================================
    // 以下是HttpURLConnection  java的标准类(java.net)相关的请求实现，区别于上面的httpclien，HttpURLConnection性能好点，API简单，体积较小，可毕竟httpclien内部与HttpURLConnection有关
    // 
    // ==================================== ==================================== ==================================== ==================================== ====================================
    //支持 HTTP 特定功能的 URLConnection
    protected HttpURLConnection connent = null;

    public String doHttpPost(StringBuilder sb, String actionURL) {
        return doHttpPost(sb, actionURL, siteUrl, false, null);
    }

    public String doHttpPost(StringBuilder sb, String actionURL, String referUrl) {
        return doHttpPost(sb, actionURL, referUrl, false, null);
    }

    public String doHttpPost(StringBuilder sb, String actionURL, String referUrl, boolean isAutoDoRedirection) {
        return doHttpPost(sb, actionURL, referUrl, isAutoDoRedirection, null);
    }

    public String doHttpPost(StringBuilder sb, String actionURL, String referUrl, Map<String, String> otherHeaders) {
        return doHttpPost(sb, actionURL, referUrl, false, otherHeaders);
    }

    /**
     * URLConnection方式的post根方法
     *
     * @param sb
     * @param actionURL
     * @param referUrl
     * @param isAutoDoRedirection
     * @param otherHeaders
     * @return
     */
    public String doHttpPost(StringBuilder sb, String actionURL, String referUrl, boolean isAutoDoRedirection, Map<String, String> otherHeaders) {

        try {
            getFixedTimeHttpUrlConnect(actionURL, referUrl);

            if (otherHeaders != null) {
                for (String key : otherHeaders.keySet()) {
                    connent.setRequestProperty(key, otherHeaders.get(key));
                }
            }
            connent.setInstanceFollowRedirects(false);
            connent.setDoInput(true);
            connent.setDoOutput(true);
            OutputStream dos = connent.getOutputStream();
            dos.write(sb.toString().getBytes());
            dos.flush();
            dos.close();
            dealLocation(isAutoDoRedirection, actionURL);
            getWebpageContentFromConnent();
            return webpageContent;
        } catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }
//public String uploadImg(String actionURL, String referUrl, String imgname, String imgpath, String[][] parmers,boolean isAutoDoRedirection, Map<String, String> otherHeaders)
//    {
//        // readFile
//        try
//        {
//             getFixedTimeHttpUrlConnect(actionURL, referUrl);
//             
//            if (otherHeaders != null) {
//                for (String key : otherHeaders.keySet()) {
//                    connent.setRequestProperty(key, otherHeaders.get(key));
//                }
//            }
//            connent.setInstanceFollowRedirects(false);
//            connent.setDoInput(true);
//            connent.setDoOutput(true);
//            
//            
//            
//            
//            OutputStream dos = connent.getOutputStream();
//            dos.write(sb.toString().getBytes());
//            dos.flush();
//            dos.close();
//            dealLocation(isAutoDoRedirection, actionURL);
//            getWebpageContentFromConnent();
//            return webpageContent;
//            
//        
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//        return null;
//    }

    public String doHttpGet2(String requestUrl) {
        try {
//            getGetHttpUrlConnect(requestUrl, siteUrl);
//            dealLocation(true, siteUrl);
//            getWebpageContentFromConnent();
//              isOpenUrlFail();
            return doHttpGet2(requestUrl, siteUrl, true);
        } catch (Exception ex) {
            log.error(ex);
        }
        return webpageContent;
    }

    public String doHttpGet2(String requestUrl, String referUrl) {
        try {
//            getGetHttpUrlConnect(requestUrl, referUrl);
//            dealLocation(true, referUrl);
//            getWebpageContentFromConnent();
//              isOpenUrlFail();
            return doHttpGet2(requestUrl, referUrl, true);
        } catch (Exception ex) {
            log.error(ex);
        }
        return webpageContent;
    }

    /**
     * URLConnection方式的get根方法
     *
     * @param requestUrl
     * @param referUrl
     * @param isAutoDoRedirection
     * @return
     */
    public String doHttpGet2(String requestUrl, String referUrl, boolean isAutoDoRedirection) {
        try {
            getGetHttpUrlConnect(requestUrl, referUrl);
            dealLocation(isAutoDoRedirection, referUrl);
            webpageContent = getWebpageContentFromConnent();
            if (!isAutoDoRedirection && redirectionUrl != null) {
                return null;
            }
            isOpenUrlFail();
        } catch (Exception ex) {
            log.error(ex);
        }
        return webpageContent;
    }

    public String doHttpGetByProxy(String requestUrl, String referUrl, boolean isAutoDoRedirection) {
        try {
            getGetHttpUrlConnect(requestUrl, referUrl);
            dealLocation(isAutoDoRedirection, referUrl);
            getWebpageContentFromConnent();
            isOpenUrlFail();
        } catch (Exception ex) {
            log.error(ex);
        }
        return webpageContent;
    }

    protected HttpURLConnection getFixedTimeHttpUrlConnect(String requestUrl, String referUrl) throws Exception {
        getHttpUrlConnect(requestUrl, referUrl);
        setTimeOut(REQUEST_TIME_OUT, CONNECT_TIME_OUT);
        return connent;
    }

    protected HttpURLConnection getHttpUrlConnect(String requestUrl, String referUrl, int timeoutSecond) throws Exception {
        getHttpUrlConnect(requestUrl, referUrl);
        int time = timeoutSecond / 2;
        setTimeOut(time, time);
        return connent;
    }

    /**
     * 处理location，如果isAutoDoRedirection为true ，默认跳转6次避免过多跳转会耗资源
     *
     * @param isAutoDoRedirection
     * @param referUrl
     * @throws Exception
     */
    public void dealLocation(boolean isAutoDoRedirection, String referUrl) throws Exception {
        getCookieFromConnent();
        for (int i = 0; i < 6; i++) {
            redirectionUrl = connent.getHeaderField("Location");
//            if (redirectionUrl==null) {
//                WebsiteUtils.getLocation(formString);
//            }
            if (!isAutoDoRedirection || redirectionUrl == null) {
                break;
            }
            getGetHttpUrlConnect(redirectionUrl, referUrl);
            referUrl = redirectionUrl;
        }
    }

    public abstract void delSiteEmailRefer();

    public void getGetHttpUrlConnect(String requestUrl, String referUrl) throws Exception {
        getFixedTimeHttpUrlConnect(requestUrl, referUrl);
        connent.setInstanceFollowRedirects(false);
        getCookieFromConnent();
    }

    protected void setTimeOut(int readTimeout, int connectTimeout) {
        connent.setReadTimeout(readTimeout);
        connent.setConnectTimeout(connectTimeout);
    }

    protected HttpURLConnection getHttpUrlConnect(String requestUrl, String referUrl) throws Exception {
        if (proxyBean == null) {
            connent = (HttpURLConnection) new URL(appendUrlToDomain(requestUrl)).openConnection();
            //因为目前没有用到代理，所以暂时注释代理功能
        } else {
            log.info("Thread" + Thread.currentThread().getId() + "：取到的代理是:" + proxyBean.getHost() + ":" + proxyBean.getPort());

            log.info("Thread" + Thread.currentThread().getId() + "：取到的代理是:" + proxyBean.getHost() + ":" + proxyBean.getPort());

            if (proxyBean.getUserID() != null && proxyBean.getUserID().length() > 0) {
                Authenticator.setDefault(new BasicAuthenticator(proxyBean.getUserID(), proxyBean.getPwd()));

            }
            connent = (HttpURLConnection) new URL(appendUrlToDomain(requestUrl)).openConnection(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyBean.getHost(), proxyBean.getPort())));
//            if (proxyBean.getUserID() != null && proxyBean.getUserID().length() > 0) {
//                connent.setRequestProperty("Proxy-Authorization", "Basic " + Base64.getEncoder().encodeToString((proxyBean.getUserID() + ":" + proxyBean.getPwd()).getBytes()));
//            }
        }
        setDefaultHeader(referUrl);
        return connent;
    }

//        protected HttpURLConnection getHttpUrlConnectByProxy(String requestUrl, String referUrl) throws Exception {
//        log.info("Thread" + Thread.currentThread().getId() + "：取到的代理是:" + proxyBean.getHost() + ":" + proxyBean.getPort());
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyBean.getHost(), proxyBean.getPort()));
//        connent = (HttpURLConnection) new URL(appendUrlToDomain(requestUrl)).openConnection(proxy);
//        return connent;
//    }
//  protected HttpURLConnection getHttpUrlConnect(String requestUrl, String referUrl,ProxyBean proxyBean) throws Exception {
//
//       Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyBean.getHost(), proxyBean.getPort()));
//        connent = (HttpURLConnection) new URL(appendUrlToDomain(requestUrl)).openConnection(proxy);
//        setDefaultHeader(referUrl);
//        return connent;
//    }
    /**
     * 设计默认头部，只适用于HttpURLConnection方式的请求，不适用于httpclient
     *
     * @param referUrl
     */
    protected void setDefaultHeader(String referUrl) {
        connent.setRequestProperty(USERAGENT_KEY, USERAGENT_FIREFOX);
        connent.setRequestProperty(ACCEPT_LANGUAGE_KEY, ACCEPT_LANGUAGE_VALUE);
//        connent.setRequestProperty(ACCEPT_ENCODING_KEY, ACCEPT_ENCODING_VALUE);
        connent.setRequestProperty(REFERER_KEY, referUrl == null ? "" : appendUrlToDomain(referUrl));
        connent.setRequestProperty(COOKIE_KEY, cookies.toString());
    }

    protected void setTextPlainHeader(String referUrl) {
        setDefaultHeader(referUrl);
        connent.setRequestProperty("Content-Type", "text/plain");
    }

    protected void setMultipartFormDataHeader(String referUrl) {
        setDefaultHeader(referUrl);
        connent.setRequestProperty("Content-Type", StringUtils.getRandomEnAndNumString(15));
    }

    protected void getCookieFromConnent() {
        CookieUtil.getCookiesFromConnent(connent, cookies.getCookiesMap());
    }

    public static String patEncode = "charset=[\"']{0,1}([\\w-]+)";

    public String getWebpageContentFromConnent() {
        webpageContent=null;
        byte[] pageData = null;
        String tmpPageCharset = pageCharset;
        try {
            pageData = ByteUtils.readBytesFromHttpConnection(connent);
            if (pageData == null) {
                return null;
            }
            tmpPageCharset = RegexUtil.getFirstString(patEncode, connent.getHeaderField("Content-Type"), 1);
            if (tmpPageCharset != null && tmpPageCharset.toLowerCase().equals("off")) {
                tmpPageCharset = "GBK";
            }
            if (tmpPageCharset == null) {
                webpageContent = new String(pageData, pageCharset);
                tmpPageCharset = RegexUtil.getFirstString(patEncode, webpageContent, 1);
                if (tmpPageCharset == null) {
                    return null;
                }
            }
            pageCharset = tmpPageCharset.trim().toUpperCase();
            webpageContent = new String(pageData, pageCharset);
        } catch (UnsupportedEncodingException ex) {
            try {
                pageCharset = "ISO8859-1";
                webpageContent = new String(pageData, pageCharset);
            } catch (Exception e) {
            }
        }
        return webpageContent;
    }

    public final boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Map<String, String> getCategorysMap() {
        return categorysMap;
    }

    public void setLastTimeStatus(String lastTimeStatus) {
        this.lastTimeStatus = lastTimeStatus;
    }

    public void setCategorysMap(Map<String, String> categorysMap) {
        this.categorysMap = categorysMap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////      用户交互接口的方法，这里覆盖掉接口的方法，并不强求实现类去做这些
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 当注册成功了之后调用
     */
    protected void registerDidSuccess() {

    }

    /**
     * 当登录成功了之后调用
     */
    protected void loginDidSuccess() {

    }

    /**
     * 当发布成功了之后调用
     */
    protected void publishDidSuccess() {

    }

    /**
     * 当回复成功了之后调用
     */
    protected void replyDidSuccess() {

    }

    /**
     * 根据本网站编码对字符串进行解码
     *
     * @param srcString
     * @return
     */
    protected String decode(String srcString) {
        return URLCoderUtil.decode(srcString, pageCharset);
    }

    /**
     * 根据本网站编码对字符串进行编码
     *
     * @param srcString
     * @return
     */
    protected String encode(String srcString) {
        return URLCoderUtil.encode(srcString, pageCharset);
    }

    /**
     * 存储其他预料不到的，少见的信息，以免修改数据库结构
     *
     * @param key
     * @param value
     */
    protected void setOtherMsg(String key, Object value) {
        site.setOtherMsg(key, value);
    }

    protected void siteTaskWillRun() {
        setRegUsername(site.getUsername());
        setRegPassword(site.getPassword());
        registerUrl = site.getRegUrlFromEmail();
        email = site.getEmail();
    }

//    @Override
//    public boolean reSendEmail() {
//        return false;
//    }
    /**
     * 获取用户配置昵称，昵称=首名字+后名字（姓氏+名字）,如果配置中获取不到，那么系统随机生成一个英文昵称
     *
     * @return
     */
    protected String getNick() {
        if (isNotTryOne()) {
            return getZhOrEnNick();
        }
        String nick = null;
//        String nick = ConfigManager.getGeneralFirstName().concat(ConfigManager.getGeneralLastname());
        if (nick == null || nick.length() == 0) {
            nick = StringUtils.getRandomPinyin();
        }
        return nick;
    }

    private String getZhOrEnNick() {
        if ((status != null) && (status.equals(Constants.STATUSTIP_XZWNC))) {
            return StringUtils.getRandomJianHan(4);
        } else {
            return StringUtils.getRandomUsername(6).toLowerCase();
        }
    }

    protected String getPublishRecordUrlByArticleTitle(int limitArticleTitleLenght) {
        return RegexUtil.getFirstString("<a[^>]*?href=[\"']{0,1}([^\\s\"'>]*)[\"']{0,1}[^>]*?>[^<]*?".concat(RegexUtil.escapeRegexSymbol(articleTitle.length() < limitArticleTitleLenght ? articleTitle : articleTitle.substring(0, limitArticleTitleLenght))).concat("[^<]*?<"), webpageContent);
    }
    private String qqPassword = null;

    protected String getQQUsername() {
        String[] qq = null;
        if (qq == null || qq.length < 2) {
            this.status = Constants.STATUSTIP_MYQQZHHMM;//没有QQ帐号和密码
            return null;
        }
        qqPassword = qq[1];
        return qq[0];
    }

    protected String getQQPassword() {
        if (qqPassword != null) {
            return qqPassword;
        }
        return null;
    }

    //该方法仅调试用
    protected void userLocalProxy() {
        proxyBean = new ProxyBean("127.0.0.1", 8888);
    }
}
