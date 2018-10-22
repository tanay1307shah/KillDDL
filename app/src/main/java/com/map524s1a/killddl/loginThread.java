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
    void callback(boolean isLoggedIn, User u);
}


public class loginThread extends Thread {

    response c;
    private String email;
    private String pswd;


    public loginThread(String email, String password, response c){
        this.email = email;
        this.pswd = pswd;
        this.c = c;
    }

    public void run(){

        boolean isLoggedIn = false;
        User u =null;
        Event e = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.43.141:3306/killddl?user=root&password=Equyi86V6V&useSSL=false");

            PreparedStatement ps = null;
            ps = conn.prepareStatement("SELECT * FROM Users WHERE email= ? and pwd = ?");
            ps.setString(1,email);
            ps.setString(2,pswd);
            Log.d("DEBUG",email + " " + pswd);
            ResultSet rs = ps.executeQuery();

            if(rs==null){
                isLoggedIn = false;
            }else{
                rs.next();
                isLoggedIn = true;
                int id = rs.getInt("userId");
                u = new User(id,email,pswd);

                ps = conn.prepareStatement("SELECT * FROM EventsTable WHERE userId = ?");
                ps.setInt(1,id);
                ResultSet rs1 = ps.executeQuery();

                if(rs != null){
                    while(rs.next()){
                        e =  new Event(rs.getString("title"),rs.getString("description"),rs.getDate("eventDate"),rs.getTime("notifyTime"),rs.getInt("frequency"),rs.getInt("importance"),rs.getInt("eventId"),rs.getString("color"));
                        u.AddEvent(e);
                    }
                }
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
