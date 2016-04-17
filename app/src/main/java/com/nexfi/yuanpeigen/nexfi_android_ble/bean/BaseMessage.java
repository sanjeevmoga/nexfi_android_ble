package com.nexfi.yuanpeigen.nexfi_android_ble.bean;

import java.io.Serializable;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class BaseMessage implements Serializable{
    private static  final long serialVersionUID = 1L;
    public int messageType;
    public String sendTime;
    public String chat_id;//会话id
    public EntiyMessage entiyMessage;//消息实体
}
