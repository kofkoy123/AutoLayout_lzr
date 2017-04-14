package com.lzr.autolayout;

import android.os.Bundle;
import android.widget.ImageView;

public class AboutActivity extends BaseActivity {

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}
