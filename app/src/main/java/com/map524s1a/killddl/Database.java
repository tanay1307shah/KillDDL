package com.map524s1a.killddl;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    FirebaseDatabase database;
    DatabaseReference dbRef;
    public Database(){
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("message");
        dbRef.setValue("Hello, World!");
    }

    public void updateEmail(String email){
        dbRef.child("user").child("email").setValue(email);
    }

    public void updatePassword(String password){
        dbRef.child("user").child("password").setValue(password);
    }

    public void addEvent(String email, String eventName, String description, Date dueDate, int frequency, int importance){
        String userId = email;
        mDatabase.child("_users").child(userId).child("_username").setValue(name);

        dbRef.child("_users").child(userId).child("_event").setValue(user);


        String key = dbRef.child("posts").push().getKey();
        Event newEvent = new Event(eventName, description, dueDate, frequency, importance);
        Map<String, Object> postValues = newEvent.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/events/" + key, postValues);
        dbRef.updateChildren(childUpdates);
    }

    public List<Event> getMonthlyEvents(String email){ //TODO
        List<Event> monthlyEvents = new ArrayList<Event>();
        Date date=java.util.Calendar.getInstance().getTime();
        System.out.println(date);
        //get current month and return all events that correspond to this month
        return monthlyEvents;
    }

    public List<Event> getDailyEvents(String email){ //TODO
        //get current day and return all events that correspond to this day
        List<Event> dailyEvents = new ArrayList<Event>();

        return dailyEvents;
    }

    public static void main(String[]args){
        // for testing
        Database db = new Database();
        db.getMonthlyEvents("killddl.usc.edu");
    }
}
