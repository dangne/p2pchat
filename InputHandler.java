/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.hcmut.demo;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import java.net.Socket;

/**
 *
 * @author dang
 */
public class InputHandler implements Runnable {
    private ClientController controller;
    private Connection connection;
    private DataInputStream input;

    public InputHandler(ClientController controller, Connection connection, DataInputStream input) {
        this.controller = controller;
        this.connection = connection;
        this.input = input;
    }

    public void parseMessage(String message) {
        String[] parts = message.split(" ", 2);

        switch (parts[0]) {
            // For client connection
            case "init": {
                String[] data = parts[1].split(" ");
                
                connection.setUsername(data[0]);
                connection.setIP(data[1]);
                connection.setPort(Integer.parseInt(data[2]));
                connection.submitConnection();
                controller.updateUserListView();
                break;}
            case "message": {
                connection.receiveMessage(parts[1]);
                break;}
            case "friend": {
                connection.receiveFriendRequest();
                break;
            }
            case "unfriend": {
                connection.receiveUnfriend();
                break;
            }
            // For server connection
            case "add": {
                String[] data = parts[1].split(" ");
                controller.addConnection(data[0], data[1], Integer.parseInt(data[2]));
                controller.updateUserListView();
                break;}
            case "done": {
                break;}
            case "invalid": {
                break;}
            default: {
                connection.sendMessage("invalid");
                break;}
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readUTF();
                System.out.println("Received message from " + connection.getUsername() + ": " + message);
//                connection.log(message);
                parseMessage(message);
            }
        } catch (EOFException eof) {
            System.out.println("Connection to " + connection.getUsername() + " closed");
            
            try {
                input.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
