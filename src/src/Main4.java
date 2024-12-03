import java.util.ArrayList;

public class Main4 {
    public static void main(String[] args) {
        String username = "caohuythinh@gmail.com";
        String user = "ThnhCaoHuy";
        String password = "mancity1st";
        TwitterBot bot = new TwitterBot();
        bot.login(username, user, password);
        TwitterTweet.getTweet(bot, "leomessisite", "1862927724576174488");
        ArrayList<String> cmm = TwitterTweet.getAllCommenters(bot, 200);
        for (String x : cmm) {
            System.out.println(x);
        }
        System.out.println(cmm.size());
    }
}
