package com.mycompany.instafacerestserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.Consumes;
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
            
            ResultSet records = Connection.createStatement().executeQuery("SELECT Username FROM Users");
            while(records.next())
                if(records.getString(1).equals(user.getUsername()))
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