package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.utils.config.FileType;
import com.example.utils.config.MimeType;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Locale;

import static com.example.utils.IntentUtils.*;

/**
 * 一些文件操作方法,记得添加
 * Created by 陈健宇 at 2018/12/2
 */
public class FileUtils {


    /**
     * 获得应用关联缓存路径，即/Android/data/包名/cache/
     */
    public static String getCachePath(Context context){
       return getCachePath(context, "");
    }

    /**
     * 获得应用关联缓存路径，并可以追加路径名
     * 追加的路径名后，需要手动建立文件夹路径，见@see makeDirs(String)
     * @param name 要追加的路径名，如“path/”
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
     * 获得应用关联文件路径，即/Android/data/包名/files/
     */
    public static String getFilePath(Context context){
        return getFilePath(context, "");
    }

    /**
     * 获得应用关联文件路径，并可以追加路径名
     * 追加的路径名后，需要手动建立文件夹路径，见@see makeDirs(String)
     * @param name 要追加的路径名，如“path/”
     */
    public static String getFilePath(Context context, String name){
        String filePath;
        if (!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
            filePath = context.getFilesDir().getPath();
        } else {
            filePath = context.getExternalFilesDir(null).getPath();
        }

        return filePath + File.separator + name;
    }

    /**
     * 根据给定的path建立文件夹
     * @param path 文件夹路径
     * @return true表示建立成功，false反之
     */
    public static boolean makeDirs(String path){
        char separator = path.charAt(path.length() - 1);
        if(!String.valueOf(separator).equals(File.separator)){
            StringBuilder builder = new StringBuilder(path);
            builder.append(File.separator);
            path = builder.toString();
        }
        File dir = new File(path);
        if(!dir.isDirectory()){
            dir.mkdirs();
        }
        return dir.isDirectory();
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
     * @return true表示成功，false表示失败
     */
    public static boolean saveImage(Context context, String fileName, Bitmap bitmap){
        if(bitmap == null)  return false;
        String newUrl = fileName.replace("/", "");
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(newUrl, Context.MODE_PRIVATE);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(bufferedOutputStream, fileOutputStream);
        }
        return false;
    }

    /**
     * 在本地文件取出保存的图片
     * @param fileName 文件名
     * @return 图片
     */
    public static Bitmap loadBitmap(Context context, String fileName){
        String newUrl = fileName.replace("/", "");
        Bitmap bitmap = null;
        BufferedInputStream bufferedInputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(newUrl);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            close(fileInputStream, bufferedInputStream);
        }
        return bitmap;
    }

    /**
     * 根据路径获得字节流
     * @return 字节流
     */
    public static byte[] getFileBytes(String path){
        byte[] bytes = new byte[0];
        try(InputStream in = new BufferedInputStream(new FileInputStream(path))){
            bytes = new byte[in.available()];
            in.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 根据路径存放字节流, 下次存放不追加
     * @param bytes 要存放的字节流
     * @param path 要存放的字节流的路径
     * @return false表示失败，反之成功
     */
    public static boolean saveFileBytes(byte[] bytes, String path){
        return saveFileBytes(bytes, path, false);
    }


    /**
     * 根据路径存放字节流
     * @param bytes 要存放的字节流
     * @param path 要存放的字节流的路径
     * @param append 下次存放时是否追加
     * @return false表示失败，反之成功
     */
    public static boolean saveFileBytes(byte[] bytes, String path, boolean append){
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try(
                FileOutputStream fileOutputStream = new FileOutputStream(file, append);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)
        ){
            bufferedOutputStream.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除某个文件或某个文件夹下所有的文件
     * @param dir 某个文件或文件夹
     */
    public static boolean deleteFiles(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteFiles(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        assert dir != null;
        return dir.delete();
    }

    /**
     * 获得某个文件夹下文件或某个文件的总大小
     * 单位经过{@link String getFormatSize(double size)}转换
     */
    public static String getFileSize(File file) {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 获得某个文件夹下文件或某个文件的总大小, 单位为B
     */
    public static long getFolderSize(File file) {
        long size = -1;
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
            if(size == -1) size = file.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 把文件大小（默认为B）转换更大的单位
     * @param size 文件大小
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

    /**
     * 找到可以打开文件的所有应用
     * @param filePath 文件的真实路径
     */
    public static void openFile(Context context, String filePath) {
        String end = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
        Intent intent = null;
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            intent = getAudioFileIntent(context, filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            intent = getVideoFileIntent(context, filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            intent = getImageFileIntent(context, filePath);
        } else if (end.equals("apk")) {
            intent = getApkFileIntent(context, filePath);
        } else if (end.equals("ppt") || end.equals("pptx")) {
            intent = getPPtFileIntent(context, filePath);
        } else if (end.equals("xls") || end.equals("xlsx")) {
            intent = getExcelFileIntent(context, filePath);
        } else if (end.equals("doc") || end.equals("docx")) {
            intent = getWordFileIntent(context, filePath);
        } else if (end.equals("pdf")) {
            intent = getPdfFileIntent(context, filePath);
        } else if (end.equals("txt")) {
            intent = getTxtFileIntent(context, filePath);
        } else if(end.equals("zip")){
            intent = getZipFileIntent(context, filePath);
        }else {
            intent = getAllIntent(context, filePath);
        }
        context.startActivity(intent);
    }

    /**
     * 根据文件路径的后缀名获取文件类型
     */
    public static String getMimeType(String filePath){
        String end = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase(Locale.getDefault());
        if (end.equals(FileType.M4A) || end.equals(FileType.MP3) || end.equals(FileType.MID) || end.equals(FileType.XMF) || end.equals(FileType.OGG) || end.equals(FileType.WAV)) {
            return MimeType.AUDIO;
        } else if (end.equals(FileType.GP3) || end.equals(FileType.MP4) || end.equals(FileType.AVI) || end.equals(FileType.RMVB)) {
            return MimeType.VIDEO;
        } else if (end.equals(FileType.JPG) || end.equals(FileType.GIF) || end.equals(FileType.PNG) || end.equals(FileType.JPEG) || end.equals(FileType.BMP)) {
            return MimeType.IMAGE;
        } else if (end.equals(FileType.APK)) {
            return MimeType.APK;
        } else if (end.equals(FileType.PPT) || end.equals(FileType.PPTX)) {
            return MimeType.PPT;
        } else if (end.equals(FileType.XLS) || end.equals(FileType.XLSX)) {
            return MimeType.XLS;
        } else if (end.equals(FileType.DOC) || end.equals(FileType.DOCX)) {
            return MimeType.DOC;
        } else if (end.equals(FileType.PDF)) {
            return MimeType.PDF;
        }else if (end.equals(FileType.TXT)) {
            return MimeType.TXT;
        } else if(end.equals(FileType.ZIP) || end.equals(FileType.RAR)){
            return MimeType.ZIP;
        }else {
            return MimeType.UNKOWN;
        }
    }

    /**
     * 关闭多个流对象
     */
    public static void close(Closeable... closeable) {
        for(Closeable close : closeable){
            close(close);
        }
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
}
