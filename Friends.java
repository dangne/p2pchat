import java.io.Serializable;

public class Friends implements Serializable {
    private String username;
    private String IPaddress;

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

    @Override
    public String toString() {
        return "User [Username=" + username + ", IP Address=" + IPaddress + "]";
    }
}
