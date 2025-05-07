package collector;

import automation.AutomationTool;
import automation.SeleniumAutomationTool;
import socialmedia.SocialMediaPlatform;
import socialmedia.TwitterPlatform;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        AutomationTool automationTool = new SeleniumAutomationTool(10);
        automationTool.open();
        SocialMediaPlatform twitterPlatform = new TwitterPlatform(automationTool);
        twitterPlatform.login(Arrays.asList()); // Nhap tai khoan
        // List<String> id = twitterPlatform.getPostIds("blockchain", 3, 1);
        try {
            List<String> commenter = twitterPlatform.getReposters("flow_blockchain", "1869168385759842653", 20);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        for (String x : commenter) {
//            System.out.println(x);
//        }
        System.out.println("Hello");
    }
}
