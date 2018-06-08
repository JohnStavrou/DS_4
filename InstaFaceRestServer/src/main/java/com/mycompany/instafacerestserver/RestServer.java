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
import org.json.JSONObject;

@Path("/users")
public class RestServer
{
    Connection connection;
    public String URL = "jdbc:sqlite:C:\\Users\\JS\\Documents\\NetBeansProjects\\InstaFaceRestServer\\instaface.db";

    @Path("/reg")
    @POST
    public Response InsertUser(String jsonString)
    {
        try
        {
            Connect();
            JSONObject json = new JSONObject(jsonString);
            
            PreparedStatement prep = connection.prepareStatement("INSERT INTO Users (Username, Password) VALUES (?, ?);");
            prep.setString(1, json.getString("Username"));
            prep.setString(2, json.getString("Password"));
            prep.addBatch();
            prep.executeBatch();
            Disconnect();
            
            return Response.status(200).entity("").build();
        }
        catch (Exception ex)
        {
            System.err.println("Something went wrong (InsertUser)!");
        }
        
        return Response.status(201).entity("").build();
    }
    
    public void Connect()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
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
            connection.setAutoCommit(false);
            connection.commit();
            connection.close();
        }
        catch(SQLException ex)
        {
            System.err.println("Something went wrong (Connect)!");
        }
    }
}