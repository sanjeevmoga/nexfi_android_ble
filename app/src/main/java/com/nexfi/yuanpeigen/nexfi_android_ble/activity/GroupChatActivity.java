package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.adapter.GroupChatAdapater;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.FileMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.ReceiveTextMsgListener;
import com.nexfi.yuanpeigen.nexfi_android_ble.model.Node;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.FileTransferUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.FileUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.ObjectBytesUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.TUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.TimeUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.underdark.transport.Link;

/**
 * Created by Mark on 2016/4/13.
 */
public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener,ReceiveTextMsgListener {

    private RelativeLayout layout_backGroup;
    private ImageView iv_addGroup, iv_camera, iv_position, iv_pic, iv_showUserInfo;
    private EditText et_chatGroup;
    private Button btn_sendMsgGroup;
    private boolean visibility_Flag = false;
    private LinearLayout layout_view;
    private ListView lv_chatGroup;
    private String userSelfId;//用户自身
    private Node node;
    BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());
    private GroupChatAdapater groupChatAdapater;
    private List<BaseMessage> mDataArrays = new ArrayList<BaseMessage>();
    public static final int REQUEST_CODE_LOCAL_IMAGE = 1;//图片
    public static final int REQUEST_CODE_SELECT_FILE = 2;//文件
    public static final int SELECT_A_PICTURE = 3;//4.4以下
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 4;//4.4以上

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        node = MainActivity.getNode();
        userSelfId = UserInfo.initUserId(userSelfId, BleApplication.getContext());
        initView();
        initAdapter();
        setClicklistener();
    }


    private void initAdapter() {
        Log.e("TAG","initAdapter--------------------------------------------");
        mDataArrays=bleDBDao.findGroupMsg();
        groupChatAdapater=new GroupChatAdapater(GroupChatActivity.this,mDataArrays);
        lv_chatGroup.setAdapter(groupChatAdapater);
        Log.e("TAG", "initAdapter---------------------------结束-----------------");
    }

    private void setClicklistener() {
        layout_backGroup.setOnClickListener(this);
        iv_addGroup.setOnClickListener(this);
        btn_sendMsgGroup.setOnClickListener(this);
        iv_pic.setOnClickListener(this);
        iv_position.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        iv_showUserInfo.setOnClickListener(this);
        node.setReceiveTextMsgListener(this);
    }

    private void initView() {
        layout_backGroup = (RelativeLayout) findViewById(R.id.layout_backGroup);
        iv_addGroup = (ImageView) findViewById(R.id.iv_addGroup);
        et_chatGroup = (EditText) findViewById(R.id.et_chatGroup);
        btn_sendMsgGroup = (Button) findViewById(R.id.btn_sendMsgGroup);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        iv_position = (ImageView) findViewById(R.id.iv_position);
        layout_view = (LinearLayout) findViewById(R.id.layout_viewGroup);
        lv_chatGroup = (ListView) findViewById(R.id.lv_chatGroup);
        iv_showUserInfo = (ImageView) findViewById(R.id.iv_showUserInfo);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_backGroup:
                finish();
                break;
            case R.id.iv_addGroup:
                if (visibility_Flag) {
                    layout_view.setVisibility(View.GONE);
                    visibility_Flag = false;
                } else {
                    layout_view.setVisibility(View.VISIBLE);
                    visibility_Flag = true;
                }
                break;
            case R.id.btn_sendMsgGroup://发送消息
                sendGroupMsg();
                et_chatGroup.setText(null);
                break;
            case R.id.iv_pic://发图片
                FileTransferUtils.selectPicFromLocal(GroupChatActivity.this);
                break;
            case R.id.iv_camera:
                showToast();
                break;
            case R.id.iv_position:
                showToast();
                break;
            case R.id.iv_showUserInfo:
                showToast();
                break;
        }
    }

    private void sendGroupMsg() {
        String contString = et_chatGroup.getText().toString();
        if (contString.length() > 0) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = MessageType.GROUP_SEND_TEXT_ONLY_MESSAGE_TYPE;
            baseMessage.sendTime= TimeUtils.getNowTime();
//            baseMessage.chat_id=userId;
            baseMessage.uuid= UUID.randomUUID().toString();
            UserMessage user = bleDBDao.findUserByUserId(userSelfId);
            TextMessage textMessage = new TextMessage();
            textMessage.textMessageContent = contString;
            textMessage.nodeId = user.nodeId;
            textMessage.userId = user.userId;
            textMessage.userNick = user.userNick;
            textMessage.userGender = user.userGender;
            textMessage.userAvatar = user.userAvatar;
            textMessage.userAge = user.userAge;
            baseMessage.userMessage = textMessage;
            byte[] send_text_data = ObjectBytesUtils.ObjectToByte(baseMessage);
            node.broadcastFrame(send_text_data);
            bleDBDao.addGroupTextMsg2(baseMessage, textMessage);
            setAdapter(baseMessage);
        }
    }



    /**
     * 根据图片路径发送图片
     *
     * @param filePath
     */
    private void sendImageMsg(String filePath) {
        File fileToSend = FileTransferUtils.scal(filePath);
        byte[] send_file_size=(""+fileToSend.length()).getBytes();
        String fileSize= Base64.encodeToString(send_file_size, Base64.DEFAULT);//文件长度
        byte[] bys_send_data = null;
        try {
            bys_send_data = FileTransferUtils.getBytesFromFile(fileToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == bys_send_data) {
            return;
        }
        String tFileData = Base64.encodeToString(bys_send_data, Base64.DEFAULT);
        String fileName = fileToSend.getName();//文件名
        ArrayList<Link> links=node.getLinks();
        if (links != null && links.size()>0) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = MessageType.GROUP_SEND_IMAGE_MESSAGE_TYPE;
            baseMessage.sendTime = TimeUtils.getNowTime();
//            baseMessage.chat_id = userId;
            baseMessage.uuid=UUID.randomUUID().toString();
            UserMessage user = bleDBDao.findUserByUserId(userSelfId);
            FileMessage fileMessage = new FileMessage();
            fileMessage.fileSize = fileSize;
            fileMessage.fileData=tFileData;
            fileMessage.fileName = fileName;
            fileMessage.filePath = filePath;
            fileMessage.nodeId = user.nodeId;
            fileMessage.userId = user.userId;
            fileMessage.userNick = user.userNick;
            fileMessage.userGender = user.userGender;
            fileMessage.userAvatar = user.userAvatar;
            fileMessage.userAge = user.userAge;
            baseMessage.userMessage = fileMessage;
            final byte[] send_file_data = ObjectBytesUtils.ObjectToByte(baseMessage);
            node.broadcastFrame(send_file_data);
            bleDBDao.addGroupTextMsg2(baseMessage, fileMessage);//geng
            setAdapter(baseMessage);
            Log.e("TAG",  "------发送结束-----------------------------------------------");
        }
    }



    private void showToast() {
        Toast.makeText(this, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveTextMsg(Object obj) {
        BaseMessage baseMesage= (BaseMessage) obj;
        if(baseMesage.messageType==MessageType.GROUP_RECEIVE_TEXT_ONLY_MESSAGE_TYPE){
            TextMessage textMessage= (TextMessage) baseMesage.userMessage;
            setAdapter(baseMesage);//设置适配器
            //转发消息
            if(node.getLinks().size()>1) {
                BaseMessage newMessage=new BaseMessage();
                newMessage.messageType=MessageType.GROUP_SEND_TEXT_ONLY_MESSAGE_TYPE;
                newMessage.sendTime = TimeUtils.getNowTime();
                newMessage.uuid=baseMesage.uuid;
                newMessage.userMessage=textMessage;
                final byte[] send_file_data = ObjectBytesUtils.ObjectToByte(newMessage);
                final Link link2 = node.getLink(textMessage.nodeId);
                if (node.getLinks().size() > 0) {
                    for (Link link1 : node.getLinks()) {
                        if (link2 != link1) {
                            link1.sendFrame(send_file_data);
                        }
                    }
                }
            }
        }else if(baseMesage.messageType==MessageType.GROUP_RECEIVE_IMAGE_MESSAGE_TYPE){
            FileMessage fileMessage=(FileMessage)baseMesage.userMessage;
            setAdapter(baseMesage);//设置适配器
            //转发消息
//            if(node.getLinks().size()>1) {
//                BaseMessage newMessage=new BaseMessage();
//                newMessage.messageType=MessageType.GROUP_SEND_TEXT_ONLY_MESSAGE_TYPE;
//                newMessage.sendTime = TimeUtils.getNowTime();
//                newMessage.uuid=baseMesage.uuid;
//                newMessage.userMessage=fileMessage;
//                final byte[] send_file_data = ObjectBytesUtils.ObjectToByte(newMessage);
//                final Link link2 = node.getLink(fileMessage.nodeId);
//                if (node.getLinks().size() > 0) {
//                    for (Link link1 : node.getLinks()) {
//                        if (link2 != link1) {
//                            link1.sendFrame(send_file_data);
//                        }
//                    }
//                }
//            }
        }



    }


    private void setAdapter(BaseMessage baseMesage) {
        mDataArrays.add(baseMesage);
        if(null==groupChatAdapater){
            groupChatAdapater=new GroupChatAdapater(GroupChatActivity.this, mDataArrays);
            lv_chatGroup.setAdapter(groupChatAdapater);
        }
        groupChatAdapater.notifyDataSetChanged();
        if (mDataArrays.size() > 0) {
            lv_chatGroup.setSelection(mDataArrays.size() - 1);// 最后一行
        }
    }


    @Override
    protected void onDestroy() {
        Log.e("TAG", node + "-------group-----onDestroy-----------------------------");
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String selectPath = null;
        if (requestCode == SELECT_A_PICTURE) {//4.4以下
            if (resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                selectPath = FileUtils.getPath(this, selectedImage);
                sendImageMsg(selectPath);
            }
        } else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {//4.4以上
            if (resultCode == RESULT_OK && null != data) {
                selectPath = TUtils.getPath(this, data.getData());
                if (null != selectPath) {
                    sendImageMsg(selectPath);
                }
            }
        } else if (requestCode == REQUEST_CODE_SELECT_FILE) {
            if (data != null) {
                Uri uri = data.getData();
                if (null != uri) {
                    String select_file_path = FileUtils.getPath(this, uri);
                    if (select_file_path != null) {
//                        sendFileMsg(select_file_path);
                    }
                }
            }
        }
    }
}
