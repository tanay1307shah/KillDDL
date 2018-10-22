package com.map524s1a.killddl;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by tanay on 10/21/2018.
 */
interface addEventResponse{
    void addEventCallback(boolean eventAdded, Event e);
}


public class addEventThread extends Thread {

    addEventResponse c;
    private String _eventName;
    private String color;
    private String _description;
    private Date _dueDate;
    private int _frequency; // number represents frequency type
    private int _importance; // importance is from 1 to 3
    private int userId;
    private Date time;


    public addEventThread(int userId,String _eventName, String color, String _description, Date _dueDate, int _frequency, int _importance, Date time, addEventResponse c) {
        Log.d("DEBUG", "FUK ME");
        this.userId = userId;
        this._eventName = _eventName;
        this.color = color;
        this._description = _description;
        this._dueDate = _dueDate;
        this._frequency = _frequency;
        this._importance = _importance;
        this.time = time;
        this.c = c;
    }

    public void run() {

        boolean eventAdded = false;

        //User u =null;

        Event e = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.11:3306/killddl?user=root&password=tShah0713!&useSSL=false");

            PreparedStatement ps = null;
            //ps = conn.prepareStatement("SELECT * FROM Users WHERE email= ? and pwd = ?");
            String eventQuery = "INSERT INTO EventsTable (userId, title, description, eventDate, notifyTime, color, important, frequency) VALUES(?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(eventQuery);
            ps.setInt(1, userId);
            ps.setString(2, _eventName);
            ps.setString(3, _description);
            ps.setDate(4, (java.sql.Date) _dueDate);
            ps.setDate(5, (java.sql.Date) time);
            ps.setString(6, color);
            ps.setInt(7, _importance);
            ps.setInt(8, _frequency);
            //Log.d("DEBUG", email + " " + pswd);
            int id = ps.executeUpdate();
            eventAdded = true;
            e = new Event(_eventName,_description,_dueDate,time,_frequency,_importance,id,"");

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }finally{
            this.c.addEventCallback(eventAdded,e);
        }
    }
}
