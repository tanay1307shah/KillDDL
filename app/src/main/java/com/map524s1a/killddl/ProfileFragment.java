package com.map524s1a.killddl;


import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

/**
 * Created by tanay on 10/15/2018.
 */

public class ProfileFragment extends Fragment {

    NotificationHelper helper;
    Button btnSend;

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

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification.Builder builder = helper.getChannelNotification();
                helper.getManager().notify(new Random().nextInt(),builder.build());
            }
        });




    }
}
