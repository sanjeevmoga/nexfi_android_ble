package com.nexfi.yuanpeigen.nexfi_android_ble.application;

import android.app.Application;
import android.content.Context;

import com.nexfi.yuanpeigen.nexfi_android_ble.uncaught.CrashHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by gengbaolong on 2016/4/14.
 */
public class BleApplication extends Application{
    private static Context mContext;
    private static String uuid= UUID.randomUUID().toString();
    private static CrashHandler crashHandler;
    private static List<Throwable> exceptionLists=new ArrayList<Throwable>();

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=getApplicationContext();
        crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

    }

    public static Context getContext(){
        return mContext;
    }

    public static String getUUID(){
        return uuid;
    }


    public static CrashHandler getCrashHandler(){
        return crashHandler;
    }

    public static List<Throwable> getExceptionLists(){
        return exceptionLists;
    }

}
