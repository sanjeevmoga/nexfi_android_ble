package com.nexfi.yuanpeigen.nexfi_android_ble.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.ChatActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.UserInformationActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;

import java.util.List;

/**
 * Created by Mark on 2016/4/13.
 */
public class UserListViewAdapter extends BaseAdapter {

    private final String USER_AGE = "userAge";
    private final String USER_AVATAR = "userAvatar";
    private final String USER_GENDER = "userGender";
    private final String USER_NICK = "userNick";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<UserMessage> userMessageList;
    private final String USER_SEX = "男";

    public UserListViewAdapter(Context context, List<UserMessage> userMessageList) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.userMessageList = userMessageList;
    }

    @Override
    public int getCount() {
        return userMessageList.size();
    }


    @Override
    public Object getItem(int position) {
        return userMessageList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UserMessage entity = userMessageList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_userlist, null);
            holder.btn_chat = (Button) convertView.findViewById(R.id.btn_chat);
            holder.iv_sex = (ImageView) convertView.findViewById(R.id.iv_sex);
            holder.iv_userhead_icon = (ImageView) convertView.findViewById(R.id.iv_userhead_icon);
            holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.layout_userList = (RelativeLayout) convertView.findViewById(R.id.layout_userList);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_userhead_icon.setImageResource(entity.userAvatar);
        holder.tv_username.setText(entity.userNick);
        if (entity.userGender.equals(USER_SEX)) {
            holder.iv_sex.setImageResource(R.mipmap.img_male);
        } else {
            holder.iv_sex.setImageResource(R.mipmap.img_female);
        }
        holder.btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra(USER_AGE,entity.userAge);
                intent.putExtra(USER_AVATAR, entity.userAvatar);
                intent.putExtra(USER_GENDER, entity.userGender);
                intent.putExtra(USER_NICK, entity.userNick);
                mContext.startActivity(intent);
            }
        });

        holder.layout_userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserInformationActivity.class);
                intent.putExtra(USER_AGE,entity.userAge);
                intent.putExtra(USER_AVATAR, entity.userAvatar);
                intent.putExtra(USER_GENDER, entity.userGender);
                intent.putExtra(USER_NICK, entity.userNick);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        public ImageView iv_userhead_icon, iv_sex;
        public Button btn_chat;
        public TextView tv_username;
        public RelativeLayout layout_userList;
    }


}
