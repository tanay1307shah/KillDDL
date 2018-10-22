package com.map524s1a.killddl;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by tanay on 10/15/2018.
 */

public class DailyFragment extends Fragment {

    private ListView lv;

    private boolean iSFirst = true;

    private EventListAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fm_layout = inflater.inflate(R.layout.activity_daily,container,false);
        lv = fm_layout.findViewById(R.id.listView);

        if(iSFirst){
//            Event e = new Event("Kill the app", "", new Date(), new Date(), 1,1,1,"");
//            EventSingleton.get(getApplicationContext()).addEventSingleton(e);

            List<Event> events = EventSingleton.get(getApplicationContext()).getEvents();
            adapter = new EventListAdapter(getApplicationContext(), 0, events);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            iSFirst = false;
        }


        return fm_layout;
    }

    public void referesh(EventListAdapter adapter){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public class  EventListAdapter extends ArrayAdapter<Event> {
        public EventListAdapter(Context context, int resource, List<Event> objects){
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item,null);
            }
            return convertView;
        }

    }
}
