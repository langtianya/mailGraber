/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

/**
 *
 * @author ocq
 */
public class CodeUtil {

    public static String tranCode(String str) {
        return str.replaceAll("&amp;", "&").replaceAll("\\s", "%20");
    }

    public static String replaceRegexKeyWord(String str) {
        return str.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
    }
}
