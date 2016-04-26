package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

/**
 * Created by Mark on 2016/4/25.
 */
public class UserInformationActivity extends AppCompatActivity {

    private final String USER_SEX = "男";
    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_GENDER = "userGender";
    private final String USER_NICK = "userNick";
    private final String IS_USERLIST = "is_userlist";
    private final String IS_SEND = "is_send";
    private final String USER_ID = "userId";
    private final String IS_RECEIVE = "is_receive";

    private boolean is_send = false;
    private boolean is_userlist = false;
    private boolean is_receive = false;

    private String userNick_send, userGender_send, userSelfId;
    private int userAge_send, userAvatar_send;

    private String userNick, userGender;
    private int userAge, userAvatar;

    private String userNick_receive, userGender_receive, userId_receive;
    private int userAge_receive, userAvatar_receive;

    private TextView textView, tv_username, tv_userAge;
    private ImageView iv_userhead_icon;
    private Button btn_finish;
    private RadioButton rb_female, rb_male;
    private RadioGroup radioGrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userSelfId = UserInfo.initUserId(userSelfId, BleApplication.getContext());
        setContentView(R.layout.activity_information);
        initView();
        initIntentData();
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
        radioGrop = (RadioGroup) findViewById(R.id.radioGrop);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rb_female.setEnabled(false);
        rb_male.setEnabled(false);
    }


    private void initIntentData() {
        BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());
        Intent intent = getIntent();
        is_userlist = intent.getBooleanExtra(IS_USERLIST, false);
        is_send = intent.getBooleanExtra(IS_SEND, false);
        is_receive = intent.getBooleanExtra(IS_RECEIVE, false);
        userId_receive = intent.getStringExtra(USER_ID);
        if (is_userlist) {
            userNick = intent.getStringExtra(USER_NICK);
            userGender = intent.getStringExtra(USER_GENDER);
            userAge = intent.getIntExtra(USER_AGE, 18);
            userAvatar = intent.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
            textView.setText(userNick);
            tv_username.setText(userNick);
            tv_userAge.setText(userAge + "");
            iv_userhead_icon.setImageResource(userAvatar);
            if (userGender.equals(USER_SEX)) {
                rb_male.setChecked(true);
            } else {
                rb_female.setChecked(true);
            }
        } else if (is_send) {
            UserMessage user1 = bleDBDao.findUserByUserId(userSelfId);
            userNick_send = user1.userNick;
            userGender_send = user1.userGender;
            userAge_send = user1.userAge;
            userAvatar_send = user1.userAvatar;
            textView.setText(userNick_send);
            tv_username.setText(userNick_send);
            tv_userAge.setText(userAge_send + "");
            iv_userhead_icon.setImageResource(userAvatar_send);
            if (userGender_send.equals(USER_SEX)) {
                rb_male.setChecked(true);
            } else {
                rb_female.setChecked(true);
            }
        } else if (is_receive) {
            UserMessage user2 = bleDBDao.findUserByUserId(userId_receive);
            userNick_receive = user2.userNick;
            userGender_receive = user2.userGender;
            userAge_receive = user2.userAge;
            userAvatar_receive = user2.userAvatar;
            textView.setText(userNick_receive);
            tv_username.setText(userNick_receive);
            tv_userAge.setText(userAge_receive + "");
            iv_userhead_icon.setImageResource(userAvatar_receive);
            if (userGender_receive.equals(USER_SEX)) {
                rb_male.setChecked(true);
            } else {
                rb_female.setChecked(true);
            }
        }
    }

}


