//package Data;


public class Users {
    private String username;
    private String IPaddress;
    private int port;
    
    public void setUserPeer(String username, String ipAddress, int port) {
		this.username = username;
		this.IPaddress = ipAddress;
		this.port = port;
	}
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String IPaddress) {
        this.IPaddress = IPaddress;
    }
    
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "User [Username=" + username + ", Port=" + port + ", IPaddress=" + IPaddress + "]";
    }
}
