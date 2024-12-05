package pagerank;

public class User {
    private String username;
    private int numberOfFollowers;
    
 // Constructor mặc định (thêm vào để Jackson có thể khởi tạo đối tượng)
    public User() {
    	
    }

    public User(String username, int numberOfFollowers) {
        this.username = username;
        this.numberOfFollowers = numberOfFollowers;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    @Override
    public String toString() {
        return this.username + " --- " + numberOfFollowers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return this.username.equals(user.getUsername());
    }
}
