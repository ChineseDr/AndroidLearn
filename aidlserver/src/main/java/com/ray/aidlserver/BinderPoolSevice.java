package com.ray.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BinderPoolSevice extends Service {
    private static final String TAG = "BinderPoolSevice";
//    private BinderPool mBinderPool=new BinderPool
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
