package com.mycompany.instafacerestserver;

import java.sql.*;

public class Database
{
    public static void main(String[] args)
    {
        //InitDB();
    }
    
    public static void InitDB()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + "\\instaface.db");
            Statement stat = conn.createStatement();
            stat.executeUpdate("DROP TABLE IF EXISTS Users;");
            stat.executeUpdate("CREATE TABLE Users (Id INTEGER PRIMARY KEY AUTOINCREMENT, Username VARCHAR(50), Password VARCHAR(50), Name VARCHAR(50), Surname VARCHAR(50), Email VARCHAR(50));");
            stat.executeUpdate("CREATE TABLE Friendships (Id INTEGER PRIMARY KEY AUTOINCREMENT, User1 VARCHAR(50), User2 VARCHAR(50));");
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