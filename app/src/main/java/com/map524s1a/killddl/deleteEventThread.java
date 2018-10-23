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
interface deleteThreadresponse{
    void callback(boolean eventDeleted);
}


public class deleteEventThread extends Thread {

    deleteThreadresponse c;
    private int eventID;


    public deleteEventThread(int eventID, deleteThreadresponse c){
        this.eventID = eventID;
        this.c = c;
    }

    public void run() {

        Event e = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.43.128:3306/smile?user=root&password=tShah0713!&useSSL=false");

            PreparedStatement ps = null;
            ps = conn.prepareStatement("DELETE FROM EventsTable WHERE eventID= ?");
            ps.setInt(1, this.eventID);
            ps.execute();

        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }finally {
            this.c.callback(true);
        }
    }
}