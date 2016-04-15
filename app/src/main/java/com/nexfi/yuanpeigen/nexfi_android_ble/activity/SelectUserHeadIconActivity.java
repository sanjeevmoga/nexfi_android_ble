package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.adapter.SelectUserHeadIconGridViewAdapter;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

/**
 * Created by Mark on 2016/4/14.
 */
public class SelectUserHeadIconActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_save;
    private RelativeLayout layout_back;
    private GridView gridView;

    private final String USER_AVATAR = "userAvatar";
    private final String ISSELECT = "isSelectUserHeadIconActivity";

    private int userAvatar;

    private SelectUserHeadIconGridViewAdapter mGridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_userheadicon);

        initView();
    }

    private void initView() {
        tv_save = (TextView) findViewById(R.id.tv_save);
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        gridView = (GridView) findViewById(R.id.gridView);
        layout_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        mGridViewAdapter = new SelectUserHeadIconGridViewAdapter(this);
        gridView.setAdapter(mGridViewAdapter);
        gridViewSetOnclickLisener();
        userAvatar = UserInfo.initUserAvatar(userAvatar, this);
    }

    private void gridViewSetOnclickLisener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != UserInfo.userHeadIcon.length - 1) {
                    userAvatar = UserInfo.userHeadIcon[position];
                } else {
                    Toast.makeText(SelectUserHeadIconActivity.this, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra(ISSELECT, true);
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_save:
                if (userAvatar != R.mipmap.img_default) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(USER_AVATAR, userAvatar);
                    setResult(1, intent);
                    finish();
                    UserInfo.saveUserHeadIcon(this, userAvatar);
                    Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "您还未选择头像哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
