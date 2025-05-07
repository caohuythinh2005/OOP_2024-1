package entity;

import java.util.List;

public class Tweet extends TweetID{
    private List<String> commenters;
    private List<String> retweeters;

    public Tweet(String username, String id, List<String> commenters, List<String> retweeters) {
        this.retweeters = retweeters;
        this.username = username;
        this.id = id;
        this.commenters = commenters;
    }


    public List<String> getCommenters() {
        return commenters;
    }

    public void setCommenters(List<String> commenters) {
        this.commenters = commenters;
    }

    public List<String> getRetweeters() {
        return retweeters;
    }

    public void setRetweeters(List<String> retweeters) {
        this.retweeters = retweeters;
    }


    @Override
    public String toString() {
        return "Tweet{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                ", commenters=" + commenters +
                ", retweeters=" + retweeters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Tweet user)) {
            return false;
        }

        return this.username.equals(user.getUsername()) && this.id.equals(user.getId());
    }
}
