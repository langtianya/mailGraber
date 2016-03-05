package com.wangzhe.service;

import com.wangzhe.util.Commons;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import org.apache.log4j.Logger;

/**
 * 要做成单例
 *
 * @author ocq
 */
public class AdslHandler {

    private static Logger log = Logger.getLogger(AdslHandler.class.getName());
    private int successCount = 0;
    private static AdslHandler instance;
    private boolean isSuccess;

    private AdslHandler() {
    }

    public static AdslHandler getInstance() {
        if (instance == null) {
            instance = new AdslHandler();
        }
        return instance;
    }

    public static void destory() {
        instance = null;
    }

    public boolean connect(String username, String password) {
        if (true) {
            return true;
        }
        try {
            Process adslProcess = Runtime.getRuntime().exec("rasdial wanghzeadsl " + username + " " + password);
            BufferedReader br = new BufferedReader(new InputStreamReader(adslProcess.getInputStream()));
            String msg;
            while (true) {
                msg = br.readLine();
                if (msg == null) {
                    break;
                }
                msg = msg.trim();
                if (msg.length() < 1) {
                    continue;
                }
                msg = msg.toLowerCase();
                //成功后跳出
                if (msg.indexOf("已连接") > -1 || msg.indexOf("connected") > -1) {
                    successCount++;
                    isSuccess = true;
                    return true;
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return false;
    }

    public boolean disConnect() {

        try {
            Process process = Runtime.getRuntime().exec("rasdial /DISCONNECT");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                line = br.readLine();
                //成功后跳出
                if (line == null) {
                    return true;
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        } finally {

        }
        return false;
    }

    public static String getIp() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces != null && networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual()) {
                    continue;
                }
                String dispName = networkInterface.getDisplayName();
                String name = networkInterface.getName();

                //ppp拨号虚拟网卡
                if ((dispName != null && (dispName.indexOf("PPP") > -1 || dispName.indexOf("SLIP") > -1)) || (name != null && name.startsWith("ppp"))) {
                    Enumeration inetAddresses = networkInterface.getInetAddresses();
                    if (!inetAddresses.hasMoreElements()) {
                        continue;
                    }
                    return ((InetAddress) inetAddresses.nextElement()).getHostAddress();
                }
            }

        } catch (Exception ex) {
            log.error(ex);
        }

        return null;
    }
    public String currentIp;
    public HashSet ips = new HashSet(500);

    public boolean reDialForNewIp(int tryCount, String username, String password) {
        try {
            if (!isSuccess) {
                return false;
            }

            for (int tryTimes = 0; tryTimes < tryCount; tryTimes++) {
                //断开连接
                disConnect();
                //尝试连接
                if (connect(username, password)) {
                    currentIp = getIp();

                    if (!ips.contains(currentIp)) {
                        ips.add(currentIp);
                        return true;
                    }
                }
                //10秒尝试拨号一次
                Commons.sleepSecond(10);
            }

            log.info("连续" + tryCount + "次拨号仍然没找到新IP，可能您的路由IP已经用完");

        } catch (Exception ex) {
            log.error(ex);
        }
        return false;
    }

    private static String getDialName() {
        try {
            Process process = Runtime.getRuntime().exec("rasdial");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String msg;
            boolean connected = false;

            while (true) {
                msg = br.readLine();
                if (msg == null) {
                    break;
                }
                msg = msg.trim();
                if (msg.length() < 1) {
                    continue;
                }

                msg = msg.toLowerCase();
                if (msg.equalsIgnoreCase("已连接") || msg.indexOf("connected") > -1) {
                    connected = true;
                    continue;
                }

                if (connected) {
                    return msg;
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }

        return null;
    }
}
