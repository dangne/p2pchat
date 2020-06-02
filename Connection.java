/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.hcmut.demo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author dang
 */
public class Connection implements Runnable {
    private final boolean SERVER = true;
    private final boolean CLIENT = false;
    private final int FRIEND = 0;
    private final int STRANGER = 1;
    private final int SENT = 2;
    private final int RECEIVED = 3; 
    private Socket s;
    private ClientController controller;
    private String username;
    private String IP;
    private int port;
    private boolean type;
    private int state;
    private InputHandler inputHandler;
    private DataOutputStream output;
    private ArrayList<String> chatData;
    private ArrayList<String> log;

    // Actively connect to server
    public Connection(ClientController controller, String IP, int port) {
        try {
            this.controller = controller;
            this.username = "server";
            this.IP = IP;
            this.port = port;
            this.type = SERVER;

            s = new Socket(IP, port);
            
            initialize(this.type);
        }
        catch (IOException e) { 
            System.out.println("Connect to " + username + " failed");
        }
    }
    
    // Actively connect to client
    public Connection(ClientController controller, String username, String IP, int port) {
        try {
            this.controller = controller;
            this.username = username;
            this.IP = IP;
            this.port = port;
            this.type = CLIENT;

            s = new Socket(IP, port); 
            
            initialize(this.type);
            
            // Send init request
            sendMessage("init " + controller.getUsername() + " " + controller.getIP() + " " + controller.getPort());
        }
        catch (IOException e) {
            System.out.println("Connect to " + username + " failed");
        }
    }

    // Passively connect to client
    public Connection(ClientController controller, Socket s) {
        this.s = s;
        
        this.controller = controller;
        this.type = CLIENT;
        
        initialize(this.type);
    }

    public void initialize(boolean type) {
        // Init empty chat data
        chatData = new ArrayList<String>();
        
        // Init input stream
        try {
            DataInputStream input = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            inputHandler = new InputHandler(controller, this, input);
            new Thread(inputHandler).start();
        }
        catch (IOException e) { e.printStackTrace(); }

        // Init output stream
        try { output = new DataOutputStream(s.getOutputStream()); }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    public void submitConnection() {
        controller.addConnection(this);
    }
    
    public void log(String message) {
        log.add(message);
    }

    public void appendChatData(String message) {
        chatData.add(message);
        String selectedUser = controller.getSelectedUser();
        if (selectedUser != null && selectedUser == username) controller.updateChatArea(chatData);
    }
    
    public void sendMessage(String message) {
        System.out.println("Sending message to " + username + ": " + message);
        String[] parts = message.split(" ", 2);
        if (parts[0].equals("message")) {
            appendChatData(controller.getUsername() + ": " + parts[1]);
        }
        try { output.writeUTF(message); }
        catch (IOException e) { e.printStackTrace(); }
    }
    
    public void receiveMessage(String message) {
        appendChatData(username + ": " + message);
    }
    
    public void sendFile(File file) {
        appendChatData("You sent " + username + " a file: " + file.getName());
        sendMessage("file " + file.getName());

        try {
            int totalBytes = 0;
            int count = 0;
            int bytesRead = 0;
            byte[] data = new byte[8192];
            DataInputStream input =
                new DataInputStream(
                    new BufferedInputStream(
                        new FileInputStream(file)));
        
            bytesRead = input.read(data, 0, 8192);
            while (bytesRead >= 0) {
                count++;
                totalBytes += bytesRead;
                System.out.printf("Sending packet %d [%d bytes]...\n", count, bytesRead);
                output.write(data, 0, bytesRead);
                output.flush();
                bytesRead = input.read(data, 0, 8192);
            }

            input.close();

            System.out.printf("Done sending file [Total: %d bytes]\n", totalBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendFriendRequest() {
        if (state == STRANGER) {
            state = SENT;
            appendChatData("Friend request sent");
        } else if (state == SENT) {
            appendChatData("Request already sent");
        } else if (state == RECEIVED) {
            state = FRIEND;
            controller.newFriend(username, IP, port);
            appendChatData("You two are now friends");
        } else if (state == FRIEND) {
        }
        
        System.out.println("Sent friend request. State: " + state);
        sendMessage("friend");
    }
    
    public void receiveFriendRequest() {
        if (state == STRANGER) {
            state = RECEIVED;
            appendChatData("Received friend request");
        } else if (state == SENT) {
            state = FRIEND;
            controller.newFriend(username, IP, port);
            appendChatData("You two are now friends");
        } else if (state == RECEIVED) {
        } else if (state == FRIEND) {
            
        }
        System.out.println("Received friend request. State: " + state);
    }
    
    public void receiveUnfriend() {
        state = STRANGER;
        controller.receiveUnfriend(username);
    }
    
    public void close() {
        System.out.println(username + " has left the chat");
        controller.removeConnection(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIP() {
        return IP;
    }
    
    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean getType() {
        return type;
    }
    
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<String> getChatData() {
        return chatData;
    }

    @Override
    public void run() {
        
    }
}
