package com.ray.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class Velocity extends View implements GestureDetector.OnGestureListener{
    public Velocity(Context context) {
        super(context);
    }

    public Velocity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Velocity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        //计算速度：单位时间内移动距离
        //参数即为单位时间，此方法可理解为获取速度的单位（dp/10ms,dp/100ms）
        velocityTracker.computeCurrentVelocity(100);
        int xVelocity = (int) velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();
        //清除回收
        velocityTracker.clear();
        velocityTracker.recycle();


        GestureDetector gestureDetector=new GestureDetector(this);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
