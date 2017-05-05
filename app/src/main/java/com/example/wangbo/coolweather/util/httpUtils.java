package com.example.wangbo.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wangbo on 2017/5/5.
 */

public class httpUtils {
    public static  void sendOkHttpRequst(String address,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);

    }
}
