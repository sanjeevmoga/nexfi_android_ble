package com.nexfi.yuanpeigen.nexfi_android_ble.util;

import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;

import java.util.List;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class BleCommunicationUtils {
    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    public static UserMessage getUserInfo(String userId){
        return null;
    }

    /**
     * 获取用户集合
     * @return
     */
    public static List<UserMessage> getUserInfoLists(){
        return null;
    }

    /**
     * 发送文本消息
     * @param textMsg
     * @param userId
     */
    public static void sendTextMessage(String textMsg,String userId){

    }

    /**
     * 接收文本消息
     */
    public static void receiveTextMessage(){

    }

    /**
     * 发送文件
     * @param filePath
     * @param userId
     */
    public static void sendCommonFileMsg(String filePath,String userId){

    }


    /***
     * 接收文件
     */
    public static void receiveCommonFileMsg(){

    }

    /**
     * 发送图片文件
     * @param filePath
     * @param userId
     */
    public static void sendImageFileMsg(String filePath,String userId){

    }

    /**
     * 接收图片
     */
    public static void receiveImageFileMsg(){

    }

}
