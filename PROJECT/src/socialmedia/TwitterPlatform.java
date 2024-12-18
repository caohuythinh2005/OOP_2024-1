package socialmedia;
import automation.AutomationTool;
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
        List<Object> elements = automationTool.findElements("css", "input[name='password']");
        return elements.isEmpty();
    }

    @Override
    public boolean login(List<String> credential) {
        System.out.println(credential);
        if (credential.size() != 3) return false;
        else {
            String username = credential.get(0);
            String email = credential.get(1);
            String password = credential.get(2);
            try {
                String url = "https://twitter.com/i/flow/login";
                automationTool.navigateTo(url);

                // Nhập username
                Object usernameInput = automationTool.waitForElement("css", "input[autocomplete='username']");
                automationTool.sendKeys(usernameInput, username);
                automationTool.sendEnter(usernameInput);

                // Kiểm tra xem có cần nhập email không
                if (isEmailInputRequired()) {
                    Object emailInput = automationTool.waitForElement("css", "input[autocomplete='on']");
                    automationTool.sendKeys(emailInput, email); // Giả sử TwitterUserPassCredentials có email
                    automationTool.sendEnter(emailInput);
                }

                // Nhập password
                Object passwordInput = automationTool.waitForElement("css", "input[name='password']");
                automationTool.sendKeys(passwordInput, password);
                automationTool.sendEnter(passwordInput);

                automationTool.waitForElement("css", "[data-testid='primaryColumn']");

                return true; // Đăng nhập thành công
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Đăng nhập thất bại
            }
        }
    }

    @Override
    public List<String> getFollowers(String userId, int scrolls) {
        navigateToFollowersPage(userId);
        HashSet<String> listFollowers = new HashSet<>();

        for (int i = 0; i < scrolls + 1; i++) {
            List<Object> profiles = automationTool.waitForElements("css", "button[data-testid='UserCell']"); // waitForElementsToBeVisible
            for (Object profile : profiles) {
                List<Object> linkElements = automationTool.findElements(profile, "css", "a");
                if (!linkElements.isEmpty()) {
                    Object linkElement = linkElements.getFirst();
                    String href = automationTool.getAttribute(linkElement, "href");
                    listFollowers.add(getUsernameFromURL(href));
                }
            }
            automationTool.scroll(0, 250);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<>(listFollowers);
    }

    @Override
    public int getFollowersCount(String userId) {
        navigateToProfilePage(userId);
        List<Object> followers = automationTool.waitForElements("xpath", "//a[@href='/" + userId + "/verified_followers']");
        String followerStr = automationTool.getText(followers.getFirst()).split(" ")[0];
        return parseNumber(followerStr);
    }

    @Override
    public List<String> getPostIds(String username, int limit, int scrolls) throws InterruptedException {
        navigateToProfilePage(username);
        int scrollCount = 0;
        HashSet<String> tweets = new HashSet<>();
        while (tweets.size() < limit && scrollCount < scrolls) {
            List<Object> elements = automationTool.waitForElements("css", "article");
            boolean check = true;

            for (Object element : elements) {
                try {
                    List<Object> ownerElements = automationTool.findElements(element, "xpath", ".//a[contains(@href, '/status/')]");
                    if (!ownerElements.isEmpty()) {
                        Object ownerElement = ownerElements.get(0);
                        String tweetUrl = automationTool.getAttribute(ownerElement, "href");
                        String tweetID = getTweetIdFromUrl(tweetUrl);

                        List<Object> userElements = automationTool.findElements(element, "xpath", ".//div[@data-testid='User-Name']//a");
                        if (!userElements.isEmpty()) {
                            Object userElement = userElements.getFirst();
                            String ownerName = automationTool.getAttribute(userElement, "href");
                            String user = getUsernameFromURL(ownerName);
                            assert user != null;
                            String entry = user + " "  + tweetID;
                            tweets.add(entry);

                            if (tweets.size() >= limit) {
                                check = false;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            automationTool.scroll(0, 250);
            Thread.sleep(250);
            scrollCount++;

            if (!check) {
                break;
            }
        }
        return new ArrayList<>(tweets);
    }

    @Override
    public List<String> getReposters(String userId, String postId, int scrolls) {
        navigateToRetweetPage(userId, postId);
        HashSet<String> reposterIds = new HashSet<>();

        for (int i = 0; i < scrolls + 1; i++) {
            List<Object> profiles = automationTool.waitForElements("css", "button[data-testid='UserCell']");
            for (Object profile : profiles) {
                List<Object> linkElements = automationTool.findElements(profile, "css", "a");
                if (!linkElements.isEmpty()) {
                    Object linkElement = linkElements.getFirst();
                    String href = automationTool.getAttribute(linkElement, "href");
                    reposterIds.add(getUsernameFromURL(href));
                }
            }
            automationTool.scroll(0, 250);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<>(reposterIds);
    }

    @Override
    public List<String> getCommenters(String userId, String postId, int scrolls) {
        navigateToTweetPage(userId, postId);
        List<String> commenters = new ArrayList<>();
        for (int i = 0; i < scrolls + 1; i++) {
            List<Object> articles = automationTool.waitForElements("css", "article[data-testid='tweet']");
            for (int j = 1; j < articles.size(); j++) {
                Object article = articles.get(j);
                List<Object> linkElements = automationTool.findElements(article, "css", "a");
                if (!linkElements.isEmpty()) {
                    Object linkElement = linkElements.getFirst();
                    String href = automationTool.getAttribute(linkElement, "href");
                    String commenter = getUsernameFromURL(href);
                    if (!commenters.contains(commenter)) {
                        commenters.add(commenter);
                    }
                }
            }
            automationTool.scroll(0, 250);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return commenters;
    }


    @Override
    public boolean isAccountAvailable(String userId) {
        navigateToProfilePage(userId);
        try {
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
                    List<Object> linkElements = automationTool.findElements(profile, "css", "a");
                    if (!linkElements.isEmpty()) {
                        Object linkElement = linkElements.getFirst();
                        String href = automationTool.getAttribute(linkElement, "href");
                        userIds.add(getUsernameFromURL(href));

                        if (++count >= limit) {
                            break;
                        }
                    }
                }
                if (count >= limit) {
                    break;
                }

                automationTool.scroll(0, 200);
                Thread.sleep(2000);
                scrollCount++;
            }
        } catch (Exception e) {
            // Xử lý exception
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
        } else if (input.endsWith("B")) {
            multiplier = 1_000_000_000;
            input = input.substring(0, input.length() - 1);
        }

        try {
            return (int)(Double.parseDouble(input) * multiplier);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input format: " + input);
        }
    }
}