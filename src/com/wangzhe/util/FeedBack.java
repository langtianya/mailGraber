/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class FeedBack {

    static final Logger log = Logger.getLogger(FeedBack.class.getName());

    /**
     * 上传不能验证的网址信息到服务器
     *
     * @param feedBackType 描述
     * @param file 描述
     */
    public static void sendUnVerification(String feedBackType, File file) {
        doSend(feedBackType, file);
    }

    /**
     * 上传不能解析的网址信息到服务器
     *
     * @param feedBackType 描述
     * @param file 描述
     */
    public static void sendUnAnalysis(String feedBackType, File file) {
        doSend(feedBackType, file);
    }

    /**
     * 上传程序的日志信息到服务器
     *
     * @param feedBackType 描述
     * @param file 描述
     */
    public static void sendLog(String feedBackType, File file) {
        doSend(feedBackType, file);
    }

    /**
     * 上传文件到服务器功能的最终实现类
     */
    private static void doSend(String feedBackType, File file) {
        //  String url = "feedback.cloudssaas.com";
        uploadFile("http://192.168.1.202:6789/cms/upload.do?feedBackType=" + feedBackType, file);
    }
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpPost httppost = new HttpPost("http://localhost:8080/cms/upload.do");
//        File file = new File("d:/e22624.pdf");
//            InputStreamEntity reqEntity = new InputStreamEntity(
//                    new FileInputStream(file), -1, ContentType.APPLICATION_OCTET_STREAM);
//            reqEntity.setChunked(true);
//             httppost.setEntity(reqEntity);
//             httpclient.execute(httppost);
//    }

    /**
     * 上传文件
     *
     * @param uploadPath 上传路径
     * @param file 描述
     */
    public static void uploadFile(String uploadPath, File file) {
        //  System.out.println("开始上传文件。。。");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(uploadPath);

            FileBody fileBody = new FileBody(file);
            // StringBody comment = new StringBody("A binary file of some kind");

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", fileBody)
                    //  .addPart("comment", comment)
                    .build();
            httppost.setEntity(reqEntity);
//            System.out.println("上传文件类型：" + ContentType.get(reqEntity).toString());
//            System.out.println("执行请求： " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
//                System.out.println("----------------------------------------");
//                System.out.println("上传之http状态" + response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
//                    System.out.println("请求后返回内容长度： " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }

    /**
     * 上传文件
     *
     * @param uploadPath 上传路径
     * @param filePath 上传源文件路径
     * @param zipFileName 描述
     * @throws Exception 描述
     */
    public static void uploadZipFile(String uploadPath, String filePath, String zipFileName) throws Exception {
        System.out.println("开始上传二进制文件。。。");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(uploadPath);
            //FileBody file = new FileBody(new File(filePath));
            InputStream inputStream = new FileInputStream(filePath);
            StringBody comment = new StringBody("A binary file of some kind");

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    //.addPart("file", file)
                    .addBinaryBody("upstream", inputStream, ContentType.create("application/zip"), zipFileName)
                    .addPart("comment", comment)
                    .build();

            httppost.setEntity(reqEntity);
            System.out.println("上传文件类型：" + ContentType.get(reqEntity).toString());

            System.out.println("执行请求： " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println("上传之http状态" + response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("请求后返回内容长度： " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
