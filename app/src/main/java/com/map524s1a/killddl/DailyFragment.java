package com.map524s1a.killddl;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.TimePicker;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.map524s1a.killddl.MainViewActivity.EVENTS_CHILD;

/**
 * Created by tanay on 10/15/2018.
 */

public class DailyFragment extends Fragment {

    private RecyclerView lv;

    private boolean iSFirst = true;
    private static final String TAG = "DailyFragment";

    private Button delbtn;
    private String eventNameString;
    private String descripString;
    //private Button
    private EditText timeVal;
    private Button detailsBtn;
    private Button shareBtn;
    private EventListAdapter adapter;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference eventsReference;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public EventListAdapter getAdapter(){
        return adapter;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fm_layout = inflater.inflate(R.layout.activity_daily,container,false);
        lv = fm_layout.findViewById(R.id.listView);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        eventsReference = mFirebaseDatabaseReference.child(EVENTS_CHILD);

        eventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) { //TODO: should only retrieve today's events
                final List<Event> listEvents = new ArrayList<>();

                Log.e(TAG ,"num events: "+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Event post = postSnapshot.getValue(Event.class);
                    Log.e(TAG, " " + post.get_eventName());
                    Date today = Calendar.getInstance().getTime();
                    Date deadline = post.get_dueDate();
                    Log.e(TAG ,"TODAY: " + today + " POST: " + post.get_dueDate());

                    if(deadline.getDay()==today.getDay()){
                        if(deadline.getMonth() == today.getMonth()){
                            if(deadline.getYear()-1900 == today.getYear()){
                                listEvents.add(post);
                            }
                        }
                    }
                }
                //sorting
                Collections.sort(listEvents, new Comparator<Event>() {
                    @Override
                    public int compare(Event a, Event b) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        // a is after b
                        if (a.get_dueDate().after(b.get_dueDate())) {
                            Log.e(TAG ,"Event: " + a.get_eventName() + " is after: " + b.get_eventName());

                            return 1;
                        } else{
                            Log.e(TAG ,"Event: " + a.get_eventName() + " is before: " + b.get_eventName());

                            return -1;
                        }
                    }
                });

                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                lv.setLayoutManager(llm);
                adapter = new EventListAdapter(getApplicationContext(),listEvents);
                lv.setAdapter(adapter);
                lv.setHasFixedSize(true);
                adapter.notifyDataSetChanged();


                ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder dropped) {
                        int startPos = dragged.getAdapterPosition();
                        int endPos = dropped.getAdapterPosition();

                        Collections.swap(listEvents,startPos,endPos);
                        adapter.notifyItemMoved(startPos,endPos);
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        int pos = viewHolder.getAdapterPosition();
                        eventsReference.child(listEvents.get(pos).get_id()).removeValue();
                        listEvents.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                });
                helper.attachToRecyclerView(lv);


            }

            @Override
            public void onCancelled(DatabaseError de) {
                Log.e("The read failed: " , de.getMessage());
            }
        });
        return fm_layout;
    }

    public void referesh(EventListAdapter adapter){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
