package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 适配Android 7.0的FileProvide，文件共享
 * 需要在manifest文件中注册provider，如下：
 * <provider
 *      android:name="androidx.core.content.FileProvider"
 *      android:authorities="（随便填）"//这里填写provider的authoritie名
 *      android:exported="false"
 *      android:grantUriPermissions="true">
 *        <meta-data
 *           ndroid:name="android.support.FILE_PROVIDER_PATHS"
 *           android:resource="@xml/file_paths" />
 * </provider>
 * Created by 陈健宇 at 2019/1/1
 */
public class FileProvider7 {

    /**
     * 适配获得url，7.0以上获得content://, 以下获得file://
     *
     */
    public static Uri getUriForFile(Context context, File file, String authorities) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fileUri = getUriForFile24(context, file, authorities);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * 通过file获得content://
     */
    public static Uri getUriForFile24(Context context, File file, String authorities) {
        Uri fileUri = FileProvider.getUriForFile(
                context,
                authorities,
                file);
        return fileUri;
    }


    /**
     * 适配apk安装的setDataAndType（）
     * @param intent 打开安装界面的intent
     * @param type  meta type
     * @param file  安装包的file
     * @param writeAble 是否写
     */
    public static void setIntentDataAndType(Context context, Intent intent, String type, File file, String authorities, boolean writeAble) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setDataAndType(getUriForFile(context, file, authorities), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }
}
