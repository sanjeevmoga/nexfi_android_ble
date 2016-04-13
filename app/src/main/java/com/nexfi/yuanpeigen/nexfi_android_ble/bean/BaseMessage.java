package com.nexfi.yuanpeigen.nexfi_android_ble.bean;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class BaseMessage {
    public String type;
    public String sendTime;
    public String chat_id;//会话id

    public enum MessageType{
        USER_LOGIN_MESSAGE_TYPE,//用户数据类型
        SINGLE_CHAT_MESSAGE_TYPE,//单聊类型
        CHAT_ROOM_MESSAGE_TYPE//群聊类型
    }


    public enum ChatMessageType{
        TEXT_ONLY_MESSAGE_TYPE,//纯文本消息
        EMOJI_ONLY_MESSAGE_TYPE,//表情消息
        COMMON_FILE_MESSAGE_TYPE,//普通文件
        IMAGE_FILE_MESSAGE_TYPE,//图片文件
        VIDEO_FILE_MESSAGE_TYPE,//音频文件
        AUDIO_FILE_MESSAGE_TYPE//视频文件
    }

}
