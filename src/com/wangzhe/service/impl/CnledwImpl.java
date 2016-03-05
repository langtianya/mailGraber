/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.service.impl;

import com.wangzhe.service.MailAddrGraber;

/**
 *
 * @author ocq
 */
public class CnledwImpl extends MailAddrGraber {

    public CnledwImpl() {
    }

    @Override
    protected String[] getMailAddr(String[] urls, String[] keywords) {
        for (int i = 0; i < 6188864; i++) {
            doHttpGet("http://www.cnledw.com/free/?id="+i);
            if (getUserHomeFail()) {
                continue;
            }
            getAndSaveEmail();
        }
        return null;
    }

    private boolean getUserHomeFail() {
        return webpageContent==null||webpageContent.indexOf("没有相关用户信息")>-1||webpageContent.indexOf("用户未通过企业审核")>-1;
    }

}
