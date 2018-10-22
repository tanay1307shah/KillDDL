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
    void callback(boolean eventDeleted, Event e);
}


public class deleteEventThread extends Thread {

    response c;
    private int eventID;


    public eventThread(int eventID, response c){
        this.eventID = eventID;
        this.c = c;
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
            //String eventQuery = "DELETE FROM EventsTable WHERE email= ? and pwd = ?";
            ps = conn.prepareStatement("DELETE FROM EventsTable WHERE eventID= ?");
            ps.setString(1,this.eventID);
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
