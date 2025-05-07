package socialmedia;

import automation.AutomationTool;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TwitterPlatform implements SocialMediaPlatform {
    private AutomationTool automationTool;

    public TwitterPlatform(AutomationTool automationTool) {
        this.automationTool = automationTool;
    }

    private boolean isEmailInputRequired() throws InterruptedException {
        Thread.sleep(5000);
        try {
            List<Object> elements = automationTool.findElements("css", "input[name='password']");
            return elements.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Xử lý khi không thể kiểm tra email input
        }
    }

    @Override
    public boolean login(List<String> credential) {
        System.out.println(credential);
        if (credential.size() != 3) return false;
        String username = credential.get(0);
        String email = credential.get(1);
        String password = credential.get(2);
        try {
            String url = "https://twitter.com/i/flow/login";
            automationTool.navigateTo(url);

            Object usernameInput = automationTool.waitForElement("css", "input[autocomplete='username']");
            automationTool.sendKeys(usernameInput, username);
            automationTool.sendEnter(usernameInput);

            if (isEmailInputRequired()) {
                Object emailInput = automationTool.waitForElement("css", "input[autocomplete='on']");
                automationTool.sendKeys(emailInput, email);
                automationTool.sendEnter(emailInput);
            }

            Object passwordInput = automationTool.waitForElement("css", "input[name='password']");
            automationTool.sendKeys(passwordInput, password);
            automationTool.sendEnter(passwordInput);

            automationTool.waitForElement("css", "[data-testid='primaryColumn']");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getFollowers(String userId, int scrolls) {
        navigateToFollowersPage(userId);
        HashSet<String> listFollowers = new HashSet<>();
        try {
            for (int i = 0; i < scrolls + 1; i++) {
                List<Object> profiles = automationTool.waitForElements("css", "button[data-testid='UserCell']");
                for (Object profile : profiles) {
                    try {
                        List<Object> linkElements = automationTool.findElements(profile, "css", "a");
                        if (!linkElements.isEmpty()) {
                            Object linkElement = linkElements.get(0);
                            String href = automationTool.getAttribute(linkElement, "href");
                            listFollowers.add(getUsernameFromURL(href));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                automationTool.scroll(0, 250);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(listFollowers);
    }

    @Override
    public int getFollowersCount(String userId) {
        navigateToProfilePage(userId);
        try {
            List<Object> followers = automationTool.waitForElements("xpath", "//a[@href='/" + userId + "/verified_followers']");
            String followerStr = automationTool.getText(followers.get(0)).split(" ")[0];
            return parseNumber(followerStr);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Xử lý khi không thể lấy được số lượng followers
        }
    }

    @Override
    public List<String> getPostIds(String username, int limit, int scrolls) throws InterruptedException{
        navigateToProfilePage(username);
        int scrollCount = 0;
        HashSet<String> tweets = new HashSet<>();
        while (tweets.size() < limit && scrollCount < scrolls) {
            List<Object> elements = automationTool.waitForElements("css", "article");
            for (Object element : elements) {
                try {
                    List<Object> ownerElements = automationTool.findElements(element, "xpath", ".//a[contains(@href, '/status/')]");
                    String tweetUrl = automationTool.getAttribute(ownerElements.getFirst(), "href");
                    assert tweetUrl != null;
                    String tweetID = getTweetIdFromUrl(tweetUrl);
                    List<Object> userElement = automationTool.findElements(element, "xpath", ".//div[@data-testid='User-Name']//a");
                    String ownerName = automationTool.getAttribute(userElement.getFirst(), "href");
                    String user = getUsernameFromURL(ownerName);
                    String entry = user + " "  + tweetID;
                    tweets.add(entry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            automationTool.scroll(0, 250);
            Thread.sleep(250);
            scrollCount++;
        }
        return new ArrayList<>(tweets);
    }

    @Override
    public List<String> getReposters(String userId, String postId, int scrolls) throws InterruptedException {
        navigateToRetweetPage(userId, postId);
        HashSet<String> reposterIds = new HashSet<>();
        for (int i = 0; i < scrolls + 1; i++) {
            try {
                List<Object> profiles = automationTool.waitForElements("css", "button[data-testid='UserCell']");
                if (profiles.isEmpty()) {
                    break;
                }
                for (Object profile : profiles) {
                    try {
                        List<Object> linkElements = automationTool.findElements(profile, "css", "a");
                        if (!linkElements.isEmpty()) {
                            Object linkElement = linkElements.get(0);
                            String href = automationTool.getAttribute(linkElement, "href");
                            reposterIds.add(getUsernameFromURL(href));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                automationTool.scroll(0, 250);

            } catch (Exception e) {
                // e.printStackTrace();
            }
            Thread.sleep(500);
            automationTool.scroll(0, 250);
        }
        return new ArrayList<>(reposterIds);
    }

    @Override
    public List<String> getCommenters(String userId, String postId, int scrolls) {
        navigateToTweetPage(userId, postId);
        List<String> commenters = new ArrayList<>();
        try {
            for (int i = 0; i < scrolls + 1; i++) {
                List<Object> articles = automationTool.waitForElements("css", "article[data-testid='tweet']");
                if (articles.isEmpty()) {
                    break;
                }
                for (int j = 1; j < articles.size(); j++) {
                    try {
                        Object article = articles.get(j);
                        List<Object> linkElements = automationTool.findElements(article, "css", "a");
                        if (!linkElements.isEmpty()) {
                            Object linkElement = linkElements.get(0);
                            String href = automationTool.getAttribute(linkElement, "href");
                            String commenter = getUsernameFromURL(href);
                            if (!commenters.contains(commenter)) {
                                commenters.add(commenter);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                automationTool.scroll(0, 250);
                Thread.sleep(250);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commenters;
    }

    @Override
    public boolean isAccountAvailable(String userId) {
        try {
            navigateToProfilePage(userId);
            automationTool.waitForElement("xpath", "//a[@href='/" + userId + "/verified_followers']");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> searchUserFromHashtag(String hashtag, int limit, int scrolls) {
        String url = "https://x.com/search?q=" + hashtag + "&src=typed_query&f=user";
        automationTool.navigateTo(url);
        HashSet<String> userIds = new HashSet<>();
        int count = 0;
        int scrollCount = 0;
        try {
            while (count < limit && scrollCount < scrolls) {
                List<Object> profiles = automationTool.waitForElements("css", "button[data-testid='UserCell']");
                for (Object profile : profiles) {
                    try {
                        List<Object> linkElements = automationTool.findElements(profile, "css", "a");
                        if (!linkElements.isEmpty()) {
                            Object linkElement = linkElements.get(0);
                            String href = automationTool.getAttribute(linkElement, "href");
                            userIds.add(getUsernameFromURL(href));
                            if (++count >= limit) break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                automationTool.scroll(0, 200);
                Thread.sleep(2000);
                scrollCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>(userIds);
    }

    @Override
    public String getPlatformName() {
        return "Twitter";
    }

    @Override
    public void close() {
        automationTool.close();
    }

    private void navigateToFollowersPage(String username) {
        String url = "https://x.com/" + username + "/followers";
        automationTool.navigateTo(url);
    }

    private void navigateToProfilePage(String username) {
        String url = "https://x.com/" + username;
        automationTool.navigateTo(url);
    }

    private void navigateToTweetPage(String username, String tweetId) {
        String url = "https://x.com/" + username + "/status/" + tweetId;
        automationTool.navigateTo(url);
    }

    private void navigateToRetweetPage(String username, String tweetId) {
        String url = "https://x.com/" + username + "/status/" + tweetId + "/retweets";
        automationTool.navigateTo(url);
    }

    private String getUsernameFromURL(String url) {
        String[] parts = url.split("/");
        for (int i = parts.length - 1; i >= 0; i--) {
            if (!parts[i].isEmpty() && !parts[i].equals("status")) {
                return parts[i];
            }
        }
        return null;
    }

    private String getTweetIdFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    private int parseNumber(String input) {
        input = input.replace(",", "");
        double multiplier = 1.0;
        if (input.endsWith("K")) {
            multiplier = 1_000;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("M")) {
            multiplier = 1_000_000;
            input = input.substring(0, input.length() - 1);
        }
        return (int) (Double.parseDouble(input) * multiplier);
    }
}
