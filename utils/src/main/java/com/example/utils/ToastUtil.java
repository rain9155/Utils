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
import com.example.utilslibrary.anim.AnimatorListener;

/**
 * 提示微技巧
 * Created by 陈健宇 at 2018/10/27
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * Toast提示
     * @param context
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

    /**
     * 下弹式提示框
     * @param viewGroup 父布局
     * @param s  要提示的内容
     */
    public static void toastMake(Context context, final ViewGroup viewGroup, String s, int backgroundColor, int textColor){
        final TextView textView = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setText(s);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 15, 0, 15);
        textView.setTextColor(textColor);
        textView.setBackgroundColor(backgroundColor);
        textView.setLayoutParams(params);
        viewGroup.addView(textView);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator translationY1 = ObjectAnimator.ofFloat(textView, "translationY",  -65f, 0f);
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(textView, "translationY", 0f, -65);
        translationY2.setStartDelay(2500);
        animatorSet.playSequentially(translationY1, translationY2);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                viewGroup.removeView(textView);
            }
        });
    }
}
