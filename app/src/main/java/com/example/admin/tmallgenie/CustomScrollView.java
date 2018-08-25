package com.example.admin.tmallgenie;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by xiesuichao on 2018/8/24.
 */

public class CustomScrollView extends ScrollView {

//    private CustomLinearLayout mCustomLinearLayout;
    private boolean isChildTop = true;
    private float mInterceptDownY = 0;
    private GestureDetector gestureDetector;
    private MotionEvent mMotionEvent;
    private boolean isScrollable = false;
    private int mScrollY = -1;
    private float mTouchDownY;
    private boolean isFirstScrollUp = true;
    private OnScrollYChangeListener mScrollYListener;

    public CustomScrollView(Context context) {
        super(context);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public interface OnScrollYChangeListener{
        void scrollYChange(int scrollY);
    }

    public void setOnScrollYChangeListener(OnScrollYChangeListener scrollYListener){
        this.mScrollYListener = scrollYListener;
    }

    private void init() {
        super.setClickable(true);
        super.setLongClickable(true);
        super.setFocusable(true);
        gestureDetector = new GestureDetector(getContext(), new CustomGestureListener());
    }

    public void setCustomLinearLayout(CustomLinearLayout customLinearLayout) {
//        this.mCustomLinearLayout = layout;
        customLinearLayout.setOnTopListener(new CustomLinearLayout.OnTopListener() {
            @Override
            public void isTop(boolean state) {
                isChildTop = state;
//                PrintUtil.log("isChildTop", isChildTop);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownY = ev.getY();
//                PrintUtil.log("mTouchDownY", mTouchDownY);
                break;

            case MotionEvent.ACTION_MOVE:
                PrintUtil.log("getScrollY", getScrollY());
                if (mScrollY == 0 ) {

                    onInterceptTouchEvent(ev);
//                    return false;
                }
                break;

        }

        return super.onTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        PrintUtil.log("ScrollView onInterceptTouchEvent");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mInterceptDownY = ev.getY();
//            PrintUtil.log("mInterceptDownY", mInterceptDownY);

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float interceptMoveY = ev.getY();
            PrintUtil.log("----------");
            PrintUtil.log("isChildTop", isChildTop);
            PrintUtil.log("interceptMoveY - mInterceptDownY", interceptMoveY - mInterceptDownY);
            PrintUtil.log("mScrollY", mScrollY);
            if (!isChildTop) {
                PrintUtil.log("return child false");
                return false;

            } else if (interceptMoveY - mInterceptDownY > 0 && mScrollY <= 10) {
                scrollTo(0, 0);
                PrintUtil.log("return this false");
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
    }

    private class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return true;
        }
    }

}
