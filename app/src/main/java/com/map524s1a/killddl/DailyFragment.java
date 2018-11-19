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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
    private Button detailsBtn;
    private Button shareBtn;
    private EventListAdapter adapter;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference eventsReference;


    protected void sendEmail(Event toShare) {
        Log.i("Send email", "");

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        final Intent intent = emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KILLDDL Reminder");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Your friend is sharing this event with you.");
        emailIntent.putExtra(Intent.EXTRA_TEXT, toShare.get_eventName());
        emailIntent.putExtra(Intent.EXTRA_TEXT, toShare.get_dueDate());
        emailIntent.putExtra(Intent.EXTRA_TEXT, toShare.get_description());




        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            getActivity().finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

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

            shareBtn = convertView.findViewById(R.id.sharebtn);
            detailsBtn.setVisibility(View.INVISIBLE);
            delbtn.setVisibility(View.INVISIBLE);

            final Event e = getItem(position);

            // set card text

            titleV.setText(e.get_eventName());
            Log.d("myTag", e.getColor());
            if(!e.getColor().isEmpty()){
                cv.setCardBackgroundColor(Color.parseColor("#"+e.getColor()));

            }
            else{
                cv.setCardBackgroundColor(Color.parseColor("#AFEEEE"));
            }

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

            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEmail(e);
                }
            });

            detailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DailyFragment.this.getContext());
                    View mView = getLayoutInflater().inflate(R.layout.activity_event_view, null);

                    TextView event = mView.findViewById(R.id.eventNameV);
                    TextView dueDate = mView.findViewById(R.id.dueDate);
                    TextView descrip = mView.findViewById(R.id.descriptionV);

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
                    builder.setView(mView);
                    AlertDialog dialog = builder.create();
                    dialog.show();}
            });
            return convertView;
        }
    }
}
