package com.nexfi.yuanpeigen.nexfi_android_ble.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mark on 2016/4/14.
 */
public class UserInfo {

    public static void saveUsername(Context context, String username) {
        SharedPreferences sp = context.getSharedPreferences("username", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userName", username);
        editor.commit();
    }

    public static void saveUsersex(Context context, String usersex) {
        SharedPreferences sp = context.getSharedPreferences("usersex", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("userSex", usersex);
        editor.commit();
    }

    public static void setConfigurationInformation(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("first_pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstIn", false);
        editor.commit();
    }

    public static void setNexFiInformation(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("first_nexfi", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isNexFi", true);
        editor.commit();
    }

    public static void saveEnabledInformation(Context context, boolean flag) {
        SharedPreferences preferences = context.getSharedPreferences("EnabledInformation", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Enabled", flag);
        editor.commit();
    }



    public static void saveUserHeadIcon(Context context, int id) {
        SharedPreferences preferences = context.getSharedPreferences("UserHeadIcon", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userhead", id);
        editor.commit();
    }

    public static void saveUserAge(Context context, int age) {
        SharedPreferences preferences = context.getSharedPreferences("UserAge", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userage", age);
        editor.commit();
    }
}
