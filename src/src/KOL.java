import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KOL extends User{
    private List<Tweet> tweets;
    private List<String> followers;
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

    public List<Tweet> getTweets(List<Tweet> tweets) {
        return this.tweets;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowers(List<String> followers) {
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
