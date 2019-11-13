package com.example.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
     * 从资源文件解析图片
     * @param resources 资源文件
     * @param resId 资源文件id
     * @param reWidth 图片希望的宽
     * @param reHeight 图片希望的高
     * @return 返回压缩后的图片
     */
    public static Bitmap decodeBitmapFromResources(Resources resources, int resId, int reWidth, int reHeight){
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        //计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reWidth, reHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 从文件加载图片
     * @param pathName 文件路径
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    /**
     * 从文件输入流解析图片
     * @param fileInputStream 文件输入流
     * @throws IOException
     */
    public static Bitmap decodeBitmapFromFileInputStream(FileInputStream fileInputStream, int reWidth, int reHeight) throws IOException {
        return decodeBitmapFromFileDescriptor(fileInputStream.getFD(), reWidth, reHeight);
    }

    /**
     * 通过文件描述符加载图片
     * @param fileDescriptor 文件描述符
     */
    public static Bitmap decodeBitmapFromFileDescriptor(FileDescriptor fileDescriptor, int reWidth, int reHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = calculateInSampleSize(options, reWidth, reHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * 从文件输入流解析图片，不要求宽高
     * @param fileInputStream 文件输入流
     * @throws IOException
     */
    public static Bitmap decodeBitmapFromFileInputStream(FileInputStream fileInputStream) throws IOException {
        return decodeBitmapFromFileDescriptor(fileInputStream.getFD());
    }

    /**
     * 通过文件描述符加载图片，不要求宽高
     * @param fileDescriptor 文件描述符
     */
    public static Bitmap decodeBitmapFromFileDescriptor(FileDescriptor fileDescriptor){
        return BitmapFactory.decodeFileDescriptor(fileDescriptor);
    }

    /**
     * 计算图片的inSampleSize
     * @param options BitmapFactory.Options
     * @param reWidth 希望图片的宽
     * @param reHeight 希望图片的高
     * @return 计算后的inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reWidth, int reHeight){
        if(reHeight <= 0 || reWidth <= 0){
            return 1;
        }
        //得到原始宽高
        final int originalWidth = options.outWidth;
        final int originalHeight = options.outHeight;
        int inSampleSize = 1;//大于1图片才有缩放效果
        if(originalHeight > reHeight || originalWidth > reWidth){
            final int halfHeight = originalHeight / 2;
            final int halfWidth = originalWidth / 2;
            //计算出最大inSampleSize保证originalWidth和originalHeight都不会小于reWidth和reHeight
            while (halfWidth / inSampleSize >= reWidth && halfHeight / inSampleSize >= reHeight){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    
}
