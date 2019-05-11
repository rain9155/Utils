package com.example.utils2;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 一些公用方法工具类
 * Created by 陈健宇 at 2018/10/28
 */
public class CommonUtil {

    /**
     * list判空
     */
    public static boolean isEmptyList(List<?> list){
        return list == null || list.size() == 0;
    }

    /**
     * drawable着色
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static Drawable getTintDrawable(Drawable originalDrawable, ColorStateList colors){
        Drawable.ConstantState state = originalDrawable.getConstantState();
        //使用tint就必须调用该方法对Drawable进行一次包装
        Drawable tintDrawable = DrawableCompat.wrap(state == null ? originalDrawable : state.newDrawable()).mutate();
        tintDrawable.setBounds(0, 0, originalDrawable.getIntrinsicWidth(), originalDrawable.getIntrinsicHeight());
        //设置tint
        DrawableCompat.setTintList(tintDrawable, colors);
        return tintDrawable;
    }

    /**
     * 获得进程名称
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 缩放图片
     * @param bm 要缩放图片
     * @param newWidth 宽度
     * @param newHeight 高度
     * @return 处理后的图片
     */
    public static Bitmap scaleImage(Bitmap bm, int newWidth, int newHeight){
        if (bm == null){
            return null;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
        if (!bm.isRecycled()){
            bm.recycle();//销毁原图片
            bm = null;
        }
        return newbm;
    }

    /**
     * 按比例缩放图片
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}
