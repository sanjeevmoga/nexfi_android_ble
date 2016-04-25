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


    private boolean is_chatsend = false;
    private boolean is_chatreceive = false;
    private boolean is_sendfile = false;
    private boolean is_receivefile = false;
    private boolean is_sendimage = false;
    private boolean is_receiveimage = false;

    private boolean is_userlist = false;

    private String userNick, userGender;
    private int userAge, userAvatar;

    private TextView textView, tv_username, tv_userAge;
    private ImageView iv_userhead_icon;
    private Button btn_finish;
    private RadioButton rb_female, rb_male;
    private RadioGroup radioGrop;

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
        Intent intent = getIntent();
        is_userlist = intent.getBooleanExtra(IS_USERLIST, false);
        is_chatsend = intent.getBooleanExtra(UserInfo.IS_CHATSEND, false);
        is_chatreceive = intent.getBooleanExtra(UserInfo.IS_CHATRECEIVE, false);
        is_sendfile = intent.getBooleanExtra(UserInfo.IS_SENDFILE, false);
        is_receivefile = intent.getBooleanExtra(UserInfo.IS_RECEIVEFILE, false);
        is_sendimage = intent.getBooleanExtra(UserInfo.IS_SENDIMAGE, false);
        is_receiveimage = intent.getBooleanExtra(UserInfo.IS_RECEIVEIMAGE, false);
        if (is_userlist) {
            userNick = intent.getStringExtra(USER_NICK);
            userGender = intent.getStringExtra(USER_GENDER);
            userAge = intent.getIntExtra(USER_AGE, 18);
            userAvatar = intent.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
        } else if (is_chatsend) {
            userNick = intent.getStringExtra(UserInfo.USER_NICK_CHATSEND);
            userGender = intent.getStringExtra(UserInfo.USER_GENDER_CHATSEND);
            userAge = intent.getIntExtra(UserInfo.USER_AGE_CHATSEND, 18);
            userAvatar = intent.getIntExtra(UserInfo.USER_AVATAR_CHATSEND, R.mipmap.img_head_6);
        } else if (is_chatreceive) {
            userNick = intent.getStringExtra(UserInfo.USER_NICK_CHATRECEIVE);
            userGender = intent.getStringExtra(UserInfo.USER_GENDER_CHATRECEIVE);
            userAge = intent.getIntExtra(UserInfo.USER_AGE_CHATRECEIVE, 18);
            userAvatar = intent.getIntExtra(UserInfo.USER_AVATAR_CHATRECEIVE, R.mipmap.img_head_6);
        } else if (is_sendfile) {
            userNick = intent.getStringExtra(UserInfo.USER_NICK_SENDFILE);
            userGender = intent.getStringExtra(UserInfo.USER_GENDER_SENDFILE);
            userAge = intent.getIntExtra(UserInfo.USER_AGE_SENDFILE, 18);
            userAvatar = intent.getIntExtra(UserInfo.USER_AVATAR_SENDFILE, R.mipmap.img_head_6);
        } else if (is_receivefile) {
            userNick = intent.getStringExtra(UserInfo.USER_NICK_RECEIVEFILE);
            userGender = intent.getStringExtra(UserInfo.USER_GENDER_RECEIVEFILE);
            userAge = intent.getIntExtra(UserInfo.USER_AGE_RECEIVEFILE, 18);
            userAvatar = intent.getIntExtra(UserInfo.USER_AVATAR_RECEIVEFILE, R.mipmap.img_head_6);
        } else if (is_sendimage) {
            userNick = intent.getStringExtra(UserInfo.USER_NICK_SENDIMAGE);
            userGender = intent.getStringExtra(UserInfo.USER_GENDER_SENDIMAGE);
            userAge = intent.getIntExtra(UserInfo.USER_AGE_SENDIMAGE, 18);
            userAvatar = intent.getIntExtra(UserInfo.USER_AVATAR_SENDIMAGE, R.mipmap.img_head_6);
        } else if (is_receiveimage) {
            userNick = intent.getStringExtra(UserInfo.USER_NICK_RECEIVEIMAGE);
            userGender = intent.getStringExtra(UserInfo.USER_GENDER_RECEIVEIMAGE);
            userAge = intent.getIntExtra(UserInfo.USER_AGE_RECEIVEIMAGE, 18);
            userAvatar = intent.getIntExtra(UserInfo.USER_AVATAR_RECEIVEIMAGE, R.mipmap.img_head_6);
        }
    }


}
