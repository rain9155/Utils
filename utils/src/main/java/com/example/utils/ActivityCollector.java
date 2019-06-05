package com.example.utils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * 活动管理器
 * Created by ASUS on 2018/5/24.
 */

public class ActivityCollector {

    public static ArrayList<AppCompatActivity> activityArrayList = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity){
        activityArrayList.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity){
        activityArrayList.remove(activity);
    }


    public static  void finishAll(){
        for(AppCompatActivity activity : activityArrayList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activityArrayList.clear();
    }
}
