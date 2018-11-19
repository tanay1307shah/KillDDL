package com.map524s1a.killddl;


import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.map524s1a.killddl.MainViewActivity.EVENTS_CHILD;

/**
 * Created by tanay on 10/15/2018.
 */

public class ProfileFragment extends Fragment {

    NotificationHelper helper;
    Button btnSend;
    Button emailSend;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference eventsReference;
    private static final String TAG = "ProfileFragment";
    private List <Event> events;

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {
                "kartikmat2@gmail.com"
        };
        String[] CC = {
                "kmathur@usc.edu"
        };
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        final Intent intent = emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KILLDDL Reminder");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
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
                events = listEvents;
            }

            @Override
            public void onCancelled(DatabaseError de) {
                Log.e("The read failed: " , de.getMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View fm_layout = inflater.inflate(R.layout.activity_user_profile,container,false);
        return fm_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        helper = new NotificationHelper(getContext());
        btnSend = (Button)view.findViewById(R.id.button3);
        emailSend = (Button)view.findViewById(R.id.emailButton);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(events.size()==0)
                {
                    helper.setMessage("You have no events scheduled.");
                }
                else{
                    helper.setMessage(events.get(0).get_dueDate().toString());
                }

                Notification.Builder builder = helper.getChannelNotification();
                helper.getManager().notify(new Random().nextInt(),builder.build());
            }
        });
        emailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }
}
