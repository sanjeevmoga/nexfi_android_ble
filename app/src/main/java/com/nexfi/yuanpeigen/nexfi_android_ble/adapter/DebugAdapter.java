package com.nexfi.yuanpeigen.nexfi_android_ble.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gengbaolong on 2016/5/2.
 */
public class DebugAdapter extends BaseAdapter {

    List<String> mExceLists;
    Context mContext;

    public DebugAdapter(Context context,List<String> mExceLists){
        this.mExceLists=mExceLists;
        this.mContext=context;
    }


    @Override
    public int getCount() {
        return mExceLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mExceLists.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView=new TextView(mContext);
        String log =mExceLists.get(position);
        textView.setText(log);
        return textView;
    }
}
