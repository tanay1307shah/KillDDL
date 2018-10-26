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

import java.util.List;
import java.util.Random;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by tanay on 10/15/2018.
 */

public class ProfileFragment extends Fragment {

    NotificationHelper helper;
    Button btnSend;
    Button emailSend;

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
                List<Event> events = EventSingleton.get(getApplicationContext()).getEvents();

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
