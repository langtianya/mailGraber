package com.wangzhe.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author ocq
 */
public class FileUtils {
    
    private static final Logger log = Logger.getLogger(FileUtils.class.getName());

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取除后缀的名称
     *
     * @return
     */
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    
    public static File getFile(String fileUrl) {
        File saveFile = new File(fileUrl);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        return saveFile;
    }
    
    public static File getNewFileName(File file, String fileName) {
        return getNewFileName(file.getPath(), fileName);
    }

    /**
     * 获取文件夹中不重复的命名
     *
     * @param url
     * @param filename 如abc.txt
     * @return
     */
    public static File getNewFileName(String url, String filename) {
        String Suffix = getFileSuffix(filename);
        filename = getFileName(filename);
        return getNewFileName(url, filename, Suffix);
    }

    /**
     *
     * @param url
     * @param filename 如 abc
     * @param Suffix 如 .txt
     * @return
     */
    public static File getNewFileName(String url, String filename, String Suffix) {
        int i = 0;
        File outFile = null;
        while (true) {
            if (i == 0) {
                outFile = new File(url + "/" + filename + Suffix);
            } else {
                outFile = new File(url + "/" + filename + "(" + i + ")" + Suffix);
            }
            if (outFile.exists()) {
                i++;
            } else {
                break;
            }
        }
        return outFile;
    }

    /**
     * 获取后缀名
     *
     * @param fileName
     * @return 如.txt
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    public static boolean saveTxtFile(String fileUrl, String body) {
        return saveTxtFile(new File(fileUrl), body);
    }
    
    public static boolean saveTxtFile(File file, String body) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file, "utf-8");
            pw.append(body);
            pw.flush();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            log.error(ex);
            return false;
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return true;
    }
    
    public static String readTxtFile(String filePath) {
        return readTxtFile(filePath, CharacterEnding.getFileCharacterEnding(filePath));
    }
    
    public static String readTxtFile(String filePath, String encoding) {
        final String file = filePath+".txt";
        return readTxtFile(new File(file), encoding);
    }
    
    public static String readTxtFile(File file) {
        return readTxtFile(file, CharacterEnding.getFileCharacterEnding(file));
    }
    
    public static String readTxtFile(File file, String encoding) {
        StringBuilder sb = new StringBuilder();
        try {
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    sb.append(temp).append("\n");
                }
                read.close();
                bufferedReader.close();
                read.close();
            } else {
                log.error("\u627e\u4e0d\u5230\u6307\u5b9a\u7684\u6587\u4ef6");
                return null;
            }
        } catch (IOException ex) {
            log.error(ex);
        }
        return sb.toString();
    }
    
    public static List readTxtFileList(String filePath) {
        return readTxtFileList(filePath, CharacterEnding.getFileCharacterEnding(filePath));
    }
    
    public static List readTxtFileList(String filePath, String encoding) {
        List<String> list = new ArrayList<>();
        try {
            filePath+=".txt";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = null;
                if (encoding.equalsIgnoreCase("utf-8")) {
                    read = IOStreamUtil.getUtf8Charset(file);
                } else {
                    read = new InputStreamReader(new FileInputStream(file), encoding);
                }
                BufferedReader bufferedReader = new BufferedReader(read);
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    list.add(temp);
                }
                read.close();
                bufferedReader.close();
            } else {
                log.error("\u627e\u4e0d\u5230\u6307\u5b9a\u7684\u6587\u4ef6");
                return null;
            }
        } catch (IOException ex) {
            log.error(ex);
        }
        return list;
    }
    
    public static String getAvatarUrl() {
        File folder = new File(Constants.avatarUrl);
        File[] subfiles = folder.listFiles();
        return subfiles[NumberUtils.getRandomNumByMax(subfiles.length)].getName();
    }
    
    public static String getPhotoUrl(String folderUrl) {
        File folder = new File(folderUrl);
        File[] subfiles = folder.listFiles();
        return subfiles[NumberUtils.getRandomNumByMax(subfiles.length)].getName();
    }
    
    public static synchronized void writeToFile(String filePath, String content) {
        if (StringUtils.isOneEmpty(filePath, content)) {
            return;
        }
        
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            Writer out = new FileWriter(file, true);
            out.write(content);
            out.flush();
            out.close();
            
        } catch (IOException ex) {
            log.error(ex);
        }
    }
    
    public static synchronized void writeLog(String fileName, String content) {
        writeToTxtFile("log/", fileName, content);
    }
    
    public static synchronized void writeToTxtFile(String fileName, List<String> content) {
        if (content == null) {
            return;
        }
        for (int i = 0; i < content.size(); i++) {
            writeLog(fileName, content.get(i));
        }
    }

//     public static synchronized void writeToTxtFile(String fileUrl, List<String> list) {
//        if (fileUrl == null || list == null) {
//            return;
//        }
//        for (String str : list) {
//            writeToTxtFile(fileUrl, str);
//        }
//    }
    public static synchronized void writeToTxtFile(String fileName, String content) {
        writeToTxtFile("", fileName, content);
    }
    
    public static synchronized void writeToTxtFile(String fatherDir, String fileName, String content) {
        writeToTxtFile(fatherDir, fileName, content, true);
    }

    public static synchronized void writeToTxtFile(String fatherDir, String fileName, String content, boolean isAppend) {
        if (StringUtils.isOneEmpty(fileName, content)) {
            return;
        }
        if (fatherDir == null) {
            fatherDir = "";
        }
        try {
            File file = new File(fatherDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            fileName = fatherDir.concat(fileName).concat(".txt");
            // 字节写入文件末尾处
            FileOutputStream fos = new FileOutputStream(fileName, isAppend);
            fos.write(content.concat("\r\n").getBytes("UTF8"));
            fos.close();
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            FileUtils.writeToTxtFile("aa", "sksssssssss");
        }
        
    }
    
}
