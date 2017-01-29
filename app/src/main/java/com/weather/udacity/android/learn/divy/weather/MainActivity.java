package com.weather.udacity.android.learn.divy.weather;

import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
                        getSupportFragmentManager().beginTransaction()
                                        .add(R.id.container, new PlaceholderFragment())
                                        .commit();
                    }
    }
    public  static class PlaceholderFragment extends Fragment{
        public PlaceholderFragment(){
        }

        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_main,container,false);
            String[] days = {"Monday","Tuesday","Wednesday","Thrusday","Friday","Saturday","Sunday"};
            ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item,days);
            ListView listView = (ListView) rootView.findViewById(R.id.list_view_forcast);
            listView.setAdapter(adapter);
            return rootView;
        }
    }
}
