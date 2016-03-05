package com.wangzhe.beans;

import java.io.InputStream;
import java.util.List;
import org.apache.http.cookie.Cookie;

/**
 * 验证码bean
 *
 * @author ocq
 */
public class CaptchaBean {

    //识别状态
    private int validateStatus;
    //最终结果
    private String recognitionResult;

    //是否是大小写，如果大小写敏感就会提示相关信息
    private boolean caseSensitive = false;
    /**
     * 是否是中文验证码 有些验证码api不支持中文验证码
     */
    private boolean isZh = false;
    //特殊的验证码，非常规验证码，实际上只要不是英文和中文验证码就属于特殊了
    private boolean isSpecial = false;
    //已经尝试的次数
    private int havetryCount;
    private long startTime;
    private long allowTotalTime = 65000;

    ///点击验证码用到start
    //response 返回的s值
    private String svalue;
    //response 返回的i值
    private String ivalue;
    private int type;
     ///点击验证码用到end

    //验证码地址
    //比如http://www.discuz.net/plugin.php?id=cloudcaptcha:get&rand=Rb3Jg3qEz3&modid=member::register&refresh=0
    private String captchaurl;

    //pr值多多大才弹出验证码
    private int pr = 2;

    private List<Cookie> cookies;

    private String mimeType;
    private byte[] imageByte;
    private InputStream imageInputStream;
    private InputStream imageFileName;

    //decap用 和deathbycaptcha用
    private int major_id;
    //decap用
    private int minor_id;
    //Captchaw用
    private String task_id;

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public int getMinor_id() {
        return minor_id;
    }

    public void setMinor_id(int minor_id) {
        this.minor_id = minor_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public void init() {
        startTime = System.currentTimeMillis();
        validateStatus = 0;
    }

    public boolean isTimeOut() {
        return System.currentTimeMillis() - startTime > allowTotalTime;
    }

    public int getHaveTryCount() {
        return havetryCount;
    }

    public void setHaveTryCount(int havetryCount) {
        this.havetryCount = havetryCount;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getAllowTotalTime() {
        return allowTotalTime;
    }

    public void setAllowTotalTime(long allTotalTime) {
        this.allowTotalTime = allTotalTime;
    }

    public String getCaptchaurl() {
        return captchaurl;
    }

    public void setCaptchaurl(String captchaurl) {
        this.captchaurl = captchaurl;
    }

    public int getPr() {
        return pr;
    }

    public void setPr(int pr) {
        this.pr = pr;
    }

    public boolean isIsZhCaptch() {
        return isZhCaptch;
    }

    public boolean isIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public void setIsZhCaptch(boolean isZhCaptch) {
        this.isZhCaptch = isZhCaptch;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    private boolean isZhCaptch = true;
    private String msg;

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public int getValidateStatus() {
        return validateStatus;
    }

    public void setValidateStatus(int validateStatus) {
        this.validateStatus = validateStatus;
    }

    public String getRecognitionResult() {
        return recognitionResult;
    }

    public void setRecognitionResult(String captchResult) {
        this.recognitionResult = captchResult;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public String getSvalue() {
        return svalue;
    }

    public void setSvalue(String svalue) {
        this.svalue = svalue;
    }

    public String getIvalue() {
        return ivalue;
    }

    public void setIvalue(String ivalue) {
        this.ivalue = ivalue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isIsZh() {
        return isZh;
    }

    public void setIsZh(boolean isZh) {
        this.isZh = isZh;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    public InputStream getImageInputStream() {
        return imageInputStream;
    }

    public void setImageInputStream(InputStream imageInputStream) {
        this.imageInputStream = imageInputStream;
    }

    public InputStream getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(InputStream imageFileName) {
        this.imageFileName = imageFileName;
    }

    public int getHavetryCount() {
        return havetryCount;
    }

    public void setHavetryCount(int havetryCount) {
        this.havetryCount = havetryCount;
    }

}
