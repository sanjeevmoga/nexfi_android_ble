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

/**
 * Created by Mark on 2016/4/14.
 */
public class UserInformationActivity extends AppCompatActivity {

    private final String USER_SEX = "男";
    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_GENDER = "userGender";
    private final String USER_NICK = "userNick";

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
                Intent intent = new Intent(UserInformationActivity.this, MainActivity.class);
                startActivity(intent);
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
    }


    private void initIntentData() {
        Intent intent = getIntent();
        userNick = intent.getStringExtra(USER_NICK);
        userGender = intent.getStringExtra(USER_GENDER);
        userAge = intent.getIntExtra(USER_AGE, 18);
        userAvatar = intent.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
    }


}
