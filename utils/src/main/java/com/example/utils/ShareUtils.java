package com.example.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;


/**
 * 分享工具
 * Created by codeest on 2016/8/22.
 */
public class ShareUtils {

    /**
     * 分享文字
     * @return true表示可以找到相应的应用打开，false表示无法找到相应的应用打开
     */
    public static boolean shareText(Context context, String text, String title){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(Intent.createChooser(intent, title));
            return true;
        }
        return false;
    }

    /**
     * 发送邮件
     */
    public static boolean sendEmail(Context context, String address, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + address));
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(Intent.createChooser(intent, title));
            return true;
        }
        return false;
    }

    /**
     * 分享图片
     */
    public static boolean sendImage(@NonNull Context context, @NonNull Uri uri, String title) {
        Intent shareIntent = new Intent()
                .setAction(Intent.ACTION_SEND)
                .setType("image/*")
                .putExtra(Intent.EXTRA_STREAM, uri);
        if(context.getPackageManager().resolveActivity(shareIntent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(Intent.createChooser(shareIntent, title));
            return true;
        }
        return false;
    }

    /**
     * 打开浏览器
     */
    public static boolean openBrowser(Context context, String address){
        if (TextUtils.isEmpty(address) || address.startsWith("file://")) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(address));
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
