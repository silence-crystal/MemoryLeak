package com.example.administrator.neicunyouhua.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.administrator.neicunyouhua.R;
import com.example.administrator.neicunyouhua.databinding.ActivityCpubusyBinding;

public class CPUBusyActivity extends AppCompatActivity {
    private ActivityCpubusyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cpubusy);

        binding.cpuLeakStart.setText("计算斐波那契数列");
        binding.cpuLeakStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computeFibonacci(40);
            }
        });

        binding.webview.getSettings().setUseWideViewPort(true);
        binding.webview.getSettings().setLoadWithOverviewMode(true);
        binding.webview.loadUrl("file:///android_asset/shiver_me_timbers.gif");

    }

    //优化前的递归造成cpu使用率过高
//    public int computeFibonacci(int positionInFibSequence) {
//        //0 1 1 2 3 5 8
//        if (positionInFibSequence <= 2) {
//            return 1;
//        } else {
//            return computeFibonacci(positionInFibSequence - 1)
//                    + computeFibonacci(positionInFibSequence - 2);
//        }
//    }

    //优化后的斐波那契数列的非递归算法 caching缓存+批处理思想
    public int computeFibonacci(int positionInFibSequence) {
        int prev = 0;
        int current = 1;
        int newValue;
        for (int i=1; i<positionInFibSequence; i++) {
            newValue = current + prev;
            prev = current;
            current = newValue;
        }
        return current;
    }
}
