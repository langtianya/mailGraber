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
public class PageNumber {

    public static int getYahooPageNumber(int pageNumber) {
        return pageNumber * 10 + 1;
    }

    public static int getGooglePageNumber(int pageNumber) {
        return pageNumber * 10;
    }

    public static int getBaiDuPageNumber(int pageNumber) {
        return pageNumber * 100;
    }
}
