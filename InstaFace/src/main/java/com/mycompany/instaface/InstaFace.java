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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONObject;

public class InstaFace extends JFrame
{
    User User;
    String Target = "http://localhost:8080/InstaFaceRestServer/api/restserver/";
    Client Client = ClientBuilder.newClient();
    
    JLabel UsernameLabel = new JLabel("", SwingConstants.CENTER);
    JLabel NameSurnameLabel = new JLabel("", SwingConstants.CENTER);
    
    JPanel LogPanel = new JPanel(new GridLayout(0, 4));
    JPanel MainPanel = new JPanel(new GridLayout(0, 4));

    public InstaFace()
    {
        super("InstaFace");
        InitializeFrame();
    }
    
    public void InitializeFrame()
    {
        CreateLogPanel();
        CreateMainPanel();
        
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
        TextField DescriptionTextField = new TextField();
        TextField CountryTextField = new TextField();
        TextField TownTextField = new TextField();
        JTextField UsernameSignIn = new JTextField();
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

                JPanel SignUpPanel = new JPanel(new GridLayout(0, 2));
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
                SignUpPanel.add(new JLabel("Description"));
                SignUpPanel.add(DescriptionTextField);
                SignUpPanel.add(new JLabel("Country"));
                SignUpPanel.add(CountryTextField);
                SignUpPanel.add(new JLabel("Town"));
                SignUpPanel.add(TownTextField);
                
                NameTextField.setText("");
                SurnameTextField.setText("");
                UsernameTextField.setText("");
                PasswordField.setText("");
                MaleRadioButton.setSelected(false);
                FemaleRadioButton.setSelected(false);
                DescriptionTextField.setText("");
                CountryTextField.setText("");
                TownTextField.setText("");
                    
                String name;
                String surname;
                String username;
                char[] password;
                int genre;
                String description;
                String country;
                String town;
                
                boolean valid;
                do
                {
                    Object []options= {"Sign Up", "Cancel"};
                    int option = JOptionPane.showOptionDialog(null, SignUpPanel, "Sign Up", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION, null, options, null);
                    if(option == 0)
                    {
                        name = NameTextField.getText();
                        surname = SurnameTextField.getText();
                        username = UsernameTextField.getText();
                        password = PasswordField.getPassword();
                        description = DescriptionTextField.getText();
                        country = CountryTextField.getText();
                        town = TownTextField.getText();

                        valid = true;
                        if(name.length() == 0
                            || surname.length() == 0
                            || username.length() == 0
                            || password.length == 0
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
                    genre = 1;
                else
                    genre = 2;
                
                WebTarget target = Client.target(Target + "signup");
                Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new User(name, surname, username, Hash(password), genre, description, country, town).ToJSON()));

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
                char[] password = PasswordSignIn.getPassword();

                if(username.length() == 0 || password.length == 0)
                {
                    JOptionPane.showMessageDialog(null, "Please enter your credentials!");
                    return;
                }

                User = new User(username, Hash(password));
                WebTarget target = Client.target(Target + "signin");
                Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.ToJSON()));

                if (response.getStatus() == 200)
                {
                    UsernameSignIn.setText("");
                    PasswordSignIn.setText("");
                    
                    setSize(600, 200);
                    remove(LogPanel);
                    
                    JSONObject json = new JSONObject(response.readEntity(String.class));
                    User = new User(json.getString("Name"), json.getString("Surname"), json.getString("Username"), json.getString("Password"), json.getInt("Genre"), json.getString("Description"), json.getString("Country"), json.getString("Town"));
                    NameSurnameLabel.setText(User.getName() + " " + User.getSurname());
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
    
    public void CreateMainPanel()
    {
        JButton SettingsButton = new JButton("Settings");
        JButton SignOutButton = new JButton("Sign Out");
        JTextField AddFriendTextField = new JTextField();
        JButton AddFriendButton = new JButton("Add");
        JButton ShowFriendsButton = new JButton("Friends");
        JTextField DeleteFriendTextField = new JTextField();
        JButton DeleteFriendButton = new JButton("Delete");

        SettingsButton.setFocusable(false);
        SettingsButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                //Settings();
            }
        });
        
        SignOutButton.setFocusable(false);
        SignOutButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                setSize(400, 100);
                remove(MainPanel);
                add(LogPanel);
            }
        });
        
        AddFriendButton.setFocusable(false);
        AddFriendButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                String friend = AddFriendTextField.getText();
        
                if(friend.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Field is empty!");
                    return;
                }

                if(friend.equals(User.getUsername()))
                {
                    JOptionPane.showMessageDialog(null, "Wrong input!");
                    return;
                }

                WebTarget target = Client.target(Target + "addfriend");
                Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new Friendship(User.getUsername(), friend).ToJSON()));

                if (response.getStatus() == 200)
                    JOptionPane.showMessageDialog(null, "Friend added successfully!");
                else if (response.getStatus() == 201)
                    JOptionPane.showMessageDialog(null, "Already friends with user " + friend + "!");
                else if (response.getStatus() == 202)
                    JOptionPane.showMessageDialog(null, "User " + friend + " does not exist!");
                else
                    JOptionPane.showMessageDialog(null, "Something went wrong!");

                AddFriendTextField.setText("");
            }
        });
        
        ShowFriendsButton.setFocusable(false);
        ShowFriendsButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                WebTarget target = Client.target(Target + "friends");
                Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(User.ToJSON()));

                if (response.getStatus() == 200)
                {
                    String[] friends = response.readEntity(String.class).split("\n");
                    JPanel ShowFriendsPanel = new JPanel();
                    ShowFriendsPanel.setLayout(new GridLayout(0, 7));
                    ShowFriendsPanel.add(new JLabel("Name", SwingConstants.CENTER));
                    ShowFriendsPanel.add(new JLabel("Surname", SwingConstants.CENTER));
                    ShowFriendsPanel.add(new JLabel("Username", SwingConstants.CENTER));
                    ShowFriendsPanel.add(new JLabel("Genre", SwingConstants.CENTER));
                    ShowFriendsPanel.add(new JLabel("Description", SwingConstants.CENTER));
                    ShowFriendsPanel.add(new JLabel("Country", SwingConstants.CENTER));
                    ShowFriendsPanel.add(new JLabel("Town", SwingConstants.CENTER));
                    ShowFriendsPanel.add(new JLabel());
                    ShowFriendsPanel.add(new JLabel());
                    ShowFriendsPanel.add(new JLabel());
                    ShowFriendsPanel.add(new JLabel());
                    ShowFriendsPanel.add(new JLabel());
                    ShowFriendsPanel.add(new JLabel());
                    ShowFriendsPanel.add(new JLabel());
                    
                    for(String friend : friends)
                    {
                        JSONObject json  = new JSONObject(friend);
                        User user = new User(json.getString("Name"), json.getString("Surname"), json.getString("Username"), json.getString("Password"), json.getInt("Genre"), json.getString("Description"), json.getString("Country"), json.getString("Town"));
                        ShowFriendsPanel.add(new JLabel(user.getName(), SwingConstants.CENTER));
                        ShowFriendsPanel.add(new JLabel(user.getSurname(), SwingConstants.CENTER));
                        ShowFriendsPanel.add(new JLabel(user.getUsername(), SwingConstants.CENTER));
                        ShowFriendsPanel.add(new JLabel(user.getGenreStr(), SwingConstants.CENTER));
                        ShowFriendsPanel.add(new JLabel(user.getDescription(), SwingConstants.CENTER));
                        ShowFriendsPanel.add(new JLabel(user.getCountry(), SwingConstants.CENTER));
                        ShowFriendsPanel.add(new JLabel(user.getTown(), SwingConstants.CENTER));
                    }
                    
                    JOptionPane.showConfirmDialog(null, ShowFriendsPanel, "Friends", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                }
                else if(response.getStatus() == 201)
                    JOptionPane.showMessageDialog(null, "There are no users in your friendlist!");
                else
                    JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
        });
        
        DeleteFriendButton.setFocusable(false);
        DeleteFriendButton.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                String friend = DeleteFriendTextField.getText();
        
                if(friend.length() == 0)
                {
                    JOptionPane.showMessageDialog(null, "Field is empty!");
                    return;
                }

                if(friend.equals(User.getUsername()))
                {
                    JOptionPane.showMessageDialog(null, "Wrong input!");
                    return;
                }

                WebTarget target = Client.target(Target + "deletefriend");
                Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(new Friendship(User.getUsername(), friend).ToJSON()));

                if (response.getStatus() == 200)
                    JOptionPane.showMessageDialog(null, "Friend deleted successfully!");
                else if (response.getStatus() == 201)
                    JOptionPane.showMessageDialog(null, "Not friends with user " + friend + "!");
                else if (response.getStatus() == 202)
                    JOptionPane.showMessageDialog(null, "User " + friend + " does not exist!");
                else
                    JOptionPane.showMessageDialog(null, "Something went wrong!");

                DeleteFriendTextField.setText("");
            }
        });
        
        MainPanel.add(NameSurnameLabel);
        MainPanel.add(UsernameLabel);
        MainPanel.add(SettingsButton);
        MainPanel.add(SignOutButton);
        MainPanel.add(new JLabel("Add Friend", SwingConstants.CENTER));
        MainPanel.add(AddFriendTextField);
        MainPanel.add(AddFriendButton);
        MainPanel.add(ShowFriendsButton);
        MainPanel.add(new JLabel("Delete Friend", SwingConstants.CENTER));
        MainPanel.add(DeleteFriendTextField);
        MainPanel.add(DeleteFriendButton);
    }
    
    public String Hash(char[] passwordArr)
    {
        try
        {
            String password = "";
            for(char c : passwordArr)
                password += c;
            
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(password.getBytes("UTF-8"), 0, password.length());
            return DatatypeConverter.printHexBinary(msdDigest.digest());
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e)
        {
            System.err.println("Something went wrong (HashPassword)!");
        }
        
        return null;
    }
}