package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

/**
 * 一些跟图片处理有关的方法
 * Created by 陈健宇 at 2019/7/1
 */
public class ImageUtils {

    /**
     * 压缩一张图片
     * @param origin 要缩放图片
     * @param newWidth 想要的宽度
     * @param newHeight 想要的高度
     * @return 处理后的图片
     */
    public static Bitmap compressBitmap(Bitmap origin, int newWidth, int newHeight){
        if(origin == null) return null;
        float scaleWidth = ((float) newWidth) / origin.getWidth();
        float scaleHeight = ((float) newHeight) / origin.getHeight();
        return compressBitmap(origin, scaleWidth, scaleHeight);
    }

    /**
     * 按比例压缩一张图片
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    public static Bitmap compressBitmap(Bitmap origin, float ratio) {
       return compressBitmap(origin, ratio, ratio);
    }

    /**
     * 按比例压缩一张图片
     * @param origin 要压缩的图片
     * @param ratioX 原图的宽的压缩比
     * @param ratioY 原图的高的压缩比
     * @return 压缩后的图片
     */
    public static Bitmap compressBitmap(Bitmap origin, float ratioX, float ratioY){
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(ratioX, ratioY);
        Bitmap newBitmap = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, true);
        if (newBitmap.equals(origin)) {
            return newBitmap;
        }
        origin.recycle();
        return newBitmap;
    }

    /**
     * 根据Uri获取图片
     * @param imageUri 图片uri
     * @return bitmap图片
     */
    public static Bitmap getImageByUri(Context context, Uri imageUri){
        try(InputStream inputStream = context.getContentResolver().openInputStream(imageUri)){
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据图片路径创建图片Uri
     * @param path 图片的真实路径
     * @return 图片Uri
     */
    public static Uri getImageUri(Context context, String path) {
        File fileOutPutImage = new File(path);
        if(!fileOutPutImage.exists()){
            try {
                fileOutPutImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return FileProvider7.getUriForFile(context, fileOutPutImage);
    }
    
}
