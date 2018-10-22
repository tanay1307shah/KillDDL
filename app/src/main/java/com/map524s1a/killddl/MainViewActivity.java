package com.map524s1a.killddl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainViewActivity extends AppCompatActivity {
    // Firebase instance variables
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    private TextView mTextMessage;
    private Switch s;
    private FloatingActionButton addBtn;

    private int month;
    private int date;
    private int year;
    private String notify;
    private String eventNameString;
    private String descripString;

    private String[] dateArr;
    private String[] monthArr;
    private String[] yearArr;
    private String[] notifyArr;

    private User user;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment f = fm.findFragmentById(R.id.fragment_container);
            FragmentTransaction ft = fm.beginTransaction();
            switch (item.getItemId()) {

                case R.id.list:
                    s.setVisibility(View.VISIBLE);
                    if(f == null){
                        f = new DailyFragment();
                        ft.add(R.id.fragment_container,f);
                        ft.commit();
                    }
                    else {
                        f = new DailyFragment();
                        ft.replace(R.id.fragment_container, f);
                        ft.commit();
                    }
                    return true;
                case R.id.notify:
                    return true;
                case R.id.profile:
                    s.setVisibility(View.INVISIBLE);
                    if(f == null){
                        f = new ProfileFragment();
                        ft.add(R.id.fragment_container,f);
                        ft.commit();
                    }
                    else {
                        f = new ProfileFragment();
                        ft.replace(R.id.fragment_container, f);
                        ft.commit();
                    }
                    return true;
                case R.id.deadline:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Intent I = getIntent();
        user = (User) I.getSerializableExtra("user");

        s = findViewById(R.id.switch1);
        dateArr = getResources().getStringArray(R.array.dateArr);
        monthArr = getResources().getStringArray(R.array.monthArr);
        yearArr = getResources().getStringArray(R.array.yearArr);
        notifyArr = getResources().getStringArray(R.array.notArrify);

        




        mTextMessage = (TextView) findViewById(R.id.message);
        addBtn = findViewById(R.id.floatingActionButton);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);
        FragmentTransaction ft = fm.beginTransaction();
        if(f == null){
            f = new MonthlyFragment();
            ft.add(R.id.fragment_container,f);
            ft.commit();
        }
        else {
            f = new MonthlyFragment();
            ft.replace(R.id.fragment_container, f);
            ft.commit();
        }


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainViewActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_event_add,null);
                Button addB = mView.findViewById(R.id.add_event);
                Spinner dateSpin = mView.findViewById(R.id.spinner_date);
                Spinner monthSpin = mView.findViewById(R.id.spinner_month);
                Spinner yearSpin = mView.findViewById(R.id.spinner_year);
                Spinner notifySpin = mView.findViewById(R.id.spinner_notify);
                ImageButton colorPick = mView.findViewById(R.id.color_picker);
                final TextView eventName = mView.findViewById(R.id.topic_val);
                final TextView description = mView.findViewById(R.id.des_val);

                dateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        date = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                monthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        month = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                yearSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        year = Integer.parseInt(yearArr[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                notifySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        notify = notifyArr[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                colorPick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                addB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(getApplicationContext(),"New com.map524s1a.killddl.Event Added!", Toast.LENGTH_SHORT).show();
                        // do event
                        eventNameString = eventName.getText().toString();
                        descripString = description.getText().toString();
                        Date d = new Date(year,month,date);
//                        addEventThread aet = new addEventThread(1,
//                                eventNameString,
//                                "",
//                                descripString,
//                                d,
//                                1,
//                                1,
//                                new Date(),
//                                new addEventResponse() {
//                                    @Override
//                                    public void addEventCallback(boolean eventAdded, Event e) {
//                                        if(eventAdded){
//                                            EventSingleton.get(getApplicationContext()).addEventSingleton(e);
//                                            Toast.makeText(getApplicationContext(),"Event Added!", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                }
//                        );
//                        aet.start();
                        Event e = new Event(eventNameString,descripString,d,new Date(),1,1,1,"");
                        EventSingleton.get(getApplicationContext()).addEventSingleton(e);
                        Toast.makeText(getApplicationContext(),"Event Added!", Toast.LENGTH_SHORT).show();


                    }

                });
                builder.setView(mView);
                AlertDialog dialog =  builder.create();
                dialog.show();
            }

        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
