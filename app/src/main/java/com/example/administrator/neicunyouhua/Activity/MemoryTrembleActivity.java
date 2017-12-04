package com.example.administrator.neicunyouhua.Activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.neicunyouhua.R;
import com.example.administrator.neicunyouhua.databinding.ActivityMemoryTrembleBinding;

import java.util.Arrays;
import java.util.Random;

public class MemoryTrembleActivity extends AppCompatActivity {
    private ActivityMemoryTrembleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory_tremble);

        binding.trembleStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imPrettySureSortingIsFree();
            }
        });

        binding.webview.getSettings().setUseWideViewPort(true);
        binding.webview.getSettings().setLoadWithOverviewMode(true);
        binding.webview.loadUrl("file:///android_asset/shiver_me_timbers.gif");
    }

    /**
     *　排序后打印二维数组，一行行打印
     */
    public void imPrettySureSortingIsFree() {
        int dimension = 300;
        int[][] lotsOfInts = new int[dimension][dimension];
        Random randomGenerator = new Random();
        for(int i = 0; i < lotsOfInts.length; i++) {
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                lotsOfInts[i][j] = randomGenerator.nextInt();
            }
        }

//        for(int i = 0; i < lotsOfInts.length; i++) {
//            String rowAsStr = "";
//            //排序
//            int[] sorted = getSorted(lotsOfInts[i]);
//            //拼接打印
//            for (int j = 0; j < lotsOfInts[i].length; j++) {
//                rowAsStr += sorted[j];
//                if(j < (lotsOfInts[i].length - 1)){
//                    rowAsStr += ", ";
//                }
//            }
//            Log.i("ricky", "Row " + i + ": " + rowAsStr);
//        }

        //优化以后
        StringBuilder sb = new StringBuilder();
        String rowAsStr = "";
        for(int i = 0; i < lotsOfInts.length; i++) {
            //清除上一行
            sb.delete(0,rowAsStr.length());
            //排序
            int[] sorted = getSorted(lotsOfInts[i]);
            //拼接打印
            for (int j = 0; j < lotsOfInts[i].length; j++) {
                sb.append(sorted[j]);
                if(j < (lotsOfInts[i].length - 1)){
                    sb.append(", ");
                }
            }
            rowAsStr = sb.toString();
            Log.i("ricky", "Row " + i + ": " + rowAsStr);
        }


    }

    public int[] getSorted(int[] input){
        int[] clone = input.clone();
        Arrays.sort(clone);
        return clone;
    }
}
