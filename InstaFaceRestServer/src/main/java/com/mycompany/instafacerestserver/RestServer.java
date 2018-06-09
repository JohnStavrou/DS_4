package com.mycompany.instafacerestserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response; 
import org.json.JSONException;
import org.json.JSONObject;

@Path("/restserver")
public class RestServer
{
    public String PATH = "C:\\Users\\JS\\Documents\\GitHub\\DS_4\\InstaFaceRestServer";
    public String URL = "jdbc:sqlite:" + PATH + "\\instaface.db";
    Connection Connection;

    @Path("/signup")
    @POST
    public Response InsertUser(String jsonString)
    {
        try
        {
            Connect();
            JSONObject json = new JSONObject(jsonString);
            User user = new User(json.getString("Username"), json.getString("Password"));
            
            ResultSet users = Connection.createStatement().executeQuery("SELECT Username FROM Users");
            while(users.next())
                if(users.getString(1).equals(user.getUsername()))
                {
                    Disconnect();
                    return Response.status(201).entity("").build();
                }

            PreparedStatement prep = Connection.prepareStatement("INSERT INTO Users (Username, Password) VALUES (?, ?);");
            prep.setString(1, user.getUsername());
            prep.setString(2, user.getPassword());
            prep.addBatch();
            prep.executeBatch();
            Disconnect();
            return Response.status(200).entity("").build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (InsertUser)!");
        }
        
        return Response.status(202).entity("").build();
    }
    
    @Path("/signin")
    @POST
    public Response GetUser(String jsonString)
    {
        try
        {
            Connect();
            JSONObject json = new JSONObject(jsonString);
            User user = new User(json.getString("Username"), json.getString("Password"));
            
            ResultSet users = Connection.createStatement().executeQuery("SELECT Username, Password FROM Users");
            while(users.next())
                if(users.getString(1).equals(user.getUsername()) && users.getString(2).equals(user.getPassword()))
                {
                    Disconnect();
                    return Response.status(200).entity("").build();
                }

            Disconnect();
            return Response.status(201).entity("").build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (GetUser)!");
        }
        
        return Response.status(202).entity("").build();
    }
    
    @Path("/addfriend")
    @POST
    public Response AddFriend(String jsonString)
    {
        try
        {
            Connect();
            JSONObject json = new JSONObject(jsonString);
            Friendship friendship = new Friendship(json.getString("User1"), json.getString("User2"));
            
            boolean exists = false;
            ResultSet users = Connection.createStatement().executeQuery("SELECT Username FROM Users");
            while(users.next())
                if(users.getString(1).equals(friendship.getUser2()))
                {
                    exists = true;
                    break;
                }
            
            if(!exists)
            {
                Disconnect();
                return Response.status(202).entity("").build();
            }
            
            ResultSet friendships = Connection.createStatement().executeQuery("SELECT User1, User2 FROM Friendships");
            while(friendships.next())
                if((friendships.getString(1).equals(friendship.getUser1()) && friendships.getString(2).equals(friendship.getUser2()))
                   || (friendships.getString(1).equals(friendship.getUser2()) && friendships.getString(2).equals(friendship.getUser1())))
                {
                    Disconnect();
                    return Response.status(201).entity("").build();
                }
            
            PreparedStatement prep = Connection.prepareStatement("INSERT INTO Friendships (User1, User2) VALUES (?, ?);");
            prep.setString(1, friendship.getUser1());
            prep.setString(2, friendship.getUser2());
            prep.addBatch();
            prep.executeBatch();
            Disconnect();
            return Response.status(200).entity("").build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (AddFriend)!");
        }
        
        return Response.status(203).entity("").build();
    }
    
    @Path("/friends")
    @POST
    public Response Friends(String username)
    {
        try
        {
            Connect();
            String friends = "";
            ResultSet friendships = Connection.createStatement().executeQuery("SELECT User1, User2 FROM Friendships");
            ResultSet users = Connection.createStatement().executeQuery("SELECT Username, Name, Surname, Email FROM Users");

            while(friendships.next())
                if(friendships.getString(1).equals(username))
                {
                    while(users.next())
                        if(users.getString(1).equals(friendships.getString(2)))
                        {
                            friends += users.getString(1) + " " + users.getString(2) + " " + users.getString(3) + " " + users.getString(4) + "\n";
                            break;
                        }
                }   
                else if(friendships.getString(2).equals(friendships.getString(2)))
                {
                    while(users.next())
                        if(users.getString(1).equals(friendships.getString(1)))
                        {
                            friends += users.getString(1) + " " + users.getString(2) + " " + users.getString(3) + " " + users.getString(4) + "\n";
                            break;
                        }
                }
            
            Disconnect();
            return Response.status(200).entity(friends).build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (Friends)!");
        }
        
        return Response.status(201).entity("").build();
    }
    
    public void Connect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection = DriverManager.getConnection(URL);
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.err.println("Something went wrong (Connect)!");
        }
    }
    
    public void Disconnect()
    {
        try
        {
            Connection.setAutoCommit(false);
            Connection.commit();
            Connection.close();
        }
        catch(SQLException ex)
        {
            System.err.println("Something went wrong (Connect)!");
        }
    }
}