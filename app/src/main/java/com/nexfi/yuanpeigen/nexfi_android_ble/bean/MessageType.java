package com.nexfi.yuanpeigen.nexfi_android_ble.bean;

/**
 * Created by gengbaolong on 2016/4/17.
 */
public class MessageType {
    public static int REQUEST_USER_INFO=1;//请求消息
    public static int RESPONSE_USER_INFO=2;//反馈消息
    public static int OFFINE_USER_INFO=3;//下线消息
    public static int SINGLE_CHAT_MESSAGE_TYPE=4;//单聊
    public static int TEXT_ONLY_MESSAGE_TYPE=5;//文本消息
    public static int EMOJI_ONLY_MESSAGE_TYPE=6;//表情消息
    public static int COMMON_FILE_MESSAGE_TYPE=7;//普通文件
    public static int IMAGE_FILE_MESSAGE_TYPE=8;//图片文件
    public static int VIDEO_FILE_MESSAGE_TYPE=9;//音频文件
    public static int AUDIO_FILE_MESSAGE_TYPE=10;//视频文件
    public static int CHAT_ROOM_MESSAGE_TYPE=11;//群聊消息

}
