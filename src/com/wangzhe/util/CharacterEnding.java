package com.wangzhe.util;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;
import java.io.File;
import java.nio.charset.Charset;
import org.apache.log4j.Logger;

public class CharacterEnding {

    private static final Logger log = Logger.getLogger(CharacterEnding.class.getName());
    private static ParsingDetector parsingDetector = new ParsingDetector(false);

    public static String getFileCharacterEnding(String filePath) {
        return getFileCharacterEnding(new File(filePath));
    }

    public static String getFileCharacterEnding(File file) {

        String fileCharacterEnding = "UTF-8";
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(JChardetFacade.getInstance());
        detector.add(UnicodeDetector.getInstance());
        detector.add(new ByteOrderMarkDetector());
        detector.add(ASCIIDetector.getInstance());
        detector.add(parsingDetector);
        Charset charset = null;
        // File f = new File(filePath);  
        try {
            charset = detector.detectCodepage(file.toURI().toURL());
        } catch (Exception ex) {
            log.error(ex);
        }
        if (charset != null) {
            fileCharacterEnding = charset.name();
        }
        return getCharset(fileCharacterEnding);
    }

    public static String getCharset(String fileCharacterEnding) {
        //System.out.println(fileCharacterEnding);
        String charset = fileCharacterEnding;
        if (fileCharacterEnding.equalsIgnoreCase("windows-1252")) {
            charset = "Unicode";
        } else if (fileCharacterEnding.equalsIgnoreCase("Big5")) {
            charset = "GB18030";
        }else if(fileCharacterEnding.equalsIgnoreCase("EUC-KR")){
            charset = "GB2312";
        }
        //log.info(fileCharacterEnding + "/" + charset);
        return charset;
    }

//    public static void main(String[] args) {
//        //File file = new File("C:\\Users\\Administrator\\Desktop\\多媒体文件导出.txt");
//        //String str = getFileCharacterEnding(file);
//        //System.out.println("===========:" + str);
//    }
}
