package com.example.administrator.neicunyouhua;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private int a = 10;
    //handler是匿名内部类的实例，会引用外部对象
    //错误的示范
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 0:
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
    //解决方法
    private static class MyHandler extends Handler{
//        private MainActivity activity;//直接持有外部类的强引用会导致内存泄漏
        private WeakReference<MainActivity> activity;//设置软引用保存，当GC的时候就会回收
        private MyHandler(MainActivity activity){
            this.activity = new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = activity.get();//获取弱引用中的对象
            if (mainActivity==null || mainActivity.isFinishing()){
                return;
            }
            switch (msg.what){
                case 0:
                    //有时候确实会需要引用外部类的资源，在里面缺没办法引用到，所以使用
                    int b = mainActivity.a;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CUtils.getInstance(this);//泄漏
//        CUtils.getInstance(getApplicationContext());//正解
        loadData();
        final TextView tv = (TextView) findViewById(R.id.tv);
//        tv.setOnClickListener();//监听执行完自动回收对象，不会继续持有外部类的引用从而导致泄漏
        //add监听是放到集合里面
//        tv.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
//            @Override
//            public void onWindowFocusChanged(boolean b) {
//                //做该做的操作
//
//                //做完后，一定要移除监听
//                tv.getViewTreeObserver().removeOnWindowFocusChangeListener(this);
//            }
//        });
        //调用传感器的伪代码
//        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
//        //注册监听
//        sensorManager.registerListener((SensorEventListener) this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
//        //不需要用的时候一定要移除监听
//        sensorManager.unregisterListener((SensorEventListener) this);
    }

    private void loadData(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    try {
//                        int b = a;//匿名内部类可以调用外部类的变量，说明他隐式持有外部类的对象 MainActivity.this.a
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

        //错误的示范
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {//不管是在timer的延迟时间中还是在线程的sleep时间中，线程都是属于运行状态下，如果是非静态类则会在运行状态下持有外部类的引用，所以会导致内存泄漏
//                while (true){
//                    try {
////                        int b = a;
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        },2000);
        //在Activity onDestroy时把timer.cancel掉然后赋空
        MyHandler handler = new MyHandler(this);
        handler.sendEmptyMessage(0);
    }


}
