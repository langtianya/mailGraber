package com.wangzhe.beans;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ocq
 */
public class SiteBean implements java.io.Serializable {

    private LongProperty id;
    private IntegerProperty typeId;
    private StringProperty typeName = new SimpleStringProperty();
    protected StringProperty siteUrl;
    private StringProperty title = new SimpleStringProperty();
    ;
    private StringProperty username;
    private StringProperty password;
    private StringProperty status = new SimpleStringProperty();
//    private StringProperty errorMsg;
    private IntegerProperty groupId;
    private StringProperty groupName;
    private StringProperty implerName;
    //private StringProperty homeUrl;
    private boolean needGetRegUrlFromEmail;
    private String regUrlFromEmail;
    //回复库
    //private LongProperty siteid;
    //private StringProperty replypageurl;
    //private StringProperty isCheck;
    private StringProperty createtime;
    //扩展
    //private StringProperty postpageurl;
    //社交
    private StringProperty emailUsername;
    private StringProperty emailPassword;

    private IntegerProperty sort;
    //站群
//    private SimpleStringProperty url;
    private StringProperty cookie;
    private StringProperty fId;
    //private IntegerProperty mode = new SimpleIntegerProperty();
    private Map<String, Object> otherMsg = new HashMap<>();
    protected String homePage;
    //用于激活
    //保存到数据库的时候的时间
    private StringProperty timeStamp;
    //是否是新增加的网站
    private boolean isNew;
    private int operateType;
    private ProxyBean proxyBean;

    protected String email;

    public void init() {
        cookie = null;
    }

    /**
     * 每个网站都会记录可以发布的版块，当检测到有可以发布的版块的时候就往该属性赋值
     */
    private StringProperty publishableCategory = new SimpleStringProperty();

    public SiteBean(String siteUrl, String status) {
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.status = new SimpleStringProperty(status);
    }

    public SiteBean(long id, String siteUrl, String status) {
        this.id = new SimpleLongProperty(id);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.status = new SimpleStringProperty(status);
    }

    public SiteBean(long id, String username, String password, String cookie, String status) {
        this.id = new SimpleLongProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.cookie = new SimpleStringProperty(cookie);
        this.status = new SimpleStringProperty(status);

    }

    public SiteBean(int typeId, String siteUrl, String title, int groupId, String status) {
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.groupId = new SimpleIntegerProperty(groupId);
        this.status = new SimpleStringProperty(status);
    }

    //
    //  pstmt = conn.prepareStatement("SELECT siteId,siteUrl,typeId,typeName FROM t_activeEmail");
    public SiteBean(long siteId, String siteUrl, int typeId, String implerName, String timeStamp, boolean isNew) {
        this.id = new SimpleLongProperty(siteId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.typeId = new SimpleIntegerProperty(typeId);
        this.implerName = new SimpleStringProperty(implerName);
        this.timeStamp = new SimpleStringProperty(timeStamp);
        this.isNew = isNew;
    }

    public SiteBean(int typeId, String siteUrl, String title, String username, String password, String status) {
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
    }

    public SiteBean(long id, int typeId, String siteUrl, String title, String username, String password, String status, String cookie, String fId, String implerName) {
        this.id = new SimpleLongProperty(id);
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
        this.cookie = new SimpleStringProperty(cookie);
        this.fId = new SimpleStringProperty(fId);
        this.implerName = new SimpleStringProperty(implerName);
    }

    public SiteBean(long id, int typeId, String siteUrl, String title, String username, String password, String status, String cookie, String implerName) {
        this.id = new SimpleLongProperty(id);
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
        this.cookie = new SimpleStringProperty(cookie);
        this.implerName = new SimpleStringProperty(implerName);
    }

    public SiteBean(long id, int typeId, String siteUrl, String title, String username, String password, String emailUsername, String emailPassword, String status, String cookie, String implerName) {
        this.id = new SimpleLongProperty(id);
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.emailUsername = new SimpleStringProperty(emailUsername);
        this.emailPassword = new SimpleStringProperty(emailPassword);
        this.status = new SimpleStringProperty(status);
        this.cookie = new SimpleStringProperty(cookie);
        this.implerName = new SimpleStringProperty(implerName);
    }

    public SiteBean(int typeId, String siteUrl, String title, String username, String password, String status, int groupId) {
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
        this.groupId = new SimpleIntegerProperty(groupId);
    }

    public SiteBean(long id, int typeId, String siteUrl, String title, String username, String password, String status, int groupId) {
        this.id = new SimpleLongProperty(id);
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
        this.groupId = new SimpleIntegerProperty(groupId);
    }

//    public SiteBean(long id, int typeId, String homeUrl, String siteUrl, String title, String username, String password, String status, String implerName) {
//        this.id = new SimpleLongProperty(id);
//        this.typeId = new SimpleIntegerProperty(typeId);
//        this.homeUrl = new SimpleStringProperty(homeUrl);
//        this.siteUrl = new SimpleStringProperty(siteUrl);
//        this.title = new SimpleStringProperty(title);
//        this.username = new SimpleStringProperty(username);
//        this.password = new SimpleStringProperty(password);
//        this.status = new SimpleStringProperty(status);
//        this.implerName = new SimpleStringProperty(implerName);
//    }
    public SiteBean(int typeId, String siteUrl, String title, String username, String password, String status, int groupId, String cookie, String fId) {
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.title = new SimpleStringProperty(title);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
        this.groupId = new SimpleIntegerProperty(groupId);
        this.cookie = new SimpleStringProperty(cookie);
        this.fId = new SimpleStringProperty(fId);
    }

    public SiteBean(int typeId, String siteUrl, String status, int groupId, int sort) {
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.status = new SimpleStringProperty(status);
        this.groupId = new SimpleIntegerProperty(groupId);
        this.sort = new SimpleIntegerProperty(sort);
    }

//    public SiteBean(long siteid, String replypageurl, String isCheck, String status) {
//        this.siteid = new SimpleLongProperty(siteid);
//        this.replypageurl = new SimpleStringProperty(replypageurl);
//        this.isCheck = new SimpleStringProperty(isCheck);
//        this.status = new SimpleStringProperty(status);
//    }
//    public SiteBean(String username, String password, String status, String cookie, long siteid) {
//        this.username = new SimpleStringProperty(username);
//        this.password = new SimpleStringProperty(password);
//        this.status = new SimpleStringProperty(status);
//        this.cookie = new SimpleStringProperty(cookie);
//        this.siteid = new SimpleLongProperty(siteid);
//    }
//    public SiteBean(int typeId, String postpageurl, String status, int sort, int groupId, long siteid) {
//        this.typeId = new SimpleIntegerProperty(typeId);
//        this.postpageurl = new SimpleStringProperty(postpageurl);
//        this.status = new SimpleStringProperty(status);
//        this.sort = new SimpleIntegerProperty(sort);
//        this.groupId = new SimpleIntegerProperty(groupId);
//        this.siteid = new SimpleLongProperty(siteid);
//    }
//    public SiteBean(long id, int typeId, String postpageurl, String username, String password, long siteid, String status, String createtime, String cookie) {
//        this.id = new SimpleLongProperty(id);
//        this.typeId = new SimpleIntegerProperty(typeId);
//        this.postpageurl = new SimpleStringProperty(postpageurl);
//        this.username = new SimpleStringProperty(username);
//        this.password = new SimpleStringProperty(password);
//        this.status = new SimpleStringProperty(status);
//        this.siteid = new SimpleLongProperty(siteid);
//        this.createtime = new SimpleStringProperty(createtime);
//        this.cookie = new SimpleStringProperty(cookie);
//    }
    public SiteBean(int groupid, int typeId, String url, String username, String password, String status) {
        this.groupId = new SimpleIntegerProperty(groupid);
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(url);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
    }

    public SiteBean(long id, int typeId, String url, String username, String password, String status) {
        this.id = new SimpleLongProperty(id);
        this.typeId = new SimpleIntegerProperty(typeId);
        this.siteUrl = new SimpleStringProperty(url);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
    }

    public SiteBean(String siteUrl, String userName, String passWord) {
        this.siteUrl = new SimpleStringProperty(siteUrl);
        this.username = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(passWord);
    }

    public Object getOtherMsg(String key) {
        if (otherMsg == null || key == null) {
            return null;
        }
        return otherMsg.get(key);
    }

    public Map<String, Object> getOtherMsg() {
        return otherMsg;
    }

    public void setOtherMsg(String key, Object value) {
        if (otherMsg == null) {
            otherMsg = new HashMap<>();
        }
        this.otherMsg.put(key, value);
    }

    public void setOtherMsg(Map<String, Object> map) {
        this.otherMsg = map;
    }

    public void removeOtherMsg(String key) {
        if (key == null) {
            return;
        }
        this.otherMsg.remove(key);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        if (this.id == null) {
            this.id = new SimpleLongProperty();
        }
        this.id.set(id);
    }

    /**
     * @return the site
     */
    public String getSiteUrl() {
        return siteUrl.get();
    }

    public StringProperty siteUrlProperty() {
        return siteUrl;
    }

    /**
     * @param site the site to set
     */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl.set(siteUrl);
    }

    /**
     * @return the uname
     */
    public String getUsername() {
        return username == null ? null : username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    /**
     * @param uname the uname to set
     */
    public void setUsername(String username) {
        if (this.username == null) {
            this.username = new SimpleStringProperty();
        }
        this.username.set(username);
    }

    /**
     * @return the pwd
     */
    public String getPassword() {
        return password == null ? null : password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPassword(String password) {
        if (this.password == null) {
            this.password = new SimpleStringProperty();
        }
        this.password.set(password);
    }

    /**
     * @return the cookie
     */
    public String getCookie() {
        return cookie == null ? null : cookie.get();
    }

    /**
     * @param cookie the cookie to set
     */
    public void setCookie(String cookie) {
        if (this.cookie == null) {
            this.cookie = new SimpleStringProperty();
        }
        this.cookie.set(cookie);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * @return the fId
     */
    public String getfId() {
        return fId == null ? null : fId.get();
    }

    /**
     * @param fId the fId to set
     */
    public void setfId(String fId) {
        if (this.fId == null) {
            this.fId = new SimpleStringProperty();
        }
        this.fId.set(fId);
    }

//    /**
//     * @return the Mode
//     */
//    public int getMode() {
//        return mode.get();
//    }
//
//    /**
//     * @param mode 描述
//     */
//    public void setMode(int mode) {
//        this.mode.set(mode);
//    }
    public String getImplerName() {
        return implerName.get();
    }

    public void setImplerName(String implerName) {
        if (this.implerName == null) {
            this.implerName = new SimpleStringProperty();
        }
        this.implerName.set(implerName);
    }

    public int getGroupId() {
        return groupId.get();
    }

    public void setGroupId(int groupId) {
        if (this.groupId == null) {
            this.groupId = new SimpleIntegerProperty(groupId);
        }
        this.groupId.set(groupId);
    }

    public String getPublishableCategory() {
        return publishableCategory.get();
    }

    public void setPublishableCategory(String publishableCategory) {
        this.publishableCategory.set(publishableCategory);
    }
//public String getErrorMsg() {
//        if (errorMsg != null) {
//            return errorMsg.get();
//        } else {
//            return null;
//        }
//    }
//    public void setErrorMsg(String errorMsg) {
//        if (this.errorMsg == null) {
//            this.errorMsg = new SimpleStringProperty();
//        }
//        this.errorMsg.set(errorMsg);
//    }

//    /**
//     * @return the siteid
//     */
//    public long getSiteid() {
//        return siteid.get();
//    }
//
//    /**
//     * @param siteid the siteid to set
//     */
//    public void setSiteid(long siteid) {
//        this.siteid.set(siteid);
//    }
//    /**
//     * @return the replypageurl
//     */
//    public String getReplypageurl() {
//        return replypageurl.get();
//    }
//
//    /**
//     * @param replypageurl the replypageurl to set
//     */
//    public void setReplypageurl(String replypageurl) {
//        this.replypageurl.set(replypageurl);
//    }
    /**
     * @return the createtime
     */
    public String getCreatetime() {
        return createtime.get();
    }

    /**
     * @param createtime the createtime to set
     */
    public void setCreatetime(String createtime) {
        this.createtime.set(createtime);
    }

//    /**
//     * @return the status
//     */
//    public String getIsCheck() {
//        return isCheck.get();
//    }
//
//    /**
//     * @param isCheck 描述
//     */
//    public void setIsCheck(String isCheck) {
//        this.isCheck.set(isCheck);
//    }
    /**
     * @return the mark
     */
    public int getSort() {
        return sort.get();
    }

    /**
     * @param sort 描述
     */
    public void setSort(int sort) {
        this.sort.set(sort);
    }

//    /**
//     * @return the postpageurl
//     */
//    public String getPostpageurl() {
//        return postpageurl.get();
//    }
//
//    /**
//     * @param postpageurl the postpageurl to set
//     */
//    public void setPostpageurl(String postpageurl) {
//        this.postpageurl.set(postpageurl);
//    }
//    public String getUrl() {
//        return url.get();
//    }
//
//    public void setUrl(String url) {
//        this.url.set(url);
//    }
    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId.get();
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId.set(typeId);
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName.get();
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName.set(typeName);
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName.get();
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }

    /**
     * @return the statusName
     */
    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    /**
     * @param statusName the statusName to set
     */
    public void setStatus(String status) {
        this.status.set(status);
    }

    @Override
    public String toString() {
        return siteUrl.get();
    }

    public boolean isNeedGetRegUrlFromEmail() {
        return needGetRegUrlFromEmail;
    }

    public void setNeedGetRegUrlFromEmail(boolean needGetRegUrlFromEmail) {
        this.needGetRegUrlFromEmail = needGetRegUrlFromEmail;
    }

    public String getRegUrlFromEmail() {
        return regUrlFromEmail;
    }

    public void setRegUrlFromEmail(String regUrlFromEmail) {
        this.regUrlFromEmail = regUrlFromEmail;
    }

    public StringProperty getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(StringProperty timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    /**
     * @return the emailUsername
     */
    public String getEmailUsername() {
        return emailUsername == null ? null : emailUsername.get();
    }

    /**
     * @param emailUsername the emailUsername to set
     */
    public void setEmailUsername(String emailUsername) {
        if (this.emailUsername == null) {
            this.emailUsername = new SimpleStringProperty();
        }
        this.emailUsername.set(emailUsername);
    }

    /**
     * @return the emailPassword
     */
    public String getEmailPassword() {
        return emailPassword == null ? null : emailPassword.get();
    }

    /**
     * @param emailPassword the emailPassword to set
     */
    public void setEmailPassword(String emailPassword) {
        if (this.emailPassword == null) {
            this.emailPassword = new SimpleStringProperty();
        }
        this.emailPassword.set(emailPassword);
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

//    //比较的是域名
//    @Override
//    public int hashCode() {
//        return UrlUtils.getSiteRootDomain(siteUrl.get()).hashCode();
//    }
//
//    //比较的是域名
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
////        if (obj instanceof SiteBean) {
////
////        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final SiteBean siteBean = (SiteBean) obj;
//        return Objects.equals(UrlUtils.getSiteRootDomain(siteUrl.get()), UrlUtils.getSiteRootDomain(siteBean.siteUrl.get()));
//    }
//    @Override
//    public int hashCode() {
//        return Constants.isReCompare ? UrlUtils.getSiteRootDomain(siteUrl.get()).hashCode() : super.hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (!Constants.isReCompare) {
//            return super.equals(obj);
//        }
//        if (obj == null) {
//            return false;
//        }
////        if (obj instanceof SiteBean) {
////
////        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final SiteBean siteBean = (SiteBean) obj;
//        return Objects.equals(UrlUtils.getSiteRootDomain(siteUrl.get()), UrlUtils.getSiteRootDomain(siteBean.siteUrl.get()));
//    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the proxyBean
     */
    public ProxyBean getProxyBean() {
        return proxyBean;
    }

    /**
     * @param proxyBean the proxyBean to set
     */
    public void setProxyBean(ProxyBean proxyBean) {
        this.proxyBean = proxyBean;
    }

}
