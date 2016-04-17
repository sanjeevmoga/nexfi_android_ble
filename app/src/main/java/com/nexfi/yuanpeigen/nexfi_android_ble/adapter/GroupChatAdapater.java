package com.nexfi.yuanpeigen.nexfi_android_ble.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.FileMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;

import java.util.List;


/**
 * Created by Mark on 2016/4/17.
 */
public class GroupChatAdapater extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<BaseMessage> coll;
    private Context mContext;

    private static final int MESSAGE_TYPE_SEND_CHAT_CONTEXT = 0;
    private static final int MESSAGE_TYPE_RECV_CHAT_CONTEXT = 1;
    private static final int MESSAGE_TYPE_SEND_FOLDER = 2;
    private static final int MESSAGE_TYPE_RECV_FOLDER = 3;
    private static final int MESSAGE_TYPE_SEND_IMAGE = 4;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 5;


    public GroupChatAdapater(Context context, List<BaseMessage> coll) {
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    private static final String TAG = ChatMessageAdapater.class.getSimpleName();

    @Override
    public int getViewTypeCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        BaseMessage entity = coll.get(position);
        switch (entity.messageType) {
            case MESSAGE_TYPE_SEND_CHAT_CONTEXT:
                return MESSAGE_TYPE_SEND_CHAT_CONTEXT;
            case MESSAGE_TYPE_RECV_CHAT_CONTEXT:
                return MESSAGE_TYPE_RECV_CHAT_CONTEXT;
            case MESSAGE_TYPE_SEND_FOLDER:
                return MESSAGE_TYPE_SEND_FOLDER;
            case MESSAGE_TYPE_RECV_FOLDER:
                return MESSAGE_TYPE_RECV_FOLDER;
            case MESSAGE_TYPE_SEND_IMAGE:
                return MESSAGE_TYPE_SEND_IMAGE;
            case MESSAGE_TYPE_RECV_IMAGE:
                return MESSAGE_TYPE_RECV_IMAGE;
        }
        return -1;
    }

    @Override
    public int getCount() {
        return coll.size();
    }

    @Override
    public Object getItem(int position) {
        return coll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BaseMessage entity = coll.get(position);
        UserMessage user = (UserMessage) entity.entiyMessage;
        TextMessage textMessage = (TextMessage) entity.entiyMessage;
        FileMessage fileMessage = (FileMessage) entity.entiyMessage;
        int msgType = entity.messageType;
        ViewHolder_chatSend viewHolder_chatSend = null;
        ViewHolder_chatReceive viewHolder_chatReceive = null;
        ViewHolder_sendFile viewHolder_sendFile = null;
        ViewHolder_ReceiveFile viewHolder_receiveFile = null;
        ViewHolder_sendImage viewHolder_sendImage = null;
        ViewHolder_ReceiveImage viewHolder_receiveImage = null;
        if (convertView == null) {
            switch (msgType) {
                case MESSAGE_TYPE_SEND_CHAT_CONTEXT:
                    viewHolder_chatSend = new ViewHolder_chatSend();
                    convertView = mInflater.inflate(R.layout.item_chatting_msg_send_group, null);
                    viewHolder_chatSend.tv_chatText_send = (TextView) convertView.findViewById(R.id.tv_chatText_send);
                    viewHolder_chatSend.tv_sendTime_send = (TextView) convertView.findViewById(R.id.tv_sendtime_send);
                    viewHolder_chatSend.iv_userhead_send_chat = (ImageView) convertView.findViewById(R.id.iv_userhead_send);
                    viewHolder_chatSend.tv_userNick_send = (TextView) convertView.findViewById(R.id.tv_userNick_send);
                    convertView.setTag(viewHolder_chatSend);
                    break;
                case MESSAGE_TYPE_RECV_CHAT_CONTEXT:
                    viewHolder_chatReceive = new ViewHolder_chatReceive();
                    convertView = mInflater.inflate(R.layout.item_chatting_msg_receive, null);
                    viewHolder_chatReceive.tv_chatText_receive = (TextView) convertView.findViewById(R.id.tv_chatText_receive);
                    viewHolder_chatReceive.tv_sendTime_receive = (TextView) convertView.findViewById(R.id.tv_sendtime_receive);
                    viewHolder_chatReceive.iv_userhead_receive_chat = (ImageView) convertView.findViewById(R.id.iv_userhead_receive);
                    viewHolder_chatReceive.tv_userNick_receive = (TextView) convertView.findViewById(R.id.tv_userNick_receive);
                    convertView.setTag(viewHolder_chatReceive);
                    break;
                case MESSAGE_TYPE_SEND_FOLDER:
                    viewHolder_sendFile = new ViewHolder_sendFile();
                    convertView = mInflater.inflate(R.layout.item_send_file, null);
                    viewHolder_sendFile.tv_sendTime_send_folder = (TextView) convertView.findViewById(R.id.tv_sendTime_send_folder);
                    viewHolder_sendFile.iv_userhead_send_folder = (ImageView) convertView.findViewById(R.id.iv_userhead_send_folder);
                    viewHolder_sendFile.tv_file_name_send = (TextView) convertView.findViewById(R.id.tv_file_name_send);
                    viewHolder_sendFile.tv_size_send = (TextView) convertView.findViewById(R.id.tv_size_send);
                    viewHolder_sendFile.iv_icon_send = (ImageView) convertView.findViewById(R.id.iv_icon_send);
                    viewHolder_sendFile.pb_send = (ProgressBar) convertView.findViewById(R.id.pb_send);
                    viewHolder_sendFile.chatcontent_send = (RelativeLayout) convertView.findViewById(R.id.chatcontent_send);
                    viewHolder_sendFile.tv_userNick_send_folder = (TextView) convertView.findViewById(R.id.tv_sendTime_send_folder);
                    convertView.setTag(viewHolder_sendFile);
                    break;
                case MESSAGE_TYPE_RECV_FOLDER:
                    viewHolder_receiveFile = new ViewHolder_ReceiveFile();
                    convertView = mInflater.inflate(R.layout.item_recevied_file, null);
                    viewHolder_receiveFile.tv_sendTime_receive_folder = (TextView) convertView.findViewById(R.id.tv_sendTime_receive_folder);
                    viewHolder_receiveFile.iv_userhead_receive_folder = (ImageView) convertView.findViewById(R.id.iv_userhead_receive_folder);
                    viewHolder_receiveFile.tv_file_name_receive = (TextView) convertView.findViewById(R.id.tv_file_name_receive);
                    viewHolder_receiveFile.tv_size_receive = (TextView) convertView.findViewById(R.id.tv_size_receive);
                    viewHolder_receiveFile.iv_icon_receive = (ImageView) convertView.findViewById(R.id.iv_icon_receive);
                    viewHolder_receiveFile.pb_receive = (ProgressBar) convertView.findViewById(R.id.pb_receive);
                    viewHolder_receiveFile.chatcontent_receive = (RelativeLayout) convertView.findViewById(R.id.chatcontent_receive);
                    viewHolder_receiveFile.tv_userNick_receive_folder = (TextView) convertView.findViewById(R.id.tv_userNick_receive_folder);
                    convertView.setTag(viewHolder_receiveFile);
                    break;
                case MESSAGE_TYPE_SEND_IMAGE:
                    viewHolder_sendImage = new ViewHolder_sendImage();
                    convertView = mInflater.inflate(R.layout.item_send_imge, null);
                    viewHolder_sendImage.chatcontent_send = (RelativeLayout) convertView.findViewById(R.id.chatcontent_send);
                    viewHolder_sendImage.iv_icon_send = (ImageView) convertView.findViewById(R.id.iv_icon_send);
                    viewHolder_sendImage.iv_userhead_send_image = (ImageView) convertView.findViewById(R.id.iv_userhead_send_image);
                    viewHolder_sendImage.tv_sendTime_send_image = (TextView) convertView.findViewById(R.id.tv_sendTime_send_image);
                    viewHolder_sendImage.pb_send = (ProgressBar) convertView.findViewById(R.id.pb_send);
                    viewHolder_sendImage.tv_userNick_send_image = (TextView) convertView.findViewById(R.id.tv_userNick_send_image);
                    convertView.setTag(viewHolder_sendImage);
                    break;
                case MESSAGE_TYPE_RECV_IMAGE:
                    viewHolder_receiveImage = new ViewHolder_ReceiveImage();
                    convertView = mInflater.inflate(R.layout.item_recevied_imge, null);
                    viewHolder_receiveImage.chatcontent_receive = (RelativeLayout) convertView.findViewById(R.id.chatcontent_receive);
                    viewHolder_receiveImage.iv_icon_receive = (ImageView) convertView.findViewById(R.id.iv_icon_receive);
                    viewHolder_receiveImage.iv_userhead_receive_image = (ImageView) convertView.findViewById(R.id.iv_userhead_receive_image);
                    viewHolder_receiveImage.tv_sendTime_receive_image = (TextView) convertView.findViewById(R.id.tv_sendTime_receive_image);
                    viewHolder_receiveImage.pb_receive = (ProgressBar) convertView.findViewById(R.id.pb_receive);
                    viewHolder_receiveImage.tv_userNick_receive_image = (TextView) convertView.findViewById(R.id.tv_userNick_receive_image);
                    convertView.setTag(viewHolder_receiveImage);
                    break;
            }
        } else {
            switch (msgType) {
                case MESSAGE_TYPE_SEND_CHAT_CONTEXT:
                    viewHolder_chatSend = (ViewHolder_chatSend) convertView.getTag();
                    break;
                case MESSAGE_TYPE_RECV_CHAT_CONTEXT:
                    viewHolder_chatReceive = (ViewHolder_chatReceive) convertView.getTag();
                    break;
                case MESSAGE_TYPE_SEND_FOLDER:
                    viewHolder_sendFile = (ViewHolder_sendFile) convertView.getTag();
                    break;
                case MESSAGE_TYPE_RECV_FOLDER:
                    viewHolder_receiveFile = (ViewHolder_ReceiveFile) convertView.getTag();
                    break;
                case MESSAGE_TYPE_SEND_IMAGE:
                    viewHolder_sendImage = (ViewHolder_sendImage) convertView.getTag();
                    break;
                case MESSAGE_TYPE_RECV_IMAGE:
                    viewHolder_receiveImage = (ViewHolder_ReceiveImage) convertView.getTag();
                    break;
            }

        }


        switch (msgType) {
            case MESSAGE_TYPE_SEND_CHAT_CONTEXT:
                viewHolder_chatSend.iv_userhead_send_chat.setImageResource(user.userAvatar);
                viewHolder_chatSend.tv_sendTime_send.setText(entity.sendTime);
                viewHolder_chatSend.tv_chatText_send.setText(textMessage.textMessageContent);
                viewHolder_chatSend.tv_userNick_send.setText(user.userNick);
                break;
            case MESSAGE_TYPE_RECV_CHAT_CONTEXT:
                viewHolder_chatReceive.iv_userhead_receive_chat.setImageResource(user.userAvatar);
                viewHolder_chatReceive.tv_sendTime_receive.setText(entity.sendTime);
                viewHolder_chatReceive.tv_chatText_receive.setText(textMessage.textMessageContent);
                viewHolder_chatReceive.tv_userNick_receive.setText(user.userNick);
                break;
            case MESSAGE_TYPE_SEND_FOLDER:
                viewHolder_sendFile.tv_userNick_send_folder.setText(user.userNick);
                viewHolder_sendFile.iv_userhead_send_folder.setImageResource(user.userAvatar);
                viewHolder_sendFile.tv_sendTime_send_folder.setText(entity.sendTime);
                viewHolder_sendFile.tv_file_name_send.setText(fileMessage.fileName);
                viewHolder_sendFile.tv_size_send.setText(fileMessage.fileSize);
                viewHolder_sendFile.iv_icon_send.setImageResource(fileMessage.fileIcon);
                viewHolder_sendFile.chatcontent_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
                    }
                });
                if (fileMessage.isPb == 0) {
                    viewHolder_sendFile.pb_send.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_sendFile.pb_send.setVisibility(View.VISIBLE);
                }
                break;
            case MESSAGE_TYPE_RECV_FOLDER:
                viewHolder_receiveFile.iv_userhead_receive_folder.setImageResource(user.userAvatar);
                viewHolder_receiveFile.tv_sendTime_receive_folder.setText(entity.sendTime);
                viewHolder_receiveFile.tv_file_name_receive.setText(fileMessage.fileName);
                viewHolder_receiveFile.tv_size_receive.setText(fileMessage.fileSize);
                viewHolder_receiveFile.tv_userNick_receive_folder.setText(user.userNick);
                viewHolder_receiveFile.iv_icon_receive.setImageResource(fileMessage.fileIcon);
                //选择文件的打开方式
                viewHolder_receiveFile.chatcontent_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
                    }
                });
                if (fileMessage.isPb == 0) {
                    viewHolder_receiveFile.pb_receive.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_receiveFile.pb_receive.setVisibility(View.VISIBLE);
                }
                break;

            case MESSAGE_TYPE_SEND_IMAGE:
                viewHolder_sendImage.chatcontent_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder_sendImage.iv_icon_send.setImageResource(fileMessage.fileIcon);
                viewHolder_sendImage.iv_userhead_send_image.setImageResource(user.userAvatar);
                viewHolder_sendImage.tv_sendTime_send_image.setText(entity.sendTime);
                viewHolder_sendImage.tv_userNick_send_image.setText(user.userNick);
                if (fileMessage.isPb == 0) {
                    viewHolder_sendImage.pb_send.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_sendImage.pb_send.setVisibility(View.VISIBLE);
                }
                break;

            case MESSAGE_TYPE_RECV_IMAGE:
                viewHolder_receiveImage.chatcontent_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "即将上线，敬请期待", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder_receiveImage.iv_icon_receive.setImageResource(fileMessage.fileIcon);
                viewHolder_receiveImage.iv_userhead_receive_image.setImageResource(user.userAvatar);
                viewHolder_receiveImage.tv_sendTime_receive_image.setText(entity.sendTime);
                viewHolder_receiveImage.tv_userNick_receive_image.setText(user.userNick);
                if (fileMessage.isPb == 0) {
                    viewHolder_receiveImage.pb_receive.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_receiveImage.pb_receive.setVisibility(View.VISIBLE);
                }
                break;
        }

        return convertView;
    }


    static class ViewHolder_chatSend {
        public TextView tv_chatText_send, tv_sendTime_send, tv_userNick_send;
        public ImageView iv_userhead_send_chat;
    }

    static class ViewHolder_chatReceive {
        public TextView tv_chatText_receive, tv_sendTime_receive, tv_userNick_receive;
        public ImageView iv_userhead_receive_chat;
    }

    static class ViewHolder_sendFile {
        public TextView tv_sendTime_send_folder, tv_size_send, tv_file_name_send, tv_userNick_send_folder;
        public ImageView iv_userhead_send_folder, iv_icon_send;
        public RelativeLayout chatcontent_send;
        public ProgressBar pb_send;
    }

    static class ViewHolder_ReceiveFile {
        public TextView tv_sendTime_receive_folder, tv_size_receive, tv_file_name_receive, tv_userNick_receive_folder;
        public ImageView iv_userhead_receive_folder, iv_icon_receive;
        public ProgressBar pb_receive;
        public RelativeLayout chatcontent_receive;
    }

    static class ViewHolder_ReceiveImage {
        public TextView tv_sendTime_receive_image, tv_userNick_receive_image;
        public ImageView iv_userhead_receive_image, iv_icon_receive;
        public ProgressBar pb_receive;
        public RelativeLayout chatcontent_receive;
    }

    static class ViewHolder_sendImage {
        public TextView tv_sendTime_send_image, tv_userNick_send_image;
        public ImageView iv_userhead_send_image, iv_icon_send;
        public RelativeLayout chatcontent_send;
        public ProgressBar pb_send;
    }


}