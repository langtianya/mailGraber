/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.beans;

import java.io.InputStream;
import java.util.List;
import org.apache.http.cookie.Cookie;

/**
 * ImageBean已经改成CaptchaBean，修改后该类暂时废弃
 *
 * @author ocq
 */
public class ImageBean {// ImageBean已经改成CaptchaBean

    private String mimeType;
    private byte[] imageByte;
//    private InputStream imageStream;
    private List<Cookie> cookies;
    private InputStream imageInputStream;
    private InputStream imageFileName;

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the cookie
     */
    public List<Cookie> getCookies() {
        return cookies;
    }

    /**
     * @param cookie the cookie to set
     */
    public void setCookies(List<Cookie> cookie) {
        this.cookies = cookie;
    }

    /**
     * @return the imageByte
     */
    public byte[] getImageByte() {
        return imageByte;
    }

    /**
     * @param imageByte the imageByte to set
     */
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

}
