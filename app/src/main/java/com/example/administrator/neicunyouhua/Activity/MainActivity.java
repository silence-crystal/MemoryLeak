package com.example.administrator.neicunyouhua.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.neicunyouhua.R;
import com.example.administrator.neicunyouhua.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btnLeak.setOnClickListener(this);
        binding.btnLeak2.setOnClickListener(this);
        binding.btnLeak3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_leak://内存泄漏实例
                LeakExampleActivity.start(this);
                break;
            case R.id.btn_leak2://内存抖动造成卡顿
                Intent intent = new Intent(this,MemoryTrembleActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_leak3://CPU使用率过高造成卡顿
                startActivity(new Intent(this,CPUBusyActivity.class));
                break;
            default:
                break;
        }

    }


}
