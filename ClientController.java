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
    private static ClientView clientView;
    private static ClientModel clientModel;
    private static Listener listener;
    private ArrayList<Connection> clientConnections;
    private static Connection serverConnection;
    private String username;
    private String IP;
    private static boolean doneFlag = false;

    public ClientController() {
        clientView = new ClientView(this);
        
        // Create listener
        listener = new Listener(this);
        
        // Create Model
        clientModel = new ClientModel(this, "Dang", listener.getPort());

        // Init empty connections list
        clientConnections = new ArrayList<Connection>();
        
        // Connect to server
        serverConnection = new Connection(this, SERVER_IP, SERVER_PORT);
//
//        // Set done flag value
//        doneFlag = true;
    }

    public void reloadChatData(String username) {
        reloadChatData(findConnection(username).getChatData());
    }
    
    public void reloadChatData(ArrayList<String> chatData) {
        // TODO: clear view's current chat
        // TODO: reload new chat data
    }
    
    public void enableView() {
        clientView.setVisible(true);
    }
    
    public Connection findConnection(String username) {
        for (Connection connection : clientConnections) {
            if (connection.getUsername().equals(username)) {
                return connection;
            }
        }

        return null;
    }

    public void addConnection(Connection connection) {
        System.out.println("Adding new connection: Username: " + connection.getUsername() + ", IP: " + connection.getIP() + ", Port: " + connection.getPort());
        clientConnections.add(connection);
    }

    public void addConnection(String username, String IP, int port) {
        System.out.println("Adding new connection: Username: " + username + ", IP: " + IP + ", Port:" + port);
        clientConnections.add(new Connection(this, username, IP, port));
        clientModel.addFriend(username, IP, port);
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

    public void sendMessage(String receiver, String message) {
        findConnection(receiver).sendMessage(message);
    }
    
    public void sendMessage(Connection receiver, String message) {
        receiver.sendMessage(message);
    }
    
    public void sendFile(String receiver, File file) {
        System.out.println("Sending " + file.getAbsolutePath() + "...");
    }
    
    public void sendFriendRequest(String receiver) {
        System.out.println("Request sent to " + receiver);
    }
    
    public void removeFriend(String receiver) {
        System.out.println("Removed friend " + receiver);
    }
    
    private void getUserListFromServer() {
        serverConnection.sendMessage("getuserlist");
    }

    public void setDoneFlag(boolean doneFlag) {
        this.doneFlag = doneFlag;
    }

    public String register(String username, String password) {
        // Return status as string
        return null;
    }

    public String login(String username, String password) {
        // Return status as string
        return null;
    }

    public static void main(String[] args) {
        ClientController client = new ClientController();
//        while (!doneFlag) {}
        client.sendMessage(serverConnection, "init " + clientModel.getUsername() + " " + clientModel.getIP() + " " + listener.getPort());
        client.getUserListFromServer();
//        while (!doneFlag) {}
        client.enableView();
    }
}