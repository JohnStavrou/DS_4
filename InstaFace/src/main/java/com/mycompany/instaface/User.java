
package com.mycompany.instaface;

import org.json.JSONObject;


public class User
{
    //private int Id;
    private String Username;
    private String Password;
    
    public User(String Username, String Password)
    {
        //this.Id = Id;
        this.Username = Username;
        this.Password = Password;
    }

    //public int getId() { return Id; }
    public String getUsername() { return Username; }
    public String getPassword() { return Password; }
    
    public String ToJSON()
    {
        String json = new JSONObject()
                  .put("Username", getUsername())
                  .put("Password", getPassword()).toString();
        System.out.println(json);
        return json;
    }
}