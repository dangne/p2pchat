/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hcmut.demo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
    private final int PENDING = 2;
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
            
            // Send init request
            sendMessage("init " + controller.getUsername() + " " + controller.getIP() + " " + controller.getPort());
            
            sendMessage("getuserlist");
        }
        catch (IOException e) { 
            System.out.println("Connect to " + controller.getUsername() + " failed");
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

    public void log(String message) {
        log.add(message);
    }
    
    public void receiveMessage(String message) {
        chatData.add(message);
        controller.reloadChatData(username);
    }
    
    public void sendMessage(String message) {
        System.out.println("Sending message to " + username + ": " + message);
        chatData.add(message);
        try { output.writeUTF(message); }
        catch (IOException e) { e.printStackTrace(); }
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

    public ArrayList<String> getChatData() {
        return chatData;
    }
    
    public void close() {
        System.out.println(username + " has left the chat");
    }

    @Override
    public void run() {
        
    }
}
