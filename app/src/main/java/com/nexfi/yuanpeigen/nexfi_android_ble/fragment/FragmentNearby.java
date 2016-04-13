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
import android.widget.PopupWindow;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.GroupChatActivity;

/**
 * Created by Mark on 2016/4/12.
 */
public class FragmentNearby extends Fragment {


    private PopupWindow mPopupWindow = null, mPopupWindow_share = null;
    private ImageView iv_add;
    private View View_pop, view_share, v_parent;
    private LinearLayout addChatRoom, share;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v_parent = inflater.inflate(R.layout.fragment_nearby, container, false);
        View_pop = inflater.inflate(R.layout.pop_menu_add, null);
        view_share = inflater.inflate(R.layout.layout_share, null);
        addChatRoom = (LinearLayout) View_pop.findViewById(R.id.add_chatRoom);
        share = (LinearLayout) View_pop.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                initPopShare();
            }
        });

        addChatRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupChatActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        iv_add = (ImageView) v_parent.findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPop();
            }
        });


        return v_parent;
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
}
