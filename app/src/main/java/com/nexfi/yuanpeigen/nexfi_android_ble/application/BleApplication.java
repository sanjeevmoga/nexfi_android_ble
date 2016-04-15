package com.nexfi.yuanpeigen.nexfi_android_ble.application;

import android.app.Application;
import android.content.Context;

import java.util.UUID;

/**
 * Created by gengbaolong on 2016/4/14.
 */
public class BleApplication extends Application{
    private static Context mContext;
    private static String uuid= UUID.randomUUID().toString();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

    public static String getUUID(){
        return uuid;
    }
}
