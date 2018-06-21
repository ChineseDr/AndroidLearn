package com.ray.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

public class Velocity extends View implements GestureDetector.OnGestureListener{
    Context mContext;
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


    /**
     * 手指接触屏幕的一瞬间
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * 比一瞬之间时间长
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 轻触之后离开，单击
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 一个down，若干个move，拖动
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * 长按
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }


    Scroller scroller=new Scroller(mContext);
    /**
     * 一个down，若干move，一个up，快速滑动
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    private void smoothScrollTo(int destX,int destY){
        int scrollX=getScrollX();
        int delta=destX-scrollX;
        //1000毫秒内滑向destX
        scroller.startScroll(scrollX,0,delta,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }
}
