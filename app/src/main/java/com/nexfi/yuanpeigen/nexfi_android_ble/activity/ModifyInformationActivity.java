package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.model.Node;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.ObjectBytesUtils;
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
    private Node node;
    private UserMessage user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_information);
        node = MainActivity.getNode();
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
        Log.e("TAG","userAvatar: "+userAvatar+"userNick: "+userNick+"----------------------------------------"+userSelfId);
//        user = bleDBDao.findUserByUserId(userSelfId);
//        Log.e("TAG", user.userNick+"-----------------------"+ user.userId);
    }

    //2130903069--------userMessage.userAvatar-------add------
    //华为-保存到数据库---2130903069====0f4c60cc-0d0a-40c8-85a9-2bfddc44ee25
    //Nexus-保存到数据库---2130903066====dca467f4-9289-4945-a377-d1ff20b0406a
    //userAvatar: 2130903069userNick: 华为----------------------------------------0f4c60cc-0d0a-40c8-85a9-2bfddc44ee25
    //华为-----------------------0f4c60cc-0d0a-40c8-85a9-2bfddc44ee25
    //null-保存到数据库---0====5b317787-f69f-4842-b9ed-780414aa74ad
    //------bleDBDao----------0



    //2130903066--------userMessage.userAvatar-------add------
    //Nexus-保存到数据库---2130903066====dca467f4-9289-4945-a377-d1ff20b0406a


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
        userGender = sex;
        UserInfo.saveUsersex(this, userGender);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
//                UserMessage user=bleDBDao.findUserByUserId(userSelfId);
                user = bleDBDao.findUserByUserId(userSelfId);
                Log.e("TAG", user.userNick+"------bleDBDao----------" + user.userGender+"-----"+newUserGender);
                Log.e("TAG", newUserAvater+"------newUserAvater----------" +newUserNick+"----"+newUserGender+"----"+newUserAge);
                Log.e("TAG", userAvatar+"------userAvatar----------" +userNick+"----"+userNick+"----"+userAge);



                if(userNick!=null && userAge!=0 && userAvatar!=0){
                    if(user.userNick.equals(userNick) && user.userAvatar==userAvatar && user.userAge==userAge && user.userGender.equals(userGender)){
                        //不发消息
                        Log.e("TAG", userAvatar+"------不发修改消息----------" +userNick+"----"+userGender+"----"+userAge);
                    }else{
                        //发消息
                        Log.e("TAG", userAvatar+"------发修改消息----------" +userNick+"----"+userGender+"----"+userAge);
                        user.userNick=userNick;
                        user.userAge=userAge;
                        user.userGender=userGender;
                        user.userAvatar=userAvatar;
                        //将修改后的数据更新到数据库中
                        bleDBDao.updateUserInfoByUserId(user,userSelfId);
                        //发送改变通知
                        BaseMessage baseMessage = new BaseMessage();
                        baseMessage.messageType = MessageType.MODIFY_USER_INFO;
                        baseMessage.userMessage = user;
                        byte[] notify_msg_bys = ObjectBytesUtils.ObjectToByte(baseMessage);
                        node.broadcastFrame(notify_msg_bys);
                    }

                }

//                if((newUserAvater==0 && newUserNick==null && newUserAge==0 && user.userGender.equals(newUserGender))){
//                    //不发消息
//                }else{
//                    if((user.userAvatar==newUserAvater && user.userNick.equals(newUserNick) && user.userAge==newUserAge && user.userGender.equals(newUserGender))){
//                        //不发消息
//                    }else{
//
//                    }
//                }



//                if((newUserAvater==0 && newUserNick==null && newUserAge==0 && user.userGender.equals(newUserGender)) ||
//                        (user.userAvatar==newUserAvater && user.userNick.equals(newUserNick) && user.userAge==newUserAge && user.userGender.equals(newUserGender))) {
////                    newUserAvater=userAvatar;
////                    newUserNick=userNick;
////                    newUserAge=userAge;
////                    newUserGender=userGender;
//                    Log.e("TAG", newUserAvater+"------不发修改消息----------" +newUserNick+"----"+newUserGender+"----"+newUserAge);
//                }
//                else{
//                    if(newUserAvater!=0){
//                        user.userAvatar=newUserAvater;
//                    }
//                    if(newUserNick!=null){
//                        user.userNick=newUserNick;
//                    }
//                    if(newUserAge!=0){
//                        user.userAge=newUserAge;
//                    }
//                    user.userGender=newUserGender;
//                    Log.e("TAG", user.userNick + "------发送修改消息----------" + newUserNick);
//                }
//                else if(newUserAvater!=0 && newUserNick!=null && newUserGender!=null && newUserAge!=0) {
//                    if (userAvatar != newUserAvater || !userNick.equals(newUserNick) || !userGender.equals(newUserGender) || userAge != newUserAge) {
//                    btn_finish.setText("完成");
//                        if (userAvatar != newUserAvater) {
//                            user.userAvatar = newUserAvater;
//                        }
//                        if(!userNick.equals(newUserNick)){
//                            user.userNick=newUserNick;
//                        }
//                        if(!userGender.equals(newUserGender)){
//                            user.userGender=newUserGender;
//                        }
//                        if(userAge != newUserAge){
//                            user.userAge = newUserAge;
//                        }
//                        Log.e("TAG", user.userAvatar + "------modify----------" + newUserAvater);
//                        user.userNick=newUserNick;
//                        user.userAvatar = newUserAvater;
//                        user.userNick = newUserNick;
//                        user.userGender = newUserGender;
//
//
//                        BaseMessage baseMessage = new BaseMessage();
//                        baseMessage.messageType = MessageType.MODIFY_USER_INFO;
//                        baseMessage.userMessage = user;
//                        byte[] notify_msg_bys = ObjectBytesUtils.ObjectToByte(baseMessage);
//                        node.broadcastFrame(notify_msg_bys);
//                        Log.e("TAG", user.userNick + "------发送修改消息----------" + newUserNick);
//                        bleDBDao.updateUserInfoByUserId(user, userSelfId);
//                    }
//                }
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
            userAvatar = data.getIntExtra(USER_AVATAR, userAvatar);
            iv_userhead_icon.setImageResource(userAvatar);
        } else if (resultCode == 2) {
            userNick = data.getStringExtra(USER_NICK);
            tv_username.setText(userNick);
            Log.e("TAG","------newUserNick----------" + newUserNick);
        } else if (resultCode == 3) {
            userAge = data.getIntExtra(USER_AGE, userAge);
            tv_userAge.setText(userAge + "");
        }
//        UserMessage user=bleDBDao.findUserByUserId(userSelfId);
//        if (user.userAvatar != newUserAvater || !user.userNick.equals(newUserNick) || !user.userGender.equals(newUserGender) || user.userAge != newUserAge) {
//            btn_finish.setText("完成");
//            user.userAvatar=newUserAvater;
//            user.userNick=newUserNick;
//            user.userGender=newUserGender;
//            user.userAge=newUserAge;
//            BaseMessage baseMessage=new BaseMessage();
//            baseMessage.messageType= MessageType.MODIFY_USER_INFO;
//            baseMessage.userMessage=user;
//            byte[] notify_msg_bys= ObjectBytesUtils.ObjectToByte(baseMessage);
//            node.broadcastFrame(notify_msg_bys);
//            bleDBDao.updateUserInfoByUserId(user, userSelfId);
//        }

    }
}
