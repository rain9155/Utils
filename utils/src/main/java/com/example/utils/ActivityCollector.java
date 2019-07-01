package com.example.utils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * 活动管理器
 * Created by ASUS on 2018/5/24.
 */

public class ActivityCollector {

    public static ArrayList<AppCompatActivity> activityArrayList = new ArrayList<>();

    /**
     * 添加一个活动
     */
    public static void addActivity(AppCompatActivity activity){
        activityArrayList.add(activity);
    }

    /**
     * 移除一个活动
     */
    public static void removeActivity(AppCompatActivity activity){
        activityArrayList.remove(activity);
    }


    /**
     * 销毁所有活动，退出应用
     */
    public static  void finishAll(){
        for(AppCompatActivity activity : activityArrayList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activityArrayList.clear();
    }
}
