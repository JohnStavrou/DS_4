package com.mycompany.instafacerestserver;

public class Post
{
    private String User1;
    private String User2;
    private String Text;

    
    public Post(String User1, String User2, String Text)
    {
        this.User1 = User1;
        this.User2 = User2;
        this.Text = Text;
    }

    public String getUser1() { return User1; }
    public String getUser2() { return User2; }
    public String getText() { return Text; }
}