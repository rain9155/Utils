package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.example.utils.provider.MyFileProvider;

import java.io.File;

/**
 * 适配Android 7.0的FileProvide，文件共享
 * 如果需要在manifest文件中注册provider，参考如下：
 * <provider
 *      android:name="androidx.core.content.FileProvider"
 *      android:authorities="（随便填）"//这里填写provider的authoritie名
 *      android:exported="false"
 *      android:grantUriPermissions="true">
 *        <meta-data
 *           ndroid:name="android.support.FILE_PROVIDER_PATHS"
 *           android:resource="@xml/file_paths" />
 * </provider>
 * 如果出现FileProvider冲突，参考：https://www.cnblogs.com/cuma/p/8108400.html
 * 只需要自己重新定义一个继承自FileProvider的类，然后在注册FileProvider时，name属性填上你自定义的FileProvider的路径
 * Created by 陈健宇 at 2019/1/1
 */
public class FileProvider7 {


    /**
     * 适配获得url，7.0以上获得content://, 以下获得file://(使用已经注册好的FileProvider)
     */
    public static Uri getUriForFile(Context context, File file) {
        return getUriForFile(context, file, MyFileProvider.AUTHORITIES);
    }

    /**
     * 通过file获得content://(使用已经注册好的FileProvider)
     */
    public static Uri getUriForFile24(Context context, File file) {
        return getUriForFile24(context, file, MyFileProvider.AUTHORITIES);
    }

    /**
     * 适配获得url，7.0以上获得content://, 以下获得file://(需要自己注册FileProvider)
     * @param file 文件
     * @param authorities FileProvider的authoritie名
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
     * 通过file获得content://(需要自己注册FileProvider)
     */
    public static Uri getUriForFile24(Context context, File file, String authorities) {
        Uri fileUri = FileProvider.getUriForFile(
                context,
                authorities,
                file);
        return fileUri;
    }


    /**
     * 适配apk安装的setDataAndType（）(使用已经注册好的FileProvider)
     */
    public static void setIntentDataAndType(Context context, Intent intent, String type, File file, boolean writeAble) {
        setIntentDataAndType(context, intent, type, file, MyFileProvider.AUTHORITIES, writeAble);
    }

    /**
     * 适配apk安装的setDataAndType（）(需要自己注册FileProvider)
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
