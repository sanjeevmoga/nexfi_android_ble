package com.nexfi.yuanpeigen.nexfi_android_ble.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;

/**
 * Created by Mark on 2016/4/25.
 */
public class UserInfo {

    public static final String USER_AGE_CHATSEND = "userAgeChatSend";
    public static final String USER_AVATAR_CHATSEND = "userAvatarChatSend";
    public static final String USER_GENDER_CHATSEND = "userGenderChatSend";
    public static final String USER_NICK_CHATSEND = "userNickChatSend";

    public static final String USER_AGE_CHATRECEIVE = "userAgeChatReceive";
    public static final String USER_AVATAR_CHATRECEIVE = "userAvatarChatReceive";
    public static final String USER_GENDER_CHATRECEIVE = "userGenderChatReceive";
    public static final String USER_NICK_CHATRECEIVE = "userNickChatReceive";

    public static final String USER_AGE_SENDFILE = "userAgeSendFile";
    public static final String USER_AVATAR_SENDFILE = "userAvatarSendFile";
    public static final String USER_GENDER_SENDFILE = "userGenderSendFile";
    public static final String USER_NICK_SENDFILE = "userNickChatSendFile";

    public static final String USER_AGE_RECEIVEFILE = "userAgeReceiveFile";
    public static final String USER_AVATAR_RECEIVEFILE = "userAvatarReceiveFile";
    public static final String USER_GENDER_RECEIVEFILE = "userGenderReceiveFile";
    public static final String USER_NICK_RECEIVEFILE = "userNickChatReceiveFile";

    public static final String USER_AGE_SENDIMAGE = "userAgeSendImage";
    public static final String USER_AVATAR_SENDIMAGE = "userAvatarSendImage";
    public static final String USER_GENDER_SENDIMAGE = "userGenderSendImage";
    public static final String USER_NICK_SENDIMAGE = "userNickChatSendImage";

    public static final String USER_AGE_RECEIVEIMAGE = "userAgeReceiveImage";
    public static final String USER_AVATAR_RECEIVEIMAGE = "userAvatarReceiveImage";
    public static final String USER_GENDER_RECEIVEIMAGE = "userGenderReceiveImage";
    public static final String USER_NICK_RECEIVEIMAGE = "userNickChatReceiveImage";

    public static boolean is_chatsend = true;
    public static boolean is_chatreceive = true;
    public static boolean is_sendfile = true;
    public static boolean is_receivefile = true;
    public static boolean is_sendimage = true;
    public static boolean is_receiveimage = true;

    public static final String IS_CHATSEND = "is_chatsend";
    public static final String IS_CHATRECEIVE = "is_chatreceive";
    public static final String IS_SENDFILE = "is_sendfile";
    public static final String IS_RECEIVEFILE = "is_receivefile";
    public static final String IS_SENDIMAGE = "is_sendimage";
    public static final String IS_RECEIVEIMAGE = "is_receiveimage";

    public static int[] userHeadIcon = {R.mipmap.img_head_1, R.mipmap.img_head_2, R.mipmap.img_head_3, R.mipmap.img_head_4, R.mipmap.img_head_5, R.mipmap.img_head_6, R.mipmap.img_head_7, R.mipmap.img_head_8, R.mipmap.img_head_9, R.mipmap.img_head_10, R.mipmap.img_head_11, R.mipmap.img_head_12, R.mipmap.img_head_13};

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

    public static void saveUserId(Context context, String UserId) {
        SharedPreferences preferences = context.getSharedPreferences("UserId", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", UserId);
        editor.commit();
    }

    public static int initUserAvatar(int userAvatar, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserHeadIcon", Context.MODE_PRIVATE);
        userAvatar = preferences.getInt("userhead", R.mipmap.img_default);
        return userAvatar;
    }

    public static String initUserNick(String userNick, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("username", Context.MODE_PRIVATE);
        userNick = preferences.getString("userName", "未填写");
        return userNick;
    }

    public static String initUserGender(String userGender, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("usersex", Context.MODE_PRIVATE);
        userGender = preferences.getString("userSex", null);
        return userGender;
    }

    public static int initUserAge(int userAge, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserAge", Context.MODE_PRIVATE);
        userAge = preferences.getInt("userage", 0);
        return userAge;
    }

    public static boolean initConfigurationInformation(boolean isFirstIn, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("first_pref", Context.MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        return isFirstIn;
    }

    public static String initUserId(String UserId, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("UserId", Context.MODE_PRIVATE);
        UserId = preferences.getString("userId", null);
        return UserId;
    }

}
