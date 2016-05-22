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
import com.wangzhe.util.RegexUtil;
import java.util.List;

/**
 *
 * @author ocq
 */
public class QiyeguCom extends MailAddrGraber {

    @Override
    protected String[] getMailAddr(ConfigParam cp) {

        for (int i = Commons.getProgress(progressFilePath); i < 886188864; i++) {
            FileUtils.writeToTxtFile("", progressFilePath, String.valueOf(i), false);

            final String requestUrl = "http://www.qiyegu.com/company/search.php?kw=&vip=0&type=0&catid=0&mode=0&areaid=0&size=0&mincapital=&maxcapital=&x=68&y=21&page=" + i;
            doHttpGet(requestUrl);
            List<String> companyUrls = RegexUtil.getList("href=[\"'](http://[^\\.]+.qiyegu.com/)[\"'][^>]*><strong", webpageContent);
            if (isRun(companyUrls)) {
                break;
            }
            for (String companyUrl : companyUrls) {
                doHttpGet(companyUrl);
                if (getUserHomeFail()) {
                    log.info("用户：" + i + "不存在");
                    continue;
                }
                getAndSaveMailAddr(i, companyUrl, cp);
            }

        }
        return null;
    }

    

    /**
     * 获取并保存邮箱
     *
     * @return
     */
    @Override
    protected List<String> getEmailFromWebpageContent() {
        final List maillAddrs = RegexUtil.getList("title=\"([a-z0-9A-Z_]{2,}@[^\\.]+\\.com)\"", webpageContent);
        if (Commons.isEmpty(maillAddrs)) {
            return super.getEmailFromWebpageContent();
        }
        return maillAddrs;
    }

    private boolean getUserHomeFail() {
        return webpageContent == null || webpageContent.indexOf("公司不存在") > -1 || webpageContent.indexOf("Not Found") > -1;
    }
}
