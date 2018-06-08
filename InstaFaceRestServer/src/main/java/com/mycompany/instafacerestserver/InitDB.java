package com.mycompany.instafacerestserver;

import java.sql.*;

public class InitDB
{
    public static String URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\instaface.db";

    public static void main(String[] args)
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            Statement stat = conn.createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS Users;");
            stat.executeUpdate("CREATE TABLE Users (Username VARCHAR(50) PRIMARY KEY, Password VARCHAR(50));");
            System.out.println ("Database created successfully!");
            
            conn.setAutoCommit(false);
            conn.commit();
            conn.close();
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            System.err.println("Something went wrong (Init DB)!");
        }
    }
}