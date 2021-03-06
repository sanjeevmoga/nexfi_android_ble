package com.nexfi.yuanpeigen.nexfi_android_ble.model;

import android.app.Activity;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.FileMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.ReceiveTextMsgListener;
import com.nexfi.yuanpeigen.nexfi_android_ble.operation.TextMsgOperation;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.Debug;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.FileTransferUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.ObjectBytesUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.TimeUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

import org.slf4j.impl.StaticLoggerBinder;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import io.underdark.Underdark;
import io.underdark.transport.Link;
import io.underdark.transport.Transport;
import io.underdark.transport.TransportKind;
import io.underdark.transport.TransportListener;
import io.underdark.util.nslogger.NSLogger;
import io.underdark.util.nslogger.NSLoggerAdapter;

public class Node implements TransportListener {
    private boolean running;
    private Activity activity;
    private long nodeId;
    private Transport transport;

    private ArrayList<Link> links = new ArrayList<>();
    private int framesCount = 0;
    BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());
    TextMsgOperation textMsgOperation = new TextMsgOperation();
    ReceiveTextMsgListener mReceiveTextMsgListener = null;

    public void setReceiveTextMsgListener(ReceiveTextMsgListener receiveTextMsgListener) {
        this.mReceiveTextMsgListener = receiveTextMsgListener;
    }

    private String userSelfId;

    public Node(Activity activity) {
        this.activity = activity;

        do {
            nodeId = new Random().nextLong();
        } while (nodeId == 0);

        if (nodeId < 0)
            nodeId = -nodeId;

        configureLogging();

        EnumSet<TransportKind> kinds = EnumSet.of(TransportKind.BLUETOOTH, TransportKind.WIFI);
        //kinds = EnumSet.of(TransportKind.WIFI);
        //kinds = EnumSet.of(TransportKind.BLUETOOTH);

        this.transport = Underdark.configureTransport(
                234235,
                nodeId,
                this,
                null,
                activity.getApplicationContext(),
                kinds
        );
    }

    private void configureLogging() {
        NSLoggerAdapter adapter = (NSLoggerAdapter)
                StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger(Node.class.getName());
        adapter.logger = new NSLogger(activity.getApplicationContext());
        adapter.logger.connect("192.168.5.203", 50000);

        Underdark.configureLogging(true);
    }

    public void start() {
        if (running)
            return;

        running = true;
        transport.start();
        Log.e("TAG", "---Node-------------------------------start------");
    }

    public void stop() {
        if (!running)
            return;

        running = false;
        transport.stop();
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public int getFramesCount() {
        return framesCount;
    }

    //发送数据
    public void broadcastFrame(byte[] frameData) {
        if (links.size() == 0) {
            if (Debug.DEBUG) {
                Log.e("TAG", "---node-------------node------broadcastFrame------------------");
            }
            return;
        }
        for (Link link : links) {
            link.sendFrame(frameData);

        }
    }

    //region TransportListener
    @Override
    public void transportNeedsActivity(Transport transport, ActivityCallback callback) {
        callback.accept(activity);
    }

    //连接
    @Override
    public void transportLinkConnected(Transport transport, Link link) {
        if(Debug.DEBUG){
            Log.e("TAG", "---node-------------------------------------transportLinkConnected------------");
        }

        links.add(link);
        Log.e("TAG", links.size() + "------连接数");
        userSelfId = UserInfo.initUserId(userSelfId, BleApplication.getContext());
        if (null != links && links.size() > 0) {//搜索到设备后就发送请求，并把自己的信息携带过去
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = MessageType.REQUEST_USER_INFO;
            baseMessage.sendTime = TimeUtils.getNowTime();
            UserMessage userMessage = bleDBDao.findUserByUserId(userSelfId);
            if(Debug.DEBUG){
                Log.e("TAG", userMessage + "------连接的nodeId----------------------------link---"+link);
            }
            userMessage.nodeId=link.getNodeId();
            if(Debug.DEBUG){
                Log.e("TAG", userMessage.nodeId + "------连接的nodeId-------------------------------");//7608227584490209377
            }
            baseMessage.userMessage = userMessage;
            bleDBDao.updateUserInfoByUserId(userMessage, userSelfId);
            byte[] data = ObjectBytesUtils.ObjectToByte(baseMessage);
            broadcastFrame(data);
        }
    }

    //断开连接
    @Override
    public void transportLinkDisconnected(Transport transport, Link link) {
        bleDBDao.deleteUserByNodeId(link.getNodeId());
        links.remove(link);//移除link
        if (Debug.DEBUG) {
            Log.e("TAG", "----断开连接-----------------------------------------" + links.size());
        }
    }

    //接收数据，自动调用
    @Override
    public void transportLinkDidReceiveFrame(Transport transport, Link link, byte[] frameData) {
        //接收到数据后将用户数据发送给对方
        BaseMessage baseMessage = (BaseMessage) ObjectBytesUtils.ByteToObject(frameData);
        if (MessageType.REQUEST_USER_INFO == baseMessage.messageType) {
            //对方发过来的请求消息中包含有对方的信息,此时可以将对方的数据保存到本地数据库
            UserMessage userMsg = baseMessage.userMessage;
            userMsg.nodeId = link.getNodeId();
            if (!bleDBDao.findSameUserByUserId(userMsg.userId)) {
                bleDBDao.add(baseMessage, userMsg);
            }
            //收到请求之后，将自己的信息封装发给对方
            BaseMessage baseMsg = new BaseMessage();
            baseMsg.messageType = MessageType.RESPONSE_USER_INFO;//反馈消息
            baseMsg.sendTime = TimeUtils.getNowTime();
            UserMessage userMg = bleDBDao.findUserByUserId(userSelfId);
            baseMsg.userMessage = userMg;
            byte[] dataM = ObjectBytesUtils.ObjectToByte(baseMsg);
            link.sendFrame(dataM);
        } else if (MessageType.RESPONSE_USER_INFO == baseMessage.messageType) {
            //接收对方反馈的用户信息
            UserMessage userMsg = baseMessage.userMessage;
            userMsg.nodeId = link.getNodeId();//这是很重要的一步，将所连接的link跟连接的用户绑定，这样通过nodeId就可以找到对应的link,这样就可以给指定的人发消息了
            //然后将接收到的用户信息保存到数据库
            if(userMsg.userNick!=null){
                if (!bleDBDao.findSameUserByUserId(userMsg.userId)) {
                    bleDBDao.add(baseMessage, userMsg);//华为-保存到数据库---2130903076====d14143dd-22dd-4c86-a9f8-a3698330ce6d
                }
            }
        } else if(MessageType.MODIFY_USER_INFO==baseMessage.messageType){//用户信息修改请求
            //接收到用户信息修改消息后，根据userId将对应的用户信息修改
            UserMessage userMessage=baseMessage.userMessage;
            userMessage.nodeId=link.getNodeId();
            Log.e("TAG",userMessage.nodeId+"-----------接收修改后的NOdeId------------"+link.getNodeId());
            bleDBDao.updateUserInfoByUserId(userMessage,userMessage.userId);//根据userId更新数据库中对应的用户信息
            bleDBDao.updateP2PMsgByUserId(userMessage,userMessage.userId);
        }else if (MessageType.OFFINE_USER_INFO == baseMessage.messageType) {//用户下线通知
            //接收对方的下线信息，将该用户从数据库移除
            if (Debug.DEBUG) {
                Log.e("TAG", "----移除断开连接的用户-----------------------------------------");
            }
        } else if (MessageType.SEND_TEXT_ONLY_MESSAGE_TYPE == baseMessage.messageType) {//文本消息
            TextMessage textMessage = (TextMessage) baseMessage.userMessage;
            baseMessage.messageType = MessageType.RECEIVE_TEXT_ONLY_MESSAGE_TYPE;
            baseMessage.chat_id = textMessage.userId;//geng
            bleDBDao.addP2PTextMsg(baseMessage, textMessage);//geng
            if (null != mReceiveTextMsgListener) {
                mReceiveTextMsgListener.onReceiveTextMsg(baseMessage);
            }
        } else if (MessageType.GROUP_SEND_TEXT_ONLY_MESSAGE_TYPE == baseMessage.messageType) {//群聊
            //接收到消息之后需要根据uuid判断
             //如果数据库有此uuid，则什么都不做
            Log.e("TAG", baseMessage.uuid + "------什么都不做-------" + bleDBDao.findSameGroupByUuid(baseMessage.uuid));
            if (!(bleDBDao.findSameGroupByUuid(baseMessage.uuid))) {
                //如果数据库没有此uuid，则将此条消息转发,并显示
                TextMessage textMessage = (TextMessage) baseMessage.userMessage;
                baseMessage.messageType = MessageType.GROUP_RECEIVE_TEXT_ONLY_MESSAGE_TYPE;
                baseMessage.chat_id = textMessage.userId;
                textMessage.nodeId = link.getNodeId();
                bleDBDao.addGroupTextMsg2(baseMessage, textMessage);
                UserMessage user = bleDBDao.findUserByUserId(userSelfId);
                    if (null != mReceiveTextMsgListener) {
                        mReceiveTextMsgListener.onReceiveTextMsg(baseMessage);
                        if (Debug.DEBUG) {
                            Log.e("TAG", textMessage.textMessageContent + "---mReceiveTextMsgListener-----------------");
                        }
                    }
            }

        }else if (MessageType.GROUP_SEND_IMAGE_MESSAGE_TYPE == baseMessage.messageType) {//群聊----发送图片
            //接收到消息之后需要根据uuid判断
            Log.e("TAG", baseMessage.uuid + "------什么都不做-------" + bleDBDao.findSameGroupByUuid(baseMessage.uuid));
            if (!(bleDBDao.findSameGroupByUuid(baseMessage.uuid))) {
                //如果数据库没有此uuid，则将此条消息转发,并显示
                FileMessage fileMessage = (FileMessage) baseMessage.userMessage;
                baseMessage.messageType = MessageType.GROUP_RECEIVE_IMAGE_MESSAGE_TYPE;
                baseMessage.chat_id = fileMessage.userId;
                fileMessage.nodeId = link.getNodeId();
                bleDBDao.addGroupTextMsg2(baseMessage, fileMessage);
                String file_name = fileMessage.fileName;//文件名
                File fileDir = null;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //存在sd卡
                    fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NexFi_ble/image");
                    if (!fileDir.exists()) {
                        fileDir.mkdirs();
                    }
                }
                String rece_file_path = fileDir + "/" + file_name;
                byte[] bys_receive = Base64.decode(fileMessage.fileData, Base64.DEFAULT);//文件数据
                File file = FileTransferUtils.getFileFromBytes(bys_receive, rece_file_path);
                fileMessage.filePath = rece_file_path;

                if (null != mReceiveTextMsgListener) {
                    mReceiveTextMsgListener.onReceiveTextMsg(baseMessage);
                    if (Debug.DEBUG) {
                        Log.e("TAG", fileMessage.textMessageContent + "---fileMessage----group-------------");
                    }
                }
            }

        }
        else if (MessageType.SINGLE_SEND_IMAGE_MESSAGE_TYPE == baseMessage.messageType) {//发送图片
            FileMessage fileMessage = (FileMessage) baseMessage.userMessage;
            baseMessage.messageType = MessageType.SINGLE_RECV_IMAGE_MESSAGE_TYPE;
            baseMessage.chat_id = fileMessage.userId;
            String file_name = fileMessage.fileName;//文件名
            File fileDir = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //存在sd卡
                fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NexFi_ble/image");
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
            }
            String rece_file_path = fileDir + "/" + file_name;
            byte[] bys_receive = Base64.decode(fileMessage.fileData, Base64.DEFAULT);//文件数据
            File file = FileTransferUtils.getFileFromBytes(bys_receive, rece_file_path);
            fileMessage.filePath = rece_file_path;
            bleDBDao.addP2PTextMsg(baseMessage, fileMessage);
            if (null != mReceiveTextMsgListener) {
                mReceiveTextMsgListener.onReceiveTextMsg(baseMessage);
            }
        } else if (baseMessage.messageType == MessageType.SINGLE_SEND_FOLDER_MESSAGE_TYPE) {//发送文件
            FileMessage fileMessage = (FileMessage) baseMessage.userMessage;
            baseMessage.messageType = MessageType.SINGLE_RECV_FOLDER_MESSAGE_TYPE;
            baseMessage.chat_id = fileMessage.userId;
            String file_name = fileMessage.fileName;//文件名
            File fileDir = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //存在sd卡
                fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NexFi_ble/file");
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
            }
            String rece_file_path = fileDir + "/" + file_name;
            byte[] bys_receive_data = Base64.decode(fileMessage.fileData, Base64.DEFAULT);
            long file_size=Long.parseLong(fileMessage.fileSize);
            String formatSize = android.text.format.Formatter.formatFileSize(BleApplication.getContext(), file_size);//
            File file = FileTransferUtils.getFileFromBytes(bys_receive_data, rece_file_path);
            fileMessage.filePath = rece_file_path;
            if (Debug.DEBUG) {
                Log.e("TAG", file_name + "----接收到文件-------" + rece_file_path + "------file-------" + file.getPath());
            }
            if (null != mReceiveTextMsgListener) {
                mReceiveTextMsgListener.onReceiveTextMsg(baseMessage);
            }
        }
    }


    /**
     * 根据nodeId获取link
     *
     * @param nodeId
     * @return
     */
    public Link getLink(long nodeId) {
        for (int i = 0; i < links.size(); i++) {
            Link link = links.get(i);
            if (link.getNodeId() == nodeId) {
                return link;
            }
        }
        return null;
    }
} // Node
