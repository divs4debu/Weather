package com.weather.udacity.android.learn.divy.weather;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by divy on 05/02/17.
 */

public class WeatherForcastParser {
    private final String LOG_TAG = WeatherForcastParser.class.getSimpleName();
    private String jsonString;
    public  WeatherForcastParser(){}
    public WeatherForcastParser(String jsonString){
        this.jsonString = jsonString;
    }

    public List<HashMap<String, Double>> parse() throws JSONException {
        JSONObject forcast = new JSONObject(jsonString);
        String forcastArray[] = new String[7];
        List<HashMap<String, Double> > weather = new ArrayList<>();

        for(int i=0; i<7;i++) {
            JSONObject temp = new JSONObject(forcast.getJSONArray("list").getString(i));
            forcastArray[i] = temp.getJSONObject("temp").toString();
            temp = new JSONObject(forcastArray[i]);
            HashMap<String, Double> day_weather = new HashMap<>();
            Iterator<String> it = temp.keys();
            while(it.hasNext()){
                String s = it.next();
                day_weather.put(s, Double.parseDouble(temp.getString(s)));
            }
            weather.add(day_weather);
        }
        return weather;
    }
}
