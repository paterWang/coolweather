package com.example.wangbo.coolweather;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wangbo.coolweather.util.httpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wangbo on 2017/5/8.
 */

public class MyDrawableActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydrawable);
        imageView = (ImageView) findViewById(R.id.iv_mydrawable);
        lodePic();
    }

    private void lodePic() {
        String picUrl = "http://guolin.tech/api/bing_pic";
        httpUtils.sendOkHttpRequst(picUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplication(), "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bicPic =response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MyDrawableActivity.this).load(bicPic).into(imageView);
                    }
                });

            }
        });
    }
}
