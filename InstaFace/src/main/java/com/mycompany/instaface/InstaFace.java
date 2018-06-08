package com.mycompany.instaface;

import java.awt.Frame;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InstaFace extends Frame
{
    public InstaFace()
    {
        Client client = ClientBuilder.newClient();
        
        WebTarget target = client.target("http://localhost:8080/InstaFaceRestServer/api/users/reg");
        User user = new User("John", "1234");
        Response res = target.request(MediaType.APPLICATION_JSON).post(Entity.json(user.ToJSON()));
        
        if (res.getStatus() == 200)
            System.out.println("OK");
        else
            System.out.println("Something went wrong");
    }
}