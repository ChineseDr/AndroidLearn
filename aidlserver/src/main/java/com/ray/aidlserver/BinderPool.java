package com.ray.aidlserver;

import android.content.Context;
import android.content.Intent;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private static final String TAG = "BinderPool";
    public static final int BINDER_NONE=-1;
    public static final int BINDER_IBOOKMANAGER=0;
    public static final int BINDER_IREMOTESERVER=1;

    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sBinderPool;
    private CountDownLatch mConnectBinderCountDownLatch;

    private BinderPool(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static BinderPool getInsatance(Context context){
        if (sBinderPool==null){
            synchronized (BinderPool.class){
                if (sBinderPool==null){
                    return new BinderPool(context);
                }
            }
        }
        return sBinderPool;
    }

    private synchronized void connectBinderpoolService(){
        mConnectBinderCountDownLatch=new CountDownLatch(1);
        //Intent service =new
    }


}
