package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;

/**
 * Created by Mark on 2016/4/13.
 */
public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout layout_backGroup;
    private ImageView iv_addGroup, iv_photo, iv_position, iv_pic;
    private EditText et_chatGroup;
    private Button btn_sendMsgGroup;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        layout_backGroup = (RelativeLayout) findViewById(R.id.layout_backGroup);
        iv_addGroup = (ImageView) findViewById(R.id.iv_addGroup);
        et_chatGroup = (EditText) findViewById(R.id.et_chatGroup);
        btn_sendMsgGroup = (Button) findViewById(R.id.btn_sendMsgGroup);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        iv_position = (ImageView) findViewById(R.id.iv_position);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_backGroup:
                break;
            case R.id.iv_addGroup:
                break;
            case R.id.et_chatGroup:
                break;
            case R.id.btn_sendMsgGroup:
                break;
            case R.id.iv_pic:
                break;
            case R.id.iv_photo:
                break;
            case R.id.iv_position:
                break;
        }
    }
}
