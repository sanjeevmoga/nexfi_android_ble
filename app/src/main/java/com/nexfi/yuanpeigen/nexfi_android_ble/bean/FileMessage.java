package com.nexfi.yuanpeigen.nexfi_android_ble.bean;

import java.io.Serializable;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class FileMessage extends BaseMessage implements Serializable {
    public String fileName;
    public String fileSize;
    public String fileIcon;
}
