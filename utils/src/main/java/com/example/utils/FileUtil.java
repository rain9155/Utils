package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

/**
 * Created by 陈健宇 at 2018/12/2
 */
public class FileUtil {

    /**
     * 获得缓存路径
     */
    public static String getCachePath(Context context, String name){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();//获得外部存储空间的缓存路径
        } else {
            cachePath = context.getCacheDir().getPath();//获得内部存储空间的缓存路径
        }
        return cachePath + File.separator + name;
    }

    /**
     * 反序列化对象到内存
     */
    public static Object restoreObject(Context context, String fileName) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try {
            fileInputStream = context.openFileInput(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(fileInputStream);
            close(objectInputStream);
        }
        return object;
    }

    /**
     * 序列化对象到本地
     */
    public static  void saveObject(Context context, String fileName, Object saveObject) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fileOutputStream);
            close(objectOutputStream);
        }
    }

    /**
     * 在本地文件保存图片
     * @param fileName 文件名
     * @param bitmap 图片
     */
    public static void saveBitmap(Context context, String fileName, Bitmap bitmap){
        String newUrl = fileName.replace("/", "");
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(newUrl, Context.MODE_PRIVATE);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 在本地文件取出保存的图片
     * @param fileName 文件名
     * @return 图片
     */
    public static Bitmap loadBitmap(Context context, String fileName){
        String newUrl = fileName.replace("/", "");
        BufferedInputStream bufferedInputStream = null;
        Bitmap bitmap = null;
        FileInputStream fileInputStream;
        try {
            fileInputStream = context.openFileInput(newUrl);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 在本地删除文件
     */
    public static boolean deleteFile(Context context, String fileName){
        String newUrl = fileName.replace("/", "");
        File file = new File(context.getFilesDir(), newUrl);
        if(file.exists()){
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 关闭流对象
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除某个文件或某个文件夹下所有的文件
     */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        assert dir != null;
        return dir.delete();
    }

    /**
     * 获得某个文件夹下文件的总大小,单位经过{@link String getFormatSize(double size)}转换
     */
    public static String getCacheSize(File file) {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 获得某个文件夹下文件的总大小, 单位为B
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size += aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 把文件大小（默认为B）转换单位
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
