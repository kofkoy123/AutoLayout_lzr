package com.lzr.autolayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText etWidth, etHeight, etChicun;
    private Button btnSkip, btnSubmit;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();

    }

    private void setListeners() {
        btnSkip.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void initViews() {
        etWidth = (EditText) findViewById(R.id.et_width);
        etHeight = (EditText) findViewById(R.id.et_height);
        etChicun = (EditText) findViewById(R.id.et_chicun);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        tvResult = (TextView) findViewById(R.id.tv_result);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_skip:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.btn_submit:
                compute();
                break;
        }
    }

    private void compute() {
        double width = Double.parseDouble(etWidth.getText().toString().trim());
        double height = Double.parseDouble(etHeight.getText().toString().trim());
        double chicun = Double.parseDouble(etChicun.getText().toString().trim());

        double res1 = (Math.pow(width, 2) + Math.pow(height, 2));
        double res2 = Math.sqrt(res1);
        double dpi = res2 / chicun;
        tvResult.setText("结果为:" + dpi);
    }
}
