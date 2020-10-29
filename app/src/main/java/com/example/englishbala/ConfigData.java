package com.example.englishbala;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigData {

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