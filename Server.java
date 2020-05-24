//package Server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//import Data.Database;
//import Data.Users;

public class Server {

	private static ArrayList<Users> OnlUser = null;
	private ServerSocket server = null;
	private Socket socket = null; // connection
	private ObjectInputStream objectInputStream = null; // obInputStream
	private ObjectOutputStream objectOutputStream = null; // obOutputClient
	public boolean isStop = false, isExit = false;
	public Database db;

	public Server(String DataPath, int port) {
		db = new Database(DataPath);
		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			ServerView.updateMessage("Server started. \n  IP : " + InetAddress.getLocalHost().getHostAddress()
					+ "\n  Port : " + server.getLocalPort());
		} catch (IOException ioe) {
			ServerView.updateMessage("\nCan not start sever " + port + ": " + ioe.getMessage());
		}
		OnlUser = new ArrayList<Users>();
		(new WaitForConnect()).start();
	}

	public class WaitForConnect extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				while (!isStop) {
					if (acceptConnection()) {
						if (isExit) {
							isExit = false;
						} else {
							objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
							objectOutputStream.writeObject(sendSessionAccept());
							objectOutputStream.flush();
							Socket newSocket = server.accept();
							(new keepClient(newSocket)).start();
						}
						objectInputStream.close();
					} else {
						objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
						objectOutputStream.writeObject("NO");
						objectOutputStream.flush();
						objectOutputStream.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class keepClient extends Thread {
		private Socket client;

		public keepClient(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				DataInputStream in = new DataInputStream(client.getInputStream());
				while (true) {
					String msg = in.readUTF();
				}		
			} catch (EOFException e) {
				for (int i = 0; i < OnlUser.size(); i++) {
					if (client.getInetAddress().toString().equals(OnlUser.get(i).getIPaddress())) {
						ServerView.updateMessage(OnlUser.get(i).getUsername() + " exit");
						OnlUser.remove(i);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean acceptConnection() {
		// Server get Login - Register request from Client
		try {
			socket = server.accept();
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object request = objectInputStream.readObject();
			String req = (String) request;

			ServerView.updateMessage(req);

			String[] getData = req.split(" ", 0); // { LOG/REG, username, password, portClient}
			if (getData.length > 1) {
				String type_req = getData[0];
				String username = getData[1];
				String password = getData[2];
				int port = Integer.parseInt(getData[3]);
				if (type_req.equals("LOG")) {
					ServerView.updateMessage("Message : LOGIN - user : " + username);
					if (loginClient(username, password, socket.getInetAddress().toString(), port)) {
						return true;
					} else {
						ServerView.updateMessage("User :" + username + "Login failed");
						return false;
					}
				} else {
					if (signupClient(username, password, socket.getInetAddress().toString(), port)) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				ServerView.updateMessage("Message : Update state user ");
				int size = OnlUser.size();
				String type_req = getData[0];
				if (type_req.equals("EXIT")) {
					for (Users user : OnlUser) {
						if (socket.getInetAddress().toString().equals(user.getIPaddress())) {
							OnlUser.remove(user);
						}
					}
				}
				if (type_req.equals("UPDATE")) {
					System.out.println("TEST");
				}
				if (size != OnlUser.size()) {
					isExit = true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private boolean loginClient(String username, String password, String ipAddress, int port) throws Exception {
		if (db.checkLogin(username, password)) {
			if (!isExistName(username)) {
				// saveNewPeer (user , ip , port )
				newUserPeer(username, ipAddress, port);
				ServerView.updateMessage(
						"User :" + username + " is Online \n  IP user:" + ipAddress + "\n  port:" + port);
				return true;
			} else {
				return false;
			}
		}
		ServerView.updateMessage("User :" + username + " wrong password");
		return false;
	}

	private boolean signupClient(String username, String password, String ipAddress, int port) throws Exception {
		if (!db.checkUserExist(username)) {
			if (!isExistName(username)) {
				db.addUser(username, password);
				newUserPeer(username, ipAddress, port);
				ServerView
						.updateMessage("User :" + username + " is Online\n  IP user:" + ipAddress + "\n  port:" + port);
				return true;
			} else
				return false;
		} else
			return false;
	}

	// check username
	private boolean isExistName(String username) throws Exception {
		if (OnlUser == null)
			return false;
		int size = OnlUser.size();
		for (int i = 0; i < size; i++) {
			Users peer = OnlUser.get(i);
			if (peer.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}

	// save name user
	private void newUserPeer(String username, String ipAddress, int port) throws Exception {
		Users newPeer = new Users();
		if (OnlUser.size() == 0)
			OnlUser = new ArrayList<Users>();
		newPeer.setUserPeer(username, ipAddress, port);
		OnlUser.add(newPeer);
	}

	// show status server
	private String sendSessionAccept() throws Exception {
		String msg = "";
		int size = OnlUser.size();
		for (int i = 0; i < size; i++) {
			Users peer = OnlUser.get(i);
			msg += peer.getUsername();
			msg += " ";
			msg += peer.getIPaddress();
			msg += " ";
			msg += peer.getPort();
			msg += " ";
		}
		StringBuilder temp = new StringBuilder(msg);
		temp.deleteCharAt(temp.length() - 1);
		msg = temp.toString();
		return msg;
	}

	public void stopServer() throws Exception {
		if (isStop == false) {
			isStop = true;
			server.close();
			socket.close();
		}
	}

}
