package com.map524s1a.killddl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

public class User {
    private String _email;
    private String _password;
    private List<Event> _events;
    private Database _db;

    public User(String email, String password){
        _email = email;
        _password = password;
        _events = new ArrayList<Event>();
        _db = new Database();
    }

    //Get months com.map524s1a.killddl.Event, returns nothing but,
    public void getMonthsEvent(String _email) //TODO
    {
        List<Event> MonthlyEvents;
        MonthlyEvents=_db.getMonthlyEvents(_email);
    }

    //Get Day event, returns nothing but
    public void getDayEvent(String _email) //TODO
    {
        List<Event> DayEvents;
        DayEvents =_db.getMonthlyEvents(_email);
    }

    //Adds the event
    public void AddEvent(Event toAdd) //TODO
    {
        //Potentially "Would you like to add an event?"
        String eventName = toAdd.get_eventName();
        String description = toAdd.get_description();
        Date dueDate = toAdd.get_dueDate();
        int frequency = toAdd.get_frequency();
        int importance = toAdd.get_importance();
        int id = toAdd.get_id();
        //Call the database addEvent, which will convert the event into the proper form
        _db.addEvent(eventName,description,dueDate,frequency,importance,id);
    }

    public void DeleteEvent(int id){ //TODO
        //Will pick up an id for an event and delete it.

        for (Iterator<Event> iter = _events.listIterator(); iter.hasNext(); ) {
            Event e = iter.next();
            if (e.get_id()==id) {
                iter.remove();
            }
        }

    }

}