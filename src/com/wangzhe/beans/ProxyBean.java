package com.wangzhe.beans;

import java.net.Proxy;

/**
 *
 * @author ocq
 */
public class ProxyBean {

    private int id;
    private String host;
    private int port;
    private String userID;
    private String pwd;
    private String source;
    private String time_stamp;
    private String serverType;
    private Proxy.Type type;

//    public enum Type {
//        HTTP,
//        SOCKS
//    };
    public ProxyBean() {
    }

    public ProxyBean(String host, int port, String userID, String pwd, String source) {
        this.host = host;
        this.port = port;
        this.userID = userID;
        this.pwd = pwd;
        this.source = source;
    }

    public ProxyBean(String host, int port, String source) {
        this.host = host;
        this.port = port;
        this.source = source;
    }

    public ProxyBean(String host, int port, Proxy.Type type) {
        this.host = host;
        this.port = port;
        this.type = type;

    }

    public ProxyBean(String host, int port, String userID, String pwd, Proxy.Type type) {
        this.host = host;
        this.port = port;
        this.userID = userID;
        this.pwd = pwd;
        this.type = type;
    }

    public ProxyBean(int id, String host, int port, String userID, String pwd, String source, String time_stamp, Proxy.Type type) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.userID = userID;
        this.pwd = pwd;
        this.source = source;
        this.time_stamp = time_stamp;
        this.type = type;
    }

    public ProxyBean(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return "主机地址" + getHost() + "，端口" + getPort() + ",用户名" + getUserID() + ",密码" + getPwd();
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
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
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

    /**
     * @return the serverType
     */
    public String getServerType() {
        return serverType;
    }

    /**
     * @param serverType the serverType to set
     */
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public Proxy.Type getType() {
        if (type == null) {
            return Proxy.Type.HTTP;//默认是http类型
        }
        return type;
    }

    public void setType(Proxy.Type type) {
        this.type = type;
    }

}
