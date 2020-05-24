/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.hcmut.demo;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author dang
 */
public class ClientController {
    //private final String SERVER_IP = "35.186.152.171";
    //private final int SERVER_PORT = 3000;
    private final int FRIEND = 0;
    private final int STRANGER = 1;
    private final int SENT = 2;
    private final int RECEIVED = 3; 
    private String serverIP;
    private int serverPort;
    private int port;
    private static ClientView clientView;
    private static ClientModel clientModel;
    private static Listener listener;
    private ArrayList<Connection> clientConnections;
    private ArrayList<Connection> clientConnectionsBuffer; // For connections that have not yet init
    private static Connection serverConnection;

    public ClientController() {
        new LoginView(this).setVisible(true);
    }

    public void onLoginSuccess(String serverIP, int serverPort, int portPeer, String username, String msg) {
        this.serverIP = serverIP.substring(1);
        this.serverPort = serverPort;
        System.out.println("serverIP " + this.serverIP);
        System.out.println("serverPort " + serverPort);
        System.out.println("portPeer " + portPeer);
        System.out.println("username " + username);
        System.out.println("msg " + msg);
        initComponents(username, portPeer, msg);
    }

    public void initComponents(String username, int port, String msg) {
        clientView = new ClientView(this);
        
        // Create listener
        listener = new Listener(this, port);
        
        // Create Model
        clientModel = new ClientModel(this, username, port);

        // Init empty connections list
        clientConnectionsBuffer = new ArrayList<Connection>();
        clientConnections = new ArrayList<Connection>();
        
        // Connect to server
        serverConnection = new Connection(this, serverIP, serverPort);

        // Connect to other clients in the network
        String[] parts = msg.split(" ");
        
        for (int i = 0 ; i < parts.length ; i += 3) {
            if (parts[i].equals(username)) continue;
            addConnection(parts[i], parts[i+1], Integer.parseInt(parts[i+2]));
        }

        updateUserListView();
        
        // Set view title
        clientView.setTitle(getUsername());
        clientView.setVisible(true);
    }
    
    public void updateChatArea(ArrayList<String> chatData) {
        clientView.updateChatArea(chatData);
    }
    
    public Connection findConnection(String username) {
        for (Connection connection : clientConnections) {
            if (connection.getUsername().equals(username)) {
                return connection;
            }
        }

        return null;
    }

    public void addConnectionBuffer(Connection connection) {
        clientConnectionsBuffer.add(connection);
    }
    
    // Adding connection method for listener
    public void addConnection(Connection connection) {
        System.out.println("Adding new connection: Username: " + connection.getUsername() + ", IP: " + connection.getIP() + ", Port: " + connection.getPort());
        clientConnections.add(connection);
        
        if (clientModel.isFriend(connection.getUsername())) {
            connection.setState(FRIEND);
        } else {            
            connection.setState(STRANGER);
        }
    }
    
    // Adding connection method for others 
    public void addConnection(String username, String IP, int port) {
        System.out.println("Adding new connection: Username: " + username + ", IP: " + IP + ", Port:" + port);
        Connection connection = new Connection(this, username, IP, port);
        clientConnections.add(connection);
        
        if (clientModel.isFriend(connection.getUsername())) {
            connection.setState(FRIEND);
        } else {            
            connection.setState(STRANGER);
        }
    }

    public void removeConnection(Connection connection) {
        clientConnections.remove(connection);
        updateUserListView();
    }
    
    public void updateUserListView() {        
        ArrayList<String> strangersList = new ArrayList<String>();
        ArrayList<String> friendsList = new ArrayList<String>();
        
        for (Connection c : clientConnections) {
            if (c.getState() == FRIEND) {
                friendsList.add(c.getUsername());
            } else {
                strangersList.add(c.getUsername());
            }
        }
        
        clientView.updateUserList(strangersList, friendsList);  
    }

    public void sendMessage(String receiver, String message) {
        findConnection(receiver).sendMessage("message " + message);
    }
    
    public void sendMessage(Connection receiver, String message) {
        receiver.sendMessage("message " + message);
    }
    
    public void sendFile(String receiver, File file) {
        System.out.println("Sending " + file.getAbsolutePath() + "...");
    }
    
    public void sendFriendRequest(String receiver) {
        System.out.println("Request sent to " + receiver);
        findConnection(receiver).sendFriendRequest();
    }
    
    public void newFriend(String username, String IP, int port) {
        clientModel.addFriend(username, IP, port);
        updateUserListView();
    }
    
    public void removeFriend(String receiver) {
        System.out.println("Removed friend " + receiver);
        Connection connection = findConnection(receiver);
        connection.sendMessage("unfriend");
        connection.setState(STRANGER);
        clientModel.removeFriend(receiver);
        updateUserListView();
        connection.appendChatData("You two are no longer friends");
    }
    
    public void receiveUnfriend(String sender) {
        findConnection(sender).appendChatData(sender + " removed you from being friend :(");
        clientModel.removeFriend(sender);
        updateUserListView();
    }
    
    public String register(String username, String password) {
        // Return status as string
        return null;
    }

    public String login(String username, String password) {
        // Return status as string
        return null;
    }

    public String getUsername() {
        return clientModel.getUsername();
    }
    
    public String getIP() {
        return clientModel.getIP();
    }
    
    public int getPort() {
        return listener.getPort();
    }
    
    public String getSelectedUser() {
        return clientView.getSelectedUser();
    }
    
    public ArrayList<String> getChatData(String username) {
        Connection connection = findConnection(username);
        if (connection != null) {
            return connection.getChatData();
        } 
        return null;
    }
    
    public static void main(String[] args) {
        ClientController client = new ClientController();
    }
}
