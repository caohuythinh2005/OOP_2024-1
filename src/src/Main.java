import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        UserManager manager = new UserManager("src\\data.txt");
        ArrayList<User> users = manager.readUsersFromFile();
        for (User x : users) {
            System.out.println(x);
        }
//        HashtagManager hashtag = new HashtagManager();
//        hashtag.readHashtagsFromFile("src\\hashtags.txt");
//        UserManager manager = new UserManager("src\\data.txt");
//        ArrayList<User> users = manager.readUsersFromFile();
//        for (User x : users) {
//
//        }
//        ArrayList<String> hashtags = hashtag.getHashtags();
//        for (String x : hashtags) {
//            System.out.println(x);
//        }
////
//        String username = "caohuythinh@gmail.com";
//        String user = "ThnhCaoHuy";
//        String password = "mancity1st";
//
//        TwitterUserScanner obj = new TwitterUserScanner(username, user, password, 100, hashtags, "src\\data.txt");
//        obj.extractUsersFromTwitter();
//        for (User x : obj.getUserList()) {
//            System.out.println(x);
//        }
    }
}