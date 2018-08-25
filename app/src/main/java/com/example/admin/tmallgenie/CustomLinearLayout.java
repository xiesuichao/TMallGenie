package com.example.admin.tmallgenie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xiesuichao on 2018/8/24.
 */

public class CustomLinearLayout extends LinearLayout implements View.OnTouchListener {

    private CustomLinearLayout thisView;
    private GestureDetector gestureDetector;
    private OnTopListener mTopListener;
    private int parentScrollY = 0;

    public CustomLinearLayout(Context context) {
        super(context);
        init();
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public interface OnTopListener{
        void isTop(boolean state);
    }

    public void setOnTopListener(OnTopListener topListener){
        this.mTopListener = topListener;
    }

    public void setCustomScrollView(CustomScrollView scrollView){
        scrollView.setOnScrollYChangeListener(new CustomScrollView.OnScrollYChangeListener() {
            @Override
            public void scrollYChange(int scrollY) {
                parentScrollY = scrollY;
            }
        });
    }

    private void init() {
        thisView = this;
        super.setOnTouchListener(this);
        super.setClickable(true);
        super.setLongClickable(true);
        super.setFocusable(true);
        gestureDetector = new GestureDetector(getContext(), new CustomGestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (parentScrollY == 0){
            return gestureDetector.onTouchEvent(event);
        }else {
            return false;
        }
    }

    private class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            thisView.scrollBy(0, (int) distanceY);
            if (getScrollY() >= 0){
                thisView.setScrollY(0);
                mTopListener.isTop(true);
            }else {
                mTopListener.isTop(false);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            return true;
        }
    }



}
