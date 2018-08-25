package com.example.admin.tmallgenie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CustomLinearLayout mContainerCll;
    private CustomScrollView mMainSv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView(){
        mMainSv = findViewById(R.id.sv_main);
        mContainerCll = findViewById(R.id.cll_item_container);

        mMainSv.setCustomLinearLayout(mContainerCll);
        mContainerCll.setCustomScrollView(mMainSv);

    }




}
