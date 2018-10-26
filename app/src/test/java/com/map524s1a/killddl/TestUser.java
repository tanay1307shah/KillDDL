package com.map524s1a.killddl;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestUser {
    @Test
    public void testUserID(){
        User testUser = new User(1, "Groot", "meow");
        assertTrue(testUser.getUserID()==1);
        System.out.println(testUser.getEvents().isEmpty() == true);
    }
    @Test
    public void testUserName(){
        User testUser = new User(1, "Groot", "meow");
        assertTrue(testUser.getEmail().equals("Groot"));
    }
    @Test
    public void testUserEvents(){
        User testUser = new User(1, "Groot", "meow");
        assertTrue(testUser.getEvents().isEmpty());
    }
}
