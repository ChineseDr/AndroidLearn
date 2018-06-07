package com.ray.providerdemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.preference.PreferenceActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("jdhsj", "run: ");
//                        try {
//                            Thread.sleep(10000);
//                            sendNotification();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                sendNotification();
                Intent intent =new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });
    }

    private void sendNotification() {
        //获取NotificationManager实例
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建一个Notification Builder对象，并设置相关属性
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("简单的Notification")
                .setContentText("只有小图标，标题和内容")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setVibrate(new long[]{0, 500, 100, 500, 100, 500})
                .setLights(0xFF00FF00, 1000, 1000);//
        //通过Builder的build对象生成Notification对象
        Notification notification=builder.build();
//        notification.ledARGB= Color.GREEN;
//        notification.ledOnMS=100;
//        notification.ledOffMS=100;
        notification.flags=Notification.FLAG_SHOW_LIGHTS;
        //调用NotificationManager的notify()方法发送通知
        notificationManager.notify(1,notification);
    }

//    private void sendNotification(){
//        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentText(" ");
//        builder.setContentTitle(" ");
//        Notification notification=builder.build();
//        notification.ledARGB= Color.RED;
//        notification.ledOnMS=1000;
//        notification.ledOffMS=1000;
//        notification.flags=Notification.FLAG_SHOW_LIGHTS;
//        manager.notify(1,notification);
//    }
}
