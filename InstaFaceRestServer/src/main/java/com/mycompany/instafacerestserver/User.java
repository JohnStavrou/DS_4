package com.mycompany.instafacerestserver;

import org.json.JSONObject;

public class User
{
    private String Name;
    private String Surname;
    private String Username;
    private String Password;
    private int Genre;
    private String Description;
    private String Country;
    private String Town;

    public User(String Name, String Surname, String Username, String Password, int Genre, String Description, String Country, String Town)
    {
        this.Name = Name;
        this.Surname = Surname;
        this.Username = Username;
        this.Password = Password;
        this.Genre = Genre;
        this.Description = Description;
        this.Country = Country;
        this.Town = Town;
    }
    
    public User(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
    }

    public String getName() { return Name; }
    public String getSurname() { return Surname; }
    public String getUsername() { return Username; }
    public String getPassword() { return Password; }
    public int getGenre() { return Genre; }
    public String getDescription() { return Description; }
    public String getCountry() { return Country; }
    public String getTown() { return Town; }
    
    public String ToJSON()
    {
        return new JSONObject()
                    .put("Name", getName())
                    .put("Surname", getSurname())
                    .put("Username", getUsername())
                    .put("Password", getPassword())
                    .put("Genre", getGenre())
                    .put("Description", getDescription())
                    .put("Country", getCountry())
                    .put("Town", getTown()).toString();
    }
}