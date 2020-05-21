package Client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class LoginView extends JFrame {

	private JPanel panelMain;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField serverIPField;
	private JTextField serverPortField;
	private JButton signupBtn;
	private JButton loginBtn;
	private JLabel statusLb;

	public static int serverPort = 3000;
	private String username = "", IPaddress = "", password = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 385, 371);
		panelMain = new JPanel();
		panelMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMain);

		usernameField = new JTextField();
		usernameField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		usernameField.setColumns(10);

		JLabel usernameLb = new JLabel("Username");
		usernameLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		usernameLb.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel passwordLb = new JLabel("Password");
		passwordLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		passwordLb.setHorizontalAlignment(SwingConstants.LEFT);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Client.setPassword(password_text.getText());
			}
		});

		JButton loginBtn = new JButton("Login");
		loginBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLoginClientPerformed(e);
			}
		});

		JLabel passwordErr_lb = new JLabel("");

		JLabel usernameErr_lb = new JLabel("");

		signupBtn = new JButton("Sign Up");
		signupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSignupClientPerformed(e);
			}
		});
		signupBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		serverIPField = new JTextField();
		serverIPField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		serverIPField.setColumns(10);

		JLabel serverIPLb = new JLabel("Server IP");
		serverIPLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		serverIPLb.setHorizontalAlignment(SwingConstants.LEFT);

		serverPortField = new JTextField();
		serverPortField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		serverPortField.setColumns(10);
		serverPortField.setText(String.valueOf(serverPort));

		JLabel serverPortLb = new JLabel("Server port");
		serverPortLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		serverPortLb.setHorizontalAlignment(SwingConstants.LEFT);

		statusLb = new JLabel("Status:");
		statusLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		JLabel loginLb = new JLabel("LOGIN PAGE");
		loginLb.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		GroupLayout gl_panelMain = new GroupLayout(panelMain);
		gl_panelMain.setHorizontalGroup(
			gl_panelMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMain.createSequentialGroup()
					.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMain.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMain.createSequentialGroup()
									.addComponent(passwordLb, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelMain.createSequentialGroup()
									.addComponent(serverIPLb, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(serverIPField, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelMain.createSequentialGroup()
									.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelMain.createSequentialGroup()
											.addComponent(serverPortLb, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
											.addGap(12)
											.addComponent(serverPortField, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panelMain.createSequentialGroup()
											.addComponent(usernameLb, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
											.addGap(12)
											.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(passwordErr_lb, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
									.addGap(393)
									.addComponent(usernameErr_lb, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelMain.createSequentialGroup()
									.addGap(11)
									.addComponent(statusLb, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelMain.createSequentialGroup()
									.addGap(11)
									.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
									.addGap(70)
									.addComponent(signupBtn, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panelMain.createSequentialGroup()
							.addGap(115)
							.addComponent(loginLb)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelMain.setVerticalGroup(
			gl_panelMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMain.createSequentialGroup()
					.addGap(0, 0, Short.MAX_VALUE)
					.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMain.createSequentialGroup()
							.addGap(85)
							.addComponent(usernameErr_lb, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
							.addGap(94)
							.addComponent(passwordErr_lb, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelMain.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(loginLb)
							.addGap(18)
							.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMain.createSequentialGroup()
									.addGap(4)
									.addComponent(usernameLb, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMain.createSequentialGroup()
									.addGap(4)
									.addComponent(passwordLb, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(34)
							.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMain.createSequentialGroup()
									.addGap(4)
									.addComponent(serverIPLb, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addComponent(serverIPField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMain.createSequentialGroup()
									.addGap(4)
									.addComponent(serverPortLb, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
								.addComponent(serverPortField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addComponent(statusLb, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addGroup(gl_panelMain.createParallelGroup(Alignment.BASELINE)
								.addComponent(loginBtn)
								.addComponent(signupBtn))))
					.addGap(76))
		);
		panelMain.setLayout(gl_panelMain);
	}

	public void btnLoginClientPerformed(java.awt.event.ActionEvent evt) {
		// Get username, password to check
		username = usernameField.getText();
		password = String.valueOf(passwordField.getPassword());
		IPaddress = serverIPField.getText();
		System.out.println(username);
		System.out.println(password);
		if (!IPaddress.equals("") && !IPaddress.equals("")) {
			try {
				// create random port for client
				Random rd = new Random();
				int portPeer = 10000 + rd.nextInt() % 1000;
				// get IP Address and port of server
				InetAddress serverIP = InetAddress.getByName(IPaddress);
				serverPort = Integer.parseInt(serverPortField.getText());
				Socket socketClient = new Socket(serverIP, serverPort);

				// Send message (username, password, port) login to server
				String msg = "LOG " + username + " " + password + " " + portPeer;

				// create object output stream to send message to server
				ObjectOutputStream StreamOut = new ObjectOutputStream(socketClient.getOutputStream());
				StreamOut.writeObject(msg);
				StreamOut.flush();

				// create object input stream to get message from server
				ObjectInputStream StreamIn = new ObjectInputStream(socketClient.getInputStream());

				// get message
				msg = (String) StreamIn.readObject();
				socketClient.close();

				// if the username input meet the deny tag, then let user know
				if (msg.equals("NO")) {
					statusLb.setText("Status: Wrong Username or Password");
					return;
				}
				System.out.println("Success");
				String[] msgLog = msg.split(" ", 0);
				System.out.println(msg);
				new ChatView(IPaddress, serverPort, portPeer, username, msgLog);
				dispose();

			} catch (Exception e) {
				statusLb.setText("Status: Server not started");
				e.printStackTrace();
			}
		} else {
			statusLb.setText("Status: Server IP or Username empty");
		}
	}
	
	public void btnSignupClientPerformed(java.awt.event.ActionEvent evt) {
		// Get username, password to check
		username = usernameField.getText();
		password = String.valueOf(passwordField.getPassword());
		IPaddress = serverIPField.getText();
		if (!IPaddress.equals("") && !IPaddress.equals("")) {
			try {
				// create random port for client
				Random rd = new Random();
				int portPeer = 10000 + rd.nextInt() % 1000;
				// get IP Address and port of server
				InetAddress serverIP = InetAddress.getByName(IPaddress);
				serverPort = Integer.parseInt(serverPortField.getText());
				Socket socketClient = new Socket(serverIP, serverPort);

				// Send message (username, password, port) login to server
				String msg = "REG " + username + " " + password + " " + portPeer;

				// create object output stream to send message to server
				ObjectOutputStream StreamOut = new ObjectOutputStream(socketClient.getOutputStream());
				StreamOut.writeObject(msg);
				StreamOut.flush();

				// create object input stream to get message from server
				ObjectInputStream StreamIn = new ObjectInputStream(socketClient.getInputStream());

				// get message
				msg = (String) StreamIn.readObject();
				socketClient.close();

				// if the username input meet the deny tag, then let user know
				if (msg.equals("NO")) {
					statusLb.setText("Status: Username existed");
					return;
				}
				System.out.println("Success");
				String[] msgLog = msg.split(" ", 0);
				new ChatView(IPaddress, serverPort, portPeer, username, msgLog);
				dispose();

			} catch (Exception e) {
				statusLb.setText("Status: Server not started");
				e.printStackTrace();
			}
		} else {
			statusLb.setText("Status: Server IP empty or Username contains special character");
		}
	}
}
