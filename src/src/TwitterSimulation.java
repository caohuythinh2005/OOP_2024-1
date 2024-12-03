import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.ArrayList;

public class TwitterSimulation {

    public TwitterSimulation() {

    }

    private String getCurrentUrl(TwitterBot bot) {
        return bot.getDriver().getCurrentUrl();
    }

    private String getUrlFollowers(String username) {
        return "https://x.com/" + username + "/followers";
    }

    private String getUrlUserProfile(String username) {
        return "https://x.com/" + username;
    }

    private String getUrlTweet(String username, String tweetID) {
        return "https://x.com/" + username + "/status/" + tweetID;
    }

    private void searchUserFromHashtag(TwitterBot bot, String hashtag) {
        String url = "https://x.com/search?q=" + hashtag + "&src=typed_query&f=user";
        bot.getDriver().get(url);
    }

    public void getProfileFromUsername(TwitterBot bot, String username) {
        String url = getUrlUserProfile(username);
        bot.getDriver().get(url);
    }

    public static String getUserNameFromURL(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    public String getUsername(TwitterBot bot) {
        return getUserNameFromURL(bot.getDriver().getCurrentUrl());
    }

    public ArrayList<String> getALlUrlProfilesFromHashTag(TwitterBot bot, String hashTag, int scrolls) {
        searchUserFromHashtag(bot, hashTag);
        return getUrlALlFollowersWithScroll(bot, scrolls);
    }

    public ArrayList<String> getALlUrlProfilesFromHashTag(TwitterBot bot, String hashTag) {
        searchUserFromHashtag(bot, hashTag);
        return getUrlALlFollowers(bot);
    }


    private ArrayList<String> getUrlALlFollowers(TwitterBot bot) {
        ArrayList<String> arrLinkProfiles = new ArrayList<String>();
        try {
            List<WebElement> profiles = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("button[data-testid='UserCell']")));
            for (int i = 0; i < profiles.size(); i++) {
                WebElement linkElement = profiles.get(i).findElement(By.cssSelector("a"));
                String href = linkElement.getAttribute("href");
                arrLinkProfiles.add(href);
            }
        } catch (Exception e) {
            // System.out.println("Failed to get link");
        }
        return arrLinkProfiles;
    }

    private ArrayList<String> getUrlALlFollowersWithScroll(TwitterBot bot, int scrolls) {
        ArrayList<String> arrLinkProfiles = new ArrayList<String>();
        JavascriptExecutor js = (JavascriptExecutor) bot.getDriver();
        for (int idx = 0; idx < scrolls; idx++) {
            try {
                List<WebElement> profiles = bot.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("button[data-testid='UserCell']")));
                for (WebElement profile : profiles) {
                    WebElement linkElement = profile.findElement(By.cssSelector("a"));
                    String href = linkElement.getAttribute("href");
                    if (arrLinkProfiles.isEmpty()) {
                        arrLinkProfiles.add(href);
                    } else if (!arrLinkProfiles.contains(href)){
                        arrLinkProfiles.add(href);
                    }
                }
            } catch (Exception e) {
                // System.out.println("Failed to get link");
            }
            js.executeScript("window.scrollBy(0, 200)");
        }
        return arrLinkProfiles;
    }

    public boolean checkAvailableAccount(TwitterBot bot){
        int ans = 0;
        String username = TwitterSimulation.getUserNameFromURL(bot.getDriver().getCurrentUrl());
        try {
            WebElement followers = bot.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/" + username + "/verified_followers']")));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public int getFollowersCount(TwitterBot bot) {
        int ans = 0;
        String username = TwitterSimulation.getUserNameFromURL(bot.getDriver().getCurrentUrl());
        System.out.println(username);
        WebElement followers = bot.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/" + username + "/verified_followers']")));
        String followerStr = followers.getText().split(" ")[0];
        System.out.println(followerStr);
        ans = NumberParser.parseNumber(followerStr);
        return ans;
    }

    public ArrayList<String> getAllFollowers(TwitterBot bot) throws InterruptedException {
        ArrayList<String> ListFollowers = new ArrayList<String>();
        JavascriptExecutor js = (JavascriptExecutor) bot.getDriver();
        for (int i = 0; i < 100; i++) {
            ArrayList<String> temp = this.getUrlALlFollowers(bot);
            if (temp.isEmpty()) break;
            if (!ListFollowers.isEmpty()) {
                for (String x : temp) {
                    if (!ListFollowers.contains(x)) {
                        ListFollowers.add(x);
                    }
                }
            } else ListFollowers = temp;
            js.executeScript("window.scrollBy(0, 200)");
        }
        //Thread.sleep(2000);
        return ListFollowers;
    }


//    public int getFollowers() {
//
//    }

}
