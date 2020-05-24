/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.hcmut.demo;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author dang
 */
public class ClientModel {
    private String FILE_PATH;
    private ClientController controller;
    private String username;
    private String IP;
    private int port;
    private ArrayList<User> friendsList;

    public ClientModel(ClientController controller, String username, int port) {
        this.controller = controller;
        this.username = username;
        this.IP = getLocalAddress();
        this.port = port;
        
        FILE_PATH = "FriendsListPort" + port + ".xml";
        
        friendsList = new ArrayList<User>();
        
        // Check if a friend list exist
        File file = new File(FILE_PATH);
        
        if (file.exists() && !file.isDirectory()) {
            System.out.println("FriendsList exist!");
            readFriendsList();
        } else {
            System.out.println("FriendsList not exist!");
            createFriendsList();
        }
    }

    public void addFriend(String username, String IP, int port) {
        System.out.println("Adding new friend " + username + " to database");
        friendsList.add(new User(username, IP, port));
        writeFriendsList();
    }
    
    public void removeFriend(String username) {
        boolean foundFlag = true;
        
        System.out.println("Removing friend " + username + " from database");
        
        for (int i = 0 ; i < friendsList.size() ; ++i) {
            if (friendsList.get(i).getUsername().equals(username)) {
                friendsList.remove(friendsList.get(i));
                foundFlag = true;
            }
        }
        
        if (foundFlag) {
            writeFriendsList();
        } else {
            System.out.println("Friend " + username + "not found");
        }
    }
    
    public void createFriendsList() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            
            // Set pretty format
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            // Create root element
            Element rootElement = doc.createElement("FriendsList");
            
            // Append root element
            doc.appendChild(rootElement);
            
            // Create source to write from
            DOMSource source = new DOMSource(doc);

            // Write data to console or file
            //transformer.transform(source, new StreamResult(System.out));
            transformer.transform(source, new StreamResult(new File(FILE_PATH)));
            
            System.out.println("New FriendsList created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void writeFriendsList() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            
            // Set pretty format
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            // Create root element
            Element rootElement = doc.createElement("FriendsList");
            
            // Append root element
            doc.appendChild(rootElement);
            
            System.out.println("Writing new FriendsList...");
            
            // Append friend element
            for (User friend : friendsList) {
                System.out.println("Writing: Username: " + friend.getUsername() + ", IP: " + friend.getIP() + ", Port: " + friend.getPort());
                rootElement.appendChild(createUserElement(doc, friend.getUsername(), friend.getIP(), friend.getPort()));
            }
            
            // Create source to write from
            DOMSource source = new DOMSource(doc);

            // Write data to console or file
            //transformer.transform(source, new StreamResult(System.out));
            transformer.transform(source, new StreamResult(new File(FILE_PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readFriendsList() {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(FILE_PATH));

            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("Friend");
            
            System.out.println("Reading FriendList...");
            System.out.println("Found " + nodeList.getLength() + " friends");
            
            for (int i = 0; i < nodeList.getLength(); i++) {
                User user = getUser(nodeList.item(i));
                System.out.println((i+1) + ". Username: " + user.getUsername() + ", IP: " + user.getIP() + ", port: " + user.getPort());
                friendsList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Node createUserElement(Document doc, String username, String IP, int port) {
        Element user = doc.createElement("Friend");

        // Set Username attribute
        user.appendChild(createTextNode(doc, "Username", username));
        
        // Set IP attribute
        user.appendChild(createTextNode(doc, "IP", IP));
        
        // Set Port attribute
        user.appendChild(createTextNode(doc, "Port", Integer.toString(port)));

        return user;
    }

    private Node createTextNode(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
    private User getUser(Node node) {
        String username;
        String IP;
        int port;
        
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            username = getTagValue("Username", element);
            IP = getTagValue("IP", element);
            port = Integer.parseInt(getTagValue("Port", element));
            return new User(username, IP, port);
        }
        return null;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
    
    private String getLocalAddress() {
        Enumeration<NetworkInterface> enumNI;
        Enumeration<InetAddress> enumIA;
        NetworkInterface ni;
        InetAddress ia;

        try {
            for (enumNI = NetworkInterface.getNetworkInterfaces(); enumNI.hasMoreElements();) {
                ni = enumNI.nextElement();
                for (enumIA = ni.getInetAddresses(); enumIA.hasMoreElements();) {
                    ia = enumIA.nextElement();
                    if (!ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                        return ia.getHostAddress().toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean isFriend(String username) {
        for (User user : friendsList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        
        return false;
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

    public void setIP(int port) {
        this.port = port;
    }

    public ArrayList<User> getFriendsList() {
        return friendsList;
    }
}
