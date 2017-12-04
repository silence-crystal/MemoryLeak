package com.example.administrator.neicunyouhua.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.neicunyouhua.R;
import com.example.administrator.neicunyouhua.Utils.CUtils;

import java.lang.ref.WeakReference;

public class LeakExampleActivity extends AppCompatActivity {
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
    private static class MyHandler extends Handler {
        //        private MainActivity activity;//直接持有外部类的强引用会导致内存泄漏
        private WeakReference<LeakExampleActivity> activity;//设置软引用保存，当GC的时候就会回收
        private MyHandler(LeakExampleActivity activity){
            this.activity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LeakExampleActivity leakExampleActivity = activity.get();//获取弱引用中的对象
            if (leakExampleActivity==null || leakExampleActivity.isFinishing()){
                return;
            }
            switch (msg.what){
                case 0:
                    //有时候确实会需要引用外部类的资源，在里面缺没办法引用到，所以使用
                    int b = leakExampleActivity.a;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_example);

        // TODO: 2017/11/30 在工具类里保存activity对象引起的内存泄漏  尽量在不会马上结束的单例工具类中保存全局的上下文
        CUtils.getInstance(this);//泄漏
//        CUtils.getInstance(getApplicationContext());//正解
        
        // TODO: 2017/11/30 两种添加监听的方式，一种是set监听使用完自动回收，一种是add监听，使用完监听必须回收
//        final TextView tv = (TextView) findViewById(R.id.tv);
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
        
        // TODO: 2017/11/30 传感器容易造成的内存泄漏
        //调用传感器的伪代码
//        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
//        //注册监听
//        sensorManager.registerListener((SensorEventListener) this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
//        //不需要用的时候一定要移除监听
//        sensorManager.unregisterListener((SensorEventListener) this);

        loadData();
    }

    private void loadData(){
        // TODO: 2017/12/1 匿名内部类会隐式持有外部类的对象，会造成内存泄露 可以在方法上加入static修饰或者不使用匿名内部类，如果使用内部类可以使用软引用
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
        MyHandler handler = new LeakExampleActivity.MyHandler(this);
        handler.sendEmptyMessage(0);
    }

    public static void start(Context context){
        Intent intent = new Intent(context,LeakExampleActivity.class);
        context.startActivity(intent);
    }
}