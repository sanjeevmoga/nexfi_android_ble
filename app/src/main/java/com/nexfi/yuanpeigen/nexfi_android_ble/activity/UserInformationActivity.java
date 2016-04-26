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

    private String userSelfId;

    private boolean is_send = false;
    private boolean is_userlist = false;

    private String userNick, userGender, userId;
    private int userAge, userAvatar;

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
        radioGrop = (RadioGroup) findViewById(R.id.radioGrop);
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
        BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());
        Intent intent = getIntent();
        is_userlist = intent.getBooleanExtra(IS_USERLIST, false);
        is_send = intent.getBooleanExtra(IS_SEND, false);
        userId = intent.getStringExtra(USER_ID);
        if (is_userlist) {
            userNick = intent.getStringExtra(USER_NICK);
            userGender = intent.getStringExtra(USER_GENDER);
            userAge = intent.getIntExtra(USER_AGE, 18);
            userAvatar = intent.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
        } else if (is_send) {
            UserMessage user = bleDBDao.findUserByUserId(userSelfId);
            userNick = user.userNick;
            userGender = user.userGender;
            userAge = user.userAge;
            userAvatar = user.userAvatar;
        } else {
            UserMessage user = bleDBDao.findUserByUserId(userId);
            userNick = user.userNick;
            userGender = user.userGender;
            userAge = user.userAge;
            userAvatar = user.userAvatar;
        }
    }


}
