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
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstaFace extends JFrame
{
    String Target = "http://localhost:8080/InstaFaceRestServer/api/restserver/";
    Client Client = ClientBuilder.newClient();

    JLabel Username = new JLabel("Username", SwingConstants.CENTER);
    JLabel Password = new JLabel("Password", SwingConstants.CENTER);
    
    JButton Button = new JButton();
    
    
    JPanel LogPanel = new JPanel();
    JPanel ImagePanel = new JPanel();
    JPanel ButtonPanel = new JPanel();
    JPanel MainPanel = new JPanel();
    
    JButton SignIn = new JButton("Sign In");
    JButton SignUp = new JButton("Sign Up");
        
    JTextField UsernameSignIn = new JTextField();
    JTextField UsernameSignUp = new JTextField();
    
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
        String password = Arrays.toString(PasswordSignIn.getPassword());
        
        if(username.length() == 0 || password.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Please enter your credentials.");
            return;
        }
        /*
        WebTarget target = Client.target(Target + "signin");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(user.ToJSON()));
        
        if (res.getStatus() == 200)
            System.out.println("OK");
        else
            System.out.println("Something went wrong");*/
    }
    
    public void SignUp()
    {
        String username = UsernameSignUp.getText();
        String password = "";
        for(char c : PasswordSignUp.getPassword())
            password += c;
        
        if(username.length() == 0 || password.length() == 0)
        {
            JOptionPane.showMessageDialog(null, "Please enter your credentials.");
            return;
        }
        
        WebTarget target = Client.target(Target + "signup");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new User(username, "1234").ToJSON()));
        
        if (response.getStatus() == 200)
            JOptionPane.showMessageDialog(null, "Successful sign up!");
        else if (response.getStatus() == 201)
            JOptionPane.showMessageDialog(null, "Username already exists!");
        else
            JOptionPane.showMessageDialog(null, "Something went wrong!");
    }
}