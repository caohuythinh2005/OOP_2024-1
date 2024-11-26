import org.bouncycastle.crypto.prng.drbg.DualECPoints;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.sql.Driver;
import java.time.Duration;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TwitterTweet {


    private String userID;
    private String tweetID;
    private String urlTweet;

    public TwitterTweet() {

    }

    public TwitterTweet(String userID, String tweetID) {
        this.setUserID(userID);
        this.setTweetID(tweetID);
    }



    public String getUrlTweet() {
        return "https://x.com/" + this.getUserID() + "/status/" + this.getTweetID();
    }

    public void getTweet(TwitterBot bot) {
        bot.getDriver().get(getUrlTweet());
    }

    public ArrayList<String> getAllCommenters(TwitterBot bot) {
        ArrayList<String> arrCommenters = new ArrayList<String>();
        try {
            List<WebElement> allCommenters = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("article[data-testid='tweet']")));
            for (int i = 1; i < allCommenters.size(); i++) {
                WebElement linkElement = allCommenters.get(i).findElement(By.cssSelector("a"));
                String href = linkElement.getAttribute("href");
                System.out.println(href);
                arrCommenters.add(href);
            }

        } catch (Exception e) {
            System.out.println("Failed");
        }
        return arrCommenters;
    }

    public static String getTweetIDFromUrl(String url) {
        // /leomessisite/status/1858644152139071907
        // format https://x.com/leomessisite/status/1858644152139071907
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    @Override
    public String toString() {
        return this.userID + " -- " + tweetID;
    }

    public String getUserID() {
        return userID;
    }

    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
