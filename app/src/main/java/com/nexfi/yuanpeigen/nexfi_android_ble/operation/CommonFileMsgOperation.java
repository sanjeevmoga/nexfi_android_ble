package com.nexfi.yuanpeigen.nexfi_android_ble.operation;

import com.nexfi.yuanpeigen.nexfi_android_ble.listener.ReceiveCommonFileListener;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.SendCommonFileListener;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class CommonFileMsgOperation {

    SendCommonFileListener mSendCommonFileListener=null;
    ReceiveCommonFileListener mReceiveCommonFileListener=null;

    /**
     * 发送文件
     * @param filePath
     * @param userId
     */
    public void sendCommonFileMsg(String filePath,String userId){

    }


    /***
     * 接收文件
     */
    public void receiveCommonFileMsg(){

    }


    /**
     * 发送图片文件
     * @param filePath
     * @param userId
     */
    public void sendImageFileMsg(String filePath,String userId){

    }

    /**
     * 接收图片
     */
    public void receiveImageFileMsg(){

    }


    //设置回调接口(监听器)的方法
    public void setSendCommonFileListener(SendCommonFileListener sendCommonFileListener) {
        mSendCommonFileListener = sendCommonFileListener;
    }

    public void setReceiveCommonFileListener(ReceiveCommonFileListener receiveCommonFileListener) {
        mReceiveCommonFileListener = receiveCommonFileListener;
    }

}
