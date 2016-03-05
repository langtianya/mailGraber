/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.service.impl;

import com.wangzhe.beans.ConfigParam;
import com.wangzhe.service.MailAddrGraber;
import com.wangzhe.util.Commons;
import com.wangzhe.util.NumberUtils;

/**
 *
 * @author ocq
 */
public class CnledwImpl extends MailAddrGraber {

    public CnledwImpl() {
        siteUrl="http://www.cnledw.com/";
    }

    @Override
    protected String[] getMailAddr(ConfigParam cp) {
        for (int i = 816; i < 6188864; i++) {
            doHttpGet("http://www.cnledw.com/free/?id="+i);
            if (getUserHomeFail()) {
                log.info("用户id"+i+"不存在");
                continue;
            } 
            appendLog("开始分析用户："+i+"的邮箱地址");
             writeToTxtFile(getEmailFromWebpageContent());
            Commons.sleepSecond(NumberUtils.getRandomNum(cp.getMinSpeed(), cp.getMaxNum()));
        }
        return null;
    }

    private boolean getUserHomeFail() {
        return webpageContent==null||webpageContent.indexOf("没有相关用户信息")>-1||webpageContent.indexOf("用户未通过企业审核")>-1;
    }

}
