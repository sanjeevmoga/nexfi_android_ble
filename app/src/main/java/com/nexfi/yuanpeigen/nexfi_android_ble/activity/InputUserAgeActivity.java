package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

/**
 * Created by Mark on 2016/4/15.
 */
public class InputUserAgeActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout layout_back;
    private TextView tv_save;
    private EditText et_inputUserAge;

    private int userAge;

    private final String USER_AGE = "userAge";
    private final String ISINPUTUSERAGE = "isInputUserAgeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_age);

        initView();
        setClickListener();

    }

    private void setClickListener() {
        tv_save.setOnClickListener(this);
        layout_back.setOnClickListener(this);
    }

    private void initView() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        tv_save = (TextView) findViewById(R.id.tv_save);
        et_inputUserAge = (EditText) findViewById(R.id.et_inputUserAge);
        userAge = UserInfo.initUserAge(userAge, this);
        if (userAge!=0){
            et_inputUserAge.setText(userAge + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra(ISINPUTUSERAGE, true);
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_save:
                if (!TextUtils.isEmpty(et_inputUserAge.getText())) {
                    userAge = Integer.parseInt(et_inputUserAge.getText().toString());
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra(USER_AGE, userAge);
                    setResult(3, intent);
                    finish();
                    UserInfo.saveUserAge(this, userAge);
                } else {
                    Toast.makeText(this, "您还未输入年龄哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
