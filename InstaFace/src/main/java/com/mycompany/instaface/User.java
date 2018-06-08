package com.mycompany.instaface;

import java.io.Serializable;
import org.json.JSONObject;

public class User
{
    private String Username;
    private String Password;
    
    public User(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
    }

    public String getUsername() { return Username; }
    public String getPassword() { return Password; }
    
    public String ToJSON()
    {
        return new JSONObject()
                  .put("Username", getUsername())
                  .put("Password", getPassword()).toString();
    }
}