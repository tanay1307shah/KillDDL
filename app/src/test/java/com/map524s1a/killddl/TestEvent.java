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
    @Test
    public void testGetId(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_id(), 3);
    }
    @Test
    public void testGetEventName(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_eventName(), "test");

    }
    @Test
    public void testGetDescription(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_description(), "Remind me about the test.");

    }

    @Test
    public void testGetDueDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_dueDate(), dt);

    }

    @Test
    public void testGetFrequency(){
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue" );
        assertEquals(testEvent.get_frequency(), 2);

    }

    @Test
    public void testGetImportance() {
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue");
        assertEquals(testEvent.get_importance(), 1);
    }
    @Test
    public void testGetColor() {
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue");
        assertEquals(testEvent.getColor(), "Blue");
    }

    @Test
    public void testGetTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 11, 9);
        Date dt = cal.getTime();
        Event testEvent = new Event("test", "Remind me about the test.",
                "10", dt, dt, 2, 1, 3, "Blue");
        assertEquals(testEvent.getTime(), dt);
    }


}
