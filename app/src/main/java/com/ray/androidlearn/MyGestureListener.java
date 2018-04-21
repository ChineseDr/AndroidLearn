package com.ray.androidlearn;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;


public class MyGestureListener implements GestureDetector.OnGestureListener {
    private static final String TAG="MyGestureListener";
    private static final int MIN_MOVE=200;
    private Context mContext;
    TextView textView;

    public MyGestureListener(Context context,TextView textView) {
        this.mContext = context;
        this.textView = textView;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(TAG,"onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(TAG,"onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(TAG,"onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG,"onScroll");
        if (motionEvent.getX()-motionEvent1.getX()>MIN_MOVE){
            textView.setText(" "+(motionEvent.getX()-motionEvent1.getX()));
            Toast.makeText(mContext,"使用手势打开",Toast.LENGTH_SHORT).show();

        }else if (motionEvent.getX()-motionEvent1.getX()<MIN_MOVE){
            textView.setText(" "+(motionEvent.getX()-motionEvent1.getX()));
            Toast.makeText(mContext,"使用手势关闭",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(TAG,"onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG,"onFling");
        if (motionEvent.getY()-motionEvent1.getY()>MIN_MOVE) {
            textView.setText(" "+(motionEvent.getY()-motionEvent1.getY()));
            Toast.makeText(mContext,"通过手势打开",Toast.LENGTH_SHORT).show();
        }else if (motionEvent.getY()-motionEvent1.getY()<MIN_MOVE){
            textView.setText(" "+(motionEvent.getY()-motionEvent1.getY()));
            Toast.makeText(mContext,"通过手势关闭",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
