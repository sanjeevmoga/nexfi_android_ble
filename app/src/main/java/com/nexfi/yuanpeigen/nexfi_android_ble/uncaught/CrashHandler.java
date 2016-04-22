package com.nexfi.yuanpeigen.nexfi_android_ble.uncaught;

import android.content.Context;
import android.util.Log;

/**
 * Created by gengbaolong on 2016/4/22.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{
    /**
     * The thread is being terminated by an uncaught exception. Further
     * exceptions thrown in this method are prevent the remainder of the
     * method from executing, but are otherwise ignored.
     *
     * @param thread the thread that has an uncaught exception
     * @param ex     the exception that was thrown
     */
    private  Context myContext;

    public CrashHandler(Context context) {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        Log.d("Debug", "uncaughtException, thread: " + thread
                + " name: " + thread.getName() + " id: " + thread.getId() + "exception: "
                + exception);
    }




    public void init(Context context) {
        myContext = context;

        // 获取系统默认的 UncaughtException 处理器
        Thread.UncaughtExceptionHandler mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
