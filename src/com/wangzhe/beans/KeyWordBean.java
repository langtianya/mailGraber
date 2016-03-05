package com.wangzhe.beans;

/**
 *
 * @author ocq
 */
public class KeyWordBean {

    private int id;
    private String keyword;
    private String url;
    private int pubcount;
    private String status;
    private String time_stamp;

    public KeyWordBean() {
    }

    public KeyWordBean(String keyword, String url) {
        this.keyword = keyword;
        this.url = url;
    }

    
    
    public KeyWordBean(int id, String keyword, String url, int pubcount, String status, String time_stamp) {
        this.id = id;
        this.keyword = keyword;
        this.url = url;
        this.pubcount = pubcount;
        this.status = status;
        this.time_stamp = time_stamp;
    }

    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the pubcpunt
     */
    public int getPubcpunt() {
        return pubcount;
    }

    /**
     * @param pubcpunt the pubcpunt to set
     */
    public void setPubcpunt(int pubcount) {
        this.pubcount = pubcount;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the time_stamp
     */
    public String getTime_stamp() {
        return time_stamp;
    }

    /**
     * @param time_stamp the time_stamp to set
     */
    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

}
