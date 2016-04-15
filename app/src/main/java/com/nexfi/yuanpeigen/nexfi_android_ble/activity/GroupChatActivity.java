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
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;

/**
 * Created by Mark on 2016/4/13.
 */
public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout layout_backGroup;
    private ImageView iv_addGroup, iv_camera, iv_position, iv_pic, iv_showUserInfo;
    private EditText et_chatGroup;
    private Button btn_sendMsgGroup;
    private boolean visibility_Flag = false;
    private LinearLayout layout_view;
    private ListView lv_chatGroup;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        initView();
        setClicklistener();

    }

    private void setClicklistener() {
        layout_backGroup.setOnClickListener(this);
        iv_addGroup.setOnClickListener(this);
        btn_sendMsgGroup.setOnClickListener(this);
        iv_pic.setOnClickListener(this);
        iv_position.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        iv_showUserInfo.setOnClickListener(this);
    }

    private void initView() {
        layout_backGroup = (RelativeLayout) findViewById(R.id.layout_backGroup);
        iv_addGroup = (ImageView) findViewById(R.id.iv_addGroup);
        et_chatGroup = (EditText) findViewById(R.id.et_chatGroup);
        btn_sendMsgGroup = (Button) findViewById(R.id.btn_sendMsgGroup);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        iv_position = (ImageView) findViewById(R.id.iv_position);
        layout_view = (LinearLayout) findViewById(R.id.layout_viewGroup);
        lv_chatGroup = (ListView) findViewById(R.id.lv_chatGroup);
        iv_showUserInfo = (ImageView) findViewById(R.id.iv_showUserInfo);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_backGroup:
                Intent intent = new Intent(GroupChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_addGroup:
                if (visibility_Flag) {
                    layout_view.setVisibility(View.GONE);
                    visibility_Flag = false;
                } else {
                    layout_view.setVisibility(View.VISIBLE);
                    visibility_Flag = true;
                }
                break;
            case R.id.btn_sendMsgGroup:
                showToast();
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
            case R.id.iv_showUserInfo:
                showToast();
                break;
        }

    }

    private void showToast() {
        Toast.makeText(this, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
    }
}
