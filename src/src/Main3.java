import java.util.Arrays;
import java.util.List;

public class Main3 {
    public static void main(String[] args) {
        // Danh sách người comment
        List<String> commenters = Arrays.asList("user1", "user2", "user3");

        // Tạo một tweet
        Tweet tweet = new Tweet("blockchainGuru", "tweet123", commenters);

        // In thông tin tweet
        System.out.println(tweet);

        // Sử dụng getter
        System.out.println("Username: " + tweet.getUsername());
        System.out.println("ID: " + tweet.getId());
        System.out.println("Commenters: " + tweet.getCommenters());
    }
}
