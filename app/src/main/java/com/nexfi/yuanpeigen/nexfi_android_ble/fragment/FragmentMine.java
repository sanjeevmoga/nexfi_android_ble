package com.nexfi.yuanpeigen.nexfi_android_ble.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.DebugActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.InputUserAgeActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.InputUsernameActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.SelectUserHeadIconActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.model.Node;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.Debug;
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
    private LinearLayout linearlayout_show;

    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_GENDER = "userGender";
    private final String USER_NICK = "userNick";
    private final String USER_SEX_MALE = "男";
    private final String USER_SEX_FEMALE = "女";

    private String userSelfId;
    private String userNick, newUserNick, userGender, newUserGender;
    private int userAge, newUserAge, newUserAvater, userAvatar;
    BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());
    private Node node;
    private Button bt_modify;
    private boolean isModified = false;
    private UserMessage user;
    private Button bt_debug;

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
        bt_modify = (Button) view.findViewById(R.id.bt_modify);
        linearlayout_show = (LinearLayout) view.findViewById(R.id.linearlayout_show);
        bt_debug = (Button) view.findViewById(R.id.bt_debug);
    }

    private void setClickListener() {
        iv_userhead_icon.setOnClickListener(this);
        layout_username.setOnClickListener(this);
        layout_userAge.setOnClickListener(this);
        if(Debug.DEBUG){
            bt_debug.setVisibility(View.VISIBLE);
            bt_debug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击之后跳转到日志界面----ListView
                    Intent intent=new Intent(getActivity(), DebugActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }

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
        user = bleDBDao.findUserByUserId(userSelfId);//geng
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
                Toast.makeText(FragmentMine.this.getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
                bt_modify.setVisibility(View.VISIBLE);
                isModified = true;
            }
        }
        userGender = sex;
        UserInfo.saveUsersex(FragmentMine.this.getActivity(), userGender);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_userhead_icon:
                Intent intent1 = new Intent(this.getActivity(), SelectUserHeadIconActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.layout_username:
                Intent intent2 = new Intent(this.getActivity(), InputUsernameActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.layout_userAge:
                Intent intent3 = new Intent(this.getActivity(), InputUserAgeActivity.class);
                startActivityForResult(intent3, 3);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            userAvatar = data.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
            if (user.userAvatar == userAvatar) {
                //没有修改
            } else {
                //修改
                linearlayout_show.setVisibility(View.VISIBLE);
                isModified = true;
            }
            iv_userhead_icon.setImageResource(userAvatar);
        } else if (resultCode == 2) {
            userNick = data.getStringExtra(USER_NICK);
            if (user.userNick.equals(userNick)) {
                //没有修改
            } else {
                //修改
                linearlayout_show.setVisibility(View.VISIBLE);
                isModified = true;
            }
            tv_username.setText(userNick);
        } else if (resultCode == 3) {
            userAge = data.getIntExtra(USER_AGE, 18);
            if (user.userAge == userAge) {
                //没有修改
            } else {
                //修改
                linearlayout_show.setVisibility(View.VISIBLE);
                isModified = true;
            }
            tv_userAge.setText(userAge + "");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Debug.DEBUG) {
            Log.e("TAG", user.nodeId + "---fragmentMine--------" + isModified);
        }
        if (isModified) {
            bt_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //隐藏按钮
                    linearlayout_show.setVisibility(View.INVISIBLE);
                    //点击之后发送通知
                    user.userNick = userNick;
                    user.userAge = userAge;
                    user.userGender = userGender;
                    user.userAvatar = userAvatar;
                    //将修改后的数据更新到数据库中
//                    bleDBDao.updateUserInfoByUserId(user, userSelfId);
//                    //
//                    Log.e("TAG", user.nodeId + "------------------fragmentMine-----------修改后的-nodeId--------");
//                    bleDBDao.updateP2PMsgByUserId(user,userSelfId);
//                    //发送改变通知
//                    BaseMessage baseMessage = new BaseMessage();
//                    baseMessage.messageType = MessageType.MODIFY_USER_INFO;
//                    baseMessage.userMessage = user;
//                    byte[] notify_msg_bys = ObjectBytesUtils.ObjectToByte(baseMessage);
//                    node.broadcastFrame(notify_msg_bys);

                }
            });
        }
    }
}
