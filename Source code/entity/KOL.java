package entity;

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
        return "KOL{" +
                "username='" + getUsername() + '\'' +
                ", numberOfFollowers=" + getNumberOfFollowers() +
                ", followers=" + followers +
                ", tweets=" + tweets +
                '}';
    }
}
