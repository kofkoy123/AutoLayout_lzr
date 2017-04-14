package com.lzr.autolayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ivBack = (ImageView) findViewById(R.id.iv_about_back);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
