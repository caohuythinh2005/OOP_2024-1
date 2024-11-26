import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class KOL {
    private String username;
    private int numberOfFollowers;
    private ArrayList<TwitterTweet> arrTwitterTweet;


    // phục vụ cho việc chỉ có username
    public KOL(String username) {
        this.setUsername(username);
    }

    // phục vụ cho việc chỉ có username, displayName

    // phục vụ cho việc có username, displayName, numberOfFollowers
    public KOL(String username, int numberOfFollowers) {
        this.setUsername(username);
        this.setNumberOfFollowers(numberOfFollowers);
    }

    public void getUser(TwitterBot bot) {
        bot.getDriver().get("https://x.com/" + username);
    }

    public ArrayList<TwitterTweet> getAllTweets(TwitterBot bot) {
        ArrayList<TwitterTweet> allTweets = new ArrayList<TwitterTweet>();
        try {
            List<WebElement> allCommenters = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("article[data-testid='tweet']")));
            for (WebElement allCommenter : allCommenters) {
                List<WebElement> linkElement = allCommenter.findElements(By.cssSelector("a"));
//                System.out.println(linkElement.size());
//                for (WebElement x : linkElement) {
//                    System.out.println(x.getAttribute("href"));
//                }
                String href = linkElement.get(3).getAttribute("href");
//                System.out.println(href);
                if (href != null) {
                    System.out.println(TwitterTweet.getTweetIDFromUrl(href));
                    allTweets.add(new TwitterTweet(username, TwitterTweet.getTweetIDFromUrl(href)));
                }
            }
        } catch (Exception e) {
            System.out.println("Failed");
        }
        return allTweets;
    }

    public ArrayList<TwitterTweet> getAllTweetsWithScroll(TwitterBot bot, int numberScroll) {
        ArrayList<TwitterTweet> allTweets = new ArrayList<TwitterTweet>();
        ArrayList<String> allTweetsID = new ArrayList<String>();
        JavascriptExecutor js = (JavascriptExecutor) bot.getDriver();
        for (int i = 0; i < numberScroll; i++) {
            try {
                List<WebElement> allCommenters = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("article[data-testid='tweet']")));
                for (WebElement allCommenter : allCommenters) {
                    List<WebElement> linkElement = allCommenter.findElements(By.cssSelector("a"));
//                System.out.println(linkElement.size());
//                for (WebElement x : linkElement) {
//                    System.out.println(x.getAttribute("href"));
//                }
                    String href = linkElement.get(3).getAttribute("href");
//                System.out.println(href);
                    if (href != null) {
                        String id = TwitterTweet.getTweetIDFromUrl(href);
                        if (allTweets.isEmpty()) {
                            allTweetsID.add(id);
                            allTweets.add(new TwitterTweet(username, id));
                        } else {
                            if (!allTweetsID.contains(id)) {
                                allTweetsID.add(id);
                                allTweets.add(new TwitterTweet(username, id));
                            }
                        }
                    }
                }
                js.executeScript("window.scrollBy(0, 100)");
            } catch (Exception e) {
                System.out.println("Failed");
            }
        }
        return allTweets;
    }

    // Getters and Setters

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfFollowers() {
        return this.numberOfFollowers;
    }

    public void setNumberOfFollowers(int numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public ArrayList<TwitterTweet> getArrTwitterTweet() {
        return arrTwitterTweet;
    }
}
