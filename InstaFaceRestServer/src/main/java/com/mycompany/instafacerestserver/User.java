package com.mycompany.instafacerestserver;

import org.json.JSONObject;

public class User
{
    private String Username;
    private String Password;
    private String Name;
    private String Surname;
    private String Email;

    public User(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
    }
    
    public User(String Username, String Name, String Surname, String Email)
    {
        this.Username = Username;
        this.Name = Name;
        this.Surname = Surname;
        this.Email = Email;
    }

    public void setName(String Name) { this.Name = Name; }
    public void setSurname(String Surname) { this.Surname = Surname; }
    public void setEmail(String Email) { this.Email = Email; }

    public String getUsername() { return Username; }
    public String getPassword() { return Password; }
    public String getName() { return Name; }
    public String getSurname() { return Surname; }
    public String getEmail() { return Email; }
    
    public String ToJSON()
    {
        return new JSONObject()
                  .put("Username", getUsername())
                  .put("Password", getPassword())
                  .put("Name", getName())
                  .put("Surname", getSurname())
                  .put("Email", getEmail()).toString();
    }
}