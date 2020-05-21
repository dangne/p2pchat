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
    private Socket s;
    private ClientController controller;
    private String username;
    private String IP;
    private int port;
    private boolean type;
    private InputHandler inputHandler;
    private DataOutputStream output;
    private ArrayList<String> chatData;

    // For server connection
    public Connection(ClientController controller, String IP, int port) {
        try { s = new Socket(IP, port); }
        catch (IOException e) { e.printStackTrace(); }

        this.controller = controller;
        this.username = "server";
        this.IP = IP;
        this.port = port;
        this.type = SERVER;

        initComponents();
    }

    // For client connecion
    public Connection(ClientController controller, Socket s) {
        this.s = s;
        
        this.controller = controller;
        // TODO: How to deal with this?
    }
    
    public Connection(ClientController controller, String username, String IP, int port) {
        try { s = new Socket(IP, port); }
        catch (IOException e) { e.printStackTrace(); }

        this.controller = controller;
        this.username = username;
        this.IP = IP;
        this.port = port;
        this.type = CLIENT;

        initComponents();
    }

    public void initComponents() {
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

    public void receiveMessage(String message) {
        chatData.add(message);
        controller.reloadChatData(username);
    }
    
    public void sendMessage(String message) {
        System.out.println("Sending message: " + message);
        chatData.add(message);
        try { output.writeUTF(message); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public String getUsername() {
        return username;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
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
