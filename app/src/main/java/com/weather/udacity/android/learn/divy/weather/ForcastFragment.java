package com.weather.udacity.android.learn.divy.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by divy on 03/02/17.
 */



public  class ForcastFragment extends Fragment {
    public ForcastFragment(){
    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        String[] days = {"Monday","Tuesday","Wednesday","Thrusday","Friday","Saturday","Sunday"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item,days);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view_forcast);
        listView.setAdapter(adapter);
        new FetchWeatherTask().execute();
        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<Void,Void,Void>{
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
        private  final String API_KEY = "f99fb4ce1eaaed8da10bb9be23edddb2";
        private final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Aligarh,In&mode=json&units=metric&cnt=7";
        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection httpURLConnection = null;
            String json;

            try {
                URL url = new URL(BASE_URL.concat("&appid=").concat(API_KEY));
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                Scanner sc = new Scanner(httpURLConnection.getInputStream());
                json = sc.next();
                sc.close();
                Log.i(LOG_TAG, json);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(httpURLConnection != null)
                    httpURLConnection.disconnect();

            }

            return null;
        }
    }
}