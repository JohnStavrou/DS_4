package com.mycompany.instafacerestserver;

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
}