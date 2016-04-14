package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.app.FragmentManager;
<<<<<<< HEAD
=======
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
>>>>>>> 89950ace7134c73f3097e243c1dc427532ffcc8f
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.fragment.FragmentMine;
import com.nexfi.yuanpeigen.nexfi_android_ble.fragment.FragmentNearby;
import com.nexfi.yuanpeigen.nexfi_android_ble.model.Node;


public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private FragmentManager mFragmentManager;
    private RadioGroup myTabRg;
    private RadioButton rb_nearby, rb_mine;
    private FragmentMine fragmentMine;
    private FragmentNearby fragmentNearby;
    private Handler mHandler;
    private boolean isExit, isFirstIn = false;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    private Node node;
    private int REQUEST_ENABLE = 1;
    Button button;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConfigurationInformation();
        node = new Node(this);
<<<<<<< HEAD
        button= (Button) findViewById(R.id.button_send);
        button.setOnClickListener(this);
        textView= (TextView) findViewById(R.id.tv_text);
//        initView();
//        initNearByFragment();
//        rb_nearby.setChecked(true);
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                isExit = false;
//            }
//        };
=======
//        getBle();
        initBle();
//        initBle();
        initView();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                isExit = false;
            }
        };
    }

    BluetoothReceiver receiver;


    private void getBle() {

    }

    private void initBle() {
        BluetoothAdapter
                mAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mAdapter.isEnabled()) {

            //弹出对话框提示用户是后打开
            Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enabler, REQUEST_ENABLE);

        }

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        for (int i = 0; i < devices.size(); i++) {
            BluetoothDevice device = (BluetoothDevice) devices.iterator().next();
            System.out.println(device.getName());
            if (Debug.DEBUG) {
                Log.e("TAG", "open bluetooth----------------------------------------" + device.getName());
            }
        }


        if (Debug.DEBUG) {
            Log.e("TAG", "open bluetooth----------------------------------------");
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        receiver = new BluetoothReceiver();
        registerReceiver(receiver, filter);
        if (Debug.DEBUG) {
            Log.e("TAG", "send broadcast------------------------------------------------------");
        }
        mAdapter.startDiscovery();
        if (Debug.DEBUG) {
            Log.e("TAG", "start discovery------------------------------------------------------");
        }

>>>>>>> 89950ace7134c73f3097e243c1dc427532ffcc8f
    }

//    private void initView() {
//        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
//        rb_nearby = (RadioButton) findViewById(R.id.rb_nearby);
//        rb_settings = (RadioButton) findViewById(R.id.rb_mine);
//        myTabRg.setOnCheckedChangeListener(this);
//    }
//
//
//    private void initNearByFragment() {
//        mFragmentManager = getFragmentManager();
//        fragmentNearby = new FragmentNearby();
//        fragmentMine = new FragmentMine();
//        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.add(R.id.container, fragmentMine)
//                .add(R.id.container, fragmentNearby)
//                .hide(fragmentMine).commit();
//    }
<<<<<<< HEAD
//
//    private void initMineFragment() {
//        mFragmentManager = getFragmentManager();
//        fragmentNearby = new FragmentNearby();
//        fragmentMine = new FragmentMine();
//        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
//        mFragmentTransaction.add(R.id.container, fragmentMine)
//                .add(R.id.container, fragmentNearby)
//                .hide(fragmentNearby).commit();
//    }
//
//
=======

    private void initView() {
        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        rb_nearby = (RadioButton) findViewById(R.id.rb_nearby);
        rb_mine = (RadioButton) findViewById(R.id.rb_mine);
        myTabRg.setOnCheckedChangeListener(this);
        if (!isFirstIn) {
            initNearByFragment();
            rb_nearby.setChecked(true);
        } else {
            initMineFragment();
            rb_mine.setChecked(true);
        }

    }

    private void initConfigurationInformation() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
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


>>>>>>> 89950ace7134c73f3097e243c1dc427532ffcc8f
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId) {
//            case R.id.rb_nearby:
//                mFragmentManager.beginTransaction()
//                        .show(fragmentNearby).hide(fragmentMine).commit();
//                break;
//            case R.id.rb_mine:
//                mFragmentManager.beginTransaction()
//                        .show(fragmentMine).hide(fragmentNearby).commit();
//                break;
//        }
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


    @Override
    protected void onStart() {
        super.onStart();
        node.start();
//        sendFrames();
    }

    @Override
    protected void onStop() {
        super.onStop();

//        if(node != null)
////            node.stop();
    }

    //得到的连接个数
    public void refreshPeers() {
        Log.e("TAG", node.getLinks().size() + " connected------------------------------------------------------------------------------------------");
//        for (int i = 0; i <node.getLinks().size(); i++) {
//            Log.e("TAG",node.getLinks().size() + " connected------------------------"+node.getLinks().get(i).getNodeId());//1 connected------------------------229689687511020547
//            //拿到连接个数
//            //怎么获得具体用户信息呢？？给每个用户发送请求，然后对方把自己的信息发过来
//        sendFrames();
//        }
    }

    //接收到的数据
    public void refreshFrames(byte[] fromData) {
        Log.e("TAG", new String(fromData) + " -----refreshFrames------------------------------------");
        textView.setText(new String(fromData));
    }


    //发送数据
    public void sendFrames() {
        //发送请求
        byte[] frameData = "request_user_info".getBytes();//发送获取用户数据的请求

        node.broadcastFrame(frameData);

//        for(int i = 0; i < 100; ++i)
//        {
//            byte[] frameData = new byte[1024];
//            new Random().nextBytes(frameData);
//
//            node.broadcastFrame(frameData);
//        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //点击的时候发送请求
        sendFrames();
    }
}
