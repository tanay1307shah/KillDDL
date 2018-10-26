package com.map524s1a.killddl;

import org.junit.Test;

import java.security.Timestamp;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class TestEvent {


    @Test
    public void testGetTimeStr(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.getTimeStr(), "10");
    }
    public void testGetId(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_id(), 3);
    }
    public void testGetEventName(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_eventName(), "test");

    }
    public void testGetDescription(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_description(), "Remind me about the test.");

    }
    public void testGetDueDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_dueDate(), dt);

    }
    public void testGetFrequency(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_frequency(), 2);

    }
    public void testGetImportance() {
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue");
        assertEquals(testEvent.get_importance(), 1);
    }

}
