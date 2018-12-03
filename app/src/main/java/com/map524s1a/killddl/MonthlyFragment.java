package com.map524s1a.killddl;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.map524s1a.killddl.DailyFragment.*;
import static com.map524s1a.killddl.MainViewActivity.EVENTS_CHILD;

/**
 * Created by tanay on 10/15/2018.
 */

public class MonthlyFragment extends Fragment {
    //private DailyFragment.EventListAdapter adapter;
    //private FrameLayout f2;
    private RecyclerView lv;

    private boolean iSFirst = true;
    private static final String TAG = "MonthlyFrag";

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

    private List<Event> listEvents;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateEvents( final List<Event> listE){
        //sorting
        Collections.sort(listE, new Comparator<Event>() {
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
        adapter = new EventListAdapter(getApplicationContext(),listE);
        lv.setAdapter(adapter);
        lv.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder dropped) {
                int startPos = dragged.getAdapterPosition();
                int endPos = dropped.getAdapterPosition();

                Collections.swap(listE,startPos,endPos);
                adapter.notifyItemMoved(startPos,endPos);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int pos = viewHolder.getAdapterPosition();
                eventsReference.child(listE.get(pos).get_id()).removeValue();
                listE.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(lv);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fm_layout = inflater.inflate(R.layout.activity_monthly,container,false);
        lv = fm_layout.findViewById(R.id.listView);
        listEvents = new ArrayList<>();

        // select the calendar
        CalendarView calendarV = (CalendarView) fm_layout.findViewById(R.id.calendarView2); // get the reference of CalendarView
        long selectedDate = calendarV.getDate(); // get selected date in milliseconds


        calendarV.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                // get events of this day
                // dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);
                // UPDATE EVENTS
                Log.e(TAG ,"THE DAY CHANGED" );

                List<Event> dailyEvents = new ArrayList<>();
                Log.e(TAG ,"today is: " + i2 + "/" + i1 + "/" + (i));

                for(int j = 0; j < listEvents.size(); j++) {
                    Event e = listEvents.get(j);
                    Date deadline = e.get_dueDate();
                    int month = deadline.getMonth();
                    int year = deadline.getYear();
                    int day = deadline.getDate();
                    Log.e(TAG ,"event looking at: " + e.get_eventName() + " on: " + (day) + "/" + (month) + "/" + (year));

                    if ((month == i1) && (year == i) && (day+1 == i2)) {
                        dailyEvents.add(e);
                    }
                }
                updateEvents(dailyEvents);
                // Toast.makeText(getApplicationContext(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
            }
        });

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        eventsReference = mFirebaseDatabaseReference.child(EVENTS_CHILD);

        eventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) { //TODO: should only retrieve today's events
                listEvents.clear();
                Log.e(TAG ,"num events: " + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Event post = postSnapshot.getValue(Event.class);
                    Log.e(TAG, " " + post.get_eventName());
                    Date today = Calendar.getInstance().getTime();
                    Date deadline = post.get_dueDate();
                    Log.e(TAG ,"TODAY: "+ today + " POST: " + post.get_dueDate());

                    if(deadline.getMonth() == today.getMonth()) {
                        if (deadline.getYear() - 1900 == today.getYear()) {
                            listEvents.add(post);
                        }
                    }
                }
                updateEvents(listEvents);
            }

            @Override
            public void onCancelled(DatabaseError de) {
                Log.e("The read failed: " , de.getMessage());
            }
        });
        return fm_layout;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View fm_layout = inflater.inflate(R.layout.activity_monthly,container,false);
//        //lv2 = fm_layout.findViewById(R.id.list_view_monthly);
//        FragmentManager fm = getChildFragmentManager();
//        Fragment f = fm.findFragmentById(R.id.list_frag_container);
//        FragmentTransaction ft = fm.beginTransaction();
//        if(f == null){
//            f = new DailyFragment();
//            ft.add(R.id.list_frag_container,f);
//            ft.commit();
//        }
//        else {
//            f = new DailyFragment();
//            ft.replace(R.id.list_frag_container, f);
//            ft.commit();
//        }
//
//        return fm_layout;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
