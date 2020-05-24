//package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerView extends JFrame {

	public static int port = 3000;
	Server server;
	// file data
	public String DataPath = "UserList.xml";

	private JPanel contentPane;
	private JTextField IPField;
	private JTextField portField;
	private static JTextArea statusField;
	private JButton stopBtn;
	private JButton startBtn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerView frame = new ServerView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void updateMessage(String msg) {
		statusField.append(msg + "\n");
	}

	/**
	 * Create the frame.
	 */
	public ServerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartServerActionPerformed(e);
			}
		});
		startBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStopActionPerformed(e);
			}
		});
		stopBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		stopBtn.setEnabled(false);

		IPField = new JTextField();
		IPField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		IPField.setColumns(10);
		try {
			IPField.setText(getIPAddress().getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane();

		JLabel IPLb = new JLabel("IP Address");
		IPLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		JLabel portLb = new JLabel("Port");
		portLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		JLabel statusLb = new JLabel("Status:");
		statusLb.setFont(new Font("Lucida Grande", Font.PLAIN, 15));

		portField = new JTextField();
		portField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		portField.setText(String.valueOf(port));
		portField.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(startBtn).addComponent(stopBtn))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(IPLb).addComponent(portLb))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(portField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
												.addComponent(IPField, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)))
								.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
										.addComponent(statusLb)))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(startBtn)
								.addComponent(IPField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(IPLb))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(stopBtn)
								.addComponent(portLb).addComponent(portField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(statusLb)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE).addContainerGap()));

		statusField = new JTextArea();
		statusField.setEnabled(false);
		scrollPane.setViewportView(statusField);
		contentPane.setLayout(gl_contentPane);
	}

	private void btnStartServerActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			statusField.setText("");
			port = Integer.parseInt(portField.getText());
			server = new Server(DataPath, port);
			stopBtn.setEnabled(true);
			startBtn.setEnabled(false);
		} catch (Exception e) {
			ServerView.updateMessage("Server start ERROR");
			e.printStackTrace();
		}
	}

	private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {
		startBtn.setEnabled(true);
		try {
			server.stopServer();
			ServerView.updateMessage("Stop Server");
		} catch (Exception e) {
			ServerView.updateMessage("Stop Server");
			e.printStackTrace();
		}
	}

	private static InetAddress getIPAddress() throws SocketException {
		Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
		while (ifaces.hasMoreElements()) {
			NetworkInterface iface = ifaces.nextElement();
			Enumeration<InetAddress> addresses = iface.getInetAddresses();

			while (addresses.hasMoreElements()) {
				InetAddress addr = addresses.nextElement();
				if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
					return addr;
				}
			}
		}

		return null;
	}
}
