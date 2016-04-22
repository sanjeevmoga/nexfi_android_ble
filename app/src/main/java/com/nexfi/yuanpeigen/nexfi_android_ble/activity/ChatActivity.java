package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.adapter.ChatMessageAdapater;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.FileMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.listener.ReceiveTextMsgListener;
import com.nexfi.yuanpeigen.nexfi_android_ble.model.Node;
import com.nexfi.yuanpeigen.nexfi_android_ble.operation.TextMsgOperation;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.Debug;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.FileTransferUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.FileUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.ObjectBytesUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.TUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.TimeUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.UserInfo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import io.underdark.transport.Link;

/**
 * Created by Mark on 2016/4/14.
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener, ReceiveTextMsgListener, Runnable {

    private RelativeLayout layout_backPrivate;
    private ImageView iv_addPrivate, iv_camera, iv_position, iv_pic;
    private EditText et_chatPrivate;
    private Button btn_sendMsgPrivate;
    private LinearLayout layout_view;
    private ListView lv_chatPrivate;
    private TextView textView_private;

    private boolean visibility_Flag = false;

    private final String USER_SEX = "男";
    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_GENDER = "userGender";
    private final String USER_NICK = "userNick";
    private final String USER_NODE_ID = "nodeId";
    private final String USER_ID = "userId";

    private String userNick, userGender;
    private int userAge, userAvatar;

    private long nodeId;
    private Node node;
    private Link link;
    private String userId;
    private String userSelfId;//用户自身
    TextMsgOperation textMsgOperation;
    BleDBDao bleDBDao = new BleDBDao(BleApplication.getContext());
    public static final int REQUEST_CODE_LOCAL_IMAGE = 1;//图片
    public static final int REQUEST_CODE_SELECT_FILE = 2;//文件
    public static final int SELECT_A_PICTURE = 3;//4.4以下
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 4;//4.4以上

    private ChatMessageAdapater chatMessageAdapater;
    private List<BaseMessage> mDataArrays = new ArrayList<BaseMessage>();

    private AlertDialog mAlertDialog;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        node = MainActivity.getNode();
        textMsgOperation = new TextMsgOperation();
        userSelfId = UserInfo.initUserId(userSelfId, BleApplication.getContext());

        initIntentData();
        initView();
        initAdapter();
        setClicklistener();

    }

    private void initDialogConnectedStatus() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.dialog_connected, null);
        mAlertDialog = new AlertDialog.Builder(this).create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(v);
        mAlertDialog.setCancelable(false);
        handler = new Handler();
        handler.postDelayed(this, 1500);
    }


    private void initAdapter() {
        mDataArrays = bleDBDao.findMsgByChatId(userId);
        chatMessageAdapater = new ChatMessageAdapater(ChatActivity.this, mDataArrays);
        lv_chatPrivate.setAdapter(chatMessageAdapater);
    }

    private void setClicklistener() {
        layout_backPrivate.setOnClickListener(this);
        iv_addPrivate.setOnClickListener(this);
        btn_sendMsgPrivate.setOnClickListener(this);
        iv_pic.setOnClickListener(this);
        iv_position.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        node.setReceiveTextMsgListener(this);
    }

    private void initView() {
        layout_backPrivate = (RelativeLayout) findViewById(R.id.layout_backPrivate);
        iv_addPrivate = (ImageView) findViewById(R.id.iv_addPrivate);
        et_chatPrivate = (EditText) findViewById(R.id.et_chatPrivate);
        btn_sendMsgPrivate = (Button) findViewById(R.id.btn_sendMsgPrivate);
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        iv_position = (ImageView) findViewById(R.id.iv_position);
        layout_view = (LinearLayout) findViewById(R.id.layout_viewPrivate);
        lv_chatPrivate = (ListView) findViewById(R.id.lv_chatPrivate);
        textView_private = (TextView) findViewById(R.id.textView_private);
        textView_private.setText(userNick);

    }

    private void initIntentData() {
        Intent intent = getIntent();
        userNick = intent.getStringExtra(USER_NICK);
        userGender = intent.getStringExtra(USER_GENDER);
        userAge = intent.getIntExtra(USER_AGE, 18);
        userAvatar = intent.getIntExtra(USER_AVATAR, R.mipmap.img_head_6);
        nodeId = intent.getLongExtra(USER_NODE_ID, 1234568L);
        userId = intent.getStringExtra(USER_ID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_backPrivate:
                finish();
                break;
            case R.id.iv_addPrivate:
                if (visibility_Flag) {
                    layout_view.setVisibility(View.GONE);
                    visibility_Flag = false;
                } else {
                    layout_view.setVisibility(View.VISIBLE);
                    visibility_Flag = true;
                }
                break;
            case R.id.btn_sendMsgPrivate:
                link = node.getLink(nodeId);
                if (link != null) {
                    sendTextMsg();
                    et_chatPrivate.setText(null);
                } else {
                    initDialogConnectedStatus();
                }
                break;
            case R.id.iv_pic:
                FileTransferUtils.selectPicFromLocal(ChatActivity.this);
                break;
            case R.id.iv_camera://改成发文件了
                FileTransferUtils.selectFileFromLocal(ChatActivity.this);//选择本地文件
                break;
            case R.id.iv_position:
                showToast();
                break;
        }
    }


    private void showToast() {
        Toast.makeText(this, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送文本消息
     */
    private void sendTextMsg() {
        String contString = et_chatPrivate.getText().toString();
        if (contString.length() > 0) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = MessageType.SEND_TEXT_ONLY_MESSAGE_TYPE;
            baseMessage.sendTime = TimeUtils.getNowTime();
            baseMessage.chat_id = userId;
            UserMessage user = bleDBDao.findUserByUserId(userSelfId);
            TextMessage textMessage = new TextMessage();
            textMessage.textMessageContent = contString;
            textMessage.nodeId = user.nodeId;
            textMessage.userId = user.userId;
            textMessage.userNick = user.userNick;
            textMessage.userGender = user.userGender;
            textMessage.userAvatar = user.userAvatar;
            textMessage.userAge = user.userAge;
            baseMessage.userMessage = textMessage;
            byte[] send_text_data = ObjectBytesUtils.ObjectToByte(baseMessage);
            if (Debug.DEBUG) {
                Log.e("TAG", link + "---ChatActivity-----" + nodeId);
            }
            if (null != link) {
                if (Debug.DEBUG) {
                    Log.e("TAG", "---ChatActivity-------------sendMsg------------link------");
                }
                link.sendFrame(send_text_data);
                setAdapter(baseMessage);
            }
        }
    }


    /**
     * 根据图片路径发送图片
     *
     * @param filePath
     */
    private void sendImageMsg(String filePath) {
        File fileToSend = FileTransferUtils.scal(filePath);
        byte[] bys = null;
        try {
            bys = FileTransferUtils.getBytesFromFile(fileToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == bys) {
            return;
        }
        String tFileSize = Base64.encodeToString(bys, Base64.DEFAULT);
        String fileName = fileToSend.getName();//文件名
        link = node.getLink(nodeId);
        if (link != null) {
            BaseMessage baseMessage = new BaseMessage();
            baseMessage.messageType = MessageType.SINGLE_SEND_IMAGE_MESSAGE_TYPE;
            baseMessage.sendTime = TimeUtils.getNowTime();
            baseMessage.chat_id = userId;
            UserMessage user = bleDBDao.findUserByUserId(userSelfId);
            FileMessage fileMessage = new FileMessage();
            fileMessage.fileSize = tFileSize;
            fileMessage.fileName = fileName;
            fileMessage.filePath = filePath;
            fileMessage.nodeId = user.nodeId;
            fileMessage.userId = user.userId;
            fileMessage.userNick = user.userNick;
            fileMessage.userGender = user.userGender;
            fileMessage.userAvatar = user.userAvatar;
            fileMessage.userAge = user.userAge;
            baseMessage.userMessage = fileMessage;
            byte[] send_file_data = ObjectBytesUtils.ObjectToByte(baseMessage);
            link.sendFrame(send_file_data);
            setAdapter(baseMessage);
        } else {
            initDialogConnectedStatus();
        }
    }


    /**
     * 根据文件路径发送文件
     *
     * @param selectFilePath
     */
    private void sendFileMsg(String selectFilePath) {
        File fileToSend = FileTransferUtils.scal(selectFilePath);
        byte[] bys_send_file = null;
        byte[] tsize = ("" + fileToSend.length()).getBytes();

        for (int i = 0; i < tsize.length; i++) {
            bys_send_file[i] = tsize[i];
        }
        bys_send_file[tsize.length] = 0;
        if (null == bys_send_file) {
            return;
        }
        String tFileSize = Base64.encodeToString(bys_send_file, Base64.DEFAULT);//文件的大小
        byte[] bys = null;
        try {
            bys = FileTransferUtils.getBytesFromFile(fileToSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String tFileData = Base64.encodeToString(bys, Base64.DEFAULT);//文件数据
        String fileName = fileToSend.getName();//文件名
        link = node.getLink(nodeId);
        byte[] bys_send = new byte[1024];
        int readsize = 0;
        FileInputStream fis=null;
        try {
            fis = new FileInputStream(fileToSend);
        }catch (Exception e){
            e.printStackTrace();
        }
        BaseMessage baseMessage=null;
        if (link != null) {
            try {
                while ((readsize = fis.read(bys_send, 0, bys_send.length)) > 0) {
                    String tFileData_fen_duan = Base64.encodeToString(bys_send, 0, readsize, Base64.DEFAULT);//文件数据
                    baseMessage = new BaseMessage();
                    baseMessage.messageType = MessageType.SINGLE_SEND_FOLDER_MESSAGE_TYPE;
                    baseMessage.sendTime = TimeUtils.getNowTime();
                    baseMessage.chat_id = userId;
                    UserMessage user = bleDBDao.findUserByUserId(userSelfId);
                    FileMessage fileMessage = new FileMessage();
                    fileMessage.fileData = tFileData_fen_duan;
                    fileMessage.fileSize = tFileSize;
                    fileMessage.fileName = fileName;
                    fileMessage.filePath = selectFilePath;
                    fileMessage.nodeId = user.nodeId;
                    fileMessage.userId = user.userId;
                    fileMessage.userNick = user.userNick;
                    fileMessage.userGender = user.userGender;
                    fileMessage.userAvatar = user.userAvatar;
                    fileMessage.userAge = user.userAge;
                    baseMessage.userMessage = fileMessage;
                    byte[] send_file_data = ObjectBytesUtils.ObjectToByte(baseMessage);
                    link.sendFrame(send_file_data);
                    Log.e("TAG","----发送文件-------"+readsize);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
//            setAdapter(baseMessage);
        } else {
            initDialogConnectedStatus();
        }
    }


    @Override
    public void onReceiveTextMsg(Object obj) {
        Log.e("TAG", obj + "----===回调------------------------------9999");
        BaseMessage baseMesage = (BaseMessage) obj;
        if (baseMesage.messageType == MessageType.RECEIVE_TEXT_ONLY_MESSAGE_TYPE) {
            TextMessage textMessage = (TextMessage) baseMesage.userMessage;
            baseMesage.chat_id = textMessage.userId;
        } else if (baseMesage.messageType == MessageType.SINGLE_RECV_IMAGE_MESSAGE_TYPE) {
            FileMessage fileMessage = (FileMessage) baseMesage.userMessage;
            baseMesage.chat_id = fileMessage.userId;
        } else if (baseMesage.messageType == MessageType.SINGLE_RECV_FOLDER_MESSAGE_TYPE) {
            FileMessage fileMessage = (FileMessage) baseMesage.userMessage;
            baseMesage.chat_id = fileMessage.userId;
        }
        setAdapter(baseMesage);//设置适配器
    }

    private void setAdapter(BaseMessage baseMesage) {
        mDataArrays.add(baseMesage);
        if (null == chatMessageAdapater) {
            chatMessageAdapater = new ChatMessageAdapater(ChatActivity.this, mDataArrays);
            lv_chatPrivate.setAdapter(chatMessageAdapater);
        }
        chatMessageAdapater.notifyDataSetChanged();
        if (mDataArrays.size() > 0) {
            lv_chatPrivate.setSelection(mDataArrays.size() - 1);// 最后一行
        }
        TextMessage textMessage = (TextMessage) baseMesage.userMessage;
        bleDBDao.addP2PTextMsg(baseMesage, textMessage);//保存到数据库
    }


    @Override
    public void run() {
        mAlertDialog.dismiss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String selectPath = null;
        if (requestCode == SELECT_A_PICTURE) {
            if (resultCode == RESULT_OK && null != data) {
                Log.i("zou", "4.4以下的");
                Uri selectedImage = data.getData();
                selectPath = FileUtils.getPath(this, selectedImage);
                sendImageMsg(selectPath);
            }
        } else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
            if (resultCode == RESULT_OK && null != data) {
                Log.i("zou", "4.4以上上的");
                selectPath = TUtils.getPath(ChatActivity.this, data.getData());
                if (null != selectPath) {
                    sendImageMsg(selectPath);
                }
            }
        } else if (requestCode == REQUEST_CODE_SELECT_FILE) {
            if (data != null) {
                Uri uri = data.getData();
                if (null != uri) {
                    String select_file_path = FileUtils.getPath(ChatActivity.this, uri);
                    if (select_file_path != null) {
                        sendFileMsg(select_file_path);
                    }
                }
            }
        }
    }

}
