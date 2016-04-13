package com.nexfi.yuanpeigen.nexfi_android_ble.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;

/**
 * Created by Mark on 2016/4/12.
 */
public class FragmentMine extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }
}
