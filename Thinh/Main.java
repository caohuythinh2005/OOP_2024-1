import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        TwitterBot bot = new TwitterBot();
        TwitterLogin login = new TwitterLogin(bot);
        TwitterDataCollector collector = new TwitterDataCollector(bot);
        String username = "caohuythinh@gmail.com";
        String user = "ThnhCaoHuy";
        String password = "mancity1st";
        login.login(username, user, password);

//        TwitterTweet tweet = new TwitterTweet("leomessisite", "1858644152139071907");
//        tweet.getTweet(bot);
//        ArrayList<String> arrCommenters = tweet.getAllCommenters(bot);
//        for (String x : arrCommenters) {
//            System.out.println(x);
//        }

        KOL newUser = new KOL("leomessisite");
        newUser.getUser(bot);
        ArrayList<TwitterTweet> allTweets = newUser.getAllTweetsWithScroll(bot, 200);
        for (TwitterTweet x : allTweets) {
            System.out.println(x.toString());
        }
        System.out.println(allTweets.size());
//        TwitterSimulation botSim = new TwitterSimulation(bot.getDriver(), bot.getWait());
//        botSim.searchUser("messi");
//        botSim.clickOnProfile(0);
//        botSim.getFollowersCount();
//        bot.getDriver().get(TwitterSimulation.getUrlFollowers(botSim.getUserID()));
//        ArrayList<String> ListFollowers = botSim.getAllFollowersWithScroll(bot);
//        System.out.println(ListFollowers.size());
        // TwitterSimulation.getUrlFollowers(botSim.getAccountName());

//        HashtagManager hashTag = new HashtagManager();
//        hashTag.readHashtagsFromFile("src\\hashtags.txt");
//        ArrayList<String> hashtags = hashTag.getHashtags();
//        for (String x : hashtags) {
//            System.out.println(x);
//        }
//
//        ArrayList<KOL> kols = new ArrayList<KOL>();
////        KOL kol1 = new KOL("123", "456", 789);
////        KOL kol2 = new KOL("aaa", "bbb", 111);
//        KolFileManager kf = new KolFileManager("src:\\data.txt");
//
//
//        // login
//        TwitterBot bot = new TwitterBot();
//        TwitterLogin login = new TwitterLogin(bot);
//        TwitterDataCollector collector = new TwitterDataCollector(bot);
//        String username = "caohuythinh@gmail.com";
//        String user = "ThnhCaoHuy";
//        String password = "mancity1st";
//        login.login(username, user, password);
//
//
//        for (String x : hashtags) {
//            TwitterBot dumpBot = new TwitterBot();
//            dumpBot.getDriver().get("https://www.google.com/");
//            TwitterSimulation botSim = new TwitterSimulation(bot.getDriver(), bot.getWait());
//            TwitterSimulation botDumpSim = new TwitterSimulation(dumpBot.getDriver(), dumpBot.getWait());
//            collector.searchKolFromHashtag(x);
//            int y = botSim.getNumberOfProfiles();
//            ArrayList<String> arr = new ArrayList<String>(botSim.getAllLinkProfiles());
//            for (String acc : arr) {
//                System.out.println(acc);
//            }
//            for (String acc : arr) {
//                dumpBot.getDriver().get(acc);
//                if (botDumpSim.checkAvailableAccount()) {
//                    int followersCount = botDumpSim.getFollowersCount();
//                    String usernameAcc = botDumpSim.getUserID();
//                    kols.add(new KOL(usernameAcc, followersCount));
//                }
//            }
//            dumpBot.close();
//            System.out.println(kols.size());
//            for (int i = 0; i < y; i++) {
//                botSim.clickOnProfile(i);
//                int followersCount = botSim.getFollowersCount();
//                String usernameAcc = botSim.getUserID();
//                kols.add(new KOL(usernameAcc, followersCount));
//                collector.searchKolFromHashtag(x);
//            }
//            kf.addKolsToFile(kols);
//        }
//        kf.addKolsToFile(kols);
        // bot.close();
        // System.out.println(kol1.getDisplayName() + "," + kol1.getUsername() + "," + kol1.getNumberOfFollowers());

//
//        // Đăng nhập vào Twitter
//        login.login(username, password);
//
////        TwitterBot bot1 = new TwitterBot();
////        TwitterLogin login1 = new TwitterLogin(bot1);
////        TwitterDataCollector collector1 = new TwitterDataCollector(bot);
////        TwitterD
//
//        // Đăng nhập vào Twitter
////        login1.login(username, user, password);
//
//        // Tìm kiếm hashtag #blockchain
////        collector.searchHashtag("blockchain");
//
//        // Đóng trình duyệt sau khi hoàn tất
//        // bot.close();
//        TwitterSimulation botSim = new TwitterSimulation(bot.getDriver(), bot.getWait());
////        botSim.searchUser("messi");
////        botSim.clickOnProfile(1);
////        botSim.getFollowersCount();
////
////        KOL user = new KOL("KevinDeBruyne");
////        Tweet obj = new Tweet("1835019601437945976", "KevinDeBruyne");
////        botSim.searchTweet(obj);
    }
}