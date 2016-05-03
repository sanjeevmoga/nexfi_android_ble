package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.adapter.DebugAdapter;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;

import java.util.List;

/**
 * Created by gengbaolong on 2016/5/2.
 */
public class DebugActivity extends AppCompatActivity {

    private ListView list_debug;
    private List<Throwable> mExceLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        list_debug = (ListView) findViewById(R.id.list_debug);
        mExceLists = BleApplication.getExceptionLists();
        DebugAdapter adapter=new DebugAdapter(getApplicationContext(),mExceLists);
        list_debug.setAdapter(adapter);
    }
}
