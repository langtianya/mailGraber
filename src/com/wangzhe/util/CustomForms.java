/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import static com.wangzhe.util.Constants.DEFAULT_ENCODE;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 自定义表单类型，如果需要自定义表单，那么请用CustomForms，其他一般的表单用Forms类，上传头像的时候用FileForms
 *
 * @author ocq
 */
public class CustomForms {

    private StringBuilder sb;
//    boolean isEncodeFrom = false;
    private String encode = DEFAULT_ENCODE;

    public CustomForms() {
        this(32);
    }

    public CustomForms(String encode) {
        this(32);
        this.encode = encode;
    }

    public CustomForms(int initSize) {
        sb = new StringBuilder(initSize);
    }

//     public CustomForms(boolean  isEncodeFrom) {
//       this.isEncodeFrom=isEncodeFrom;
//    }
    /**
     * 添加形如step=agreement&action=register&agree=这种固定键值对的字符串到表单
     *
     * @param str
     */
    public CustomForms addNotEncode(String str) {
        sb.append(str);
        return this;
    }

    public CustomForms addMap(Map<String, String> map) {

        for (String key : map.keySet()) {
            addAndEncode(key, map.get(key));
        }
        return this;
    }

    public CustomForms addNotEncode(String key, String value) {
//        this.sb.append("&").append(key).append("=").append(isEncodeFrom ? value : value);
        if (sb.length() > 1) {
            this.sb.append("&").append(key).append("=").append(value);
        } else {
            this.sb.append(key).append("=").append(value);
        }
        return this;
    }

    public CustomForms addAndEncode(String key, String value) {
//        this.sb.append("&").append(key).append("=").append(isEncodeFrom ? value : value);
        if (sb.length() > 1) {
            this.sb.append("&").append(key).append("=").append(URLCoderUtil.encode(value, encode));
//            this.sb.append("&").append(key).append("=").append(encode!=null ? URLCoderUtil.encode(value, encode) : value);
        } else {
            this.sb.append(key).append("=").append(URLCoderUtil.encode(value, encode));
        }
        return this;
    }

    public CustomForms update(String key, String value) {
//        this.remove(key);
//        this.add(key, value);
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public String toFormatString() {

        return sb.toString();
    }

    public boolean contains(String key) {
        if (this.toString() == null || key == null || key.equals("")) {
            return false;
        }
        return this.toString().toLowerCase().contains(new StringBuilder("&").append(key.toLowerCase()).append("="));
    }

    public int size() {
        return this.sb.length();
    }

    public static void main(String[] args) {
        CustomForms form = new CustomForms("");
        form.addAndEncode("username", "52454");
        form.addAndEncode("action", "3543536");
        System.out.println(form.toString());

    }
//
//    public String getEncode() {
//        return encode;
//    }
//
//    public void setEncode(String encode) {
//        this.encode = encode;
//    }

    public StringBuilder toStringBuilder() {
        return sb;
    }

}
