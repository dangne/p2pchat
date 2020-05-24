/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.hcmut.demo;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import static javax.swing.SwingUtilities.isLeftMouseButton;

/**
 *
 * @author dang
 */
public class ClientView extends javax.swing.JFrame {

    /**
     * Creates new form ClientView
     */
    public ClientView(ClientController clientController) {
        ADD_FRIEND_ICON = new javax.swing.ImageIcon("/home/dang/Pictures/add_friend.png");
        REMOVE_FRIEND_ICON = new javax.swing.ImageIcon("/home/dang/Pictures/remove_friend.png");
        
        this.clientController = clientController;
        previousFindText = "";
        previousMessageText = "";
        initComponents();
        friendListModel = new javax.swing.DefaultListModel<String>();
        friendList.setModel(friendListModel);
        friendList.setSelectedIndex(0);
        strangerListModel = new javax.swing.DefaultListModel<String>();
        strangerList.setModel(strangerListModel);
        strangerList.setSelectedIndex(0);
        updateSelectedUser();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        chatPanel = new javax.swing.JPanel();
        chatScroll = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        headerPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        friendButton = new javax.swing.JButton();
        inputPanel = new javax.swing.JPanel();
        sendFileButton = new javax.swing.JButton();
        sendMessageButton = new javax.swing.JButton();
        messageTextField = new javax.swing.JTextField();
        userPanel = new javax.swing.JPanel();
        userTabbedPane = new javax.swing.JTabbedPane();
        friendListScroll = new javax.swing.JScrollPane();
        friendList = new javax.swing.JList<>();
        strangerListScroll = new javax.swing.JScrollPane();
        strangerList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        chatScroll.setViewportView(chatTextArea);

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chatScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chatScroll)
        );

        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setIcon(new javax.swing.ImageIcon("/home/dang/Pictures/on.png")); // NOI18N
        statusLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        nameLabel.setText("Minh Dang");

        friendButton.setIcon(new javax.swing.ImageIcon("/home/dang/Pictures/add_friend.png")); // NOI18N
        friendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerPanelLayout.createSequentialGroup()
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(friendButton))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(friendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sendFileButton.setText("File");
        sendFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendFileButtonMouseClicked(evt);
            }
        });

        sendMessageButton.setText("Send");
        sendMessageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendMessageButtonMouseClicked(evt);
            }
        });

        messageTextField.setText("Say something...");
        messageTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                messageTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                messageTextFieldFocusLost(evt);
            }
        });
        messageTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                messageTextFieldKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addComponent(sendFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sendMessageButton))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sendFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sendMessageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(messageTextField)
        );

        userTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                userTabbedPaneStateChanged(evt);
            }
        });

        friendList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                friendListMouseReleased(evt);
            }
        });
        friendListScroll.setViewportView(friendList);

        userTabbedPane.addTab("Friends", friendListScroll);

        strangerList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                strangerListMouseReleased(evt);
            }
        });
        strangerListScroll.setViewportView(strangerList);

        userTabbedPane.addTab("Strangers", strangerListScroll);

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(headerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void messageTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_messageTextFieldFocusGained
        messageTextField.setText(previousMessageText);
    }//GEN-LAST:event_messageTextFieldFocusGained

    private void messageTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_messageTextFieldFocusLost
        previousMessageText = messageTextField.getText();
        if (previousMessageText.equals("")) {
            messageTextField.setText(DEFAULT_MESSAGE_TEXT);
        }
    }//GEN-LAST:event_messageTextFieldFocusLost

    private void messageTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_messageTextFieldKeyReleased
        String messageText = messageTextField.getText();
        if (selectedUser != null && enableSendMessage) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER && !messageText.equals(DEFAULT_MESSAGE_TEXT)) {
                clientController.sendMessage(selectedUser, messageText);
            }
        }
    }//GEN-LAST:event_messageTextFieldKeyReleased

    private void sendMessageButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendMessageButtonMouseClicked
        String messageText = messageTextField.getText();
        if (selectedUser != null && enableSendMessage) {
            if (isLeftMouseButton(evt) && !messageText.equals(DEFAULT_MESSAGE_TEXT)) {
                clientController.sendMessage(selectedUser, messageText);
            }
        }
    }//GEN-LAST:event_sendMessageButtonMouseClicked

    private void sendFileButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendFileButtonMouseClicked
        if (selectedUser != null && enableSendFile && isLeftMouseButton(evt)) {
            int result = fileChooser.showOpenDialog(this);
            
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                clientController.sendFile(selectedUser, selectedFile);
            }
        }
    }//GEN-LAST:event_sendFileButtonMouseClicked

    private void friendListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendListMouseReleased
        updateSelectedUser();
    }//GEN-LAST:event_friendListMouseReleased

    private void userTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_userTabbedPaneStateChanged
        if (userTabbedPane.getSelectedIndex() == FRIENDS_TAB) {
            enableAddFriend = false;
            enableRemoveFriend = true;
            enableSendMessage = true;
            enableSendFile = true;
            friendButton.setIcon(REMOVE_FRIEND_ICON);
        }
        
        if (userTabbedPane.getSelectedIndex() == STRANGERS_TAB) {
            enableAddFriend = true;
            enableRemoveFriend = false;
            enableSendMessage = false;
            enableSendFile = false;
            friendButton.setIcon(ADD_FRIEND_ICON);
        }
        
        updateSelectedUser();
    }//GEN-LAST:event_userTabbedPaneStateChanged

    private void friendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendButtonMouseClicked
        if (userTabbedPane.getSelectedIndex() == FRIENDS_TAB) {
            if (selectedUser != null && isLeftMouseButton(evt)) {
                clientController.removeFriend(selectedUser);
            }
        }
        if (userTabbedPane.getSelectedIndex() == STRANGERS_TAB) {
            if (selectedUser != null && isLeftMouseButton(evt)) {
                clientController.sendFriendRequest(selectedUser);
            }
        }
    }//GEN-LAST:event_friendButtonMouseClicked

    private void strangerListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_strangerListMouseReleased
        updateSelectedUser();
    }//GEN-LAST:event_strangerListMouseReleased
    
    public String getSelectedUser() {
        return selectedUser;
    }
    
    public void updateChatArea(ArrayList<String> chatData) {
        chatTextArea.setText("");
        for (String message : chatData) {
            chatTextArea.append(message + "\n");
        }
    }
    
    public void updateUserList(ArrayList<String> strangerList, ArrayList<String> friendList) {
        strangerListModel.removeAllElements();
        friendListModel.removeAllElements();
        
        for (String stranger : strangerList) {
            strangerListModel.addElement(stranger);
        }
        
        for (String friend : friendList) {
            friendListModel.addElement(friend);
        }
        
        updateSelectedUser();
    }
    
    private void updateSelectedUser() {
        if (userTabbedPane.getSelectedIndex() == FRIENDS_TAB) {
            if (friendList.getSelectedValue() == null) {
                friendList.setSelectedIndex(0);
            }
            
            selectedUser = friendList.getSelectedValue();
        }
        
        if (userTabbedPane.getSelectedIndex() == STRANGERS_TAB) {
            if (strangerList.getSelectedValue() == null) {
                strangerList.setSelectedIndex(0);
            }
            
            selectedUser = strangerList.getSelectedValue();
        }
        
        nameLabel.setText(selectedUser);
        if (selectedUser != null) updateChatArea(clientController.getChatData(selectedUser));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {            
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientView(new ClientController()).setVisible(true);
            }
        });
    }

    // Personal variables declaration
    private final int FRIENDS_TAB = 0;
    private final int STRANGERS_TAB = 1;
    private final String DEFAULT_MESSAGE_TEXT = "Say something...";
    private final javax.swing.ImageIcon ADD_FRIEND_ICON;
    private final javax.swing.ImageIcon REMOVE_FRIEND_ICON;
    private ClientController clientController;
    private String previousFindText;
    private String previousMessageText;
    private String selectedUser;
    private boolean enableAddFriend;
    private boolean enableRemoveFriend;
    private boolean enableSendMessage;
    private boolean enableSendFile;
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chatPanel;
    private javax.swing.JScrollPane chatScroll;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton friendButton;
    private javax.swing.DefaultListModel<String> friendListModel;
    private javax.swing.JList<String> friendList;
    private javax.swing.JScrollPane friendListScroll;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton sendFileButton;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.DefaultListModel<String> strangerListModel;
    private javax.swing.JList<String> strangerList;
    private javax.swing.JScrollPane strangerListScroll;
    private javax.swing.JPanel userPanel;
    private javax.swing.JTabbedPane userTabbedPane;
    // End of variables declaration//GEN-END:variables
}
