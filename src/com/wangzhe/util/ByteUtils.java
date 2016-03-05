/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author ocq
 */
public class ByteUtils {

    /**
     * 连接两个字节数组，返回连接好的新字节数组
     *
     * @param bytes1
     * @param bytes2
     * @return
     */
    private static byte[] concat(byte[] bytes1, byte[] bytes2) {
        byte[] big = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, big, 0, bytes1.length);
        System.arraycopy(bytes2, 0, big, bytes1.length, bytes2.length);
        return big;
    }

    /**
     * 把原来的字节数组扩展两倍大小后返回
     *
     * @param bytes
     * @return
     */
    public static byte[] extend(byte[] bytes) {
        byte[] big = new byte[bytes.length * 2];
        System.arraycopy(bytes, 0, big, 0, bytes.length);
        bytes = null;
        return big;
    }

    /**
     * 计算一个字节数组的Md5值
     *
     * @param bytes
     * @return
     */
    private static byte[] md5(byte[] bytes) {
        MessageDigest dist = null;
        byte[] result = null;
        try {
            dist = MessageDigest.getInstance("MD5");
            result = dist.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }

    /**
     * 把字节数组转换为16进制表示的字符串
     *
     * @param b
     * @return
     */
    private static String byte2HexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        char[] hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        if (b == null) {
            return "null";
        }

        int offset = 0;
        int len = b.length;

        // 检查索引范围
        int end = offset + len;
        if (end > b.length) {
            end = b.length;
        }

        sb.delete(0, sb.length());
        for (int i = offset; i < end; i++) {
            sb.append(hex[(b[i] & 0xF0) >>> 4]).append(hex[b[i] & 0xF]);
        }
        return sb.toString();
    }

    /**
     * 把整形数转换为字节数组
     *
     * @param i 描述
     * @return 描述
     */
    public static byte[] long2bytes(long i) {
        byte[] b = new byte[8];
        for (int m = 0; m < 8; m++, i >>= 8) {
            b[7 - m] = (byte) (i & 0x000000FF); // 奇怪, 在C# 整型数是低字节在前 byte[]
            // bytes =
            // BitConverter.GetBytes(i);
            // 而在JAVA里，是高字节在前
        }
        return b;
    }

    /**
     * 把一个16进制字符串转换为字节数组，字符串没有空格，所以每两个字符 一个字节
     *
     * @param s 描述
     * @return 描述
     */
    public static byte[] hexString2Byte(String s) {
        int len = s.length();
        byte[] ret = new byte[len >>> 1];
        for (int i = 0; i <= len - 2; i += 2) {
            ret[i >>> 1] = (byte) (Integer.parseInt(s.substring(i, i + 2)
                    .trim(), 16) & 0xFF);
        }
        return ret;
    }

    /**
     * 最大限制为3m内容大小
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static byte[] readDataFromInputStream(InputStream is) throws IOException {
        if (is == null) {
            return null;
        }
        int nowSize = 152 * 1024;
        byte[] byteBuf = new byte[nowSize];
        try {
            int readSize = 0;
            while (true) {
                int num = is.read();
                if (num == -1) {
                    break;
                }
                if (readSize >= nowSize) {
                    byteBuf = ByteUtils.extend(byteBuf);
                    nowSize = byteBuf.length;
                }
                byteBuf[readSize++] = (byte) num;
                if (readSize > 3 * 1024 * 1024) {
                    break;
                }
            }
            if (readSize < 1) {
                return null;
            }
            if (readSize == nowSize) {
                return byteBuf;
            }
            byte[] res = new byte[readSize];
            System.arraycopy(byteBuf, 0, res, 0, readSize);
            return res;
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    /**
     * 读取GZIPInputStream流中的数据
     * @param zis
     * @return
     * @throws IOException 
     */
    public static  byte[] readDataFromGZIPInputStream(GZIPInputStream zis) throws IOException {
        try {
            byte byteBuf[] = new byte[152 * 1024];
            int size = 0;
            while (true) {
                int readed = zis.read(byteBuf, size, byteBuf.length - size);
                if (readed < 0) {
                    break;
                }
                size += readed;
                if (size == byteBuf.length) {
                    byteBuf = ByteUtils.extend(byteBuf);
                }
            }
            if (size < 1) {
                return null;
            }
            byte[] ret = new byte[size];
            System.arraycopy(byteBuf, 0, ret, 0, size);
            return ret;
        } finally {
            zis.close();
        }
    }
    
    
     public static byte[] readBytesFromHttpConnection(HttpURLConnection con) {
        return readDataFromURLConnection(con, true, 2 * 1024 * 1024);
    }
      public static byte[] readAllDataFromInputStream(InputStream is, int preferSize) throws Exception {
        return readAllDataFromInputStream(is, 0, 2 * 1024 * 1024);
    }

    public static byte[] readAllDataFromInputStream(InputStream is, int preferSize, int maxSize) throws Exception {
        if (is == null) {
            return null;
        }
        if (preferSize < 1) {
            preferSize = 50 * 1024;
        }
        byte buf[] = new byte[preferSize];
        int readed = 0;
        while (true) {
            int c = is.read();
            if (c == -1) {
                break;
            }
            if (readed >= preferSize) {
                buf = ByteUtils.extend(buf);
                preferSize = buf.length;
            }
            buf[readed++] = (byte) c;

            //长度超过1M的就过滤掉，避免照成内存溢出
            if (maxSize > 0 && readed > maxSize) {
                return null;
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
        if (readed < 1) {
            return null;
        }

        if (readed == preferSize) {
            return buf;
        }
        byte[] ret = new byte[readed];
        System.arraycopy(buf, 0, ret, 0, readed);
        buf = null;

        return ret;
    }

    public static byte[] readDataFromURLConnection(HttpURLConnection con, boolean close) {
        return readDataFromURLConnection(con, close, 2 * 1024 * 1024);
    }

    public static byte[] readDataFromURLConnection(HttpURLConnection con, boolean close, int MAX_SIZE) {
        try {
            int contentLength = 50 * 1024;
            InputStream is = null;

            try {
                String l = con.getHeaderField("Content-Length");
                if (l != null) {
                    contentLength = Integer.parseInt(l);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (MAX_SIZE > 0 && contentLength > MAX_SIZE) {
                URL url = con.getURL();
                String host = null;
                if (url != null) {
                    host = url.getHost();
                }
            }
            is = con.getInputStream();
            if (con.getHeaderField("Content-Encoding") != null) {
                if (con.getHeaderField("Content-Encoding").equalsIgnoreCase("gzip")) {
                    return ByteUtils.readDataFromGZIPInputStream(new GZIPInputStream(is));
                }
            }
            return readAllDataFromInputStream(is, contentLength, MAX_SIZE);
        } catch (Exception ex) {
            System.err.println(ex.toString());
            //ex.printStackTrace();
        } finally {
            if (close) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                }
            }
        }

        return null;
    }
}
