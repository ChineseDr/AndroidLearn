package com.ray.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ray.aidlserver.IRemoteServer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    //1.
    private IRemoteServer iServier=null;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: "+name.toString());
            //远程服务对象
            iServier = IRemoteServer.Stub.asInterface(service);
            if (iServier==null){
                Log.d(TAG, "onServiceConnected: failed");
            }else {
                Log.d(TAG, "onServiceConnected: succeed");
                try {
                    iServier.set(20);
                    textView.setText(iServier.get()+" ");
                    Log.d(TAG, "onServiceConnected: "+iServier.get());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iServier = null;
        }
    };

    private Button bindService,unBindService;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //启动远程Service
        //Intent intent=new Intent(this,IRemoteServer.class);
//        Intent intent=new Intent("com.ray.aidlserver.IRemoteServer");
        //intent.setClassName("com.ray.aidlserver","com.ray.aidlserver.IRemoteServer");
        //intent.setAction(IRemoteServer.class.getName());

        //Log.d(TAG, MainActivity.this.getPackageName()+"onClick: "+IRemoteServer.class.getName());
        ///bindService(intent,connection, Context.BIND_AUTO_CREATE);
//        iServier.set(20);这样调用为空是因为onServiceConnected在绑定成功时进行回调，
// 但不保证在执行bindService后立马回调，我们在绑定后立马获取service实例，
// 但此时不保证onServiceConnected已经被回调。 也就是onServiceConnected还没有被调用。
// 此时当然iServier还为空了。所以使用iServier引用代码放到可以确保onServiceConnected执行之后
    }

    private void init(){
        bindService=findViewById(R.id.button);
        bindService.setOnClickListener(this);
        unBindService=findViewById(R.id.button2);
        unBindService.setOnClickListener(this);
        textView=findViewById(R.id.textView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                //启动远程Service
                //Intent intent=new Intent(MainActivity.this,IRemoteServer.class);
                //intent.setAction(IRemoteServer.class.getName());
                Intent intent=new Intent();
                intent.setAction("com.ray.aidlserver.IRemoteServer");
                intent.setPackage("com.ray.aidlserver");

                Log.d(TAG, "onClick: "+IRemoteServer.class.getName());
                bindService(intent,connection, Context.BIND_AUTO_CREATE);
//                try {
//                    iServier.set(20);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
                break;
            //停止Service
            case R.id.button2:
                try {
                    iServier.set(60);
                    textView.setText(iServier.get()+"");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                unbindService(connection);
            break;

        }

    }
}
