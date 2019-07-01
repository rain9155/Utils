package com.example.utils;

import android.util.Log;

/**
 * log工具
 * Created by 陈健宇 at 2019/6/6
 */
public class LogUtils {

    private static boolean isDebug = BuildConfig.DEBUG;

    public static void d(String tag, String msg){
        if(isDebug) Log.d(tag, msg);
    }

    public static void e(String tag, String msg){
        if(isDebug) Log.e(tag, msg);
    }

}
