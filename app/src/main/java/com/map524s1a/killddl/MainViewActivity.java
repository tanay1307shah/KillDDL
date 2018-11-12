package com.map524s1a.killddl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainViewActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener  {
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private RecyclerView mMessageRecyclerView;
    private static final String TAG = "MainViewActivity";


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        ImageView messageImageView;
        TextView messengerTextView;
        CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
//            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
//            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
        }
    }
    private FirebaseRecyclerAdapter<Event, MessageViewHolder> // todo note: instead of friendly message
            mFirebaseAdapter;


    private String mUsername;
    private String mPhotoUrl;

    private TextView mTextMessage;
    private Switch s;
    private FloatingActionButton addBtn;
    private TextView monthlyTag;
    private TextView dailyTag;
    private TextView timeVal;


    private Button detailsBtn;
    private Button delbtn;


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

    public static final String EVENTS_CHILD = "events";
    private LinearLayoutManager mLinearLayoutManager;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;


    private int hour;
    private int min;
    private String time;
    private User user;
    private DailyFragment.EventListAdapter adapter;

    // toggling month and day
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment f = fm.findFragmentById(R.id.fragment_container);
            FragmentTransaction ft = fm.beginTransaction();
            switch (item.getItemId()) {

                case R.id.list:
                    s.setVisibility(View.VISIBLE);
                    dailyTag.setVisibility(View.VISIBLE);
                    monthlyTag.setVisibility(View.VISIBLE);
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
                    dailyTag.setVisibility(View.INVISIBLE);
                    monthlyTag.setVisibility(View.INVISIBLE);
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
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = "anon";

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
//        Intent I = getIntent();
//        user = (User) I.getSerializableExtra("user");

        mMessageRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        // New child entries
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        SnapshotParser<Event> parser = new SnapshotParser<Event>() {
            @Override
            public Event parseSnapshot(DataSnapshot dataSnapshot) {
                Event userEvent = dataSnapshot.getValue(Event.class);
                if (userEvent != null) {
                    userEvent.set_id(dataSnapshot.getKey());
                }
                return userEvent;
            }
        };

        DatabaseReference eventsReference = mFirebaseDatabaseReference.child(EVENTS_CHILD);
        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(eventsReference, parser)
                        .build();

//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Event, MessageViewHolder>(options) {
//            @Override
//            public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
//                return new MessageViewHolder(inflater.inflate(R.layout.item_message, viewGroup, false));
//            }
//
//            @Override
//            protected void onBindViewHolder(final MessageViewHolder viewHolder,
//                                            int position,
//                                            Event friendlyMessage) {
//                if (friendlyMessage.get_eventName() != null) {
//                    viewHolder.messageTextView.setText(friendlyMessage.get_eventName());
//                    viewHolder.messageTextView.setVisibility(TextView.VISIBLE);
//                    viewHolder.messageImageView.setVisibility(ImageView.GONE);
//                }
//            }
//        };

//        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
//                int lastVisiblePosition =
//                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
//                // If the recycler view is initially being loaded or the
//                // user is at the bottom of the list, scroll to the bottom
//                // of the list to show the newly added message.
//                if (lastVisiblePosition == -1 ||
//                        (positionStart >= (friendlyMessageCount - 1) &&
//                                lastVisiblePosition == (positionStart - 1))) {
//                    mMessageRecyclerView.scrollToPosition(positionStart);
//                }
//            }
//        });
//
//        mMessageRecyclerView.setAdapter(mFirebaseAdapter);


        s = findViewById(R.id.switch1);
        dateArr = getResources().getStringArray(R.array.dateArr);
        monthArr = getResources().getStringArray(R.array.monthArr);
        yearArr = getResources().getStringArray(R.array.yearArr);
        notifyArr = getResources().getStringArray(R.array.notArrify);
        monthlyTag = findViewById(R.id.monthlyTag);
        dailyTag = findViewById(R.id.dailyTag);



        dailyTag.setTextColor(getResources().getColor(R.color.black));
        monthlyTag.setTextColor(getResources().getColor(R.color.white));

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // Daily View

                    dailyTag.setTextColor(getResources().getColor(R.color.white));
                    monthlyTag.setTextColor(getResources().getColor(R.color.black));

                    FragmentManager fm = getSupportFragmentManager();
                    Fragment f = fm.findFragmentById(R.id.fragment_container);
                    FragmentTransaction ft = fm.beginTransaction();
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
                }
                else{
                    //Monthly View
                    dailyTag.setTextColor(getResources().getColor(R.color.black));
                    monthlyTag.setTextColor(getResources().getColor(R.color.white));


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
                }
            }
        });

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
                final AlertDialog alert = builder.create();

                View mView = getLayoutInflater().inflate(R.layout.activity_event_add,null);
                Button addB = mView.findViewById(R.id.add_event);
                Spinner dateSpin = mView.findViewById(R.id.spinner_date);
                Spinner monthSpin = mView.findViewById(R.id.spinner_month);
                Spinner yearSpin = mView.findViewById(R.id.spinner_year);
                Spinner notifySpin = mView.findViewById(R.id.spinner_notify);
                ImageButton colorPick = mView.findViewById(R.id.color_picker);
                timeVal = mView.findViewById(R.id.t_val);
                final TextView eventName = mView.findViewById(R.id.topic_val);
                final TextView description = mView.findViewById(R.id.des_val);

                // set view to onclick view
//                builder.setView(mView);
//                alert.show();

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


                timeVal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainViewActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_time_picker,null);
                        final TimePicker  tp = mView.findViewById(R.id.timePicker);
                        Button db = mView.findViewById(R.id.doneBtn);

                        db.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hour = tp.getHour();
                                min = tp.getMinute();
                                if(hour < 12){
                                    time = hour + ":" + min + " AM";
                                }else{
                                    time = hour +":" + min + " PM";
                                }
                                timeVal.setText(time);
                            }
                        });
                    }
                });

                // ADDING EVENTS WORKS
                addB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eventNameString = eventName.getText().toString();
                        descripString = description.getText().toString();
                        Date d = new Date(year,month,date);
                        Event newEvent = new Event(eventNameString,descripString,time,d,new Date(),1,1, "id", "");


                        mFirebaseDatabaseReference.child(EVENTS_CHILD)
                                .push().setValue(newEvent, new DatabaseReference.CompletionListener() {

                            // updates entry with own unique key
                            @Override
                            public void onComplete(DatabaseError databaseError,
                                                   DatabaseReference databaseReference) {
                                String uniqueID = databaseReference.getKey();
                                mFirebaseDatabaseReference.child(EVENTS_CHILD).child(uniqueID).child("_id").setValue(uniqueID);

                            }
                        });

                        Log.d(TAG, " added new event to database!...");
                        Toast.makeText(getApplicationContext(),"Event Added!", Toast.LENGTH_SHORT).show();
                        alert.dismiss();
                    }
                });

                builder.setView(mView);
                AlertDialog dialog =  builder.create();
                dialog.show();
            }

        });

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    //TODO handle sign out
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sign_out_menu:
//                mFirebaseAuth.signOut();
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
//                mUsername = ANONYMOUS;
//                startActivity(new Intent(this, SignInActivity.class));
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


}
