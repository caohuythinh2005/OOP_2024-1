import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;

public class Main5 {
    public static void main(String[] args) throws InterruptedException {
        String username = "caohuythinh@gmail.com";
        String user = "ThnhCaoHuy";
        String password = "mancity1st";
        TwitterBot bot = new TwitterBot();
        bot.login(username, user, password);
        // TwitterTweet.getTweet(bot, "leomessisite", "1862927724576174488");
//        TwitterFollower.getFollowersTab(bot, "leomessisite");
//        ArrayList<String> arrFollowers = TwitterFollower.getAllFollowersWithScroll(bot, 100);
//        for (String x : arrFollowers) {
//            System.out.println(x);
//        }
//        TwitterTweet.getProfile(bot, "leomessisite");
//        ArrayList<String> tweets = TwitterTweet.getAllTweetsIDWithScroll(bot, 30);
//        for (String x : tweets) {
//            TwitterTweet.getTweet(bot, "leomessisite", "1862927724576174488");
//            ArrayList<String> cmm = TwitterTweet.getAllCommenters(bot, 200);
//        }
//
//
//        for (String x : cmm) {
//            System.out.println(x);
//        }
//        System.out.println(cmm.size());
    }
}
