package com.example.administrator.neicunyouhua.Utils;

import android.content.Context;

/**
 * Created by cuizhize on 2017/10/25.
 */

public class CUtils {
    private static CUtils instance;
    private Context context;
    private CUtils(Context context){
        this.context = context;
    }

    public static CUtils getInstance(Context context){
        if (instance == null){
            instance = new CUtils(context);
        }
        return instance;
    }
}
