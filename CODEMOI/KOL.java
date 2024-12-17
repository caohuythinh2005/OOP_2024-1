package pagerank;

import java.util.ArrayList;
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

    public List<Tweet> getTweets() {
        return this.tweets;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowers() {
        return this.followers;
    }

    @Override
    public String toString() {
        return "model.KOL{" +
                "username='" + getUsername() + '\'' +
                ", numberOfFollowers=" + getNumberOfFollowers() +
                ", followers=" + followers +
                ", tweets=" + tweets +
                '}';
    }
}
