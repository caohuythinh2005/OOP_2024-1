package collector;
import automation.AutomationTool;
import automation.SeleniumAutomationTool;
import datamanager.AdvancedDataManager;
import datamanager.AdvancedDataManagerJson;
import datamanager.DataManager;
import datamanager.DataManagerJson;
import entity.KOL;
import entity.RankedKOL;
import entity.Tweet;
import pagerank.Node;
import pagerank.WeightedGraph;
import pagerank.WeightedPageRank;
import socialmedia.SocialMediaPlatform;
import socialmedia.TwitterPlatform;

import java.io.IOException;
import java.util.*;

public class DataCollector {
    private AutomationTool automationTool;
    private AutomationTool anotherAutomationTool;
    private SocialMediaPlatform platform;
    private SocialMediaPlatform anotherPlatform;


    private String username;
    private String email;
    private String password;

    public DataCollector(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        automationTool = new SeleniumAutomationTool(20);
        anotherAutomationTool = new SeleniumAutomationTool(20);
        platform = new TwitterPlatform(automationTool);
        anotherPlatform = new TwitterPlatform(anotherAutomationTool);

    }
    public KOL collectData(String code, String user, int limitTweets, int scrollsTweetsID, int scrollsCommenters, int scrollsRetweeters, int scrollsFollowers) throws InterruptedException {
        // 0 la ko lay tweet
        // 1 la lay tweet
        Thread.sleep(2000);
        List<Tweet> tweets = new ArrayList<>();
        List<String> followers = new ArrayList<>();
        if (platform.isAccountAvailable(user)) {
            if (code.equals("1")) {
                List<String> postIds = platform.getPostIds(user, limitTweets, scrollsTweetsID);
                for (String postId : postIds) {
                    System.out.println(postId);
                }
                Thread.sleep(3000);
                for (String postId : postIds) {
                    String[] words = postId.split(" ");
                    if (words[0].equals(user)) {
                        List<String> commenters = platform.getCommenters(user, words[1], scrollsCommenters);
                        Thread.sleep(3000);
                        List<String> retweeters = platform.getReposters(user, words[1], scrollsRetweeters);
                        Thread.sleep(3000);
                        tweets.add(new Tweet(user, words[1], commenters, retweeters));
                    }
                }
            }
            followers = platform.getFollowers(user, scrollsFollowers);
            Thread.sleep(3000);
            int followerCount = platform.getFollowersCount(user);
            Thread.sleep(3000);
            return new KOL(user, followerCount, followers, tweets);
        }
        return null;
    }

    public void getUserData(int limit, int scrolls) throws InterruptedException, IOException {
        automationTool.open();
        anotherAutomationTool.open();
        Thread.sleep(2000);
        platform.login(Arrays.asList(username, email, password));
        List<List<String>> obj = null;
        AdvancedDataManager<String> advancedDataManager = new AdvancedDataManagerJson<>(Arrays.asList("src\\resource\\current_hashtags.json", "src\\resource\\old_hashtags.json"), String.class);
        try {
            obj = advancedDataManager.getAllData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DataManager<String> dataManager = new DataManagerJson<>("src\\resource\\data.json", String.class);
        HashSet<String> profiles = new HashSet<>();

        for (String hashtag : obj.getFirst()) {
            List<String> user = platform.searchUserFromHashtag(hashtag, limit, scrolls);
            for (String user1 : user) {
                if (anotherPlatform.isAccountAvailable(user1)) {
                    int followerCount = anotherPlatform.getFollowersCount(user1);
                    profiles.add(user1 + "," + followerCount);
                    Thread.sleep(2000);
                }

            }
            advancedDataManager.moveData(0, 1, hashtag);
            Thread.sleep(2000);
        }
        automationTool.close();
        anotherPlatform.close();
        List<String> lstProfiles = new ArrayList<>(profiles);
        dataManager.writeData(lstProfiles);
    }

    public void getKOLData(String code, int limitTweets, int scrollsTweetsID, int scrollsCommenters, int scrollsRetweeters, int scrollsFollowers) throws InterruptedException, IOException {
        automationTool.open();
        AdvancedDataManager<String> kolManager = new AdvancedDataManagerJson<>(Arrays.asList("src\\resource\\current_kol_name.json", "src\\resource\\old_kol_name.json"), String.class);
        DataManager<KOL> kolDataManager = new DataManagerJson<>("src\\resource\\kol_data.json", KOL.class);
        List<List<String>> obj = kolManager.getAllData();
        List<String> listKOLName = obj.getFirst();
        Thread.sleep(2000);
        platform.login(Arrays.asList(username, email, password));
        for (String kolName : listKOLName) {
            System.out.println(kolName);
            KOL kol = collectData(code, kolName, limitTweets, scrollsTweetsID, scrollsCommenters, scrollsRetweeters, scrollsFollowers);
            if (kol != null) {
                kolDataManager.addData(kol);
            }
            kolManager.moveData(0, 1, kolName);
        }
        automationTool.close();
    }

    public void filterKOL(int followerThreshold) throws IOException {
        String inputFilePath = "src\\resource\\data.json";
        String outputFilePath = "src\\resource\\current_kol_name.json";
        List<String> lines = new DataManagerJson<String>(inputFilePath, String.class).readData();
        List<String> filteredKOLs = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.replaceAll("[\\[\\]\"]", "").split(",");

            if (parts.length < 2) {
                System.err.println("Skipping invalid line: " + line);
                continue;
            }

            String kolName = parts[0].trim();
            int followers;
            try {
                followers = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                System.err.println("Skipping line with invalid follower count: " + line);
                continue;
            }

            if (followers > followerThreshold) {
                filteredKOLs.add(kolName);
            }
        }

        // Save filtered KOLs as JSON using DataSaver
        new DataManagerJson<String>(outputFilePath, String.class).writeData(filteredKOLs);
        System.out.println("Filtered KOLs have been saved to " + outputFilePath);
    }

    public Node getOrCreateNode(Map<String, Node> nodeCache, String username) {
        return nodeCache.computeIfAbsent(username, Node::new);
    }

    public WeightedGraph buildGraph(List<KOL> kols) {
        WeightedGraph graph = new WeightedGraph();
        Map<String, Node> nodeCache = new HashMap<>();
        for (KOL kol : kols) {
            Node kolNode = getOrCreateNode(nodeCache, kol.getUsername()); // Node của KOL chính
            kolNode.setType("KOL");
            graph.addNode(kolNode);

            // Tính toán dựa trên followers
            double followerWeight = kol.getNumberOfFollowers();
            for (String follower : kol.getFollowers()) {
                Node followerNode = getOrCreateNode(nodeCache, follower);
                graph.addEdge(followerNode, kolNode, followerWeight);
            }

            // Xử lý từng tweet của KOL
            for (Tweet tweet : kol.getTweets()) {
                // Thêm edge từ commenter đến tweet
                for (String commenter : tweet.getCommenters()) {
                    Node commenterNode = getOrCreateNode(nodeCache, commenter);
                    graph.addEdge(commenterNode, kolNode, 1.0); // Commenter trỏ đến KOL
                }

                // Thêm edge từ retweeter đến tweet
                for (String retweeter : tweet.getRetweeters()) {
                    Node retweeterNode = getOrCreateNode(nodeCache, retweeter);
                    graph.addEdge(retweeterNode, kolNode, 1.0); // Retweeter trỏ đến KOL
                }
            }
        }

        return graph;
    }

    public void getRankKOL() throws IOException {
        String inputFilePath = "src\\resource\\kol_data.json";
        String outputFilePath = "src\\resource\\ranked.json";

        System.out.println("Loading KOL data...");
        DataManager<KOL> dataManager = new DataManagerJson<>(inputFilePath, KOL.class);
        List<KOL> kols = dataManager.readData();
        System.out.println("Loaded " + kols.size() + " KOLs successfully.");

        // 2. Xây dựng đồ thị từ danh sách KOL
        WeightedGraph graph = buildGraph(kols);
        System.out.println("Graph built successfully. Number of nodes: " + graph.getNodes().size());

        // 3. Tính toán PageRank
        System.out.println("Computing PageRank...");
        WeightedPageRank pageRank = new WeightedPageRank(graph);
        pageRank.computePageRank(10);
        Map<Node, Double> ranks = pageRank.getRanks();
        System.out.println("PageRank computation completed.");

        // 4. Lọc chỉ các node là KOL và sắp xếp theo PageRank
        System.out.println("Filtering and processing KOL results...");
        List<Node> rankedNodes = pageRank.getRankedNodes();

        // Chỉ lấy các node là KOL
        List<RankedKOL> RankedKOLs = new ArrayList<>();
        int rankIndex = 1;
        for (Node node : rankedNodes) {
            if (node.getType().equalsIgnoreCase("KOL")) { // Kiểm tra node có phải là KOL
                RankedKOLs.add(new RankedKOL(rankIndex++, node.getId(), ranks.get(node)));
            }
        }

        // 5. Lưu kết quả vào file JSON
        System.out.println("\nSaving PageRank results to JSON...");
        DataManager<RankedKOL> rankManager = new DataManagerJson<>(outputFilePath, RankedKOL.class);
        rankManager.writeData(RankedKOLs);
        System.out.println("PageRank results saved to " + outputFilePath);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        DataCollector collector = new DataCollector("ThnhCaoHuy","caohuythinh@gmail.com", "mancity1st");
        collector.getUserData(3, 2);
        // collector.filterKOL(100000);
        // collector.getKOLData("1", ThnhCaoHuy","caohuythinh@gmail.com", "mancity1st", 10, 10, 5, 5, 5);
        collector.getRankKOL();
    }
}
