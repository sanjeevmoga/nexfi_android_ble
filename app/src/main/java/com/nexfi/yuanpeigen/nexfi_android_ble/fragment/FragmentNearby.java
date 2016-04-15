package com.nexfi.yuanpeigen.nexfi_android_ble.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.GroupChatActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.adapter.UserListViewAdapter;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2016/4/12.
 */
public class FragmentNearby extends Fragment implements View.OnClickListener {

    private PopupWindow mPopupWindow = null, mPopupWindow_share = null;
    private ImageView iv_add;
    private View View_pop, view_share, v_parent;
    private LinearLayout addChatRoom, share;
    private ListView lv_userlist;
    private List<UserMessage> userMessageList = new ArrayList<UserMessage>();
    private UserListViewAdapter userListViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView(inflater, container);
        setClickListener();
        initVirtualData();
        return v_parent;
    }

    private void initVirtualData() {
        UserMessage userMessage1 = new UserMessage();
        userMessage1.userAvatar = R.mipmap.img_head_3;
        userMessage1.userNick = "Mark";
        userMessage1.userGender = "男";
        userMessage1.userAge = 23;
        userMessageList.add(userMessage1);
        UserMessage userMessage2 = new UserMessage();
        userMessage2.userAvatar = R.mipmap.img_head_6;
        userMessage2.userNick = "高圆圆";
        userMessage2.userGender = "女";
        userMessage2.userAge = 27;
        userMessageList.add(userMessage2);
        UserMessage userMessage3 = new UserMessage();
        userMessage3.userAvatar = R.mipmap.img_head_9;
        userMessage3.userNick = "周星驰";
        userMessage3.userGender = "男";
        userMessage3.userAge = 18;
        userMessageList.add(userMessage3);
        userListViewAdapter = new UserListViewAdapter(FragmentNearby.this.getActivity(), userMessageList);
        lv_userlist.setAdapter(userListViewAdapter);
    }

    private void setClickListener() {
        share.setOnClickListener(this);
        addChatRoom.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        v_parent = inflater.inflate(R.layout.fragment_nearby, container, false);
        View_pop = inflater.inflate(R.layout.pop_menu_add, null);
        view_share = inflater.inflate(R.layout.layout_share, null);
        lv_userlist = (ListView) v_parent.findViewById(R.id.lv_userlist);
        addChatRoom = (LinearLayout) View_pop.findViewById(R.id.add_chatRoom);
        share = (LinearLayout) View_pop.findViewById(R.id.share);
        iv_add = (ImageView) v_parent.findViewById(R.id.iv_add);
    }


    private void initPop() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(View_pop, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        }
        mPopupWindow.showAsDropDown(iv_add, 0, 0);
    }


    private void initPopShare() {
        if (mPopupWindow_share == null) {
            mPopupWindow_share = new PopupWindow(view_share, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow_share.setBackgroundDrawable(new ColorDrawable(0x00000000));
        }
        mPopupWindow_share.showAtLocation(v_parent, Gravity.CENTER, 0, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share:
                mPopupWindow.dismiss();
                initPopShare();
                break;

            case R.id.add_chatRoom:
                Intent intent = new Intent(getActivity(), GroupChatActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;

            case R.id.iv_add:
                initPop();
                break;
        }
    }
}
