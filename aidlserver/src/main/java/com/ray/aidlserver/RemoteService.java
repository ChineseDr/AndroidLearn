package com.ray.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class RemoteService extends Service {
    private static final String TAG = "RemoteService";
    int mValue=-1;
    private final IRemoteServer.Stub iServer = new IRemoteServer.Stub() {
        @Override
        public int get() throws RemoteException {
            return mValue;
        }

        @Override
        public void set(int value) throws RemoteException {
            mValue=value;
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: "+intent.getAction());
        return iServer;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
