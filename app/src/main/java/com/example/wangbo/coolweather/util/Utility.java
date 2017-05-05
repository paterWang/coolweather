package com.example.wangbo.coolweather.util;

import android.text.TextUtils;

import com.example.wangbo.coolweather.db.City;
import com.example.wangbo.coolweather.db.County;
import com.example.wangbo.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangbo on 2017/5/5.
 */

public class Utility {

    /**
     * 获取省信息
     *
     * @param response
     * @return
     */
    public static boolean handlerProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceCode(jsonObject.getInt("id"));
                    province.setProvinceName(jsonObject.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取县信息
     *
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handlerCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    County county = new County();
                    county.setCityId(cityId);
                    county.setWeatherId(jsonObject.getString("weather_id"));
                    county.setCountyName(jsonObject.getString("name"));
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 获取市信息
     *
     * @return
     */
    public static boolean handlerCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0;i<jsonArray.length();i++ ){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setCityName(jsonObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                    return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
