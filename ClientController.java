/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hcmut.demo;

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
    private final String SERVER_IP = "35.186.152.171";
    private final int SERVER_PORT = 3000;
    private final int FRIEND = 0;
    private final int STRANGER = 1;
    private final int PENDING = 2;
    private static ClientView clientView;
    private static ClientModel clientModel;
    private static Listener listener;
    private ArrayList<Connection> clientConnections;
    private ArrayList<Connection> clientConnectionsBuffer; // For connections that have not yet init
    private static Connection serverConnection;

    public ClientController() {
        clientView = new ClientView(this);
        
        // Create listener
        listener = new Listener(this);
        
        // Create Model
        clientModel = new ClientModel(this, "Dang" + listener.getPort(), listener.getPort());

        // Init empty connections list
        clientConnections = new ArrayList<Connection>();
        
        // Connect to server
        serverConnection = new Connection(this, SERVER_IP, SERVER_PORT);
        
        // Set view title
        clientView.setTitle(getUsername());
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
    
    public void addConnection(Connection connection) {
        System.out.println("Adding new connection: Username: " + connection.getUsername() + ", IP: " + connection.getIP() + ", Port: " + connection.getPort());
        clientConnections.add(connection);
        
        if (clientModel.isFriend(connection.getUsername())) {
            connection.setState(FRIEND);
        } else {            
            connection.setState(STRANGER);
        }
    }
    
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
    }

    public void removeConnection(String username, String IP) {
        clientConnections.remove(findConnection(username));
    }

    public void removeConnection(String data) {
        String username = data.split(" ")[0];
        clientConnections.remove(findConnection(username));
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
    }
    
    public void receiveUnfriend(String sender) {
        System.out.println(sender + " removed you from being friend :(");
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
    
    public static void main(String[] args) {
        ClientController client = new ClientController();
        clientView.setVisible(true);
    }
}