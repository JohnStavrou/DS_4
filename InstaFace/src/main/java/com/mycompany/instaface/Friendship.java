package com.mycompany.instaface;

import org.json.JSONObject;

public class Friendship
{
    private String User1;
    private String User2;
    
    public Friendship(String User1, String User2)
    {
        this.User1 = User1;
        this.User2 = User2;
    }

    public String getUser1() { return User1; }
    public String getUser2() { return User2; }
    
    public String ToJSON()
    {
        return new JSONObject()
                  .put("User1", getUser1())
                  .put("User2", getUser2()).toString();
    }
}