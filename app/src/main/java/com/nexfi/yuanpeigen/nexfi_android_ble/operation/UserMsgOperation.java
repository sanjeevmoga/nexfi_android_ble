package com.nexfi.yuanpeigen.nexfi_android_ble.operation;

import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.LoginListener;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.LogoutListener;

import java.util.List;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class UserMsgOperation {

    LoginListener mLoginListener=null;
    LogoutListener mLogoutListener=null;
    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    private UserMessage getUserInfo(String userId){
        return null;
    }

    /**
     * 获取用户集合
     * @return
     */
    private List<UserMessage> getUserInfoLists(){
        return null;
    }


    //设置回调接口(监听器)的方法
    private void setLoginListener(LoginListener loginListener) {
        mLoginListener = loginListener;
    }

    private void setLogoutListener(LogoutListener logoutListener) {
        mLogoutListener = logoutListener;
    }
}
