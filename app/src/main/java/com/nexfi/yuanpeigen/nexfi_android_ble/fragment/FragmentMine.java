package com.nexfi.yuanpeigen.nexfi_android_ble.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.InputUserAgeActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.InputUsernameActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.SelectUserHeadIconActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

/**
 * Created by Mark on 2016/4/14.
 */
public class FragmentMine extends Fragment implements View.OnClickListener {

    private View view;
    private TextView tv_username, tv_userAge;
    private ImageView iv_userhead_icon;
    private RadioButton rb_female, rb_male;
    private RadioGroup radioGrop;
    private RelativeLayout layout_username, layout_userAge;

    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_GENDER = "userGender";
    private final String USER_NICK = "userNick";
    private final String USER_SEX_MALE = "男";
    private final String USER_SEX_FEMALE = "女";

    private String userNick, userGender;
    private int userAge, userAvatar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView(inflater, container);
        initData();
        setClickListener();
        setViewData();
        return view;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        tv_userAge = (TextView) view.findViewById(R.id.tv_userAge);
        iv_userhead_icon = (ImageView) view.findViewById(R.id.iv_userhead_icon);
        layout_userAge = (RelativeLayout) view.findViewById(R.id.layout_userAge);
        layout_username = (RelativeLayout) view.findViewById(R.id.layout_username);
        radioGrop = (RadioGroup) view.findViewById(R.id.radioGrop);
        rb_female = (RadioButton) view.findViewById(R.id.rb_female);
        rb_male = (RadioButton) view.findViewById(R.id.rb_male);
        radioSetOnCheckedListener();
    }

    private void setClickListener() {
        iv_userhead_icon.setOnClickListener(this);
        layout_username.setOnClickListener(this);
        layout_userAge.setOnClickListener(this);
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
        userAge = UserInfo.initUserAge(userAge, FragmentMine.this.getActivity());
        userAvatar = UserInfo.initUserAvatar(userAvatar, FragmentMine.this.getActivity());
        userGender = UserInfo.initUserGender(userGender, FragmentMine.this.getActivity());
        userNick = UserInfo.initUserNick(userNick, FragmentMine.this.getActivity());
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
        if (userGender!=null) {
            if (!userGender.equals(sex)) {
                Toast.makeText(FragmentMine.this.getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
            }
        }
        userGender = sex;
        UserInfo.saveUsersex(FragmentMine.this.getActivity(), userGender);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_userhead_icon:
                Intent intent1 = new Intent(FragmentMine.this.getActivity(), SelectUserHeadIconActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.layout_username:
                Intent intent2 = new Intent(FragmentMine.this.getActivity(), InputUsernameActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.layout_userAge:
                Intent intent3 = new Intent(FragmentMine.this.getActivity(), InputUserAgeActivity.class);
                startActivityForResult(intent3, 3);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            userAvatar = data.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
            iv_userhead_icon.setImageResource(userAvatar);
        } else if (resultCode == 2) {
            userNick = data.getStringExtra(USER_NICK);
            tv_username.setText(userNick);
        } else if (resultCode == 3) {
            userAge = data.getIntExtra(USER_AGE, 18);
            tv_userAge.setText(userAge + "");
        }
    }


}
