package com.nexfi.yuanpeigen.nexfi_android_ble.application;

import android.app.Application;
import android.content.Context;

import com.nexfi.yuanpeigen.nexfi_android_ble.uncaught.CrashHandler;

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
        CrashHandler handler=new CrashHandler(mContext);
        handler.init(mContext);
    }

    public static Context getContext(){
        return mContext;
    }

    public static String getUUID(){
        return uuid;
    }

}
