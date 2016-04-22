package com.nexfi.yuanpeigen.nexfi_android_ble.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nexfi.yuanpeigen.nexfi_android_ble.R;

/**
 * Created by gengbaolong on 2016/4/22.
 */
public class BigImageActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView big_image_view;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        Intent intent = getIntent();
        if (intent != null)
        {
            byte[] bis = intent.getByteArrayExtra("bitmap");
            bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        }
        initView();
        big_image_view.setImageBitmap(bitmap);
        big_image_view.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private void initView() {
        big_image_view= (ImageView) findViewById(R.id.big_image_view);
        big_image_view.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //点击大图片
        finish();
    }
}
