package com.baremind.utils;

import java.io.File;
import java.time.LocalDate;

/**
 * Created by gaolianli on 2015/6/18.
 */
public class FileUtils {

    public static String getResYearMonthPath(){
        LocalDate localDate = LocalDate.now();
        String path = localDate.getYear() + "/" + localDate.getMonthValue() + "/";
        return path;
    }

    public static void mkdirs(File file){
        if(file.isDirectory()){
            if (!file.exists()) {
                file.mkdirs();
            }
        }else {
            File fileDir = file.getParentFile();
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }
    }

    public static String getExt(String fileName){
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        return prefix;
    }

    public static String getName(String fileName){
        String prefix=fileName.substring(0, fileName.lastIndexOf("."));
        return prefix;
    }

  /*  public static String getTempFileName(File file){
        String result = CoreConfig.getNewId() + "." + getExt(file.getName());
        return result;
    }*/
}
