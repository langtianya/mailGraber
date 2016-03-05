package com.wangzhe.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 生成：单词，句子，句子标点，段落，文章 单词长度：2-10个字母 句子长度：8-20个单词 段落数：4-6段 标点符号：, . ? !
 *
 * @author ocq
 */
public class ArticleUtil {

    private static Random random = new Random();

//    public static void main(String[] args) {
//        //System.out.println("【随机单词为】：" + createWord());
//        //System.out.println("【随机标点符号为】：" + createMark());
//        //System.out.println("【随机句子为】：" + createSentence());
//        //System.out.println("【随机段落为】：" + createParagraph());
//        //System.out.println("【随机文章为】：" + createArticle());
//        //System.out.println("【随机大写字母为】：" + createUpperCase());
//        //System.out.println(createSentence(3, 2));
//    }

    /**
     * （3-6段）创建文章
     *
     * @return 描述
     */
    public static String createArticle() {
        int randomNum = random.nextInt(3 + 1);//取随机整数
        int aLength = 3 + randomNum;//随机文章长度（3-6段）

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aLength; i++) {
            String article = createParagraph();
            sb.append(article);
        }
        return sb.toString();
    }

    /**
     * 创建段落（4-9）段落每行80个字母
     *
     * @return 描述
     */
    public static String createParagraph() {
        int minpLength = 4;//最小句子长度
        int maxpLength = 9;//最大句子长度
        String paragraph = null;//段落

        StringBuilder sb = new StringBuilder();

        int length = maxpLength - minpLength;//最大句子数和最小句子数之差
        int randomNum = random.nextInt(length + 1);//差值之间取随机整数
        int pLength = minpLength + randomNum;//计算得到的随机句子长度（包括4和9）
        for (int i = 0; i < pLength; i++) {
            String sentence = createSentence();
            sb.append(sentence);
        }

        //paragraph = "&nbsp;&nbsp;&nbsp;&nbsp;" + sb.toString();//每段之前加空格
        paragraph = sb.toString();//每段之前加空格
        String str = paragraph;
        List<String> strList = new ArrayList<>();

        //     System.out.println();
//        while (str.toCharArray().length > 80) {//当每行大于80个英文字符时，加上\n
//            String str_new = str.substring(0, 80);
        //str_new += "<br/>";
        //System.out.println(str_new);
//            str = str.substring(80, str.length());
//            strList.add(str_new);
//        }
        strList.add(str + "<br/>");//加上最后一句
        return Commons.listToString(strList);//返回
    }

    /**
     * 创建句子（5-10范围内随机生成）
     *
     * @return 描述
     */
    public static String createSentence() {
        int minSentenceLength = 5;//最小句子长度
        int maxSentenceLength = 10;//最大句子长度
        String sentence = null;

        StringBuilder sb = new StringBuilder();

        int length = maxSentenceLength - minSentenceLength;//最大单词数和最小单词数之差
        int randomNum = random.nextInt(length + 1);//差值之间取随机整数
        int sentenceLength = minSentenceLength + randomNum;//计算得到的随机句子长度（包括8和20）
        for (int i = 0; i < sentenceLength; i++) {
            String word = createWord();
            word += " ";
            sb.append(word);
        }
        sentence = sb.toString();
        String str = "";
        if (sentence.endsWith(" ")) {
            int lastindex = sentence.lastIndexOf(" ");
            str = StringUtils.substring(sentence, 0, lastindex);
            str += createMark();
            str = createUpperCase() + str;
            //System.out.println("首字母 ：" + str.charAt(0));
        }
        return str;
    }

    /**
     * 创建句子（5-10范围内随机生成）
     *
     * @return 描述
     */
    public static String createSentence(int maxSentenceLength, int minSentenceLength) {
        //minSentenceLength最小句子长度
        //maxSentenceLength最大句子长度
        String sentence = null;

        StringBuilder sb = new StringBuilder();

        int length = maxSentenceLength - minSentenceLength;//最大单词数和最小单词数之差
        int randomNum = random.nextInt(length + 1);//差值之间取随机整数
        int sentenceLength = minSentenceLength + randomNum;//计算得到的随机句子长度（包括8和20）
        for (int i = 0; i < sentenceLength; i++) {
            String word = createWord();
            word += " ";
            sb.append(word);
        }
        sentence = sb.toString();
        String str = "";
        if (sentence.endsWith(" ")) {
            int lastindex = sentence.lastIndexOf(" ");
            str = StringUtils.substring(sentence, 0, lastindex);
            str += createMark();
            str = createUpperCase() + str;
            //System.out.println("首字母 ：" + str.charAt(0));
        }
        return str;
    }

    /**
     * 创建单词（2-10范围内随机生成）
     *
     * @return 描述
     */
    public static String createWord() {
        int minWordLength = 2;//最小长单词度
        int maxWordLength = 10;//最大单词长度
        String randomLengthStr = null;
        int length = maxWordLength - minWordLength;//最大长度和最小长度之差
        //System.out.println("length:" + length);

        int randomNum = random.nextInt(length + 1);//差值之间取随机整数
        //System.out.println("randomNum:" + randomNum);
        int wordLength = minWordLength + randomNum;//计算得到的随机单词长度（最小长度 + 差值随机数）
        String base = "abcdefghijklmnopqrstuvwxyz";
        if (wordLength > 0) {
            //System.out.println("wordLength:" + wordLength);
            String str = RandomStringUtils.random(wordLength, base);//获得随机长度的String
            if (str != null) {
                randomLengthStr = str;
            }
        }
        if (randomLengthStr.equals("usa")) {//如果单词为usa，则改为USA
            randomLengthStr = "USA";
        }
        if (randomLengthStr.equals("china")) {//如果单词为china，则改为China
            randomLengthStr = "China";
        }
        return randomLengthStr;
    }

    /**
     * 随机26个大写字母，生成一个
     *
     * @return 描述
     */
    public static String createUpperCase() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String randomLengthStr = RandomStringUtils.random(1, base);//获得随机长度的String
        return randomLengthStr;
    }

    /**
     * 随机获得英文标点符号
     *
     * @return 描述
     */
    public static String createMark() {
        String base = ".?!";

        String randomMark = RandomStringUtils.random(1, base);//获得随机长度的String
        return randomMark;
    }

}
