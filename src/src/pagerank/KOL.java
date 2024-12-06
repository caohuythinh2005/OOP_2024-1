package pagerank;

import java.util.ArrayList;
import java.util.List;

public class KOL extends User{
    private List<Tweet> tweets;
    private List<String> followers;
    
 // Constructor mặc định (thêm vào để Jackson có thể khởi tạo đối tượng)
    public KOL() {
        super("", 0); // Gọi constructor của lớp cha (User) với giá trị mặc định
    }
    
    public KOL(String username, int numberOfFollowers, List<String> followers, List<Tweet> tweets) {
        super(username, numberOfFollowers);
        this.followers = followers;
        this.tweets = tweets;
    }

    public void addTweet(Tweet tweet) {
        if (!tweets.contains(tweet)) {
            this.tweets.add(tweet);
        }
    }

    public void addFollower(String user) {
        followers.add(user);
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return this.tweets;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowers() {
        return this.followers;
    }

    public String toString() {
        return "KOL{" +
                "username='" + getUsername() + '\'' +
                ", numberOfFollowers=" + getNumberOfFollowers() +
                ", followers=" + followers +
                ", tweets=" + tweets +
                '}';
    }
}
