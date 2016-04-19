package com.nexfi.yuanpeigen.nexfi_android_ble.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gengbaolong on 2016/4/14.
 */
public class BleDBHelper extends SQLiteOpenHelper {
    public BleDBHelper(Context context) {
        super(context, "blue.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户
        db.execSQL("create table userInfoma (_id integer primary key autoincrement,messageType Integer(20),sendTime varchar(20),chat_id varchar(20),nodeId varchar(20),userId varchar(20),userNick varchar(20),userAge Integer(20),userGender varchar(20),userAvatar Integer(20))");
        //单聊
//        db.execSQL("create table textP2PMess(_id integer primary key autoincrement,messageType Integer(20),sendTime varchar(20),chat_id varchar(20),nodeId varchar(20),userId varchar(20),userNick varchar(20),userAge Integer(20),userGender varchar(20),userAvatar Integer(20),textMessageContent varchar(20)," +
//                "fileName varchar(20),fileSize varchar(20),fileIcon Integer(20),isPb Integer(20))");
        db.execSQL("create table textP2PMess(_id integer primary key autoincrement,messageType Integer(20),sendTime varchar(20),chat_id varchar(20),nodeId varchar(20),userId varchar(20),userNick varchar(20),userAge Integer(20),userGender varchar(20),userAvatar Integer(20),textMessageContent varchar(20))");
//        //群聊
        db.execSQL("create table textGroupMs (_id integer primary key autoincrement,messageType Integer(20),sendTime varchar(20),chat_id varchar(20),nodeId varchar(20),userId varchar(20),userNick varchar(20),userAge Integer(20),userGender varchar(20),userAvatar Integer(20),textMessageContent varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
