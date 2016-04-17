package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2016/4/14.
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

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

    private String userNick, userGender;
    private int userAge, userAvatar;

    private ChatMessageAdapater chatMessageAdapater;
    private List<BaseMessage> mDataArrays = new ArrayList<BaseMessage>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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
//                sendMsg();
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
        final String contString = et_chatPrivate.getText().toString();
        if (contString.length() > 0) {

        }
    }
}
