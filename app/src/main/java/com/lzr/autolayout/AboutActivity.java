package com.lzr.autolayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private Button btnSkip;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnSkip.setVisibility(View.GONE);
        tvTitle.setText("关于");
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
