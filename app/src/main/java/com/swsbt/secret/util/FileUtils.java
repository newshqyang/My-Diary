package com.swsbt.secret.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileUtils {

    /*
    创建文件
     */
    public static File createFile(String pathAndName) {
        File file = new File(pathAndName);
        if (file.exists()) {
            if (!file.delete()) {
                return null;
            }
        }
        try {
            if (file.createNewFile()) {
                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    移动文件或者文件夹
     */
    public static boolean moveFile(String movedFilePath, String moveToPath) {
        File file = new File(movedFilePath);
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File f : fileList) {
                    moveFile(f.getAbsolutePath(), moveToPath);
                }
            }
        }
        return file.renameTo(new File(moveToPath + "/" + file.getName()));
    }

    /*
    删除文件或文件夹
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File f : fileList) {
                    deleteFile(f.getAbsolutePath());
                }
            }
        }
        return file.delete();
    }

    /*
    获取文件路径中最后一个文件夹或者文件的名称
     */
    public static String getLastFolderOrFileName(String path) {
        String[] fileList = path.split("/");
        return fileList[fileList.length - 1];
    }

    /*
    获取文件后缀名
     */
    public static String getSuffix(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.indexOf(".") + 1);
        }
        return fileName;
    }

    /*
    创建文件夹
     */
    public static boolean createNewFolder(String folderPath) {
        boolean r = false;
        File file = new File(folderPath);
        if (!file.exists()) {
            r = file.mkdirs();
        }
        return r;
    }

    /*
    获取格式化后的大小，字符串
     */
    private static final long GB_SIZE = 1073741824L;
    private static final long MB_SIZE = 1048576L;
    private static final long KB_SIZE = 1024L;
    public static String getFormatSize(long size) {
        if (size > GB_SIZE) {      // 如果超过 1 G
            return (getNumberWithDecimalPoint((double)size / GB_SIZE, 1)) + "GB";
        } else if (size > MB_SIZE) {
            return (getNumberWithDecimalPoint((double)size / MB_SIZE, 1)) + "MB";
        } else if (size > KB_SIZE) {
            return (getNumberWithDecimalPoint((double)size / KB_SIZE, 1)) + "KB";
        } else {
            return size + "B";
        }
    }

    /*
    获取小数点后几位
     */
    public static String getNumberWithDecimalPoint(double number, int theLastFew) {
        return ("" + number).substring(0, ("" + number).indexOf(".") + theLastFew + 1);
    }

    /*
    获取当前可用空间，单位byte
     */
    public static long getUsefulSize() {
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return sf.getBlockSizeLong() * sf.getAvailableBlocksLong();
    }

    /*
    获取内部存储设备总共空间，单位byte
     */
    public static long getTotalSize() {
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return sf.getBlockSizeLong() * sf.getBlockCountLong();
    }

    /*
    文件排序
     */
    public static void sortFileByName(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
    }


    /*
    获取路径下的所有文件
     */
    public static List<File> getPathFileList(String path) {
        File file = new File(path);
        return listAllFiles(file);
    }

    private static List<File> listAllFiles(File file) {
        List<File> harukaFileList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File f : files) {
            Calendar calendar = Calendar.getInstance(); // 获取文件修改日期
            long time = f.lastModified();
            calendar.setTimeInMillis(time);
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String updateDateTime = dateFormat.format(calendar.getTime());

            File harukaFile = new File(f.getPath());
            harukaFileList.add(harukaFile);
        }
        return harukaFileList;
    }

    /*
    搜索文件或文件夹
     */
    public static List<String> searchFile(String fileName, List<String> pathMap) {
        List<String> foundList = new ArrayList<>();
        for (String path : pathMap) {
            if (path.split("/")[path.split("/").length - 1].toLowerCase().contains(fileName.toLowerCase())) {
                foundList.add(path);
            }
        }
        return foundList;
    }

    public static boolean searchFileOrFolder(String path, List<String> foundList) {
        File fileDir = new File(path);
        File[] files = fileDir.listFiles();
        if (files != null) {
            for (File f : files) {
                foundList.add(f.getAbsolutePath());
                // 如果是文件夹 继续读取
                if (f.isDirectory()) {
                    searchFileOrFolder(f.getAbsolutePath(), foundList);
                }
            }
        }
        return true;
    }
}
