package com.example.admin.tmallgenie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by xiesuichao on 2018/8/24.
 */

public class CustomLinearLayout extends LinearLayout {

    private OnTopListener mTopListener;
    private OnBottomListener mBottomListener;
    private int parentScrollY = 0;
    private Scroller mScroller;
    private int mWindowHeight;
    private int[] mLayoutLocationArr;
    private final int BOTTOM_HEIGHT = 200;
    private float mLastMoveY;

    public CustomLinearLayout(Context context) {
        this(context, null);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public interface OnTopListener {
        void isTop(boolean state);
    }

    public void setOnTopListener(OnTopListener topListener) {
        this.mTopListener = topListener;
    }

    public interface OnBottomListener{
        void isBottom();
    }

    public void setOnBottomListener(OnBottomListener bottomListener){
        this.mBottomListener = bottomListener;
    }

    public void setCustomScrollView(CustomScrollView scrollView) {
        scrollView.setOnScrollYChangeListener(new CustomScrollView.OnScrollYChangeListener() {
            @Override
            public void scrollYChange(int scrollY) {
                parentScrollY = scrollY;
            }
        });
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mLayoutLocationArr = new int[2];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (parentScrollY != 0) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMoveY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float scrollY = mLastMoveY - moveY;
                scrollBy(0, (int) scrollY);
                if (getScrollY() >= 0) {
                    setScrollY(0);
                    mTopListener.isTop(true);
                } else {
                    mTopListener.isTop(false);
                }

                mLastMoveY = moveY;
                break;

            case MotionEvent.ACTION_UP:
                if (getScrollY() <= -(mWindowHeight - mLayoutLocationArr[1] - BOTTOM_HEIGHT)) {
                    mBottomListener.isBottom();
                }else {
                    mScroller.startScroll(0, getScrollY(), 0, 0 - getScrollY(), 500);
                    invalidate();
                }
                break;
        }
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mWindowHeight = dm.heightPixels;
        getLocationInWindow(mLayoutLocationArr);
    }


}
