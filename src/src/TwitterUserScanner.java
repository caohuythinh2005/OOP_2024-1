import java.io.FileNotFoundException;
import java.util.ArrayList;


import java.util.Stack;

public class TwitterUserScanner {
    private ArrayList<String> hashtags;
    private TwitterBot bot;
    private TwitterSimulation botSim;
    private String username;
    private String user;
    private String password;
    private int scrolls;
    private String filePathData;
    private UserManager manager;
    private ArrayList<User> LUsers;

    public TwitterUserScanner(String username, String user, String password, int scrolls, ArrayList<String> hashtags, String filePathData) {
        this.username = username;
        this.user = user;
        this.password = password;
        this.setHashtags(hashtags);
        this.scrolls = scrolls;
        botSim = new TwitterSimulation();
        this.filePathData = filePathData;
        manager = new UserManager(this.filePathData);
    }


    public void extractUsersFromTwitter() throws FileNotFoundException {
        this.LUsers = manager.readUsersFromFile();
        bot = new TwitterBot();
        bot.login(username, user, password);
        ArrayList<String> oldUsername = new ArrayList<String>();
        for (User x : LUsers) {
            oldUsername.add(x.getUsername());
        }
        int i = 0;
        for (String x : hashtags) {
            TwitterBot dumpBot = new TwitterBot();
            dumpBot.getDriver().get("https://www.google.com/");
            ArrayList<String> obj = botSim.getALlUrlProfilesFromHashTag(this.bot, x, this.scrolls);
            for (String acc : obj) {
                i++;
                if (i == 20) {
                    dumpBot.close();
                    dumpBot = new TwitterBot();
                    dumpBot.getDriver().get("https://www.google.com/");
                    i = 0;
                }
                if (oldUsername.isEmpty()) {
                    dumpBot.getDriver().get(acc);
                    if (this.botSim.checkAvailableAccount(dumpBot)) {
                        int followerCount  = this.botSim.getFollowersCount(dumpBot);
                        String usernameAcc = this.botSim.getUsername(dumpBot);
                        LUsers.add(new User(usernameAcc, followerCount));
                        oldUsername.add(acc);
                    }
                } else {
                    if (!oldUsername.contains(acc)) {
                        dumpBot.getDriver().get(acc);
                        if (this.botSim.checkAvailableAccount(dumpBot)) {
                            int followerCount  = this.botSim.getFollowersCount(dumpBot);
                            String usernameAcc = this.botSim.getUsername(dumpBot);
                            LUsers.add(new User(usernameAcc, followerCount));
                            oldUsername.add(acc);
                        }
                    }
                }
            }
            manager.addUsersToFile(LUsers);
            dumpBot.close();
        }
        bot.close();
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

    public ArrayList<User> getUserList() {
        return this.LUsers;
    }

}
