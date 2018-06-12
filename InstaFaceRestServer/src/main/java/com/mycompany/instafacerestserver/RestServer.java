package com.mycompany.instafacerestserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    public Response SignUp(String jsonString)
    {
        try
        {
            Connect();
            JSONObject json = new JSONObject(jsonString);
            User user = new User(json.getString("Name"), json.getString("Surname"), json.getString("Username"), json.getString("Password"), json.getInt("Genre"), json.getString("Description"), json.getString("Country"), json.getString("Town"));
            
            ResultSet users = Connection.createStatement().executeQuery("SELECT Username FROM Users");
            while(users.next())
                if(users.getString(1).equals(user.getUsername()))
                {
                    Disconnect();
                    return Response.status(201).entity("").build();
                }

            PreparedStatement prep = Connection.prepareStatement("INSERT INTO Users (Name, Surname, Username, Password, Genre, Description, Country, Town) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, user.getName());
            prep.setString(2, user.getSurname());
            prep.setString(3, user.getUsername());
            prep.setString(4, user.getPassword());
            prep.setInt(5, user.getGenre());
            prep.setString(6, user.getDescription());
            prep.setString(7, user.getCountry());
            prep.setString(8, user.getTown());
            prep.addBatch();
            prep.executeBatch();
            Disconnect();
            return Response.status(200).entity("").build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (SignUp)!");
        }
        
        return Response.status(202).entity("").build();
    }
    
    @Path("/signin")
    @POST
    public Response SignIn(String jsonString)
    {
        try
        {
            Connect();
            JSONObject json = new JSONObject(jsonString);
            User user = new User(json.getString("Username"), json.getString("Password"));
            
            ResultSet users = Connection.createStatement().executeQuery("SELECT * FROM Users");
            while(users.next())
                if(users.getString(4).equals(user.getUsername()) && users.getString(5).equals(user.getPassword()))
                {
                    String str = new User(users.getString(2), users.getString(3), users.getString(4), users.getString(5), users.getInt(6), users.getString(7), users.getString(8), users.getString(9)).ToJSON();
                    Disconnect();
                    return Response.status(200).entity(str).build();
                }

            Disconnect();
            return Response.status(201).entity("").build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (SignIn)!");
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
    public Response ShowFriends(String jsonString)
    {
        try
        {
            Connect();
            JSONObject json = new JSONObject(jsonString);
            User user = new User(json.getString("Username"), json.getString("Password"));

            String friends = "";
            ResultSet friendships = Connection.createStatement().executeQuery("SELECT User1, User2 FROM Friendships");
            ResultSet users = Connection.createStatement().executeQuery("SELECT * FROM Users");

            while(friendships.next())
                if(friendships.getString(1).equals(user.getUsername()))
                {
                    while(users.next())
                        if(users.getString(3).equals(friendships.getString(2)))
                        {
                            friends += new User(users.getString(2), users.getString(3), users.getString(4), users.getString(5), users.getInt(6), users.getString(7), users.getString(8), users.getString(9)).ToJSON() + "\n";
                            break;
                        }
                }   
                else if(friendships.getString(2).equals(user.getUsername()))
                {
                    while(users.next())
                        if(users.getString(3).equals(friendships.getString(1)))
                        {
                            friends += new User(users.getString(2), users.getString(3), users.getString(4), users.getString(5), users.getInt(6), users.getString(7), users.getString(8), users.getString(9)).ToJSON() + "\n";
                            break;
                        }
                }
            
            Disconnect();
            if(friends.equals(""))
                return Response.status(201).entity("").build();
            else
                return Response.status(200).entity(friends).build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (ShowFriends)!");
        }
        
        return Response.status(202).entity("").build();
    }
    
    @Path("/deletefriend")
    @POST
    public Response DeleteFriend(String jsonString)
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
            
            ResultSet friendships = Connection.createStatement().executeQuery("SELECT * FROM Friendships");
            while(friendships.next())
                if((friendships.getString(2).equals(friendship.getUser1()) && friendships.getString(3).equals(friendship.getUser2()))
                   || (friendships.getString(2).equals(friendship.getUser2()) && friendships.getString(3).equals(friendship.getUser1())))
                {
                    Connection.createStatement().execute("DELETE FROM Friendships WHERE Id=" + friendships.getInt(1) + ";");
                    System.out.println("OK");
                    Disconnect();
                    return Response.status(200).entity("").build();
                }
            
            Disconnect();
            return Response.status(201).entity("").build();
        }
        catch (SQLException | JSONException ex)
        {
            System.err.println("Something went wrong (DeleteFriend)!");
        }
        
        return Response.status(203).entity("").build();
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