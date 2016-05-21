/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.service.impl;

import com.wangzhe.beans.ConfigParam;
import com.wangzhe.service.MailAddrGraber;
import com.wangzhe.util.FileUtils;
import com.wangzhe.util.RegexUtil;
import java.util.List;

/**
 *
 * @author ocq
 */
public class LedWangImpl extends MailAddrGraber {

    public LedWangImpl() {
        siteUrl = "http://www.cnledw.com/";
    }

    @Override
    protected String[] getMailAddr(ConfigParam cp) {
        ;
        for (int i = Integer.valueOf(FileUtils.readTxtFile(progressFilePath).trim()); i < 886188864; i++) {
            FileUtils.writeToTxtFile("", progressFilePath, String.valueOf(i), false);

            final String requestUrl = "http://www.cnledw.com/free/?id=" + i;
            doHttpGet(requestUrl);
            if (getUserHomeFail()) {
                log.info("用户：" + i + "不存在");
                continue;
            }
            getAndSaveMailAddr(i, requestUrl, cp);
        }
        return null;
    }

    protected List<String>  getEmailFromWebpageContent(){
        webpageContent = webpageContent.replaceAll("<em>", "");
        webpageContent = webpageContent.replaceAll("</em>", "");
        return super.getEmailFromWebpageContent();
    }

    private boolean getUserHomeFail() {
        return webpageContent == null || webpageContent.indexOf("没有相关用户信息") > -1 || webpageContent.indexOf("用户未通过企业审核") > -1;
    }

}
