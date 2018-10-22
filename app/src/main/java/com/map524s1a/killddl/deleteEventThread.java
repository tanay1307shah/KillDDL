//package com.map524s1a.killddl;
//
//import android.util.Log;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * Created by tanay on 10/21/2018.
// */
//interface deleteThreadresponse{
//    void callback(boolean eventDeleted, Event e);
//}
//
//
//public class deleteEventThread extends Thread {
//
//    response c;
//    private int eventID;
//
//
//    public deleteEventThread(int eventID, response c){
//        this.eventID = eventID;
//        this.c = c;
//    }
//
//    public void run(){
//
//        boolean eventAdded = false;
//
//        //User u =null;
//
//        Event e = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.43.128:3306/smile?user=root&password=tShah0713!&useSSL=false");
//
//            PreparedStatement ps = null;
//            ps = conn.prepareStatement("DELETE FROM EventsTable WHERE eventID= ?");
//            ps.setString(1, this.eventID);
//            //Log.d("DEBUG",email + " " + pswd);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs == null) {
//                eventAdded = false;
//            } else {
//                rs.next();
//                eventAdded = true;
//                int id = rs.getInt("userId");
//                //u = new User(id, email, pswd);
//
//            }
//        } catch (ClassNotFoundException e1) {
//            e1.printStackTrace();
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }