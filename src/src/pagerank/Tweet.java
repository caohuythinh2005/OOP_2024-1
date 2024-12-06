package pagerank;
import java.util.List;

public class Tweet {
    private String username; // Người đăng tweet
    private String id;       // ID của tweet
    private List<String> commenters; // Danh sách người comment

 // Constructor mặc định (thêm vào để Jackson có thể khởi tạo đối tượng)
    public Tweet() {
        
    }
    
    // Constructor
    public Tweet(String username, String id, List<String> commenters) {
        this.username = username;
        this.id = id;
        this.commenters = commenters;
    }

    // Getter và Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCommenters() {
        return commenters;
    }

    public void setCommenters(List<String> commenters) {
        this.commenters = commenters;
    }

    // Ghi đè toString để hiển thị thông tin dễ đọc
    @Override
    public String toString() {
        return "Tweet{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", commenters=" + commenters +
                '}';
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Tweet)) {
            return false;
        }

        Tweet user = (Tweet) o;

        return this.username.equals(user.getUsername()) && this.id.equals(user.getId());
    }
}
