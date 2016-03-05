package com.wangzhe.beans;

import java.util.List;

/**
 * the params of user config
 *
 * @author ocq
 */
public class ConfigParam {

    /**
     *
     */
    private List<String> grabIds;
    /**
     * grab max num
     */
    private int maxNum;
    /**
     * the minimum speed to request url
     */
    private int minSpeed;
    /**
     * the maximum speed to request url
     */
    private int maxSpeed;
    /**
     * urls will grab mail address from
     */
    private String[] grabUrls;
    /**
     * 邮箱相关的 公司名、人名、网页中包含的关键词
     */
    private String[] keywords;

    /**
     * @return the grabIds
     */
    public List<String> getGrabIds() {
        return grabIds;
    }

    /**
     * @param grabIds the grabIds to set
     * @return
     */
    public ConfigParam setGrabIds(List<String> grabIds) {
        this.grabIds = grabIds;
        return this;
    }

    /**
     * @return the maxNum
     */
    public int getMaxNum() {
        return maxNum;
    }

    /**
     * @param maxNum the maxNum to set
     * @return
     */
    public ConfigParam setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        return this;
    }

    /**
     * @return the minSpeed
     */
    public int getMinSpeed() {
        return minSpeed;
    }

    /**
     * @param minSpeed the minSpeed to set
     */
    public ConfigParam setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
        return this;
    }

    /**
     * @return the maxSpeed
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * @param maxSpeed the maxSpeed to set
     * @return
     */
    public ConfigParam setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
        return this;
    }

    /**
     * @return the grabUrls
     */
    public String[] getGrabUrls() {
        return grabUrls;
    }

    /**
     * @param grabUrls the grabUrls to set
     * @return
     */
    public ConfigParam setGrabUrls(String... grabUrls) {
        this.grabUrls = grabUrls;
        return this;
    }

    /**
     * @param grabUrl
     * @return
     */
    public ConfigParam setGrabUrls(String grabUrl) {
        this.grabUrls = new String[]{grabUrl};
        return this;
    }

    /**
     * @return the keywords
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }
}
