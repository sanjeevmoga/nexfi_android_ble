package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.fragment.FragmentMine;
import com.nexfi.yuanpeigen.nexfi_android_ble.fragment.FragmentNearby;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private FragmentManager mFragmentManager;
    private RadioGroup myTabRg;
    private RadioButton rb_nearby, rb_settings;
    private FragmentMine fragmentMine;
    private FragmentNearby fragmentNearby;
    private Handler mHandler;
    private boolean isExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initNearByFragment();
        rb_nearby.setChecked(true);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isExit = false;
            }
        };
    }

    private void initView() {
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        rb_nearby = (RadioButton) findViewById(R.id.rb_nearby);
        rb_settings = (RadioButton) findViewById(R.id.rb_mine);
        myTabRg.setOnCheckedChangeListener(this);
    }


    private void initNearByFragment() {
        mFragmentManager = getFragmentManager();
        fragmentNearby = new FragmentNearby();
        fragmentMine = new FragmentMine();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container, fragmentMine)
                .add(R.id.container, fragmentNearby)
                .hide(fragmentMine).commit();
    }

    private void initMineFragment() {
        mFragmentManager = getFragmentManager();
        fragmentNearby = new FragmentNearby();
        fragmentMine = new FragmentMine();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container, fragmentMine)
                .add(R.id.container, fragmentNearby)
                .hide(fragmentNearby).commit();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_nearby:
                mFragmentManager.beginTransaction()
                        .show(fragmentNearby).hide(fragmentMine).commit();
                break;
            case R.id.rb_mine:
                mFragmentManager.beginTransaction()
                        .show(fragmentMine).hide(fragmentNearby).commit();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                mHandler.sendEmptyMessageDelayed(0, 1500);
                Toast.makeText(this, "再按一次退出NexFi", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                finish();
            }
        }
        return true;
    }
}