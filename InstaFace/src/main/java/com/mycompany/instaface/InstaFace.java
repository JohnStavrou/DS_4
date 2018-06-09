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
    JLabel NameLabel = new JLabel("Name");
    JLabel Emaillabel = new JLabel("Email");
    JLabel FriendsLabel = new JLabel();


    JPanel LogPanel = new JPanel();
    JPanel MainPanel = new JPanel();
    JPanel SettingsPanel = new JPanel();
    
    JButton SignIn = new JButton("Sign In");
    JButton SignUp = new JButton("Sign Up");
    JButton Settings = new JButton("Settings");
    JButton SignOut = new JButton("Sign Out");
    JButton AddFriend = new JButton("Add Friend");
    JButton Friends = new JButton("Friends");


    JTextField UsernameSignIn = new JTextField();
    JTextField UsernameSignUp = new JTextField();
    JTextField NameTextField = new JTextField();
    JTextField EmailTextField = new JTextField();
    JTextField AddFriendTextField = new JTextField();
    
    JPasswordField PasswordSignIn = new JPasswordField();
    JPasswordField PasswordSignUp = new JPasswordField();

    public InstaFace()
    {
        super("InstaFace");
        InitializeFrame();
    }
    
    public void InitializeFrame()
    {
        SignIn.setFocusable(false);
        SignIn.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                SignIn();
            }
        });
        
        SignUp.setFocusable(false);
        SignUp.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                SignUp();
            }
        });
        
        LogPanel.setLayout(new GridLayout(3, 3));
        LogPanel.add(Username);
        LogPanel.add(Password);
        LogPanel.add(new JLabel());
        LogPanel.add(UsernameSignIn);
        LogPanel.add(PasswordSignIn);
        LogPanel.add(SignIn);
        LogPanel.add(UsernameSignUp);
        LogPanel.add(PasswordSignUp);
        LogPanel.add(SignUp);
        
        Settings.setFocusable(false);
        Settings.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                Settings();
            }
        });
        
        SettingsPanel.setLayout(new GridLayout(0, 2));
        SettingsPanel.add(NameLabel);
        SettingsPanel.add(NameTextField);
        
        SignOut.setFocusable(false);
        SignOut.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                SignOut();
            }
        });
        
        AddFriend.setFocusable(false);
        AddFriend.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                AddFriend();
            }
        });
        
        Friends.setFocusable(false);
        Friends.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                Friends();
            }
        });
        
        MainPanel.setLayout(new GridLayout(0, 3));
        MainPanel.add(UsernameLabel);
        MainPanel.add(Settings);
        MainPanel.add(SignOut);
        MainPanel.add(AddFriendTextField);
        MainPanel.add(AddFriend);
        MainPanel.add(Friends);
        
        setSize(400, 150);
        setBackground(Color.GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        add(LogPanel);
        setVisible(true);
    }
    
    public void SignIn()
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
            OpenMainFrame();
        else if (response.getStatus() == 201)
            JOptionPane.showMessageDialog(null, "Wrong username or password!");
        else
            JOptionPane.showMessageDialog(null, "Something went wrong!");
    }
    
    public void OpenMainFrame()
    {
        setSize(600, 200);
        remove(LogPanel);
        UsernameLabel.setText(User.getUsername());
        add(MainPanel);
    }
    
    public void SignUp()
    {
        String username = UsernameSignUp.getText();
        String password = "";
        for(char c : PasswordSignUp.getPassword())
            password += c;
        
        if(username.length() == 0 || password.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Please enter your credentials!");
            return;
        }
        
        WebTarget target = Client.target(Target + "signup");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new User(username, password).ToJSON()));
        
        if (response.getStatus() == 200)
        {
            JOptionPane.showMessageDialog(null, "Successful sign up!");
            UsernameSignUp.setText("");
            PasswordSignUp.setText("");
        }
        else if (response.getStatus() == 201)
            JOptionPane.showMessageDialog(null, "Username already exists!");
        else
            JOptionPane.showMessageDialog(null, "Something went wrong!");
    }
    
     public void SignOut()
    {
        setSize(400, 150);
        remove(MainPanel);
        UsernameLabel.setText("");
        UsernameSignIn.setText("");
        PasswordSignIn.setText("");
        User = null;
        add(LogPanel);
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
    
    public void Friends()
    {
        WebTarget target = Client.target(Target + "friends");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.getUsername()));
        
        if (response.getStatus() == 200)
        {
            FriendsLabel.setText(response.readEntity(String.class));
            JOptionPane.showConfirmDialog(null, FriendsLabel, "Friends", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null, "Something went wrong!");
    }
    
    public void Settings()
    {
        WebTarget target = Client.target(Target + "settings");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.getUsername()));
        
        if (response.getStatus() == 200)
        {
            
        }
    }
}