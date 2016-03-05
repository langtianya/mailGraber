package com.wangzhe.util;

import com.wangzhe.beans.SiteBean;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author ocq
 */
public class ContainerUtils {

    public static boolean isContainsOne(Collection colle, String source) {
        Assert.notEmpty(colle, source);
        Assert.notNull(source);
        Iterator iter = colle.iterator();
        while (iter.hasNext()) {
            String string = (String) iter.next();
            if (source.indexOf(string) > -1) {
                System.out.println("【包含的标识为：】 【" + string + "】");
                return true;
            }
        }
        return false;
    }

    /**
     * 去重复的元素
     *
     * @param list
     * @return
     */
    public static List<String> removeRepeat(List<String> list) {
        if (ContainerUtils.isEmpty(list)) {
            return list;
        }
//            Assert.notEmpty(list);
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取一个随机的元素
     *
     * @param list
     * @return
     */
    public static String getRandomElement(List<String> list) {
        if (list == null) {
            return null;
        }
        removeRepeat(list);
        return list.get(NumberUtils.getRandomNumByMax(list.size()));
    }

    /**
     * 获取一个随机的value
     *
     * @param map
     * @return
     */
    public static String getRandomValue(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        return map.get(getRandomKey(map));
    }

    /**
     * 获取一个随机的key
     *
     * @param map
     * @return
     */
    public static String getRandomKey(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        String[] keys = new String[map.keySet().size()];
        map.keySet().toArray(keys);

        return keys[NumberUtils.getRandomNumByMax(map.size())];
    }

    /**
     * 翻转key value的值
     *
     * @param map
     * @return
     */
    public static Map<String, String> reversalKeyValue(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        Map<String, String> resultMap = new HashMap<>(map.size());
        for (String key : map.keySet()) {
            resultMap.put(map.get(key), key);

        }
        return resultMap;
    }

    /**
     * 给定的集合为null则返回true Return <code>true</code> if the supplied Collection is
     * <code>null</code> or empty. Otherwise, return <code>false</code>.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean notEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map... map) {
        for (Map obj : map) {
            if (obj == null || obj.size() == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean notEmpty(Map... map) {
        return !isEmpty(map);
    }

    public static SiteBean[] getSiteArrayFromSet(List<?> srcSet) {
        if (srcSet == null) {
            return null;
        }
        SiteBean[] sitesArr = new SiteBean[srcSet.size()];
        srcSet.toArray(sitesArr);
        return sitesArr;
    }

    public static String getMsgByReguExps(Collection colle, String source) {
        if (StringUtils.isAllEmpty(source)) {
            return null;
        }
        for (Object str : colle) {
            String msg = RegexUtil.getFirstString((String) str, source, Thread.MIN_PRIORITY);
            if (msg != null && msg.length() > 0) {
                return msg;
            }
        }
        return null;
    }

    //一维数组
    public static boolean isCollectionNull(Collection... coll) {
        for (Collection obj : coll) {
            if (obj == null || obj.size() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String[][] mapToStrArr2(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        String[][] res = new String[map.size()][2];
        for (int i = 0; i < map.size(); i++) {
            res[i][0] = it.next();
            res[i][1] = map.get(res[i][0]);
        }
        return res;
    }

    /**
     * 合并两个二维数组为一个
     *
     * @param arr1 String[][]
     * @param arr2 String[][]
     * @return String[][]
     */
    public static String[][] mergeArr2(String[][] srcarr, String[][] newArr) {
        if (ArrayUtils.isEmpty(srcarr)) {
            return newArr;
        }
        if (ArrayUtils.isEmpty(newArr)) {
            return srcarr;
        }
        String[][] res = new String[srcarr.length + newArr.length][2];
        int index = 0;
        for (int i = 0; srcarr != null && i < srcarr.length; i++) {
            res[i][0] = srcarr[i][0];
            res[i][1] = srcarr[i][1];
            index++;
        }
        try {
            for (int i = 0; newArr != null && i < newArr.length; i++) {
                boolean iscontinue = false;
                for (int j = 0; res != null && j < res.length; j++) {
                    if ((res[j][0] != null && newArr[i][0] != null) && (res[j][0].equals(newArr[i][0]) || res[j][0].equals(newArr[i][0])) && !res[j][1].equals("")) {
                        res[index][0] = "";
                        res[index][1] = "";
                        index++;
                        iscontinue = true;
                        continue;
                    }
                }
                if (iscontinue) {
                    continue;
                }
                res[index][0] = newArr[i][0];
                res[index][1] = newArr[i][1];
                index++;
            }
        } catch (Exception ex) {
        }
        return res;
    }
}
