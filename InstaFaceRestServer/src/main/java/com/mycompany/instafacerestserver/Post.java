package com.mycompany.instafacerestserver;

import org.json.JSONObject;

public class Post
{
    private int Id;
    private String User1;
    private String User2;
    private String Text;

    public Post(String User1, String User2, String Text)
    {
        this.User1 = User1;
        this.User2 = User2;
        this.Text = Text;
    }
    
    public Post(int Id, String User1, String User2, String Text)
    {
        this.Id = Id;
        this.User1 = User1;
        this.User2 = User2;
        this.Text = Text;
    }
    
    public int getId() { return Id; }
    public String getUser1() { return User1; }
    public String getUser2() { return User2; }
    public String getText() { return Text; }
    
    public String ToJSON()
    {
        return new JSONObject()
                    .put("Id", getId())
                    .put("User1", getUser1())
                    .put("User2", getUser2())
                    .put("Text", getText()).toString();
    }
}