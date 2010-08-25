package com.wisdom.example.commons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.FileCopyUtils;

/**
 * 
 * <b>业务说明</b>:<br>
 * 文件工具类
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: May 14, 2008<br>
 * <b>建立时间</b>: 2:50:47 PM<br>
 * <b>项目名称</b>: financial<br>
 * <b>包及类名</b>: com.wisdom.financial.commons.FileUtils<br>
 */
@SuppressWarnings("unchecked")
public abstract class FileUtils extends org.apache.commons.io.FileUtils {
    
    public static void copyFile(String sourcePathname, String destinationPathname) throws IOException {
        copyFile(sourcePathname,destinationPathname,true);
    }
    
    public static void copyFile(String sourcePathname, String destinationPathname, boolean preserveFileDate) throws IOException {
            copyFile(new File(sourcePathname),new File(destinationPathname),preserveFileDate);
    }
    public static void copyFileToDirectory(String sourcePathname,File destinationDirectory) throws IOException {
            copyFileToDirectory(new File(sourcePathname),destinationDirectory);
    }

    public static void moveFile(String sourcePathname, String destinationPathname) throws IOException {
        moveFile(sourcePathname,destinationPathname,true);
    }

    public static void moveFile(String sourcePathname, String destinationPathname, boolean preserveFileDate) throws IOException {
        copyFile(sourcePathname,destinationPathname,preserveFileDate);
            forceDelete(new File(sourcePathname));
    }
    public static File mkDirectory(String directoryPathname) throws Exception {
            File dir = new File(directoryPathname);
            if (dir.exists()) {
                if (dir.isDirectory()) {
                    return dir;
                }else{
                    throw new Exception("backupSealImagePath "+directoryPathname+" is not a directory");
                }
            }
            forceMkdir(dir);
            return dir;
    }
    
    //合并一些org.springframework.util.FileCopyUtils方法
    
    public static void writeByteArrayToFile(byte[] in, String outFilename) throws IOException {
            File out = new File(outFilename);
            FileCopyUtils.copy(in,out);
    }
    
    public static byte[] readFileToByteArray(String inFilename) throws IOException {
            File in = new File(inFilename);
            return FileCopyUtils.copyToByteArray(in);
    }
    
	public static byte[] readUrlResourceToByteArray(String name, Class clazz) throws IOException {
        return readFileToByteArray(clazz.getResource(name).getFile());
    }
    
    public static byte[] readStreamResourceToByteArray(String name, Class clazz) throws IOException {
            InputStream inputStream = clazz.getResourceAsStream(name);
            return FileCopyUtils.copyToByteArray(inputStream);
    }

}
