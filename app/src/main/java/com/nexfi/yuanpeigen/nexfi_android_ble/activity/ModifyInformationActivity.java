package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

/**
 * Created by Mark on 2016/4/29.
 */
public class ModifyInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_username, tv_userAge;
    private ImageView iv_userhead_icon;
    private RadioButton rb_female, rb_male;
    private Button btn_finish;
    private RadioGroup radioGrop;
    private RelativeLayout layout_username, layout_userAge;

    private final String USER_SEX_MALE = "男";
    private final String USER_SEX_FEMALE = "女";
    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_NICK = "userNick";


    private String userSelfId;
    private String userNick, newUserNick, userGender, newUserGender;
    private int userAge, newUserAge, newUserAvater, userAvatar;
    BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);
        initData();
        initView();
        setClickListener();
        setViewData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initView() {
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_userAge = (TextView) findViewById(R.id.tv_userAge);
        iv_userhead_icon = (ImageView) findViewById(R.id.iv_userhead_icon);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        layout_userAge = (RelativeLayout) findViewById(R.id.layout_userAge);
        layout_username = (RelativeLayout) findViewById(R.id.layout_username);
        radioGrop = (RadioGroup) findViewById(R.id.radioGrop);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
        rb_male = (RadioButton) findViewById(R.id.rb_male);
    }

    private void setViewData() {
        tv_username.setText(userNick);
        if (userAge == 0) {
            tv_userAge.setText("未填写");
        } else {
            tv_userAge.setText(userAge + "");
        }
        if (userGender != null) {
            if (userGender.equals(USER_SEX_MALE)) {
                rb_male.setChecked(true);
            } else {
                rb_female.setChecked(true);
            }
        }
        iv_userhead_icon.setImageResource(userAvatar);

    }

    private void initData() {
        userSelfId = UserInfo.initUserId(userSelfId, BleApplication.getContext());
        userAge = UserInfo.initUserAge(userAge, BleApplication.getContext());
        userAvatar = UserInfo.initUserAvatar(userAvatar, BleApplication.getContext());
        userGender = UserInfo.initUserGender(userGender, BleApplication.getContext());
        userNick = UserInfo.initUserNick(userNick, BleApplication.getContext());
    }


    private void setClickListener() {
        iv_userhead_icon.setOnClickListener(this);
        layout_username.setOnClickListener(this);
        layout_userAge.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        radioSetOnCheckedListener();
    }

    private void radioSetOnCheckedListener() {
        radioGrop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_male:
                        modify_userGender(USER_SEX_MALE);
                        break;
                    case R.id.rb_female:
                        modify_userGender(USER_SEX_FEMALE);
                        break;
                }
            }
        });
    }

    private void modify_userGender(String sex) {
        if (userGender != null) {
            if (!userGender.equals(sex)) {
                Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
            }
        }
        newUserGender = sex;
        UserInfo.saveUsersex(this, userGender);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                finish();
                break;
            case R.id.iv_userhead_icon:
                Intent intent1 = new Intent(this, SelectUserHeadIconActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.layout_username:
                Intent intent2 = new Intent(this, InputUsernameActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.layout_userAge:
                Intent intent3 = new Intent(this, InputUserAgeActivity.class);
                startActivityForResult(intent3, 3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            newUserAvater = data.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
            iv_userhead_icon.setImageResource(newUserAvater);
        } else if (resultCode == 2) {
            newUserNick = data.getStringExtra(USER_NICK);
            tv_username.setText(newUserNick);
        } else if (resultCode == 3) {
            newUserAge = data.getIntExtra(USER_AGE, 18);
            tv_userAge.setText(newUserAge + "");
        }
        UserMessage user=bleDBDao.findUserByUserId(userSelfId);
        if (user.userAvatar != newUserAvater || !user.userNick.equals(newUserNick) || !user.userGender.equals(newUserGender) || user.userAge != newUserAge) {
            btn_finish.setText("完成");
            user.userAvatar=newUserAvater;
            user.userNick=newUserNick;
            user.userGender=newUserGender;
            user.userAge=newUserAge;
            bleDBDao.updateUserInfoByUserId(user, userSelfId);
        }

    }
}
