package com.ray.androidlearn;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
 * 此处指定为：输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
 */
public class MyTask extends AsyncTask<String, Integer, Integer> {
    private TextView textView;
    private ProgressBar progressBar;

    public MyTask(TextView textView, ProgressBar progressBar) {
        this.textView = textView;
        this.progressBar = progressBar;
    }

    //执行 线程任务前的操作,根据需求复写
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("加载中");

    }

    //接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果,必须复写，从而自定义线程任务
    @Override
    protected Integer doInBackground(String... strings) {
        // 自定义的线程任务，耗时操作
        int count=0;
        int length=1;
        try {
            while (count<100){
                count+=length;
                // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
                publishProgress(count);
                Thread.sleep(50);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    //在主线程 显示线程任务执行的进度,根据需求复写
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
        textView.setText("完成"+values[0]+"%");
    }

    //接收线程任务执行结果、将执行结果显示到UI组件,必须复写，从而自定义UI操作
    @Override
    protected void onPostExecute(Integer integer) {
        // UI操作，执行完毕后，则更新UI
        super.onPostExecute(integer);
        textView.setText("加载完成");
    }

    //将异步任务设置为：取消状态
    @Override
    protected void onCancelled() {
        super.onCancelled();
        textView.setText("已取消");
    }
}
