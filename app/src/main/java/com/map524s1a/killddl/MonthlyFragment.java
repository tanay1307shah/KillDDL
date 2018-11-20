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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fm_layout = inflater.inflate(R.layout.activity_monthly,container,false);
        lv = fm_layout.findViewById(R.id.listView);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        eventsReference = mFirebaseDatabaseReference.child(EVENTS_CHILD);

        eventsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) { //TODO: should only retrieve today's events
                List<Event> listEvents = new ArrayList<>();

                Log.e(TAG ,"num events: " + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Event post = postSnapshot.getValue(Event.class);
                    Log.e(TAG, " " + post.get_eventName());
                    Date today = Calendar.getInstance().getTime();
                    Date deadline = post.get_dueDate();
                    Log.e(TAG ,"TODAY: "+today + " POST: " + post.get_dueDate());

                    if(deadline.getMonth() == today.getMonth()) {
                        if (deadline.getYear() - 1900 == today.getYear()) {
                            listEvents.add(post);
                        }
                    }
                }

                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                lv.setLayoutManager(llm);
                adapter = new EventListAdapter(getApplicationContext(),listEvents);
                lv.setAdapter(adapter);
                lv.setHasFixedSize(true);
                adapter.notifyDataSetChanged();
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
