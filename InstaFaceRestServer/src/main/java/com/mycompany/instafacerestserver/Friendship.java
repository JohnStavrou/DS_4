package com.mycompany.instafacerestserver;

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
}