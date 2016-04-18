package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.adapter.ChatMessageAdapater;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.ReceiveTextMsgListener;
import com.nexfi.yuanpeigen.nexfi_android_ble.model.Node;
import com.nexfi.yuanpeigen.nexfi_android_ble.operation.TextMsgOperation;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.Debug;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.ObjectBytesUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

import java.util.ArrayList;
import java.util.List;

import io.underdark.transport.Link;

/**
 * Created by Mark on 2016/4/14.
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener,ReceiveTextMsgListener {

    private RelativeLayout layout_backPrivate;
    private ImageView iv_addPrivate, iv_camera, iv_position, iv_pic;
    private EditText et_chatPrivate;
    private Button btn_sendMsgPrivate;
    private LinearLayout layout_view;
    private ListView lv_chatPrivate;
    private TextView textView_private;

    private boolean visibility_Flag = false;

    private final String USER_SEX = "男";
    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_GENDER = "userGender";
    private final String USER_NICK = "userNick";
    private final String USER_NODE_ID="nodeId";
    private final String USER_ID="userId";

    private String userNick, userGender;
    private int userAge, userAvatar;

    private long nodeId;
    private Node node;
    private String userId;
    private String userSelfId;//用户自身
    TextMsgOperation textMsgOperation;
    BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());

    private ChatMessageAdapater chatMessageAdapater;
    private List<BaseMessage> mDataArrays = new ArrayList<BaseMessage>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        node = MainActivity.getNode();
        textMsgOperation=new TextMsgOperation();
        userSelfId= UserInfo.initUserId(userSelfId, BleApplication.getContext());
//        node=new Node(ChatActivity.this);
        initIntentData();
        initView();
        setClicklistener();

    }

    private void setClicklistener() {
        layout_backPrivate.setOnClickListener(this);
        iv_addPrivate.setOnClickListener(this);
        btn_sendMsgPrivate.setOnClickListener(this);
        iv_pic.setOnClickListener(this);
        iv_position.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        node.setReceiveTextMsgListener(this);
    }

    private void initView() {
        layout_backPrivate = (RelativeLayout) findViewById(R.id.layout_backPrivate);
        iv_addPrivate = (ImageView) findViewById(R.id.iv_addPrivate);
        et_chatPrivate = (EditText) findViewById(R.id.et_chatPrivate);
        btn_sendMsgPrivate = (Button) findViewById(R.id.btn_sendMsgPrivate);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        iv_position = (ImageView) findViewById(R.id.iv_position);
        layout_view = (LinearLayout) findViewById(R.id.layout_viewPrivate);
        lv_chatPrivate = (ListView) findViewById(R.id.lv_chatPrivate);
        textView_private = (TextView) findViewById(R.id.textView_private);
        textView_private.setText(userNick);

    }

    private void initIntentData() {
        Intent intent = getIntent();
        userNick = intent.getStringExtra(USER_NICK);
        userGender = intent.getStringExtra(USER_GENDER);
        userAge = intent.getIntExtra(USER_AGE, 18);
        userAvatar = intent.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
        nodeId=intent.getLongExtra(USER_NODE_ID, 1234568L);
        userId=intent.getStringExtra(USER_ID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_backPrivate:
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_addPrivate:
                if (visibility_Flag) {
                    layout_view.setVisibility(View.GONE);
                    visibility_Flag = false;
                } else {
                    layout_view.setVisibility(View.VISIBLE);
                    visibility_Flag = true;
                }
                break;
            case R.id.btn_sendMsgPrivate:
                sendMsg();
                et_chatPrivate.setText(null);
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
        }
    }

    private void showToast() {
        Toast.makeText(this, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
    }

    private void sendMsg() {
        String contString = et_chatPrivate.getText().toString();
        if (contString.length() > 0) {
            if (Debug.DEBUG) {
                Log.e("TAG","---ChatActivity-------------sendMsg------------------");
            }
            BaseMessage baseMessage=new BaseMessage();
            baseMessage.messageType= MessageType.SEND_TEXT_ONLY_MESSAGE_TYPE;
            UserMessage user=bleDBDao.findUserByUserId(userSelfId);
            TextMessage textMessage=new TextMessage();
            textMessage.textMessageContent=contString;
            textMessage.nodeId=user.nodeId;
            textMessage.userId=user.userId;
            textMessage.userNick=user.userNick;
            textMessage.userGender=user.userGender;
            textMessage.userAvatar=user.userAvatar;
            textMessage.userAge=user.userAge;
            baseMessage.userMessage=textMessage;
            byte[] send_text_data = ObjectBytesUtils.ObjectToByte(baseMessage);
            Link link=node.getLink(nodeId);
            if (Debug.DEBUG) {
                Log.e("TAG",link+"---ChatActivity-----"+nodeId);
            }
            if(null!=link){
                if (Debug.DEBUG) {
                    Log.e("TAG","---ChatActivity-------------sendMsg------------link------");
                }
                link.sendFrame(send_text_data);
                setAdapter(baseMessage);
            }
        }
    }

    @Override
    public void onReceiveTextMsg(Object obj) {
        Log.e("TAG", obj + "----===回调------------------------------9999");
        BaseMessage baseMesage= (BaseMessage) obj;
        setAdapter(baseMesage);
    }

    private void setAdapter(BaseMessage baseMesage) {
        mDataArrays.add(baseMesage);
        if(null==chatMessageAdapater){
            chatMessageAdapater=new ChatMessageAdapater(ChatActivity.this,mDataArrays);
            lv_chatPrivate.setAdapter(chatMessageAdapater);
        }
        chatMessageAdapater.notifyDataSetChanged();
    }
}
