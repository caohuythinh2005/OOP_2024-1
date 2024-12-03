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

public class TwitterFollower {
    public static String getUrlFollowers(String username) {
        return "https://x.com/" + username + "/followers";
    }

    public static void getFollowersTab(TwitterBot bot, String username) {
        bot.getDriver().get(TwitterFollower.getUrlFollowers(username));
    }

    public static ArrayList<String> getAllFollowers(TwitterBot bot) {
        ArrayList<String> arrLinkProfiles = new ArrayList<String>();
        try {
            List<WebElement> profiles = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("button[data-testid='UserCell']")));
            for (WebElement profile : profiles) {
                WebElement linkElement = profile.findElement(By.cssSelector("a"));
                String href = linkElement.getAttribute("href");
                arrLinkProfiles.add(TwitterSimulation.getUserNameFromURL(href));
            }
        } catch (Exception e) {
            // System.out.println("Failed to get link");
        }
        return arrLinkProfiles;
    }

    public static ArrayList<String> getAllFollowersWithScroll(TwitterBot bot, int scrolls) throws InterruptedException {
        ArrayList<String> ListFollowers = new ArrayList<String>();
        JavascriptExecutor js = (JavascriptExecutor) bot.getDriver();
        for (int i = 0; i < scrolls + 1; i++) {
            ArrayList<String> temp = getAllFollowers(bot);
            if (temp.isEmpty()) break;
            if (!ListFollowers.isEmpty()) {
                for (String x : temp) {
                    if (!ListFollowers.contains(x)) {
                        ListFollowers.add(x);
                    }
                }
            } else ListFollowers = temp;
            js.executeScript("window.scrollBy(0, 350)");
        }
        //Thread.sleep(2000);
        return ListFollowers;
    }
}
