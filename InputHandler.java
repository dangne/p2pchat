/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.hcmut.demo;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
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
            case "UPDATE": {
                //connection.sendMessage("YES");
                break;
            }
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
            case "file": {
                try {
                    int totalBytes = 0;
                    int count = 0;
                    int bytesRead = 0;
                    byte[] data = new byte[8192];
                    DataOutputStream output =
                        new DataOutputStream(
                            new BufferedOutputStream(
                                new FileOutputStream("from_" + connection.getUsername() + "_" + parts[1])));
                
                    System.out.println("Receiving " + parts[1] + "...");

                    bytesRead = input.read(data, 0, 8192);
                    while (bytesRead >= 0) {
                        count++;
                        totalBytes += bytesRead;
                        System.out.printf("Writing packet %d [%d bytes]...\n", count, bytesRead);
                        output.write(data, 0, bytesRead);
                        output.flush();
                        if (bytesRead < 8192) {
                            System.out.printf("Done receiving file %s [Total: %d bytes]\n", parts[1], totalBytes);
                            break;
                        }
                        bytesRead = input.read(data, 0, 8192);
                    }
                
                    output.close();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // For server connection
            case "invalid": {
                break;
            }
            default: {
                connection.sendMessage("invalid");
                break;
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readUTF();
                System.out.println("Received message from " + connection.getUsername() + ": " + message);
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
