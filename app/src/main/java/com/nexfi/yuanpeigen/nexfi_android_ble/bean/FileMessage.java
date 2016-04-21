package com.nexfi.yuanpeigen.nexfi_android_ble.bean;

import java.io.Serializable;

/**
 * Created by gengbaolong on 2016/4/13.
 */
public class FileMessage extends TextMessage implements Serializable {
    private static  final long serialVersionUID = 2L;
    public String fileName;
    public String fileSize;
    public int fileIcon;
    public int isPb;
    public String filePath;
}
