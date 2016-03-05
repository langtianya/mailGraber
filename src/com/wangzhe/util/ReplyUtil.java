/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wangzhe.util;

import com.wangzhe.util.RegexUtil;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author ocq
 */
public class ReplyUtil {

    /**
     * list去重
     *
     * @param list 描述
     * @return list 描述
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 根据版本和回帖版块页获得最大页码数
     *
     * @param fid 描述
     * @param page 回帖版块页
     * @return 最大页码数
     */
    public static int getMaxPageNum(String fid, String page) {
        int maxPageNum = 0;
        List<String> pageNumList = getPageNumList(fid, page);
        if (pageNumList != null) {

            int[] pna = null;//pageNum数组
            if (pageNumList == null || pageNumList.isEmpty()) {
                System.out.println("没有找到页数参数page");
            } else {
                String[] pageNumArray = (String[]) pageNumList.toArray(new String[pageNumList.size()]);//list转为String数组
                pna = new int[pageNumArray.length];
                for (int i = 0; i < pageNumArray.length; i++) {
                    pna[i] = Integer.parseInt(pageNumArray[i]);
                }
                if (pna.length > 0) {
                    int a = pna[0];
                    for (int i = 0; i < pna.length; i++) {
                        if (pna[i] > a) {
                            a = pna[i];
                            maxPageNum = a;
                        }
                    }
                }
            }
        }
        return maxPageNum;
    }

    /**
     * 根据版本和回帖版块页获得页码集合list
     *
     * @param fid 描述
     * @param page 回帖版块页 
     * @return 描述
     */
    public static List<String> getPageNumList(String fid, String page) {
        List<String> pageNumList = null;
        if (!page.contains("forum-" + fid)) {
            pageNumList = RegexUtil.getList("fid=" + fid + "(?:&|&amp;)page=(\\d+)", page);
        }
        if (pageNumList == null) {
            pageNumList = RegexUtil.getList("forum-" + fid + "-(\\d+)", page);
        }
        if (pageNumList == null) {
            pageNumList = RegexUtil.getList("f-" + fid + "-(\\d+)", page);
        }
        //System.out.println("【pageNumList.size】："+pageNumList.size());
        return pageNumList;
    }
}
