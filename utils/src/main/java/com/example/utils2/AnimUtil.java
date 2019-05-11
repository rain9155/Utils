package com.example.utils2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 切换控件显隐性动画工具
 * Created by 陈健宇 at 2018/11/19
 */
public class AnimUtil {

    /**
     * 通过透明度显示控件
     */
    public static void showByAlpha(final View view){
        int shortAnimTime = 200;
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1).setDuration(shortAnimTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        }).start();
    }

    /**
     * 通过透明度隐藏控件
     */
    public static void hideByAlpha(final View view){
        int shortAnimTime = 200;
        view.animate().alpha(0).setDuration(shortAnimTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

    /**
     * 放大复原动画
     * @param scale 放大值，传入-1使用默认值
     */
    public static void scale(View view, float scale){
        if(scale == -1) scale = 1.3f;
        int shortTime = 300;
        PropertyValuesHolder phv1 = PropertyValuesHolder.ofFloat("scaleX", scale, 1f);
        PropertyValuesHolder phv2 = PropertyValuesHolder.ofFloat("scaleY", scale, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, phv1, phv2);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(shortTime);
        animator.start();
    }
}
