package com.wangzhe.beans;

import java.util.List;
import org.apache.http.cookie.Cookie;

/**
 *已经改成CaptchaBean，修改后该类暂时废弃
 * @author ocq
 */
public class ImageClickBean {
     //
    private int validateStatus;
    //最终结果
    private String recognitionResult;
    //是否是大小写
    private boolean caseSensitive = false;
    //已经尝试的次数
    private int havetryCount;
    private long startTime;
    private long allowTotalTime = 65000;
    //response 返回的s值
    private String svalue;
    //response 返回的i值
    private String ivalue;
    //
    private int pr = 3;

    private List<Cookie> cookies;

    /**
     * @return the validateStatus
     */
    public int getValidateStatus() {
        return validateStatus;
    }

    /**
     * @param validateStatus the validateStatus to set
     */
    public void setValidateStatus(int validateStatus) {
        this.validateStatus = validateStatus;
    }

    /**
     * @return the recognitionResult
     */
    public String getRecognitionResult() {
        return recognitionResult;
    }

    /**
     * @param recognitionResult the recognitionResult to set
     */
    public void setRecognitionResult(String recognitionResult) {
        this.recognitionResult = recognitionResult;
    }

    /**
     * @return the caseSensitive
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * @param caseSensitive the caseSensitive to set
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * @return the havetryCount
     */
    public int getHavetryCount() {
        return havetryCount;
    }

    /**
     * @param havetryCount the havetryCount to set
     */
    public void setHavetryCount(int havetryCount) {
        this.havetryCount = havetryCount;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the allowTotalTime
     */
    public long getAllowTotalTime() {
        return allowTotalTime;
    }

    /**
     * @param allowTotalTime the allowTotalTime to set
     */
    public void setAllowTotalTime(long allowTotalTime) {
        this.allowTotalTime = allowTotalTime;
    }

    /**
     * @return the pr
     */
    public int getPr() {
        return pr;
    }

    /**
     * @param pr the pr to set
     */
    public void setPr(int pr) {
        this.pr = pr;
    }

    /**
     * @return the cookies
     */
    public List<Cookie> getCookies() {
        return cookies;
    }

    /**
     * @param cookies the cookies to set
     */
    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    /**
     * @return the svalue
     */
    public String getSvalue() {
        return svalue;
    }

    /**
     * @param svalue the svalue to set
     */
    public void setSvalue(String svalue) {
        this.svalue = svalue;
    }

    /**
     * @return the ivalue
     */
    public String getIvalue() {
        return ivalue;
    }

    /**
     * @param ivalue the ivalue to set
     */
    public void setIvalue(String ivalue) {
        this.ivalue = ivalue;
    }
    
    
}
