package com.example.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utils.listener.AnimatorListener;

/**
 * 提示微技巧
 * Created by 陈健宇 at 2018/10/27
 */
public class ToastUtils {

    private static Toast toast;

    /**
     * Toast提示
     * @param context 注意，要传进Application的Context，否则会内存泄漏
     * @param message 的内容
     */
    public static void showToast(Context context, String message){
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义toast请求
     * @param toastView 传入null则使用默认布局
     */
    @SuppressLint("ResourceAsColor")
    public static void toastInCenter(Context context, String message, View toastView){
        if(toastView == null){
            toastView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
            TextView textView = toastView.findViewById(R.id.tv_toast);
            textView.setText(message);
        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }

    /**
     自定义toast请求
     * @param resouceId 传入0则使用默认布局
     */
    public static void toastInCenter(Context context, String message, int resouceId){
        toastInCenter(context, message,  LayoutInflater.from(context).inflate(resouceId, null));
    }

}
