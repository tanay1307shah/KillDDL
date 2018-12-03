package com.map524s1a.killddl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class event_View_Activity extends AppCompatActivity {

    private Event e;
    private RelativeLayout rv;
    private List<Event> eventList;
    private int loc;
    private Button removeBtn;
    private Button savBtn;
    private EditText event, dueDate,descrip;
    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference eventsReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        Intent i = getIntent();

        Bundle extras = i.getExtras();

        e = (Event) extras.getSerializable("event");

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        eventsReference = mFirebaseDatabaseReference.child("events");

        removeBtn = findViewById(R.id.delBtn);
        savBtn = findViewById(R.id.savBtn);

         event = findViewById(R.id.eventNameV);
         dueDate = findViewById(R.id.dueDate);
         descrip = findViewById(R.id.descriptionV);

         rv = findViewById(R.id.rv);

//         rv.setBackgroundColor(Color.parseColor(e.getColor()));

         event.setText(e.get_eventName());
         DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
         dueDate.setText(dateFormat.format(e.get_dueDate()));
         descrip.setText(e.get_description());

        savBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e.set_eventName(event.toString());
                SimpleDateFormat d = new SimpleDateFormat("YYYY-mm-dd");
                try {
                    e.set_dueDate(d.parse(dueDate.toString()));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                e.set_description(descrip.toString());
                Toast.makeText(getApplicationContext(),"Event Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

         removeBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 eventsReference.child(e.get_id()).removeValue();
                 Log.e("delete", " deleted " + e.get_eventName());
                 Toast.makeText(getApplicationContext(),"Event deleted", Toast.LENGTH_SHORT).show();
                 finish();
                 //TODO close popup
             }
         });
    }

}
