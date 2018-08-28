package com.example.admin.tmallgenie;

import android.content.Intent;
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

        mLinearLayout.setOnBottomListener(new CustomLinearLayout.OnBottomListener() {
            @Override
            public void isBottom() {
                startActivityForResult(new Intent(getApplicationContext(), SecondActivity.class), 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            mLinearLayout.scrollTo(0, 0);
        }
    }
}
