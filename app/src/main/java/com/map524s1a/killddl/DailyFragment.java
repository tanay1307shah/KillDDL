package com.map524s1a.killddl;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import java.util.Date;
import java.util.List;
import java.util.HashMap;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.map524s1a.killddl.MainViewActivity.EVENTS_CHILD;

/**
 * Created by tanay on 10/15/2018.
 */

public class DailyFragment extends Fragment {

    private ListView lv;

    private boolean iSFirst = true;
    private static final String TAG = "DailyFragment";

    private Button delbtn;
    private String eventNameString;
    private String descripString;
    //private Button
    private EditText timeVal;
    private Button detailsBtn;
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
            public void onDataChange(DataSnapshot snapshot) {
                List<Event> listEvents = new ArrayList<>();
                Log.e(TAG ,"num events: "+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Event post = postSnapshot.getValue(Event.class);
                    Log.e(TAG, " " + post.get_eventName());
                    listEvents.add(post);
                }

                adapter = new EventListAdapter(getApplicationContext(), 0, listEvents);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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

    public class EventListAdapter extends ArrayAdapter<Event> {
        public EventListAdapter(Context context, int resource, List<Event> objects){
            super(context, resource, objects);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item,null);
            }
            CardView cv = convertView.findViewById(R.id.card);
            TextView titleV = convertView.findViewById(R.id.evetitle);
            TextView timeV = convertView.findViewById(R.id.timeval);
            TextView uniqueID = convertView.findViewById(R.id.uniqueID);
            Button statusBtn = convertView.findViewById(R.id.statusBtn);
            detailsBtn = convertView.findViewById(R.id.detailsBtn);
            delbtn = convertView.findViewById(R.id.deleteBtn);

            detailsBtn.setVisibility(View.INVISIBLE);
            delbtn.setVisibility(View.INVISIBLE);
            final Event e = getItem(position);

            // set card text
            titleV.setText(e.get_eventName());

            timeV.setText(e.getTimeStr());
            uniqueID.setText(e.get_id());

            cv.setOnClickListener(new View.OnClickListener() {
                // show details
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

            delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventsReference.child(e.get_id()).removeValue();
                    Log.e(TAG, " deleted " + e.get_eventName());

                }
            });

            detailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DailyFragment.this.getContext());
                    final View mView = getLayoutInflater().inflate(R.layout.activity_event_view, null);

                    EditText event = mView.findViewById(R.id.eventNameV);
                    EditText dueDate = mView.findViewById(R.id.dueDate);
                    EditText descrip = mView.findViewById(R.id.descriptionV);

                    event.setText(e.get_eventName());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    dueDate.setText(dateFormat.format(e.get_dueDate()));
                    descrip.setText(e.get_description());

                    Button removeBtn  = mView.findViewById(R.id.delBtn);
                    removeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            eventsReference.child(e.get_id()).removeValue();
                            Log.e(TAG, " deleted " + e.get_eventName());
                            //TODO close popup
                        }
                    });

                    //add functionality for edit button
                    final Button editBtn = mView.findViewById(R.id.editBtn);

                    //fake remove button, not connected to database
                    //use the.childmethod and then call the event getters to get values: String eventName, String description,String timeStr, Date dueDate, Date time, int frequency, int importance, String id,String color
                    //use the mview.findbyid to get new values
                    //use the firebase updateChildren() function, create map, to update values.
                    editBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Event temp = e;
                            //View mView = getLayoutInflater().inflate(R.layout.activity_event_view,null);
                            timeVal = mView.findViewById(R.id.dueDate);

                            EditText eventName = mView.findViewById(R.id.eventNameV);
                            EditText description = mView.findViewById(R.id.descriptionV);

                            //eventName.setText(e.get_eventName());
                            //description.setText(e.get_description());

                            String tempEventName = eventName.getText().toString();
                            String tempDescription = description.getText().toString();

                            Log.e(TAG, " eventname: " + eventName);

                            HashMap<String, Object> updatedValues = new HashMap<String, Object>();
                            updatedValues.put("_eventName", tempEventName);
                            updatedValues.put("_description", tempDescription);

                            eventsReference.child(e.get_id()).updateChildren(updatedValues);
                            Log.e(TAG, " Edited " + e.get_eventName());
                            //TODO close popup
                        }
                    });

//                    editBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(DailyFragment.this.getContext());
//                            final AlertDialog alert = builder.create();
//
//                            View mView = getLayoutInflater().inflate(R.layout.activity_event_add,null);
//                            //Button addB = mView.findViewById(R.id.add_event);
//                            //Spinner dateSpin = mView.findViewById(R.id.spinner_date);
//                            //Spinner monthSpin = mView.findViewById(R.id.spinner_month);
//                            //Spinner yearSpin = mView.findViewById(R.id.spinner_year);
//                            //Spinner notifySpin = mView.findViewById(R.id.spinner_notify);
//                            //ImageButton colorPick = mView.findViewById(R.id.color_picker);
//                            timeVal = mView.findViewById(R.id.dueDate);
//                            final EditText eventName = mView.findViewById(R.id.eventNameV);
//                            final EditText description = mView.findViewById(R.id.descriptionV);
//
//                            // set view to onclick view
////                builder.setView(mView);
////                alert.show();
//
////                            dateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                                @Override
////                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                                    date = position;
////                                }
////
////                                @Override
////                                public void onNothingSelected(AdapterView<?> parent) {
////
////                                }
////                            });
//
////                            monthSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                                @Override
////                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                                    month = position;
////                                }
////
////                                @Override
////                                public void onNothingSelected(AdapterView<?> parent) {
////
////                                }
////                            });
//
////                            yearSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                                @Override
////                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                                    year = Integer.parseInt(yearArr[position]);
////                                }
////
////                                @Override
////                                public void onNothingSelected(AdapterView<?> parent) {
////
////                                }
////                            });
//
////                            notifySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                                @Override
////                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                                    notify = notifyArr[position];
////                                }
////
////                                @Override
////                                public void onNothingSelected(AdapterView<?> parent) {
////
////                                }
////                            });
//
////                            colorPick.setOnClickListener(new View.OnClickListener() {
////                                @Override
////                                public void onClick(View v) {
////
////                                }
////                            });
//
//
////                            timeVal.setOnClickListener(new View.OnClickListener() {
////                                @Override
////                                public void onClick(View v) {
////                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainViewActivity.this);
////                                    View mView = getLayoutInflater().inflate(R.layout.dialog_time_picker,null);
////                                    final TimePicker tp = mView.findViewById(R.id.timePicker);
////                                    Button db = mView.findViewById(R.id.doneBtn);
////
////                                    db.setOnClickListener(new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View v) {
////                                            hour = tp.getHour();
////                                            min = tp.getMinute();
////                                            if(hour < 12){
////                                                time = hour + ":" + min + " AM";
////                                            }else{
////                                                time = hour +":" + min + " PM";
////                                            }
////                                            timeVal.setText(time);
////                                        }
////                                    });
////                                }
////                            });
//
//                            // ADDING EVENTS WORKS
//                            editBtn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    eventNameString = eventName.getText().toString();
//                                    descripString = description.getText().toString();
//                                    Date d = new Date(year,month,date);
//                                    Event newEvent = new Event(eventNameString,descripString,time,d,new Date(),1,1, "id", "");
//
//                                    //get ID from firebase and update parameters
//                                    mFirebaseDatabaseReference.child(EVENTS_CHILD)
//                                            .push().setValue(newEvent, new DatabaseReference.CompletionListener() {
//
//                                        // updates entry with own unique key
//                                        @Override
//                                        public void onComplete(DatabaseError databaseError,
//                                                               DatabaseReference databaseReference) {
//                                            String uniqueID = databaseReference.getKey();
//                                            mFirebaseDatabaseReference.child(EVENTS_CHILD).child(uniqueID).child("_id").setValue(uniqueID);
//
//                                        }
//                                    });
//
//                                    Log.d(TAG, " added new event to database!...");
//                                    Toast.makeText(getApplicationContext(),"Event Added!", Toast.LENGTH_SHORT).show();
//                                    alert.dismiss();
//                                }
//                            });
//
//                            builder.setView(mView);
//                            AlertDialog dialog =  builder.create();
//                            dialog.show();
//                        }
//
//                    });

                    //end functionality for edit button
                    builder.setView(mView);
                    AlertDialog dialog = builder.create();
                    dialog.show();}
            });
            return convertView;
        }
    }
}
