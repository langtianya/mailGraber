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
public class ZgbfwCom extends MailAddrGraber {

    @Override
    protected String[] getMailAddr(ConfigParam cp) {
        ;

        for (int i = Commons.getProgress(progressFilePath); i < 886188864; i++) {
            FileUtils.writeToTxtFile("", progressFilePath, String.valueOf(i), false);

            final String requestUrl = "http://www.zgbfw.com/shop/CompanyList.aspx?pn=1&page=" + i;
            doHttpGet(requestUrl);
            List<String> companyUrls = RegexUtil.getList("href=[\"'](http://www.zgbfw.com/shop/[^\"']+)[\"']", webpageContent);
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
        final List maillAddrs = RegexUtil.getList(">([a-z0-9A-Z_]{2,}@[^\\.]+\\.com)<", webpageContent);
        if (Commons.isEmpty(maillAddrs)) {
            return super.getEmailFromWebpageContent();
        }
        return maillAddrs;
    }

    private boolean getUserHomeFail() {
        return webpageContent == null || webpageContent.indexOf("无法找到该页") > -1;
    }
}
