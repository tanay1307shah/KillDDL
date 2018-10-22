package com.map524s1a.killddl;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by tanay on 10/15/2018.
 */

public class DailyFragment extends Fragment {

    private ListView lv;

    private boolean iSFirst = true;

    private Button delbtn;
    private Button detailsBtn;
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

            TextView titleV = convertView.findViewById(R.id.evetitle);
            TextView timeV = convertView.findViewById(R.id.timeval);
            Button statusBtn = convertView.findViewById(R.id.statusBtn);
            detailsBtn = convertView.findViewById(R.id.detailsBtn);
            delbtn = convertView.findViewById(R.id.deleteBtn);

            detailsBtn.setVisibility(View.INVISIBLE);
            delbtn.setVisibility(View.INVISIBLE);
            final Event e = getItem(position);

            titleV.setText(e.get_eventName());
            titleV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailsBtn.getVisibility() == View.INVISIBLE && delbtn.getVisibility() == View.INVISIBLE) {
                        detailsBtn.setVisibility(View.VISIBLE);
                        delbtn.setVisibility(View.VISIBLE);
                    }
                    else{
                        detailsBtn.setVisibility(View.INVISIBLE);
                        delbtn.setVisibility(View.INVISIBLE);
                    }
                }
            });

                detailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"lol", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(DailyFragment.this.getContext());
                        View mView = getLayoutInflater().inflate(R.layout.activity_event_view, null);
                        builder.setView(mView);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

            return convertView;
        }

    }
}
