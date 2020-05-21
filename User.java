/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hcmut.demo;

import java.net.Socket;

/**
 *
 * @author dang
 */
public class User{
    private String username;
    private String IP;
    private int port;
    private boolean isFriend;
    
    public User(String username, String IP, int port, boolean isFriend) {
        this.username = username;
        this.IP = IP;
        this.port = port;
        this.isFriend = isFriend;
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
    
    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }
    
    public boolean isFriend() {
        return isFriend;
    }
}
