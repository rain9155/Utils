package com.example.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 多语言切换工具
 * Created by 陈健宇 at 2019/5/5
 */
public class LanguageUtil {

    //跟随系统
    public static final String SYSTEM = "system";
    // 简体中文
    public static final String SIMPLIFIED_CHINESE = "zh";
    // 英文
    public static final String ENGLISH = "en";

    //该应用支持的语言
    private static Map<String, Locale> mSupportLans = new HashMap<String, Locale>(2){{
        put(SIMPLIFIED_CHINESE, Locale.SIMPLIFIED_CHINESE);
        put(ENGLISH, Locale.ENGLISH);
    }};

    /**
     * 是否支持该语言
     * @param lan  要支持的语言
     * @return true 支持， false 不支持
     */
    public static boolean isSupportLanguage(String lan){
        return mSupportLans.containsKey(lan);
    }


    /**
     * 配置Context，使它支持给定的语言
     * @param context 要配置的配置Context
     * @param lan 要设置的语言
     * @return 配置过的Context
     */
    public static Context attachBaseContext(Context context, String lan){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationContext(context, lan);
        } else {
            return updateConfigurationContext(context, lan);
        }
    }

    /**
     * android7.0之前，需要update configuration
     * @param context 要update的Context
     * @param lan 要替换的语言
     * @return 已经update过的Context
     */
    private static Context updateConfigurationContext(Context context, String lan){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = getSupportLanguage(lan);
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, displayMetrics);
        return context;
    }

    /**
     * android7.0之后，需要set local 到 configuration
     * @param context 要set的Context
     * @param language 要替换的语言
     * @return 一个通过configuration创建的新的Context
     */
    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationContext(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = getSupportLanguage(language);
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }


    /**
     * 获取支持的语言的Locale, 没有就返回默认的系统语言Locale
     * @param lan 要支持的语言
     * @return 支持的语言的Locale
     */
    public static Locale getSupportLanguage(String lan){
        if(isSupportLanguage(lan))
            return mSupportLans.get(lan);
        return getSystemDefaultLanguage();
    }

    /**
     * 获取系统默认语言的Local
     * @return Locale 系统默认语言的Local
     */
    public static Locale getSystemDefaultLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

}
