package com.example.admin.tmallgenie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CustomLinearLayout mLinearLayout;
    private CustomScrollView mScrollView;
    private RelativeLayout mTopTitleRl;
    private TextView mTopTabTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();

    }

    private void initView(){
        mScrollView = findViewById(R.id.sv_main);
        mLinearLayout = findViewById(R.id.cll_item_container);
        mTopTitleRl = findViewById(R.id.rl_top_title);
        mTopTabTv = findViewById(R.id.tv_top_tab);

    }

    private void setListener(){
        mScrollView.setTopTitleView(mTopTitleRl);
        mScrollView.setTopTabView(mTopTabTv);
        mLinearLayout.setCustomScrollView(mScrollView);

    }


}
