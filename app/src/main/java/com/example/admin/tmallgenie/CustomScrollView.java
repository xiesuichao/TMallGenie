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

    private boolean isChildTop = true;
    private float mInterceptDownY = 0;
    private int mScrollY = -1;
    private OnScrollYChangeListener mScrollYListener;

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollYChangeListener{
        void scrollYChange(int scrollY);
    }

    public void setOnScrollYChangeListener(OnScrollYChangeListener scrollYListener){
        this.mScrollYListener = scrollYListener;
    }


    public void setCustomLinearLayout(CustomLinearLayout customLinearLayout) {
        customLinearLayout.setOnTopListener(new CustomLinearLayout.OnTopListener() {
            @Override
            public void isTop(boolean state) {
                isChildTop = state;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE && mScrollY == 0){
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
    }



}
