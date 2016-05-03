package com.nexfi.yuanpeigen.nexfi_android_ble.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.BigImageActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.ModifyInformationActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.activity.UserInformationActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.BaseMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.FileMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.MessageType;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.TextMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.FileTransferUtils;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.FileUtils;

import java.util.List;


/**
 * Created by Mark on 2016/4/25.
 */
public class ChatMessageAdapater extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<BaseMessage> coll;
    private Context mContext;

    private TextMessage textMessage = null;
    private FileMessage fileMessage = null;


    public ChatMessageAdapater(Context context, List<BaseMessage> coll) {
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    private static final String TAG = ChatMessageAdapater.class.getSimpleName();

    @Override
    public int getViewTypeCount() {
        return 15;
    }

    @Override
    public int getItemViewType(int position) {
        BaseMessage entity = coll.get(position);
        switch (entity.messageType) {
            case 7:
                return MessageType.SEND_TEXT_ONLY_MESSAGE_TYPE;
            case 8:
                return MessageType.RECEIVE_TEXT_ONLY_MESSAGE_TYPE;
            case 9:
                return MessageType.SINGLE_SEND_FOLDER_MESSAGE_TYPE;
            case 10:
                return MessageType.SINGLE_RECV_FOLDER_MESSAGE_TYPE;
            case 11:
                return MessageType.SINGLE_SEND_IMAGE_MESSAGE_TYPE;
            case 12:
                return MessageType.SINGLE_RECV_IMAGE_MESSAGE_TYPE;
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
        BaseMessage entity = coll.get(position);
        int msgType = entity.messageType;
        if (msgType == MessageType.SEND_TEXT_ONLY_MESSAGE_TYPE || msgType == MessageType.RECEIVE_TEXT_ONLY_MESSAGE_TYPE) {
            textMessage = (TextMessage) entity.userMessage;
        } else if (msgType == MessageType.SINGLE_SEND_FOLDER_MESSAGE_TYPE || msgType == MessageType.SINGLE_RECV_FOLDER_MESSAGE_TYPE || msgType == MessageType.SINGLE_SEND_IMAGE_MESSAGE_TYPE || msgType == MessageType.SINGLE_RECV_IMAGE_MESSAGE_TYPE) {
            fileMessage = (FileMessage) entity.userMessage;
        }


        ViewHolder_chatSend viewHolder_chatSend = null;
        ViewHolder_chatReceive viewHolder_chatReceive = null;
        ViewHolder_sendFile viewHolder_sendFile = null;
        ViewHolder_ReceiveFile viewHolder_receiveFile = null;
        ViewHolder_sendImage viewHolder_sendImage = null;
        ViewHolder_ReceiveImage viewHolder_receiveImage = null;
        if (convertView == null) {
            switch (msgType) {
                case 7:
                    viewHolder_chatSend = new ViewHolder_chatSend();
                    convertView = mInflater.inflate(R.layout.item_chatting_msg_send, null);
                    viewHolder_chatSend.tv_chatText_send = (TextView) convertView.findViewById(R.id.tv_chatText_send);
                    viewHolder_chatSend.tv_sendTime_send = (TextView) convertView.findViewById(R.id.tv_sendtime_send);
                    viewHolder_chatSend.iv_userhead_send_chat = (ImageView) convertView.findViewById(R.id.iv_userhead_send);
                    convertView.setTag(viewHolder_chatSend);
                    break;
                case 8:
                    viewHolder_chatReceive = new ViewHolder_chatReceive();
                    convertView = mInflater.inflate(R.layout.item_chatting_msg_receive, null);
                    viewHolder_chatReceive.tv_chatText_receive = (TextView) convertView.findViewById(R.id.tv_chatText_receive);
                    viewHolder_chatReceive.tv_sendTime_receive = (TextView) convertView.findViewById(R.id.tv_sendtime_receive);
                    viewHolder_chatReceive.iv_userhead_receive_chat = (ImageView) convertView.findViewById(R.id.iv_userhead_receive);
                    convertView.setTag(viewHolder_chatReceive);
                    break;
                case 9:
                    viewHolder_sendFile = new ViewHolder_sendFile();
                    convertView = mInflater.inflate(R.layout.item_send_file, null);
                    viewHolder_sendFile.tv_sendTime_send_folder = (TextView) convertView.findViewById(R.id.tv_sendTime_send_folder);
                    viewHolder_sendFile.iv_userhead_send_folder = (ImageView) convertView.findViewById(R.id.iv_userhead_send_folder);
                    viewHolder_sendFile.tv_file_name_send = (TextView) convertView.findViewById(R.id.tv_file_name_send);
                    viewHolder_sendFile.tv_size_send = (TextView) convertView.findViewById(R.id.tv_size_send);
                    viewHolder_sendFile.iv_icon_send = (ImageView) convertView.findViewById(R.id.iv_icon_send);
                    viewHolder_sendFile.pb_send = (ProgressBar) convertView.findViewById(R.id.pb_send);
                    viewHolder_sendFile.chatcontent_send = (RelativeLayout) convertView.findViewById(R.id.chatcontent_send);
                    convertView.setTag(viewHolder_sendFile);
                    break;
                case 10:
                    viewHolder_receiveFile = new ViewHolder_ReceiveFile();
                    convertView = mInflater.inflate(R.layout.item_recevied_file, null);
                    viewHolder_receiveFile.tv_sendTime_receive_folder = (TextView) convertView.findViewById(R.id.tv_sendTime_receive_folder);
                    viewHolder_receiveFile.iv_userhead_receive_folder = (ImageView) convertView.findViewById(R.id.iv_userhead_receive_folder);
                    viewHolder_receiveFile.tv_file_name_receive = (TextView) convertView.findViewById(R.id.tv_file_name_receive);
                    viewHolder_receiveFile.tv_size_receive = (TextView) convertView.findViewById(R.id.tv_size_receive);
                    viewHolder_receiveFile.iv_icon_receive = (ImageView) convertView.findViewById(R.id.iv_icon_receive);
                    viewHolder_receiveFile.pb_receive = (ProgressBar) convertView.findViewById(R.id.pb_receive);
                    viewHolder_receiveFile.chatcontent_receive = (RelativeLayout) convertView.findViewById(R.id.chatcontent_receive);
                    convertView.setTag(viewHolder_receiveFile);
                    break;
                case 11:
                    viewHolder_sendImage = new ViewHolder_sendImage();
                    convertView = mInflater.inflate(R.layout.item_send_imge, null);
                    viewHolder_sendImage.chatcontent_send = (RelativeLayout) convertView.findViewById(R.id.chatcontent_send);
                    viewHolder_sendImage.iv_icon_send = (ImageView) convertView.findViewById(R.id.iv_icon_send);
                    viewHolder_sendImage.iv_userhead_send_image = (ImageView) convertView.findViewById(R.id.iv_userhead_send_image);
                    viewHolder_sendImage.tv_sendTime_send_image = (TextView) convertView.findViewById(R.id.tv_sendTime_send_image);
                    viewHolder_sendImage.pb_send = (ProgressBar) convertView.findViewById(R.id.pb_send);
                    convertView.setTag(viewHolder_sendImage);
                    break;
                case 12:
                    viewHolder_receiveImage = new ViewHolder_ReceiveImage();
                    convertView = mInflater.inflate(R.layout.item_recevied_imge, null);
                    viewHolder_receiveImage.chatcontent_receive = (RelativeLayout) convertView.findViewById(R.id.chatcontent_receive);
                    viewHolder_receiveImage.iv_icon_receive = (ImageView) convertView.findViewById(R.id.iv_icon_receive);
                    viewHolder_receiveImage.iv_userhead_receive_image = (ImageView) convertView.findViewById(R.id.iv_userhead_receive_image);
                    viewHolder_receiveImage.tv_sendTime_receive_image = (TextView) convertView.findViewById(R.id.tv_sendTime_receive_image);
                    viewHolder_receiveImage.pb_receive = (ProgressBar) convertView.findViewById(R.id.pb_receive);
                    convertView.setTag(viewHolder_receiveImage);
                    break;
            }
        } else {
            switch (msgType) {
                case 7:
                    viewHolder_chatSend = (ViewHolder_chatSend) convertView.getTag();
                    break;
                case 8:
                    viewHolder_chatReceive = (ViewHolder_chatReceive) convertView.getTag();
                    break;
                case 9:
                    viewHolder_sendFile = (ViewHolder_sendFile) convertView.getTag();
                    break;
                case 10:
                    viewHolder_receiveFile = (ViewHolder_ReceiveFile) convertView.getTag();
                    break;
                case 11:
                    viewHolder_sendImage = (ViewHolder_sendImage) convertView.getTag();
                    break;
                case 12:
                    viewHolder_receiveImage = (ViewHolder_ReceiveImage) convertView.getTag();
                    break;
            }

        }


        switch (msgType) {
            case 7:
                viewHolder_chatSend.iv_userhead_send_chat.setImageResource(textMessage.userAvatar);

                viewHolder_chatSend.iv_userhead_send_chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ModifyInformationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });

                viewHolder_chatSend.tv_sendTime_send.setText(entity.sendTime);
                viewHolder_chatSend.tv_chatText_send.setText(textMessage.textMessageContent);
                break;
            case 8:
                viewHolder_chatReceive.iv_userhead_receive_chat.setImageResource(textMessage.userAvatar);

                viewHolder_chatReceive.iv_userhead_receive_chat.setOnClickListener(new AvatarClick(position));

                viewHolder_chatReceive.tv_sendTime_receive.setText(entity.sendTime);
                viewHolder_chatReceive.tv_chatText_receive.setText(textMessage.textMessageContent);
                break;
            case 9://发送文件
                viewHolder_sendFile.iv_userhead_send_folder.setImageResource(fileMessage.userAvatar);

                viewHolder_sendFile.iv_userhead_send_folder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ModifyInformationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });


                viewHolder_sendFile.tv_sendTime_send_folder.setText(entity.sendTime);
                viewHolder_sendFile.tv_file_name_send.setText(fileMessage.fileName);
                long send_file_size = Long.parseLong(fileMessage.fileSize);
                String send_formatSize = android.text.format.Formatter.formatFileSize(mContext, send_file_size);//
                viewHolder_sendFile.tv_size_send.setText(send_formatSize);
                viewHolder_sendFile.iv_icon_send.setImageResource(fileMessage.fileIcon);
                viewHolder_sendFile.chatcontent_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != fileMessage.filePath) {
                            Intent intent = FileUtils.openFile(fileMessage.filePath);
                            Log.e("TAG", fileMessage.filePath + "-------------------send路径-----");
                            try {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(BleApplication.getContext(), "无法访问", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if (fileMessage.isPb == 0) {
                    viewHolder_sendFile.pb_send.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_sendFile.pb_send.setVisibility(View.VISIBLE);
                }
                break;
            case 10://接收文件
                viewHolder_receiveFile.iv_userhead_receive_folder.setImageResource(fileMessage.userAvatar);

                viewHolder_receiveFile.iv_userhead_receive_folder.setOnClickListener(new AvatarClick(position));

                viewHolder_receiveFile.tv_sendTime_receive_folder.setText(entity.sendTime);
                viewHolder_receiveFile.tv_file_name_receive.setText(fileMessage.fileName);
                long rece_file_size = Long.parseLong(fileMessage.fileSize);
                String formatSize_rece = android.text.format.Formatter.formatFileSize(BleApplication.getContext(), rece_file_size);//
                viewHolder_receiveFile.tv_size_receive.setText(formatSize_rece);
                viewHolder_receiveFile.iv_icon_receive.setImageResource(fileMessage.fileIcon);
                viewHolder_receiveFile.chatcontent_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != fileMessage.filePath) {
                            Intent intent = FileUtils.openFile(fileMessage.filePath);
                            Log.e("TAG", fileMessage.filePath + "-------------------receive路径-----" + intent);
                            try {
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(BleApplication.getContext(), "无法访问", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if (fileMessage.isPb == 0) {
                    viewHolder_receiveFile.pb_receive.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_receiveFile.pb_receive.setVisibility(View.VISIBLE);
                }
                break;
            case 11:
                final byte[] bys_send = Base64.decode(fileMessage.fileData, Base64.DEFAULT);
                final Bitmap bitmap = FileTransferUtils.getPicFromBytes(bys_send);
                viewHolder_sendImage.iv_icon_send.setImageBitmap(bitmap);
                viewHolder_sendImage.iv_icon_send.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder_sendImage.iv_userhead_send_image.setImageResource(fileMessage.userAvatar);

                viewHolder_sendImage.iv_userhead_send_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ModifyInformationActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                });

                viewHolder_sendImage.tv_sendTime_send_image.setText(entity.sendTime);
                if (fileMessage.isPb == 0) {
                    viewHolder_sendImage.pb_send.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_sendImage.pb_send.setVisibility(View.VISIBLE);
                }
                viewHolder_sendImage.chatcontent_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, BigImageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("bitmap", bys_send);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.img_scale_in, R.anim.img_scale_out);
                    }
                });
                break;

            case 12:
                final byte[] bys_receive = Base64.decode(fileMessage.fileData, Base64.DEFAULT);
                Bitmap bitmap_receive = FileTransferUtils.getPicFromBytes(bys_receive);
                viewHolder_receiveImage.iv_icon_receive.setImageBitmap(bitmap_receive);
                viewHolder_receiveImage.iv_icon_receive.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder_receiveImage.iv_userhead_receive_image.setImageResource(fileMessage.userAvatar);

                viewHolder_receiveImage.iv_userhead_receive_image.setOnClickListener(new AvatarClick(position));

                viewHolder_receiveImage.tv_sendTime_receive_image.setText(entity.sendTime);
                if (fileMessage.isPb == 0) {
                    viewHolder_receiveImage.pb_receive.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder_receiveImage.pb_receive.setVisibility(View.VISIBLE);
                }
                viewHolder_receiveImage.chatcontent_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, BigImageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("bitmap", bys_receive);
                        mContext.startActivity(intent);
                        ((Activity) mContext).overridePendingTransition(R.anim.img_scale_in, R.anim.img_scale_out);
                    }
                });
                break;
        }
        return convertView;
    }


    //单击事件实现
    class AvatarClick implements View.OnClickListener {
        public int position;

        public AvatarClick(int p) {
            position = p;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(mContext, UserInformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data_obj", coll.get(position));
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }


    class ViewHolder_chatSend {
        public TextView tv_chatText_send, tv_sendTime_send;
        public ImageView iv_userhead_send_chat;
    }

    class ViewHolder_chatReceive {
        public TextView tv_chatText_receive, tv_sendTime_receive;
        public ImageView iv_userhead_receive_chat;
    }

    static class ViewHolder_sendFile {
        public TextView tv_sendTime_send_folder, tv_size_send, tv_file_name_send;
        public ImageView iv_userhead_send_folder, iv_icon_send;
        public RelativeLayout chatcontent_send;
        public ProgressBar pb_send;
    }

    static class ViewHolder_ReceiveFile {
        public TextView tv_sendTime_receive_folder, tv_size_receive, tv_file_name_receive;
        public ImageView iv_userhead_receive_folder, iv_icon_receive;
        public ProgressBar pb_receive;
        public RelativeLayout chatcontent_receive;
    }

    static class ViewHolder_ReceiveImage {
        public TextView tv_sendTime_receive_image;
        public ImageView iv_userhead_receive_image, iv_icon_receive;
        public ProgressBar pb_receive;
        public RelativeLayout chatcontent_receive;
    }

    static class ViewHolder_sendImage {
        public TextView tv_sendTime_send_image;
        public ImageView iv_userhead_send_image, iv_icon_send;
        public RelativeLayout chatcontent_send;
        public ProgressBar pb_send;
    }


}