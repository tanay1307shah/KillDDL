package com.map524s1a.killddl;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by tanay on 10/21/2018.
 */
interface response{
    void callback(boolean eventAdded, Event e);
}


public class eventThread extends Thread {

    response c;
    private String _eventName;
    private String color;
    private String _description;
    private Date _dueDate;
    private int _frequency; // number represents frequency type
    private int _importance; // importance is from 1 to 3
    private int _id;
    private User u;
    private Date time;


    public eventThread(String _eventName, String color, String _description, Date _dueDate, int _frequency, int _importance, int _id, Date time, response c, User u){
        this._eventName = _eventName;
        this.color = color;
        this._description = _description;
        this._dueDate = _dueDate;
        this._frequency = _frequency;
        this._importance = _importance;
        this._id = _id;
        this.time = time;
        this.c = c;
        this.u = u;
    }

    public void run(){

        boolean eventAdded = false;

        //User u =null;

        Event e = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.43.128:3306/smile?user=root&password=tShah0713!&useSSL=false");

            PreparedStatement ps = null;
            //ps = conn.prepareStatement("SELECT * FROM Users WHERE email= ? and pwd = ?");
            String eventQuery = "INSERT INTO EventsTable (userId, title, description, eventDate, notifyTime, color, important, frequency) VALUES(" + u.getUserID() + ", " + this._eventName + ", " + this._description + ", " + this._dueDate + ", " + this.time + "," + this.color + ", " + this._importance + ", " + this._frequency + ")";
            ps = conn.prepareStatement(eventQuery);
            //ps.setString(1,email);
            //ps.setString(2,pswd);
            Log.d("DEBUG",email + " " + pswd);
            ResultSet rs = ps.executeQuery();

            if(rs==null){
                eventAdded = false;
            }else{
                rs.next();
                eventAdded = true;
//                int id = rs.getInt("userId");
//                u = new User(id,email,pswd);
//
//                ps = conn.prepareStatement("SELECT * FROM EventsTable WHERE userId = ?");
//                ps.setInt(1,id);
//                ResultSet rs1 = ps.executeQuery();
//
//                if(rs != null){
//                    while(rs.next()){
//                        e =  new Event(rs.getString("title"),rs.getString("description"),rs.getDate("eventDate"),rs.getTime("notifyTime"),rs.getInt("frequency"),rs.getInt("importance"),rs.getInt("eventId"),rs.getString("color"));
//                        u.AddEvent(e);
//                    }
//                }

            }
        }catch(SQLException sqle){
            Log.d("ERROR","SQL Exception " + sqle.getMessage());
        }catch (ClassNotFoundException cnfe){
            Log.d("ERROR","class not found Exception " + cnfe.getMessage());
        }finally {
            this.c.callback(isLoggedIn,u);
        }
    }
}
