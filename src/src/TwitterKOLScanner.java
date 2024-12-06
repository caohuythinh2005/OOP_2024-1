import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class TwitterKOLScanner {
    private String username;
    private String user;
    private String password;
    private int scrolls;
    private TwitterBot bot;
    private String filePath;
    private String jsonPath;


    private String filePathSaved;
    private List<String> userList;


    public TwitterKOLScanner(String jsonPath, String filePath, String filePathSaved, String username, String user, String password, int scrolls) {
        this.filePathSaved = filePathSaved;
        this.filePath = filePath;
        this.username = username;
        this.user = user;
        this.password = password;
        this.scrolls = scrolls;
        this.jsonPath = jsonPath;
        bot = new TwitterBot();
        bot.login(this.username, this.user, this.password);
        this.userList = new ArrayList<>();
    }

    private List<String> readUsers(String filePath) throws FileNotFoundException {
        List<String> userL = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                userL.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userL;
    }

    public void readUsersFromFile() throws FileNotFoundException {
        List<String> userL = readUsers(this.filePath);
        List<String> userLCollected = readUsers(this.filePathSaved);
        for (String x : userL) {
            if (!userLCollected.contains(x)) {
                userList.add(x);
            }
        }

    }

    public ArrayList<KOL> collectData() throws InterruptedException {
        List<KOL> kolsCollected = DataLoader.loadKOLsFromJsonFile(jsonPath);
        ArrayList<KOL> kols = new ArrayList<>();
        kols.addAll(kolsCollected);
        for (String x : userList) {
            kols.add(collectUserData(x));
            DataSaver.saveKOLsToJsonFile(kols, jsonPath);
            this.saveKols(x, filePathSaved);
        }
        return kols;
    }

    public void saveKols(String username, String filePath) {
        try {
            FileWriter myWriter = new FileWriter(filePath, true);
            myWriter.write(username);
            myWriter.write('\n');
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    private KOL collectUserData(String username) throws InterruptedException {
        Thread.sleep(500);
        TwitterSimulation botSim = new TwitterSimulation();
        TwitterFollower.getFollowersTab(this.bot, username);
        Thread.sleep(500);
        ArrayList<String> arrFollowers = TwitterFollower.getAllFollowersWithScroll(this.bot, this.scrolls);
        Thread.sleep(500);
        System.out.println(arrFollowers.size());
        TwitterTweet.getProfile(this.bot, username);
        Thread.sleep(500);
        ArrayList<String> tweetsID = TwitterTweet.getAllTweetsIDWithScroll(this.bot, this.scrolls);
        Thread.sleep(500);
        System.out.println(tweetsID.size());
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (String id : tweetsID) {
            TwitterTweet.getTweet(bot, username, id);
            ArrayList<String> cmm = TwitterTweet.getAllCommenters(this.bot, this.scrolls);
            Thread.sleep(500);
            tweets.add(new Tweet(username, id, cmm));
        }
        TwitterTweet.getProfile(this.bot, username);
        int followerCount  = botSim.getFollowersCount(this.bot);


        return new KOL(username, followerCount, arrFollowers, tweets);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScrolls() {
        return scrolls;
    }

    public void setScrolls(int scrolls) {
        this.scrolls = scrolls;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePathSaved() {
        return filePathSaved;
    }

    public void setFilePathSaved(String filePathSaved) {
        this.filePathSaved = filePathSaved;
    }
}
