package com.nexfi.yuanpeigen.nexfi_android_ble.bean;

import java.io.Serializable;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class UserMessage extends BaseMessage implements Serializable {
    public String userId;
    public long nodeId;
    public String userNick;
    public int userAge;
    public String userGender;
    public int userAvatar;

    @Override
    public String toString() {
        return "userId="+userId+",userNick="+userNick+",messageType="+messageType+",nodeId"+nodeId;
    }
}
