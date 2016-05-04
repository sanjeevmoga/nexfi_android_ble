package com.nexfi.yuanpeigen.nexfi_android_ble.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.FileMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.helper.BleDBHelper;

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
    public void add(BaseMessage baseMessage, UserMessage userMessage) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("messageType", baseMessage.messageType);
        values.put("sendTime", baseMessage.sendTime);
        values.put("chat_id", baseMessage.chat_id);
        values.put("uuid", baseMessage.uuid);
        baseMessage.userMessage = userMessage;
        values.put("nodeId", userMessage.nodeId);
        values.put("userId", userMessage.userId);
        values.put("userNick", userMessage.userNick);
        values.put("userAge", userMessage.userAge);
        values.put("userGender", userMessage.userGender);
        values.put("userAvatar", userMessage.userAvatar);
        db.insert("userInfomat", null, values);
        db.close();
        Log.e("TAG", userMessage.userNick+"-保存到数据库---"+userMessage.userAvatar+"===="+userMessage.userId);//dafc930f-be67-4b2c-bada-f52c5265d8d5
        //ba8011cc-fcda-4a97-8074-492805d26a92
        //有新用户上线
        context.getContentResolver().notifyChange(
                Uri.parse("content://www.nexfi_ble_user.com"), null);
    }


    /**
     * 根据userId修改数据库中原有用户信息
     *
     * @param userMessage
     * @param userId
     */
    public void updateUserInfoByUserId(UserMessage userMessage, String userId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nodeId", userMessage.nodeId);
        values.put("userId", userMessage.userId);
        values.put("userNick", userMessage.userNick);
        values.put("userAge", userMessage.userAge);
        values.put("userGender", userMessage.userGender);
        values.put("userAvatar", userMessage.userAvatar);
        db.update("userInfomat", values, "userId=?", new String[]{userId});
        db.close();
        context.getContentResolver().notifyChange(
                Uri.parse("content://www.nexfi_ble_user.com"), null);
        Log.e("TAG", userMessage.userNick + "---------------------用户数据改变了------------------" + userMessage.userAvatar);
    }


    /**
     * 查找所有用户(把用户自身排除)
     *
     * @param userId
     * @return
     */
    public List<UserMessage> findAllUsers(String userId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("userInfomat", null, null, null, null, null, null);
        List<UserMessage> mDatas = new ArrayList<UserMessage>();
        List<UserMessage> mList = new ArrayList<UserMessage>();
        while (cursor.moveToNext()) {
            UserMessage user = new UserMessage();
            user.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
            user.userId = cursor.getString(cursor.getColumnIndex("userId"));
            user.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            user.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            user.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            user.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            if (!userId.equals(user.userId)) {
                mDatas.add(user);
            }
        }
        cursor.close();
        db.close();
        return mDatas;
    }

    /**
     * 根据用户id查找用户
     *
     * @param userId
     * @return
     */
    public UserMessage findUserByUserId(String userId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userInfomat", null, "userId=?", new String[]{userId}, null, null, null);
        if (cursor.moveToNext()) {
            UserMessage user = new UserMessage();
            user.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
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
     *
     * @param nodeId
     * @return
     */
    public UserMessage findUserByNodeId(long nodeId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userInfomat", null, "nodeId=?", new String[]{nodeId + ""}, null, null, null);
        if (cursor.moveToNext()) {
            UserMessage user = new UserMessage();
            user.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
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
     *
     * @param userId
     * @return
     */
    public boolean findSameUserByUserId(String userId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("userInfomat", null, "userId=?", new String[]{userId}, null, null, null);
        if (cursor.moveToNext()) {
            return true;
        }
        return false;
    }


    /**
     * 根据userId删除用户信息
     */
    public void deleteUserByNodeId(long nodeId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("userInfomat", "nodeId = ?",
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
    public void addP2PTextMsg(BaseMessage baseMessage, TextMessage textMessage) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("messageType", baseMessage.messageType);
        values.put("sendTime", baseMessage.sendTime);
        values.put("chat_id", baseMessage.chat_id);
        values.put("uuid", baseMessage.uuid);
        if (baseMessage.messageType == MessageType.SEND_TEXT_ONLY_MESSAGE_TYPE || baseMessage.messageType == MessageType.RECEIVE_TEXT_ONLY_MESSAGE_TYPE) {
            baseMessage.userMessage = textMessage;
            values.put("nodeId", textMessage.nodeId);
            values.put("userId", textMessage.userId);
            values.put("textMessageContent", textMessage.textMessageContent);
            values.put("userNick", textMessage.userNick);
            values.put("userAge", textMessage.userAge);
            values.put("userGender", textMessage.userGender);
            values.put("userAvatar", textMessage.userAvatar);
        }

        //TODO
        if (baseMessage.messageType == MessageType.SINGLE_SEND_IMAGE_MESSAGE_TYPE || baseMessage.messageType == MessageType.SINGLE_RECV_IMAGE_MESSAGE_TYPE) {
            FileMessage fileMessage = (FileMessage) textMessage;
            values.put("nodeId", fileMessage.nodeId);
            values.put("userId", fileMessage.userId);
            values.put("textMessageContent", fileMessage.textMessageContent);
            values.put("userNick", fileMessage.userNick);
            values.put("userAge", fileMessage.userAge);
            values.put("userGender", fileMessage.userGender);
            values.put("userAvatar", fileMessage.userAvatar);
            values.put("fileName", fileMessage.fileName);
            values.put("filePath", fileMessage.filePath);
            values.put("fileIcon", fileMessage.fileIcon);
            values.put("fileSize", fileMessage.fileSize);
            values.put("fileData", fileMessage.fileData);
            values.put("isPb", fileMessage.isPb);
        }
        db.insert("textP2PMessg", null, values);
        db.close();
    }


    /**
     * 根据会话id查找对应的单对单聊天记录
     *
     * @param chat_id
     * @return
     */
    public List<BaseMessage> findMsgByChatId(String chat_id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("textP2PMessg", null, "chat_id=?", new String[]{chat_id}, null, null, null);
        List<BaseMessage> mDatas = new ArrayList<BaseMessage>();
        while (cursor.moveToNext()) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = cursor.getInt(cursor.getColumnIndex("messageType"));
            baseMessage.sendTime = cursor.getString(cursor.getColumnIndex("sendTime"));
            baseMessage.chat_id = cursor.getString(cursor.getColumnIndex("chat_id"));
            baseMessage.uuid = cursor.getString(cursor.getColumnIndex("uuid"));
            FileMessage fileMessage = new FileMessage();
            fileMessage.textMessageContent = cursor.getString(cursor.getColumnIndex("textMessageContent"));
            fileMessage.userId = cursor.getString(cursor.getColumnIndex("userId"));
            fileMessage.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
            fileMessage.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            fileMessage.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            fileMessage.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            fileMessage.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            fileMessage.filePath = cursor.getString(cursor.getColumnIndex("filePath"));
            fileMessage.fileName = cursor.getString(cursor.getColumnIndex("fileName"));
            fileMessage.fileSize = cursor.getString(cursor.getColumnIndex("fileSize"));
            fileMessage.fileIcon = cursor.getInt(cursor.getColumnIndex("fileIcon"));
            fileMessage.fileData = cursor.getString(cursor.getColumnIndex("fileData"));
            fileMessage.isPb = cursor.getInt(cursor.getColumnIndex("isPb"));
            baseMessage.userMessage = fileMessage;
            mDatas.add(baseMessage);
        }
        cursor.close();
        db.close();
        return mDatas;
    }


    /**
     * 根据userId更新数据库中的单聊数据
     * @param userMessage
     * @param userId
     */
    public void updateP2PMsgByUserId(UserMessage userMessage, String userId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nodeId", userMessage.nodeId);
        values.put("userId", userMessage.userId);
        values.put("userNick", userMessage.userNick);
        values.put("userAge", userMessage.userAge);
        values.put("userGender", userMessage.userGender);
        values.put("userAvatar", userMessage.userAvatar);
        db.update("textP2PMessg", values, "userId=?", new String[]{userId});
        db.close();
        context.getContentResolver().notifyChange(
                Uri.parse("content://www.nexfi_ble_user_single.com"), null);
        Log.e("TAG", userMessage.userNick + "-----聊天数据改变了-------" + userMessage.userAvatar+"------"+userMessage.nodeId);
    }


    public List<BaseMessage> findMsgByChatIdAndUserId(String chat_id,String userId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("textP2PMessg", null, "chat_id=?", new String[]{chat_id}, null, null, null);
        List<BaseMessage> mDatas = new ArrayList<BaseMessage>();
        while (cursor.moveToNext()) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = cursor.getInt(cursor.getColumnIndex("messageType"));
            baseMessage.sendTime = cursor.getString(cursor.getColumnIndex("sendTime"));
            baseMessage.chat_id = cursor.getString(cursor.getColumnIndex("chat_id"));
            baseMessage.uuid = cursor.getString(cursor.getColumnIndex("uuid"));
            FileMessage fileMessage = new FileMessage();
            fileMessage.textMessageContent = cursor.getString(cursor.getColumnIndex("textMessageContent"));
            UserMessage userMessage=findUserByUserId(userId);
            fileMessage.userId = userMessage.userId;
            fileMessage.nodeId = userMessage.nodeId;
            fileMessage.userNick = userMessage.userNick;
            fileMessage.userAvatar = userMessage.userAvatar;
            fileMessage.userGender =userMessage.userGender;
            fileMessage.userAge = userMessage.userAge;
            fileMessage.filePath = cursor.getString(cursor.getColumnIndex("filePath"));
            fileMessage.fileName = cursor.getString(cursor.getColumnIndex("fileName"));
            fileMessage.fileSize = cursor.getString(cursor.getColumnIndex("fileSize"));
            fileMessage.fileIcon = cursor.getInt(cursor.getColumnIndex("fileIcon"));
            fileMessage.fileData = cursor.getString(cursor.getColumnIndex("fileData"));
            fileMessage.isPb = cursor.getInt(cursor.getColumnIndex("isPb"));
            baseMessage.userMessage = fileMessage;
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
    public void addGroupTextMsg(BaseMessage baseMessage, TextMessage textMessage) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("messageType", baseMessage.messageType);
        values.put("sendTime", baseMessage.sendTime);
        values.put("chat_id", baseMessage.chat_id);
        values.put("uuid", baseMessage.uuid);
        baseMessage.userMessage = textMessage;
        values.put("nodeId", textMessage.nodeId);
        values.put("userId", textMessage.userId);
        values.put("textMessageContent", textMessage.textMessageContent);
        values.put("userNick", textMessage.userNick);
        values.put("userAge", textMessage.userAge);
        values.put("userGender", textMessage.userGender);
        values.put("userAvatar", textMessage.userAvatar);
        db.insert("textGroupMesg", null, values);
        db.close();
    }



    /**
     * 保存群聊数据
     *
     * @param
     */
    public void addGroupTextMsg2(BaseMessage baseMessage, TextMessage textMessage) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("messageType", baseMessage.messageType);
        values.put("sendTime", baseMessage.sendTime);
        values.put("chat_id", baseMessage.chat_id);
        values.put("uuid", baseMessage.uuid);
        if (baseMessage.messageType == MessageType.GROUP_SEND_TEXT_ONLY_MESSAGE_TYPE || baseMessage.messageType == MessageType.GROUP_RECEIVE_TEXT_ONLY_MESSAGE_TYPE) {
            baseMessage.userMessage = textMessage;
            values.put("nodeId", textMessage.nodeId);
            values.put("userId", textMessage.userId);
            values.put("textMessageContent", textMessage.textMessageContent);
            values.put("userNick", textMessage.userNick);
            values.put("userAge", textMessage.userAge);
            values.put("userGender", textMessage.userGender);
            values.put("userAvatar", textMessage.userAvatar);
        }

        //TODO
        if (baseMessage.messageType == MessageType.GROUP_SEND_IMAGE_MESSAGE_TYPE || baseMessage.messageType == MessageType.GROUP_RECEIVE_IMAGE_MESSAGE_TYPE) {
            FileMessage fileMessage = (FileMessage) textMessage;
            values.put("nodeId", fileMessage.nodeId);
            values.put("userId", fileMessage.userId);
            values.put("textMessageContent", fileMessage.textMessageContent);
            values.put("userNick", fileMessage.userNick);
            values.put("userAge", fileMessage.userAge);
            values.put("userGender", fileMessage.userGender);
            values.put("userAvatar", fileMessage.userAvatar);
            values.put("fileName", fileMessage.fileName);
            values.put("filePath", fileMessage.filePath);
            values.put("fileIcon", fileMessage.fileIcon);
            values.put("fileSize", fileMessage.fileSize);
            values.put("fileData", fileMessage.fileData);
            values.put("isPb", fileMessage.isPb);
        }
        db.insert("textGroupMesg", null, values);
        db.close();
    }


    /**
     * 查找群聊聊天记录
     *
     * @return
     */
    public List<BaseMessage> findGroupMsg() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("textGroupMesg", null, null, null, null, null, null);
        List<BaseMessage> mDatas = new ArrayList<BaseMessage>();
        while (cursor.moveToNext()) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = cursor.getInt(cursor.getColumnIndex("messageType"));
            baseMessage.sendTime = cursor.getString(cursor.getColumnIndex("sendTime"));
            baseMessage.chat_id = cursor.getString(cursor.getColumnIndex("chat_id"));
            baseMessage.uuid = cursor.getString(cursor.getColumnIndex("uuid"));
//            TextMessage textMessage = new TextMessage();
//            textMessage.textMessageContent = cursor.getString(cursor.getColumnIndex("textMessageContent"));
//            textMessage.userId = cursor.getString(cursor.getColumnIndex("userId"));
//            textMessage.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
//            textMessage.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
//            textMessage.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
//            textMessage.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
//            textMessage.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
//            baseMessage.userMessage = textMessage;
            FileMessage fileMessage = new FileMessage();
            fileMessage.textMessageContent = cursor.getString(cursor.getColumnIndex("textMessageContent"));
            fileMessage.userId = cursor.getString(cursor.getColumnIndex("userId"));
            fileMessage.nodeId = cursor.getLong(cursor.getColumnIndex("nodeId"));
            fileMessage.userNick = cursor.getString(cursor.getColumnIndex("userNick"));
            fileMessage.userAvatar = cursor.getInt(cursor.getColumnIndex("userAvatar"));
            fileMessage.userGender = cursor.getString(cursor.getColumnIndex("userGender"));
            fileMessage.userAge = cursor.getInt(cursor.getColumnIndex("userAge"));
            fileMessage.filePath = cursor.getString(cursor.getColumnIndex("filePath"));
            fileMessage.fileName = cursor.getString(cursor.getColumnIndex("fileName"));
            fileMessage.fileSize = cursor.getString(cursor.getColumnIndex("fileSize"));
            fileMessage.fileIcon = cursor.getInt(cursor.getColumnIndex("fileIcon"));
            fileMessage.fileData = cursor.getString(cursor.getColumnIndex("fileData"));
            fileMessage.isPb = cursor.getInt(cursor.getColumnIndex("isPb"));
            baseMessage.userMessage = fileMessage;
            mDatas.add(baseMessage);
        }
        cursor.close();
        db.close();
        return mDatas;
    }


    /**
     * 根据用户uuid查找是否有相同聊天数据
     *
     * @param uuid
     * @return
     */
    public boolean findSameGroupByUuid(String uuid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("textGroupMesg", null, "uuid=?", new String[]{uuid}, null, null, null);
        if (cursor.moveToNext()) {
            return true;
        }
        return false;
    }


}
