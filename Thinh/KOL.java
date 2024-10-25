import java.util.ArrayList;

public class KOL {
    private String username;
    private String displayName;
    private int numberOfFollowers;

    // phục vụ cho việc chỉ có username
    public KOL(String username) {
        this.setUsername(username);
    }

    // phục vụ cho việc chỉ có username, displayName
    public KOL(String username, String displayName) {
        this.setUsername(username);
        this.setDisplayName(displayName);
    }

    // phục vụ cho việc có username, displayName, numberOfFollowers
    public KOL(String username, String displayName, int numberOfFollowers) {
        this.setUsername(username);
        this.setDisplayName(displayName);
        this.setNumberOfFollowers(numberOfFollowers);
    }

    // Override
//    @Override
//    public String toString() {
//        return "KOL{"+
//                "username=" + username + '\'' +
//                ", displayName=" + displayName + '\'' +
//                ", numberOfFollowers=" + numberOfFollowers +
//                '}';
//    }

    // Getters and Setters

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getNumberOfFollowers() {
        return this.numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }
}
