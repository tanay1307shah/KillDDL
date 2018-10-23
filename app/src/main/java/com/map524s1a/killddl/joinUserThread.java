package com.map524s1a.killddl;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by tanay on 10/22/2018.
 */


interface addUserResponse{
    void addUserCallback(User u);
}


public class joinUserThread extends Thread {
    private String email;
    private String password;
    private String name;
    private String gender;
    private String imgUrl;
    private addUserResponse c;

    public joinUserThread(String email, String password, String name, String gender, String imgUrl, addUserResponse c) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.imgUrl = imgUrl;
        this.c = c;
    }

    public void run() {

        User u = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.11:3306/killddl?user=root&password=tShah0713!&useSSL=false");

            PreparedStatement ps = null;
            //ps = conn.prepareStatement("SELECT * FROM Users WHERE email= ? and pwd = ?");
            String eventQuery = "INSERT INTO Users (email,pwd,fullName, gender,imgUrl) VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(eventQuery);

            ps.setString(1, this.email);
            ps.setString(2, this.password);
            ps.setString(3, this.name);
            ps.setString(4, this.gender);
            ps.setString(5, this.imgUrl);

            int id = ps.executeUpdate();

            u = new User(id, email, password);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.c.addUserCallback(u);
        }

    }
}
