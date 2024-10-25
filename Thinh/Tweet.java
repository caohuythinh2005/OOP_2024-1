import java.util.Date;
import org.bouncycastle.crypto.prng.drbg.DualECPoints;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class Tweet {
    private String tweetId;
    private String author;
    private int retweets;
    private int likes;
    // Constructor
    public Tweet(String tweetId, String author) {
        setTweetId(tweetId);
        setAuthor(author);
    }

    // Getters and Setters
    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    // Method to display Tweet information
//    @Override
//    public String toString() {
//        return "Tweet{" +
//                "tweetId='" + tweetId + '\'' +
//                ", author=" + author.getDisplayName() + " (@" + author.getUsername() + ")" +
//                ", retweets=" + retweets +
//                ", likes=" + likes +
//                '}';
//    }
}
