package com.nexfi.yuanpeigen.nexfi_android_ble.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.helper.BleDBHelper;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.Debug;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gengbaolong on 2016/4/14.
 */
public class BleDBDao {
    private Context context;
    BleDBHelper helper;

    public BleDBDao(Context context) {
        this.context = context;
        helper = new BleDBHelper(context);
    }

    /**
     * 保存用户数据
     *
     * @param
     */
    public void add(BaseMessage baseMessage,UserMessage userMessage) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("messageType",baseMessage.messageType);
        values.put("sendTime",baseMessage.sendTime);
        values.put("chat_id",baseMessage.chat_id);
        baseMessage.userMessage=userMessage;
        values.put("nodeId",userMessage.nodeId);
        values.put("userId", userMessage.userId);
        values.put("userNick", userMessage.userNick);
        values.put("userAge", userMessage.userAge);
        values.put("userGender", userMessage.userGender);
        values.put("userAvatar", userMessage.userAvatar);
        db.insert("userInfoma", null, values);
        db.close();
        if(Debug.DEBUG){
            Log.e("TAG", userMessage.userGender+"----dao---add=====------------"+userMessage.nodeId);
        }
        //有新用户上线
        context.getContentResolver().notifyChange(
                Uri.parse("content://www.nexfi_ble_user.com"), null);
    }


    /**
     * 查找所有用户(把用户自身排除)
     * @param userId
     * @return
     */
    public List<UserMessage> findAllUsers(String userId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("userInfoma", null, null, null, null, null, null);
        List<UserMessage> mDatas = new ArrayList<UserMessage>();
        List<UserMessage> mList = new ArrayList<UserMessage>();
        while (cursor.moveToNext()) {
            UserMessage user = new UserMessage();
//            user.messageType = cursor.getString(cursor.getColumnIndex("messageType"));
//            user.sendTime = cursor.getString(cursor.getColumnIndex("sendTime"));
//            user.chat_id = cursor.getString(cursor.getColumnIndex("chat_id"));
            user.nodeId=cursor.getLong(cursor.getColumnIndex("nodeId"));
            user.userId = cursor.getString(cursor.getColumnIndex("userId"));
            user.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            user.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            user.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            user.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            if(!userId.equals(user.userId)){
                mDatas.add(user);
            }
        }
        cursor.close();
        db.close();
        return mDatas;
    }

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    public UserMessage findUserByUserId(String userId){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userInfoma", null, "userId=?", new String[]{userId}, null, null, null);
        if(cursor.moveToNext()){
            UserMessage user = new UserMessage();
            user.nodeId=cursor.getLong(cursor.getColumnIndex("nodeId"));
            user.userId = cursor.getString(cursor.getColumnIndex("userId"));
            user.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            user.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            user.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            user.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            return user;
        }
        return null;
    }


    /**
     * 根据nodeId查找用户
     * @param nodeId
     * @return
     */
    public UserMessage findUserByNodeId(long nodeId){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userInfoma", null, "nodeId=?", new String[]{nodeId+""}, null, null, null);
        if(cursor.moveToNext()){
            UserMessage user = new UserMessage();
            user.nodeId=cursor.getLong(cursor.getColumnIndex("nodeId"));
            user.userId = cursor.getString(cursor.getColumnIndex("userId"));
            user.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            user.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            user.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            user.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            return user;
        }
        return null;
    }


    /**
     * 根据用户id查找是否有相同数据
     * @param userId
     * @return
     */
    public boolean findSameUserByUserId(String userId){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userInfoma", null, "userId=?", new String[]{userId}, null, null, null);
        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }


    /**
     * 根据userId删除用户信息
     */
    public void deleteUserByNodeId(long nodeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("userInfoma", "nodeId = ?",
                new String[]{nodeId + ""});
        db.close();
        //有用户下线
        context.getContentResolver().notifyChange(
                Uri.parse("content://www.nexfi_ble_user.com"), null);
    }





    /**
     * 保存单聊数据
     *
     * @param
     */
    public void addP2PTextMsg(BaseMessage baseMessage,TextMessage textMessage) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("messageType",baseMessage.messageType);
        values.put("sendTime",baseMessage.sendTime);
        values.put("chat_id",baseMessage.chat_id);
        baseMessage.userMessage=textMessage;
        values.put("nodeId",textMessage.nodeId);
        values.put("userId", textMessage.userId);
        values.put("textMessageContent",textMessage.textMessageContent);
        values.put("userNick", textMessage.userNick);
        values.put("userAge", textMessage.userAge);
        values.put("userGender", textMessage.userGender);
        values.put("userAvatar", textMessage.userAvatar);
        //TODO
        db.insert("textP2PMess", null, values);
        db.close();
        if(Debug.DEBUG){
            Log.e("TAG", textMessage.userGender+"----dao---add=====------------"+textMessage.nodeId);
        }
    }


    /**
     * 根据会话id查找对应的单对单聊天记录
     *
     * @param chat_id
     * @return
     */
    public List<BaseMessage> findMsgByChatId(String chat_id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("textP2PMess", null, "chat_id=?", new String[]{chat_id}, null, null, null);
        List<BaseMessage> mDatas = new ArrayList<BaseMessage>();
        while (cursor.moveToNext()) {
            BaseMessage baseMessage=new BaseMessage();
            baseMessage.messageType=cursor.getInt(cursor.getColumnIndex("messageType"));
            baseMessage.sendTime=cursor.getString(cursor.getColumnIndex("sendTime"));
            baseMessage.chat_id=cursor.getString(cursor.getColumnIndex("chat_id"));
            TextMessage textMessage = new TextMessage();
            textMessage.textMessageContent = cursor.getString(cursor.getColumnIndex("textMessageContent"));
            textMessage.userId = cursor.getString(cursor.getColumnIndex("userId"));
            textMessage.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
            textMessage.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            textMessage.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            textMessage.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            textMessage.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            baseMessage.userMessage=textMessage;
            mDatas.add(baseMessage);
        }
        cursor.close();
        db.close();
        return mDatas;
    }





    /**
     * 保存群聊文本数据
     *
     * @param
     */
    public void addGroupTextMsg(BaseMessage baseMessage,TextMessage textMessage) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("messageType",baseMessage.messageType);
        values.put("sendTime",baseMessage.sendTime);
        values.put("chat_id",baseMessage.chat_id);
        baseMessage.userMessage=textMessage;
        values.put("nodeId",textMessage.nodeId);
        values.put("userId", textMessage.userId);
        values.put("textMessageContent",textMessage.textMessageContent);
        values.put("userNick", textMessage.userNick);
        values.put("userAge", textMessage.userAge);
        values.put("userGender", textMessage.userGender);
        values.put("userAvatar", textMessage.userAvatar);
        db.insert("textGroupMs", null, values);
        db.close();
        if(Debug.DEBUG){
            Log.e("TAG", textMessage.userGender+"----dao---add=====------------"+textMessage.nodeId);
        }
    }

    /**
     * 查找群聊聊天记录
     * @return
     */
    public List<BaseMessage> findGroupMsg() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("textGroupMs", null, null, null, null, null, null);
        List<BaseMessage> mDatas = new ArrayList<BaseMessage>();
        while (cursor.moveToNext()) {
            BaseMessage baseMessage=new BaseMessage();
            baseMessage.messageType=cursor.getInt(cursor.getColumnIndex("messageType"));
            baseMessage.sendTime=cursor.getString(cursor.getColumnIndex("sendTime"));
            baseMessage.chat_id=cursor.getString(cursor.getColumnIndex("chat_id"));
            TextMessage textMessage = new TextMessage();
            textMessage.textMessageContent = cursor.getString(cursor.getColumnIndex("textMessageContent"));
            textMessage.userId = cursor.getString(cursor.getColumnIndex("userId"));
            textMessage.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
            textMessage.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            textMessage.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            textMessage.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            textMessage.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            baseMessage.userMessage=textMessage;
            mDatas.add(baseMessage);
        }
        cursor.close();
        db.close();
        return mDatas;
    }


}
