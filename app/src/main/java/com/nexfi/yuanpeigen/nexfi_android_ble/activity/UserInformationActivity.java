package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.FileMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;

/**
 * Created by Mark on 2016/4/27.
 */
public class UserInformationActivity extends AppCompatActivity {

    private final String USER_SEX = "男";

    private String userNick, userGender;
    private int userAge, userAvatar;


    private TextView textView, tv_username, tv_userAge;
    private ImageView iv_userhead_icon;
    private Button btn_finish;
    private RadioButton rb_female, rb_male;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initIntentData();
        initView();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_userAge = (TextView) findViewById(R.id.tv_userAge);
        iv_userhead_icon = (ImageView) findViewById(R.id.iv_userhead_icon);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setText(userNick);
        tv_username.setText(userNick);
        tv_userAge.setText(userAge + "");
        iv_userhead_icon.setImageResource(userAvatar);
        if (userGender.equals(USER_SEX)) {
            rb_male.setChecked(true);
        } else {
            rb_female.setChecked(true);
        }
        rb_female.setEnabled(false);
        rb_male.setEnabled(false);
    }

    private void initIntentData() {
        Intent intent = getIntent();
        BaseMessage baseMessage = (BaseMessage) intent.getSerializableExtra("data_obj");
        UserMessage userMessage = (UserMessage) intent.getSerializableExtra("userList");
        if (userMessage != null) {
            userNick = userMessage.userNick;
            userGender = userMessage.userGender;
            userAge = userMessage.userAge;
            userAvatar = userMessage.userAvatar;
        } else {
            if (baseMessage.messageType == MessageType.SEND_TEXT_ONLY_MESSAGE_TYPE || baseMessage.messageType == MessageType.RECEIVE_TEXT_ONLY_MESSAGE_TYPE) {
                TextMessage textMessage = (TextMessage) baseMessage.userMessage;
                userNick = textMessage.userNick;
                userGender = textMessage.userGender;
                userAge = textMessage.userAge;
                userAvatar = textMessage.userAvatar;
            } else if (baseMessage.messageType == MessageType.SINGLE_SEND_FOLDER_MESSAGE_TYPE || baseMessage.messageType == MessageType.SINGLE_RECV_FOLDER_MESSAGE_TYPE || baseMessage.messageType == MessageType.SINGLE_SEND_IMAGE_MESSAGE_TYPE || baseMessage.messageType == MessageType.SINGLE_RECV_IMAGE_MESSAGE_TYPE) {
                FileMessage fileMessage = (FileMessage) baseMessage.userMessage;
                userNick = fileMessage.userNick;
                userGender = fileMessage.userGender;
                userAge = fileMessage.userAge;
                userAvatar = fileMessage.userAvatar;
            }
        }

    }

}

