import org.bouncycastle.crypto.prng.drbg.DualECPoints;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.sql.Array;
import java.sql.Driver;
import java.time.Duration;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TwitterTweet {
    public static String getUrlTweet(String username, String id) {
        return "https://x.com/" + username + "/status/" + id;
    }

    public static void getTweet(TwitterBot bot, String username, String id) {
        bot.getDriver().get(getUrlTweet(username, id));
    }

    public static void getProfile(TwitterBot bot, String username) {
        bot.getDriver().get("https://x.com/" + username);
    }


    public static ArrayList<String> getAllTweetsIDWithScroll(TwitterBot bot, int scrolls) throws InterruptedException {
        ArrayList<String> allTweetsID = new ArrayList<String>();
        JavascriptExecutor js = (JavascriptExecutor) bot.getDriver();
        for (int idx = 0; idx < scrolls + 1; idx++) {
            Thread.sleep(2000);
            try {
                List<WebElement> allCommenters = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("article[data-testid='tweet']")));
                for (WebElement allCommenter : allCommenters) {
                    List<WebElement> linkElement = allCommenter.findElements(By.cssSelector("a"));
                    String href = linkElement.get(3).getAttribute("href");
                    if (href != null) {
                        String id = TwitterTweet.getTweetIDFromUrl(href);
                        if (allTweetsID.isEmpty()) {
                            allTweetsID.add(id);
                        } else {
                            if (!allTweetsID.contains(id)) {
                                allTweetsID.add(id);
                            }
                        }
                    }
                }
                js.executeScript("window.scrollBy(0, 350)");
            } catch (Exception e) {
                // System.out.println("Failed");
            }
        }
        return allTweetsID;
    }

    public static ArrayList<String> getAllCommenters(TwitterBot bot, int scrolls) {
        JavascriptExecutor js = (JavascriptExecutor) bot.getDriver();
        ArrayList<String> arrCommenters = new ArrayList<String>();
        try {
            for (int idx = 0; idx < scrolls + 1; idx++) {
                Thread.sleep(2000);
                List<WebElement> allCommenters = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("article[data-testid='tweet']")));
                for (int i = 1; i < allCommenters.size(); i++) {
                    WebElement linkElement = allCommenters.get(i).findElement(By.cssSelector("a"));
                    String href = linkElement.getAttribute("href");
                    if (!arrCommenters.contains(href)) {
                        arrCommenters.add(href);
                    }
                }
                js.executeScript("window.scrollBy(0, 200)");
            }
        } catch (Exception e) {
            //System.out.println("Failed");
        }
        return arrCommenters;
    }

    public static String getTweetIDFromUrl(String url) {
        // /leomessisite/status/1858644152139071907
        // format https://x.com/leomessisite/status/1858644152139071907
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}

