package com.example.wangbo.coolweather;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangbo.coolweather.db.City;
import com.example.wangbo.coolweather.db.Province;
import com.example.wangbo.coolweather.util.Utility;
import com.example.wangbo.coolweather.util.httpUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wangbo on 2017/5/5.
 */

public class ChooseCountyFramgment extends Fragment {
    private ImageButton imageButton;
    private TextView textView;
    private ListView listView;
    private String TAG = "ChooseCountyFramgment";
    private ArrayAdapter<String> adpater;
    private ArrayList<String> dialate = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_choosecounty, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageButton = (ImageButton) view.findViewById(R.id.ibt_goback);
        textView = (TextView) view.findViewById(R.id.tv_country);
        listView = (ListView) view.findViewById(R.id.lv_city);
        adpater = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dialate);
        listView.setAdapter(adpater);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                querCity(position);
            }


        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        inteData();
    }

    private void querCity(final int id) {
        httpUtils.sendOkHttpRequst("http://guolin.tech/api/china/" + all.get(id).getProvinceCode(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

              boolean df=  Utility.handlerCityResponse(response.body().string(),all.get(id).getId());
                if (df){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        });
    }

    private List<Province> all;
    private Handler handler = new Handler() {



        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    all = DataSupport.findAll(Province.class);
                    dialate.clear();
                    for (Province p : all) {
                        dialate.add(p.getProvinceName());
                    }
                    adpater.notifyDataSetChanged();


                    break;
                }
                case 2:{
                    List<City> allCity = DataSupport.findAll(City.class);
                    dialate.clear();
                    for (City p : allCity) {
                        dialate.add(p.getCityName());
                    }
                    adpater.notifyDataSetChanged();
                    break;
                }
            }
        }
    };

    private void inteData() {
        all = DataSupport.findAll(Province.class);
        if(all.size()>0){
            for (Province p : all) {
                dialate.add(p.getProvinceName());
            }
            adpater.notifyDataSetChanged();
        }else {
            httpUtils.sendOkHttpRequst("http://guolin.tech/api/china", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    boolean b = Utility.handlerProvinceResponse(response.body().string());
                    if (b) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }
            });
        }
    }
}
