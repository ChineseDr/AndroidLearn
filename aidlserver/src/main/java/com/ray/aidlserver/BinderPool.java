package com.ray.aidlserver;

import android.content.Context;

import java.util.concurrent.CountDownLatch;

public class BinderPool {
    private static final String TAG = "BinderPool";
    public static final int BINDER_NONE=-1;
    public static final int BINDER_IBOOKMANAGER=0;
    public static final int BINDER_IREMOTESERVER=1;

    private Context mContext;
    private IBinderPool mBinderPool;
    private CountDownLatch mConnectionBinderCountDownLatch;
}
