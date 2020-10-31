package com.example.englishbala;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;

public class ConfigData {
    // 软件所需权限列表
    public static String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    // 是否第一次运行或者是否获得了应有的权限
    public static boolean isFirst;
    public static String isFirstName = "isFirst";

    // SharedDataName
    public static String SharedDataName = "configData";
    // 是否为夜间模式
    public static boolean isNight;
    public static String isNightName = "isNight";

    // 是否已登录
    public static boolean isLogged;
    public static String isLoggedName = "isLogged";

    // 当前已登录的用户ID
    public static long SinaNumLogged;
    public static String SinaNumLoggedName = "SinaNumLogged";

    // 是否为修改计划
    // 0为否，1为是
    public static final String UPDATE_NAME = "update";
    public static final int isUpdate = 1;
    public static final int notUpdate = 0;

    // 获取isFirst的值
    public static boolean getIsFirst() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE);
        isFirst = preferences.getBoolean(isFirstName,true);
        return isFirst;
    }

    // 设置isFirst的值
    public static void setIsFirst(boolean isFirst) {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(isFirstName, isFirst);
        editor.apply();
    }

    // 获得当前是否为夜间模式
    public static boolean getIsNight() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE);
        isNight = preferences.getBoolean(isNightName, false);
        return isNight;
    }
    // 设置当前是否为夜间模式
    public static void setIsNight(boolean isNight) {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(isNightName, isNight);
        editor.apply();
    }

    // 得到isLogged值
    public static boolean getIsLogged() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE);
        isLogged = preferences.getBoolean(isLoggedName, false);
        return isLogged;
    }

    // 设置isLogged值
    public static void setIsLogged(boolean isLogged) {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(isLoggedName, isLogged);
        editor.apply();
    }

    // 获得SinaNumLogged值
    public static long getSinaNumLogged() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE);
        SinaNumLogged = preferences.getLong(SinaNumLoggedName, 0);
        return SinaNumLogged;
    }

    // 设置SinaNumLogged值
    public static void setSinaNumLogged(long sinaNumLogged) {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences(SharedDataName, Context.MODE_PRIVATE).edit();
        editor.putLong(SinaNumLoggedName, sinaNumLogged);
        editor.apply();
    }
}
