package com.ray.androidlearn;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SecondActivity extends Activity implements View.OnClickListener{
    private Button start,cancel;
    private TextView textView;
    private ProgressBar progressBar;
    MyTask myTask ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        start = findViewById(R.id.btn);
        start.setOnClickListener(this);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progress_bar);
        myTask=new MyTask(textView,progressBar);
    }

    /**
     * Notification使用流程
     * 1.获取NotificationManager对象，getSystemService()
     * 2.创建Notification Builder对象，出于兼容性考虑一般使用v4包中的NotificationCompat
     * 3.设置Builder对象相关属性，
     * 4.使用Builder对象的build方法生成Notification对象，
     *   8.0之后更新了build方法，第二个参数表示优先级，可以随便写一个字符串
     * 5.调用NotificationManager对象的notify()方法通知
     */
    private void sendNotification() {
        //获取NotificationManager实例
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建NotificationChannel
        String channelId= "chat";
        String channelName = "聊天对话";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(channelId,channelName,importance);
        //创建一个Notification Builder对象，并设置相关属性
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_camera_alt_24dp)
                .setContentTitle("简单的Notification")
                .setContentText("只有小图标，标题和内容")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_phone_24dp))
                .setWhen(System.currentTimeMillis())
                //设置角标数量
                .setNumber(3)
                .setAutoCancel(true);
        //通过Builder的build对象生成Notification对象
        Notification notification=builder.build();
        //调用NotificationManager的notify()方法发送通知
        notificationManager.notify(1,notification);
    }

    //创建通知渠道,需要渠道ID，渠道名和重要等级这三个参数，ID可以随便定义，但必须全局唯一，名字显示给用户，重要等级用户可以改变
    private void createNotificationChannel(String channelId,String channelName,int importance){
        NotificationChannel channel = new NotificationChannel(channelId,channelName,importance);
        //显示app角标
        channel.setShowBadge(true);
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                myTask.execute();
                break;
            case R.id.cancel:
                myTask.cancel(true);
                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
                break;
                default:
                    sendNotification();
        }
    }
}
