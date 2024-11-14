import java.util.ArrayList;

public class KOL {
    private String username;
    private int numberOfFollowers;

    // phục vụ cho việc chỉ có username
    public KOL(String username) {
        this.setUsername(username);
    }

    // phục vụ cho việc chỉ có username, displayName

    // phục vụ cho việc có username, displayName, numberOfFollowers
    public KOL(String username, int numberOfFollowers) {
        this.setUsername(username);
        this.setNumberOfFollowers(numberOfFollowers);
    }
    // Getters and Setters

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfFollowers() {
        return this.numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }
}
