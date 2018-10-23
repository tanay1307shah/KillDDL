package com.map524s1a.killddl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanay on 10/20/2018.
 */

public class EventSingleton {

    private Context c;
    private ArrayList<Event> Events;
    private static EventSingleton sEventSingleton;

    private EventSingleton(Context c){
        this.c = c;
        Events = new ArrayList<>();
    }

    public  void deleteEvent(Event e){
        Events.remove(e);
    }

    public static EventSingleton get(Context c){
        if(sEventSingleton == null){
            sEventSingleton = new EventSingleton(c);
        }
        return sEventSingleton;
    }

    public void addEventSingleton(Event mv){
        Events.add(mv);
    }

    public Event getEvents(int position) {
        if (position >=0 && position < Events.size()) {
            return Events.get(position);
        }
        else {
            return null;
        }
    }

    public List<Event> getEvents(){
        List<Event> m = new ArrayList<>(Events);
        return m;
    }

}
