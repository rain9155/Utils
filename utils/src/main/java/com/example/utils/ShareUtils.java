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
     */
    public static void shareText(Context context, String text, String title){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(Intent.createChooser(intent, title));
        }else {
            // 找不到指定的 Activity
            ToastUtils.showToast(context, "找不到指定的应用来分享");
        }
    }

    /**
     * 发送邮件
     */
    public static void sendEmail(Context context, String address, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + address));
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(Intent.createChooser(intent, title));
        }else {
            // 找不到指定的 Activity
            ToastUtils.showToast(context, "找不到指定的应用来发送邮件");
        }
    }

    /**
     * 分享图片
     */
    public static void sendImage(@NonNull Context context, @NonNull Uri uri, String title) {
        Intent shareIntent = new Intent()
                .setAction(Intent.ACTION_SEND)
                .setType("image/*")
                .putExtra(Intent.EXTRA_STREAM, uri);
        if(context.getPackageManager().resolveActivity(shareIntent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(Intent.createChooser(shareIntent, title));
        }else {
            // 找不到指定的 Activity
            ToastUtils.showToast(context, "找不到指定的应用来分享");
        }
    }

    /**
     * 打开浏览器
     */
    public static void openBrowser(Context context, String address){
        if (TextUtils.isEmpty(address) || address.startsWith("file://")) {
            ToastUtils.showToast(context, "该链接无法使用浏览器打开");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(address));
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(intent);
        }else {
            ToastUtils.showToast(context, "找不到指定应用打开浏览器");
        }
    }
}
