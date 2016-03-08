/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.service.impl;

import com.wangzhe.beans.ConfigParam;
import com.wangzhe.service.MailAddrGraber;
import com.wangzhe.util.Commons;
import com.wangzhe.util.FileUtils;
import com.wangzhe.util.NumberUtils;
import com.wangzhe.util.RegexUtil;
import java.util.List;

/**
 *
 * @author ocq
 */
public class LedWangImpl extends MailAddrGraber {
 private static  final String MAIL_ADDR_RELATED = "log/"+LedWangImpl.class.getSimpleName()+"邮箱对应公司信息";
  private static  final String PROGRESS_FILE = "log/"+LedWangImpl.class.getSimpleName()+"进度";
 
    public LedWangImpl() {
        siteUrl = "http://www.cnledw.com/";
    }

    @Override
    protected String[] getMailAddr(ConfigParam cp) {
        ;
        appendLog("开始挖掘.....");
        for (int i = Integer.valueOf(FileUtils.readTxtFile(PROGRESS_FILE).trim()); i < 886188864; i++) {
            FileUtils.writeToTxtFile("", PROGRESS_FILE,String.valueOf(i),false);
            
            final String requestUrl = "http://www.cnledw.com/free/?id=" + i;
            doHttpGet(requestUrl);
            if (getUserHomeFail()) {
                log.info("用户：" + i + "不存在");
                continue;
            }
            appendLog("开始分析用户：" + i + "的邮箱地址");
            final List<String> emails = getEmailFromWebpageContent();
            if (emails!=null&&emails.size()>0) {
             writeToTxtFile(emails);
             writeToTxtFile(emails.toString()+"---"+requestUrl, MAIL_ADDR_RELATED);
            }
            final int sleepTime = NumberUtils.getRandomNum(cp.getMinSpeed(), cp.getMaxSpeed());
            Commons.sleep(sleepTime);
            appendLog("休眠" + sleepTime + "毫秒秒后继续挖掘.....");
        }
        return null;
    }

    private String getCompanyInfo() {
         RegexUtil.getList("([a-z0-9A-Z_]{2,}@[^>]*.com)", webpageContent);
        return getEmailFromWebpageContent().get(0);
    }

    private boolean getUserHomeFail() {
        return webpageContent == null || webpageContent.indexOf("没有相关用户信息") > -1 || webpageContent.indexOf("用户未通过企业审核") > -1;
    }

}
