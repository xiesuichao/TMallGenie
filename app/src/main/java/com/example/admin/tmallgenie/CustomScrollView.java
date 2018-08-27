package com.example.admin.tmallgenie;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by xiesuichao on 2018/8/24.
 */

public class CustomScrollView extends ScrollView {

    private boolean isChildTop = true;
    private float mInterceptDownY = 0;
    private int mScrollY = -1;
    private int[] mChildLlLocationArr;
    private int mChildLlInitY;
    private int[] mScrollViewLocationArr;
    private int[] mFirstTitleTvLocationArr;
    private int mFirstTitleTvInitY;
    private int[] mSecondTitleTvLocationArr;
    private int mSecondTitleTvInitY;
    private int mStatusBarHeight;
    private OnScrollYChangeListener mScrollYListener;
    private CustomLinearLayout mChildCustomLinearLayout;
    private View mTopTitleView;
    private TextView mTopTabView;
    private TextView mFirstTitleTv;
    private TextView mSecondTitleTv;


    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public interface OnScrollYChangeListener {
        void scrollYChange(int scrollY);
    }

    public void setOnScrollYChangeListener(OnScrollYChangeListener scrollYListener) {
        this.mScrollYListener = scrollYListener;
    }

    private void init() {
        mChildLlLocationArr = new int[2];
        mScrollViewLocationArr = new int[2];
        mFirstTitleTvLocationArr = new int[2];
        mSecondTitleTvLocationArr = new int[2];
    }

    public void setTopTitleView(View view) {
        this.mTopTitleView = view;
    }

    public void setTopTabView(TextView textView) {
        this.mTopTabView = textView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE && mScrollY == 0) {
            onInterceptTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mInterceptDownY = ev.getY();

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float interceptMoveY = ev.getY();
            if (!isChildTop) {
                return false;
            } else if (interceptMoveY - mInterceptDownY > 0 && mScrollY <= 10) {
                scrollTo(0, 0);
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollY = t;
        mScrollYListener.scrollYChange(t);

        mChildCustomLinearLayout.getLocationInWindow(mChildLlLocationArr);

        if (mChildLlLocationArr[1] <= mStatusBarHeight + mTopTitleView.getHeight()) {
            mTopTitleView.layout(0,
                    0 - (t - (mChildLlInitY - mStatusBarHeight - mTopTitleView.getHeight())),
                    mTopTitleView.getWidth(),
                    mTopTitleView.getHeight() - (t - (mChildLlInitY - mStatusBarHeight - mTopTitleView.getHeight())));
        } else {
            mTopTitleView.layout(0, 0, mTopTitleView.getWidth(), mTopTitleView.getHeight());
        }

        mFirstTitleTv.getLocationInWindow(mFirstTitleTvLocationArr);
        mSecondTitleTv.getLocationInWindow(mSecondTitleTvLocationArr);

        if (mFirstTitleTvLocationArr[1] <= mStatusBarHeight) {
            mTopTabView.setVisibility(VISIBLE);
            mTopTabView.setText(mFirstTitleTv.getText());

        } else if (mFirstTitleTvLocationArr[1] > mStatusBarHeight) {
            mTopTabView.setVisibility(INVISIBLE);
            mTopTabView.setText("");
        }

        if (mSecondTitleTvLocationArr[1] <= mStatusBarHeight + mTopTabView.getHeight()
                && mSecondTitleTvLocationArr[1] > mStatusBarHeight) {
            mTopTabView.layout(0,
                    0 - (t - (mSecondTitleTvInitY - mStatusBarHeight - mTopTabView.getHeight())),
                    mTopTabView.getWidth(),
                    mTopTabView.getHeight() - (t - (mSecondTitleTvInitY - mStatusBarHeight - mTopTabView.getHeight()))
            );
        }else if (mSecondTitleTvLocationArr[1] <= mStatusBarHeight){
            mTopTabView.layout(0, 0, mTopTabView.getWidth(), mTopTabView.getHeight());
            mTopTabView.setText(mSecondTitleTv.getText());
        }
        else {
            mTopTabView.layout(0, 0, mTopTabView.getWidth(), mTopTabView.getHeight());
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        FrameLayout childFrameLayout = (FrameLayout) getChildAt(0);
        mChildCustomLinearLayout = (CustomLinearLayout) childFrameLayout.getChildAt(1);
        mFirstTitleTv = (TextView) ((LinearLayout) mChildCustomLinearLayout.getChildAt(0)).getChildAt(0);
        mSecondTitleTv = (TextView) ((LinearLayout) mChildCustomLinearLayout.getChildAt(0)).getChildAt(1);
        mFirstTitleTv.getLocationInWindow(mFirstTitleTvLocationArr);
        mSecondTitleTv.getLocationInWindow(mSecondTitleTvLocationArr);
        mFirstTitleTvInitY = mFirstTitleTvLocationArr[1];
        mSecondTitleTvInitY = mSecondTitleTvLocationArr[1];

        PrintUtil.log("mFirstTitleTvInitY", mFirstTitleTvInitY);
        PrintUtil.log("mSecondTitleTvInitY", mSecondTitleTvInitY);

        getLocationInWindow(mScrollViewLocationArr);
        mStatusBarHeight = mScrollViewLocationArr[1];

        mChildCustomLinearLayout.getLocationInWindow(mChildLlLocationArr);
        mChildLlInitY = mChildLlLocationArr[1];

        mChildCustomLinearLayout.setOnTopListener(new CustomLinearLayout.OnTopListener() {
            @Override
            public void isTop(boolean state) {
                isChildTop = state;
            }
        });

    }


}
