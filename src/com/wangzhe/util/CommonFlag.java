/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

/**
 * 常用标示符
 *
 * @author ocq
 */
public class CommonFlag {

    private static final String[] regSuccess = new String[]{"", ""};//注册成功
    private static final String[] userNameUsered = new String[]{"", ""};//用户名已使用
    private static final String[] passwordWeak = new String[]{"", ""};//密码太弱
    private static final String[] emailUsered = new String[]{"", ""};//邮箱已使用
    private static final String[] regClosed = new String[]{"registration is currently disabled", "registrations have been disabled"};//注册关闭
    private static final String[] regFast = new String[]{"", ""};//频繁注册

    private static final String[] loginSuccess = new String[]{"", ""};//登录成功
    private static final String[] loginFast = new String[]{"", ""};//频繁登录
    private static final String[] usernameOrPassWrong = new String[]{"invalid username/password", "Benutzername und/oder das Passwort ist falsch"};//用户名或密码错
    private static final String[] accountBlocked = new String[]{"", ""};//账号被禁
    private static final String[] accountWaitActive = new String[]{"", ""};//账号待激活
    private static final String[] accountWaitAudit = new String[]{"", ""};//账号待审核

    private static final String[] publishSuccess = new String[]{"", ""};//发布成功
    private static final String[] artTitleTooSort = new String[]{"", ""};//标题太短
    private static final String[] artTitleTooLong = new String[]{"", ""};//标题太长
    private static final String[] artContentTooSort = new String[]{"", ""};//文章内容太短
    private static final String[] artContentTooLong = new String[]{"", ""};//文章内容太长
    private static final String[] artContentHaveBadWord = new String[]{"", ""};//包含违禁词
    private static final String[] cannotPublishHyperlink = new String[]{"", ""};//不能带超链接
    private static final String[] publishWaitAudit = new String[]{"", ""};//发布待审核
    private static final String[] publishFast = new String[]{"", ""};//频繁发布
    private static final String[] isFourmClose = new String[]{"forums are currently closed for", ""};//论坛关闭

    private static final String[] captchaError = new String[]{"", ""};//验证码识别错误
    private static final String[] answerError = new String[]{"", ""};//验证回答问题错误
    private static final String[] ipBaneded = new String[]{"", ""};//ip被禁止

    private static final String[] isOpenUrlFail = new String[]{"the page you were looking for in this blog does not exist", ""};//打开url地址是否成功
    private static final String[] qqqq = new String[]{"", ""};//

    public static boolean isRegSuccess(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : regSuccess) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUserNameUsered(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : userNameUsered) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPasswordWeak(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : passwordWeak) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmailUsered(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : emailUsered) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRegClosed(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : regClosed) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRegFast(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : regFast) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLoginSuccess(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : loginSuccess) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLoginFast(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : loginFast) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUsernameOrPassWrong(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : usernameOrPassWrong) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAccountBlocked(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : accountBlocked) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAccountWaitActive(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : accountWaitActive) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAccountWaitAudit(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : accountWaitAudit) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPublishSuccess(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : publishSuccess) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArtTitleTooSort(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : artTitleTooSort) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArtTitleTooLong(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : artTitleTooLong) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArtContentTooSort(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : artContentTooSort) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArtContentTooLong(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : artContentTooLong) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArtContentHaveBadWord(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : artContentHaveBadWord) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCannotPublishHyperlink(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : cannotPublishHyperlink) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPublishWaitAudit(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : publishWaitAudit) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPublishFast(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : publishFast) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCaptchaError(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : captchaError) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnswerError(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : answerError) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIpBaneded(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : ipBaneded) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOpenUrlFail 
        (String srcContent) {
        Assert.isNull(srcContent);
        for (String str :isOpenUrlFail) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFourmClose(String srcContent) {
        Assert.isNull(srcContent);
        for (String str : isFourmClose) {
            if (srcContent.contains(str)) {
                return true;
            }
        }
        return false;
    }

}
