package com.weather.udacity.android.learn.divy.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by divy on 03/02/17.
 */



public  class ForcastFragment extends Fragment {
    public ForcastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forcast_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_forcast:
                new FetchWeatherTask().execute("202001");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, days);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view_forcast);
        listView.setAdapter(adapter);
        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, Void> {
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
        private final String API_KEY = "f99fb4ce1eaaed8da10bb9be23edddb2";
        private final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
        private int numDays =  7;
        private String mode = "json";
        private String unit = "metric";


        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            String json = "";


            try {
                final String LOC_PARAM = "q";
                final String MODE_PARAM = "mode";
                final String DAYS_PARAM = "cnt";
                final String UNIT_PARAM = "units";
                final String APP_ID = "appid";

                Uri uri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(LOC_PARAM,params[0])
                        .appendQueryParameter(MODE_PARAM,mode)
                        .appendQueryParameter(DAYS_PARAM,String.valueOf(numDays))
                        .appendQueryParameter(UNIT_PARAM,unit)
                        .appendQueryParameter(APP_ID,API_KEY)
                        .build();

                URL url = new URL(uri.toString());
                Log.i(LOG_TAG, String.valueOf(url));
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                Scanner sc = new Scanner(httpURLConnection.getInputStream());
                while(sc.hasNext()) {
                    json += sc.next();
                }
                sc.close();
                Log.i(LOG_TAG, json);
                WeatherForcastParser parser = new WeatherForcastParser(json);
                parser.parse();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();

            }

            return null;
        }
    }
}

