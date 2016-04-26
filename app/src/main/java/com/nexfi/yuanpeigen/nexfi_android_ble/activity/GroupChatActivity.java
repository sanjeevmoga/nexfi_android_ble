package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.ReceiveTextMsgListener;
import com.nexfi.yuanpeigen.nexfi_android_ble.model.Node;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.Debug;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.ObjectBytesUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.TimeUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);
        node = MainActivity.getNode();
        Log.e("TAG", node+"-------onCreate---------------------------------------------------");
        userSelfId = UserInfo.initUserId(userSelfId, BleApplication.getContext());
        initView();
        initAdapter();
        setClicklistener();
    }


    private void initAdapter() {
        mDataArrays=bleDBDao.findGroupMsg();
        groupChatAdapater=new GroupChatAdapater(GroupChatActivity.this,mDataArrays);
        lv_chatGroup.setAdapter(groupChatAdapater);
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
            case R.id.iv_pic:
                showToast();
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
        if(Debug.DEBUG){
            Log.e("TAG","-----group---发送群聊-------------------send============");
        }
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
            bleDBDao.addGroupTextMsg(baseMessage,textMessage);
            setAdapter(baseMessage);
            if(Debug.DEBUG){
                Log.e("TAG",user.nodeId+"-----group----------------------send============"+contString);
            }
        }
    }

    private void showToast() {
        Toast.makeText(this, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReceiveTextMsg(Object obj) {
        BaseMessage baseMesage= (BaseMessage) obj;
        TextMessage textMessage= (TextMessage) baseMesage.userMessage;
//        baseMesage.chat_id=textMessage.userId;
        setAdapter(baseMesage);//设置适配器
//        //转发
//        UserMessage userSelf = bleDBDao.findUserByUserId(userSelfId);
//        baseMesage.messageType=MessageType.GROUP_SEND_TEXT_ONLY_MESSAGE_TYPE;
//        baseMesage.sendTime=TimeUtils.getNowTime();
//        baseMesage.userMessage=userSelf;
//        if(Debug.DEBUG){
//            Log.e("TAG",textMessage.nodeId+"-----group--转发--send============"+textMessage.textMessageContent);
//        }
//        byte[] send_file_data = ObjectBytesUtils.ObjectToByte(baseMesage);
//        Link link=node.getLink(textMessage.nodeId);
//        ArrayList<Link> links=node.getLinks();
//        if(links.size()>0){
//            if(Debug.DEBUG){
//                Log.e("TAG","-----转发-------------------======="+links.size());
//            }
//            for (Link link1:links) {
//                if(link1!=link){
//                    link1.sendFrame(send_file_data);
//                    Log.e("TAG", "-----转发------------------link1-=======");
//                }
//            }
//            if(Debug.DEBUG){
//                Log.e("TAG","-----group----send=====broadcastFrame======="+links.size());
//            }
//            if(!bleDBDao.findSameGroupByUuid(baseMesage.uuid)){
//                bleDBDao.addGroupTextMsg(baseMesage,textMessage);
//                setAdapter(baseMesage);
//            }
//        }

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
//        TextMessage textMessage= (TextMessage) baseMesage.userMessage;
//        bleDBDao.addGroupTextMsg(baseMesage, textMessage);//保存到数据库
    }


    @Override
    protected void onDestroy() {
        Log.e("TAG",node+"-------group-----onDestroy-----------------------------");
        super.onDestroy();
    }
}
