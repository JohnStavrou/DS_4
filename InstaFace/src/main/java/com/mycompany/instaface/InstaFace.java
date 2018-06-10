package com.mycompany.instaface;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstaFace extends JFrame
{
    User User;
    String Target = "http://localhost:8080/InstaFaceRestServer/api/restserver/";
    Client Client = ClientBuilder.newClient();

    JLabel Username = new JLabel("Username", SwingConstants.CENTER);
    JLabel Password = new JLabel("Password", SwingConstants.CENTER);
    JLabel UsernameLabel = new JLabel("", SwingConstants.CENTER);
    JLabel PasswordLabel = new JLabel("Password");
    JLabel NameLabel = new JLabel("Name");
    JLabel SurnameLabel = new JLabel("Surname");
    JLabel FriendsLabel = new JLabel();

    JPanel LogPanel = new JPanel();
    JPanel MainPanel = new JPanel();
    JPanel SettingsPanel = new JPanel();
    
    JButton SettingsButton = new JButton("Settings");
    JButton SignOutButton = new JButton("Sign Out");
    JButton AddFriendButton = new JButton("Add Friend");
    JButton ShowFriendsButton = new JButton("Friends");

    JTextField AddFriendTextField = new JTextField();

    public InstaFace()
    {
        super("InstaFace");
        InitializeFrame();
    }
    
    public void InitializeFrame()
    {
        
        CreateLogPanel();
        //CreateMainPanel();
        
        SettingsButton.setFocusable(false);
        SettingsButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                Settings();
            }
        });
        /*
        SettingsPanel.setLayout(new GridLayout(0, 2));
        SettingsPanel.add(PasswordLabel);
        SettingsPanel.add(PasswordField);
        SettingsPanel.add(NameLabel);
        SettingsPanel.add(NameTextField);
        SettingsPanel.add(SurnameLabel);
        SettingsPanel.add(SurnameTextField);
        SettingsPanel.add(EmailLabel);
        SettingsPanel.add(EmailTextField);*/
        
        SignOutButton.setFocusable(false);
        SignOutButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                setSize(400, 100);
                remove(MainPanel);
                UsernameLabel.setText("");

                User = null;
                add(LogPanel);
            }
        });
        
        AddFriendButton.setFocusable(false);
        AddFriendButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                AddFriend();
            }
        });
        
        ShowFriendsButton.setFocusable(false);
        ShowFriendsButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                ShowFriends();
            }
        });
        
        MainPanel.setLayout(new GridLayout(0, 3));
        MainPanel.add(UsernameLabel);
        MainPanel.add(SettingsButton);
        MainPanel.add(SignOutButton);
        MainPanel.add(AddFriendTextField);
        MainPanel.add(AddFriendButton);
        MainPanel.add(ShowFriendsButton);
        
        setSize(400, 100);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        add(LogPanel);
        setVisible(true);
    }
    
    public void CreateLogPanel()
    {
        JButton SignUpButton = new JButton("Sign Up");
        TextField NameTextField = new TextField();
        TextField SurnameTextField = new TextField();
        TextField UsernameTextField = new TextField();
        JPasswordField PasswordField = new JPasswordField();
        JRadioButton MaleRadioButton = new JRadioButton("Male");
        JRadioButton FemaleRadioButton = new JRadioButton("Female");
        TextField CountryTextField = new TextField();
        TextField TownTextField = new TextField();
        JTextField UsernameSignIn = new JTextField();//auta kena meta
        JPasswordField PasswordSignIn = new JPasswordField();
        JButton SignInButton = new JButton("Sign In");
        
        SignUpButton.setFocusable(false);
        SignUpButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                MaleRadioButton.addMouseListener(new java.awt.event.MouseAdapter()
                {
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                        if(MaleRadioButton.isSelected() && FemaleRadioButton.isSelected())
                            FemaleRadioButton.setSelected(false);
                    }
                });
                
                FemaleRadioButton.addMouseListener(new java.awt.event.MouseAdapter()
                {
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                        if(FemaleRadioButton.isSelected() && MaleRadioButton.isSelected())
                            MaleRadioButton.setSelected(false);
                    }
                });     
                
                JPanel SignUpPanel = new JPanel();
                SignUpPanel.setLayout(new GridLayout(0, 2));
                SignUpPanel.add(new JLabel("Name"));
                SignUpPanel.add(NameTextField);
                SignUpPanel.add(new JLabel("Surname"));
                SignUpPanel.add(SurnameTextField);
                SignUpPanel.add(new JLabel("Username"));
                SignUpPanel.add(UsernameTextField);
                SignUpPanel.add(new JLabel("Password"));
                SignUpPanel.add(PasswordField);
                SignUpPanel.add(MaleRadioButton);
                SignUpPanel.add(FemaleRadioButton);
                SignUpPanel.add(new JLabel("Country"));
                SignUpPanel.add(CountryTextField);
                SignUpPanel.add(new JLabel("Town"));
                SignUpPanel.add(TownTextField);
                
                String name;
                String surname;
                String username;
                String password;
                String genre;
                String country;
                String town;
                
                boolean valid;
                do
                {
                    NameTextField.setText("");
                    SurnameTextField.setText("");
                    UsernameTextField.setText("");
                    PasswordField.setText("");
                    MaleRadioButton.setSelected(false);
                    FemaleRadioButton.setSelected(false);
                    CountryTextField.setText("");
                    TownTextField.setText("");
                    
                    Object []options= {"Sign Up", "Cancel"};
                    int option = JOptionPane.showOptionDialog(null, SignUpPanel, "Sign Up", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, null);
                    if(option == 0)
                    {
                        name = NameTextField.getText();
                        surname = SurnameTextField.getText();
                        username = UsernameTextField.getText();
                        password = "";
                        for(char c : PasswordField.getPassword())
                            password += c;
                        
                        country = CountryTextField.getText();
                        town = TownTextField.getText();

                        valid = true;
                        if(name.length() == 0
                            || surname.length() == 0
                            || username.length() == 0
                            || password.length() == 0
                            || (!MaleRadioButton.isSelected() && !FemaleRadioButton.isSelected())
                            || country.length() == 0
                            || town.length() == 0)
                        {
                            JOptionPane.showMessageDialog(null, "Please enter your credentials!");
                            valid = false;
                        }
                    }
                    else return;
                } while(!valid);
                
                if(MaleRadioButton.isSelected())
                    genre = "Male";
                else
                    genre = "Female";
                
                WebTarget target = Client.target(Target + "signup");
                Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new User(name, surname, username, password, genre, country, town).ToJSON()));

                if (response.getStatus() == 200)
                    JOptionPane.showMessageDialog(null, "Successful sign up!");
                else if (response.getStatus() == 201)
                    JOptionPane.showMessageDialog(null, "Username already exists!");
                else
                JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
        });
        
        SignInButton.setFocusable(false);
        SignInButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                String username = UsernameSignIn.getText();
                String password = "";
                for(char c : PasswordSignIn.getPassword())
                    password += c;

                if(username.length() == 0 || password.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please enter your credentials!");
                    return;
                }

                User = new User(username, password);
                WebTarget target = Client.target(Target + "signin");
                Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.ToJSON()));

                if (response.getStatus() == 200)
                {
                    UsernameSignIn.setText("");
                    PasswordSignIn.setText("");
                    
                    setSize(600, 200);
                    remove(LogPanel);
                    UsernameLabel.setText(User.getUsername());
                    add(MainPanel);
                }
                else if (response.getStatus() == 201)
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                else
                    JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
        });

        LogPanel.setLayout(new GridLayout(0, 4));
        LogPanel.add(new JLabel("Username", SwingConstants.CENTER));
        LogPanel.add(new JLabel("Password", SwingConstants.CENTER));
        LogPanel.add(new JLabel());
        LogPanel.add(SignUpButton);
        LogPanel.add(UsernameSignIn);
        LogPanel.add(PasswordSignIn);
        LogPanel.add(SignInButton);
    }
    
    public void Settings()
    {
        Object []options= {"Save", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, SettingsPanel, "Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, null);
        if(option == 0)
        {
            
            
            WebTarget target = Client.target(Target + "settings");
            Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.getUsername()));
        
        if (response.getStatus() == 200)
        {
            
        }
        }


//        WebTarget target = Client.target(Target + "settings");
//        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.getUsername()));
//        
//        if (response.getStatus() == 200)
//        {
//            
//        }
    }
    
    public void AddFriend()
    {
        String user2 = AddFriendTextField.getText();
        
        if(user2.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Field is empty!");
            return;
        }
        
        if(user2.equals(User.getUsername()))
        {
            JOptionPane.showMessageDialog(null, "Wrong input!");
            return;
        }
        
        WebTarget target = Client.target(Target + "addfriend");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new Friendship(User.getUsername(), user2).ToJSON()));
        
        if (response.getStatus() == 200)
        {
            JOptionPane.showMessageDialog(null, "Friend added successfully!");
            AddFriendTextField.setText("");
        }
        else if (response.getStatus() == 201)
            JOptionPane.showMessageDialog(null, "Already friends with user " + user2 + "!");
        else if (response.getStatus() == 202)
            JOptionPane.showMessageDialog(null, "User " + user2 + " does not exist!");
        else
            JOptionPane.showMessageDialog(null, "Something went wrong!");
    }
    
    public void ShowFriends()
    {
        WebTarget target = Client.target(Target + "showfriends");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.getUsername()));
        
        if (response.getStatus() == 200)
        {
            FriendsLabel.setText(response.readEntity(String.class));
            JOptionPane.showConfirmDialog(null, FriendsLabel, "Show Friends", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "Something went wrong!");
    }
    
    
}