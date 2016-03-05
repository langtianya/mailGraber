package com.wangzhe.beans;

import com.wangzhe.util.ContainerUtils;
import com.wangzhe.util.RegexUtil;
import com.wangzhe.util.StringUtils;
import com.wangzhe.util.URLCoderUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * 本来是用得最多的表单类，其他的都是比较少用的，如果本类解决不了的时候才考虑用个其他的
 * 常见表单用Forms类，上传头像的时候用FileForms，如果需要自定义表单，那么请用CustomForms
 *
 * @author ocq
 */
public class Forms {

    private List<NameValuePair> params;

    public Forms() {
        this(32);
    }

    public Forms(int initSize) {
        params = new ArrayList<NameValuePair>(initSize);
    }

    public Forms(String str) {
        this(32);
        add(str);
    }

    /**
     * 添加形如step=agreement&action=register&agree=这种固定键值对的字符串到表单
     *
     * @param str
     */
    public Forms add(String str) {

        Map<String, String> forms = stringToMap(str);
        for (String key : forms.keySet()) {
//            params.add(new BasicNameValuePair(key, forms.get(key)));
            add(key, forms.get(key));
        }
        return this;
    }

    private Map<String, String> stringToMap(String str) {
        return RegexUtil.getMap("([^=&]*?)=([^&\\s]*)", str);
    }

    public Forms add(Map<String, String> map) {

        for (String key : map.keySet()) {
            add(key, map.get(key));
        }
        return this;
    }

    public Forms add(String key, String value) {
        if (key == null || value == null) {
            return this;
        }
        this.params.add(new BasicNameValuePair(key, value));
        return this;
    }

    public Forms update(String key, String value) {
        this.remove(key);
        this.add(key, value);
        return this;
    }

    public void remove(String key) {
        if (key == null) {
            return;
        }
        List<NameValuePair> delParams = new ArrayList<NameValuePair>();
        for (NameValuePair param : params) {
            if (param.getName().equals(key)) {
                delParams.add(param);
            }
        }
        if (ContainerUtils.notEmpty(delParams)) {
            params.removeAll(delParams);
        }

    }

    public List<NameValuePair> get() {
        return params;
    }

    public String getName(int index) {
        return params.get(index).getName();
    }

    public String getValue(int index) {
        return params.get(index).getValue();
    }

    @Override
    public String toString() {
        if (params.size() < 1) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(params.get(0).getName()).append("=").append(params.get(0).getValue());
        for (int i = 1; i < params.size(); i++) {
            sb.append("&").append(params.get(i).getName()).append("=").append(params.get(i).getValue());
        }
        return sb.toString();// sb.toString().replaceFirst("&", "");
    }
    
    /**
     * 如果charset不为空，返回表单的值是用charset编码过的，否则不进行编码
     * @param charset
     * @return 
     */
     public String toString(String charset) {
        if (params.size() < 1) {
            return "";
        }
         if (StringUtils.isEmpty(charset)) {
             return toString();
         }

        StringBuilder sb = new StringBuilder();
        sb.append(params.get(0).getName()).append("=").append(URLCoderUtil.encode(params.get(0).getValue(),charset));
        for (int i = 1; i < params.size(); i++) {
            sb.append("&").append(params.get(i).getName()).append("=").append(URLCoderUtil.encode(params.get(i).getValue(),charset));
        }
        return sb.toString();// sb.toString().replaceFirst("&", "");
    }

     
    public String toFormatString() {
        if (params.size() < 1) {
            return "";
        }

        StringBuilder sb = toStringBuilder();
        return sb.toString();
    }

    private StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(params.get(0).getName()).append("=").append(params.get(0).getValue());//.append("\n");
        for (int i = 1; i < params.size(); i++) {
            sb.append("&").append(params.get(i).getName()).append("=").append(params.get(i).getValue());//.append("\n");
        }
        return sb;
    }

    /**
     * 返回StringBuilder形式表示，如果encode为null或者为""就不会对表单的值编码，否则返回的表单值是用encode编码过的
     * @param charset
     * @return 
     */
    public StringBuilder toStringBuilder(String charset) {
        if (params.size() < 1) {
            return null;
        }
        if (StringUtils.isEmpty(charset)) {
            return  toStringBuilder();
        }
       StringBuilder sb= new StringBuilder();
         sb.append(params.get(0).getName()).append("=").append(URLCoderUtil.encode(params.get(0).getValue(), charset));
        for (int i = 1; i < params.size(); i++) {
            sb.append("&").append(params.get(i).getName()).append("=").append(URLCoderUtil.encode(params.get(i).getValue(), charset));
        }
        return sb;
    }

    public boolean contains(String key) {
        if (this.toString() == null || key == null || key.equals("")) {
            return false;
        }
        return this.toString().toLowerCase().contains(new StringBuilder("&").append(key.toLowerCase()).append("="));
    }

    public int size() {
        return this.params.size();
    }

    public static void main(String[] args) {
        Forms form = new Forms();
        form.add("username=", "52454");
        form.add("&action=", "3543536");
        System.out.println(form.toString());

    }
}
