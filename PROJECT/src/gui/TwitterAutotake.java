package gui;
import automation.AutomationTool;
import automation.SeleniumAutomationTool;
import datamanager.AdvancedDataManager;
import datamanager.AdvancedDataManagerJson;
import datamanager.DataManager;
import datamanager.DataManagerJson;
import entity.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pagerank.Node;
import pagerank.WeightedGraph;
import pagerank.WeightedPageRank;
import socialmedia.SocialMediaPlatform;
import socialmedia.TwitterPlatform;

import java.io.*;
import java.util.*;

public class TwitterAutotake extends Application {

    private String username;
    private String email;
    private String password;
    private final List<String> hashtags = new ArrayList<>();

    private final ObservableList<User> userList = FXCollections.observableArrayList();
    private ObservableList<User> copyList=FXCollections.observableArrayList();
    private final ObservableList<RankedKOL> rankedList = FXCollections.observableArrayList();

    private KOL collectData(SocialMediaPlatform platform, String code, String user, int limitTweets, int scrollsTweetsID, int scrollsCommenters, int scrollsRetweeters, int scrollsFollowers) throws InterruptedException {
        // code = 0 neu ko tweet
        // code = 1 neu nhan tweet
        Thread.sleep(2000);
        List<Tweet> tweets = new ArrayList<>();
        List<String> followers = new ArrayList<>();
        if (platform.isAccountAvailable(user)) {
            if (code.equals("1")) {
                List<String> postIds = platform.getPostIds(user, limitTweets, scrollsTweetsID);
                Thread.sleep(1000);
                for (String postId : postIds) {
                    List<String> commenters = platform.getCommenters(user, postId, scrollsCommenters);
                    Thread.sleep(1000);
                    List<String> retweeters = platform.getReposters(user, postId, scrollsRetweeters);
                    Thread.sleep(1000);
                    tweets.add(new Tweet(user, postId, commenters, retweeters));
                }
            }
            followers = platform.getFollowers(user, scrollsFollowers);
            Thread.sleep(1000);
            int followerCount = platform.getFollowersCount(user);
            Thread.sleep(1000);
            return new KOL(user, followerCount, followers, tweets);
        }
        return null;
    }

    private Node getOrCreateNode(Map<String, Node> nodeCache, String username) {
        return nodeCache.computeIfAbsent(username, Node::new);
    }


    private WeightedGraph buildGraph(List<KOL> kols) {
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

    private void getRankKOL() throws IOException {
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
        List<KOLRank> kolRanks = new ArrayList<>();
        int rankIndex = 1;
        for (Node node : rankedNodes) {
            if (node.getType().equalsIgnoreCase("KOL")) { // Kiểm tra node có phải là KOL
                kolRanks.add(new KOLRank(rankIndex++, node.getId(), ranks.get(node)));
            }
        }

        // 5. Lưu kết quả vào file JSON
        System.out.println("\nSaving PageRank results to JSON...");
        DataManager<KOLRank> rankManager = new DataManagerJson<>(outputFilePath, KOLRank.class);
        rankManager.writeData(kolRanks);
        System.out.println("PageRank results saved to " + outputFilePath);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Giao diện bước 1
        VBox step1 = new VBox(10);
        step1.setPadding(new Insets(20));
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button nextButton1 = new Button("Next");

        step1.getChildren().addAll(new Label("Twitter account"),
                usernameField, emailField, passwordField, nextButton1);

        // Giao diện bước 2
        VBox step2 = new VBox(10);
        step2.setPadding(new Insets(20));
        TextArea hashtagsField = new TextArea();
        hashtagsField.setWrapText(true);
        hashtagsField.setPrefWidth(400);
        hashtagsField.setPrefHeight(200);
        hashtagsField.setPromptText("Enter hashtags separated by commas");
        Button loadFileButton = new Button("Load from File (.json)");
        Button finishButton = new Button("Find User By Hashtags");

        step2.getChildren().addAll(new Label("Find User"),
                loadFileButton,hashtagsField, finishButton);

        // Giao diện bước 3
        TextField thresholdField = new TextField();
        thresholdField.setPrefWidth(400);
        thresholdField.setPromptText("Enter threshold (followers)");
        Button applyThresholdButton = new Button("Sort KOLs");
        HBox thresBox=new HBox();
        thresBox.getChildren().addAll(thresholdField,applyThresholdButton);

        //Tạo bảng KOL-Follower
        TableColumnFactory<User> columnFactory = new TableColumnFactory<>();
        TableViewFactory<User> tableViewFactory = new TableViewFactory<>();

        TableColumn<User, String> usernameColumn = columnFactory.createColumn(
                "Username", 0.5, User::getUsername);

        TableColumn<User, String> followersColumn = columnFactory.createColumn(
                "Followers", 0.5, user -> String.valueOf(user.getNumberOfFollowers()));

        TableView<User> tableView = tableViewFactory.createTableView(
                List.of(usernameColumn, followersColumn), userList);

        tableView.setPrefWidth(400);


        Button backBox=new Button("Back");
        Button tweetMore=new Button("Tweet Data and Pagerank");
        Button pagerankBox=new Button("Pagerank");
        ButtonBoxFactory buttonBoxFactory3 = new ButtonBoxFactory();
        HBox buttonBox = buttonBoxFactory3.createButtonBox(backBox, tweetMore, pagerankBox);

        // Tạo VBox cho step3, chứa TableView và các nút
        VBox step3 = new VBox(10);
        step3.setPadding(new Insets(20));
        step3.getChildren().addAll(new Label("KOL Data"), thresBox, tableView, buttonBox);

        //Giao diện bước 4
        Button backBox1=new Button("Back");
        Button exitButton=new Button("Finish");
        Button importFile=new Button("Import data file");
        ButtonBoxFactory buttonBoxFactory4 = new ButtonBoxFactory();
        HBox finalBox = buttonBoxFactory4.createButtonBox(backBox1, exitButton);

        loadRankedDataFromFile();

        //Tạo bảng Ranked KOLs
        TableColumnFactory<RankedKOL> columnFinal = new TableColumnFactory<>();
        TableViewFactory<RankedKOL> tableViewFinal = new TableViewFactory<>();

        TableColumn<RankedKOL, String> rankColumn = columnFinal.createColumn(
                "Rank", 0.33, kol -> String.valueOf(kol.getRankIndex()));

        TableColumn<RankedKOL, String> fusernameColumn = columnFinal.createColumn(
                "Username", 0.33, RankedKOL::getUsername);

        TableColumn<RankedKOL, String> scoreColumn = columnFinal.createColumn(
                "Score", 0.33, kol -> String.valueOf(kol.getScore()));

        TableView<RankedKOL> finalTable = tableViewFinal.createTableView(
                List.of(rankColumn, fusernameColumn, scoreColumn), rankedList);

        finalTable.setPrefWidth(600);


        VBox step4=new VBox(10);
        step4.setPadding(new Insets(20));
        step4.getChildren().addAll(new Label("Ranked KOLs"),finalTable,importFile,finalBox);

        // Chuyển giao diện
        StackPane root = new StackPane();
        root.getChildren().addAll(step1,step2,step3,step4);
        step2.setVisible(false);
        step3.setVisible(false);
        step4.setVisible(false);

        nextButton1.setOnAction(_ -> {
            username = usernameField.getText();
            email = emailField.getText();
            password = passwordField.getText();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please fill in all fields!");
            } else {
                step1.setVisible(false);
                step2.setVisible(true);
            }
        });

        finishButton.setOnAction(_ -> {
            String hashtagsInput = hashtagsField.getText();
            if (!hashtagsInput.isEmpty()) {
                if (hashtags.isEmpty()) {
                    parseHashtags(hashtagsInput);
                }
            }

            if (hashtags.isEmpty()) {
                showAlert("Error", "Please enter valid hashtags!");
                return;
            }

            DataManager<String> dumpDataManager = new DataManagerJson<>("src\\resource\\current_hashtags.json", String.class);
            for (String hashtag : hashtags) {
                try {
                    dumpDataManager.addData(hashtag);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            AutomationTool automationTool = new SeleniumAutomationTool(20);
            automationTool.open();
            SocialMediaPlatform twitterPlatform = new TwitterPlatform(automationTool);

            AutomationTool anotherAutomationTool = new SeleniumAutomationTool(20);
            anotherAutomationTool.open();
            SocialMediaPlatform anotherTwitterPlatform = new TwitterPlatform(anotherAutomationTool);

            twitterPlatform.login(Arrays.asList(username, email, password));
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
                List<String> username = twitterPlatform.searchUserFromHashtag(hashtag, 3, 1);
                for (String user : username) {
                    int followerCount = anotherTwitterPlatform.getFollowersCount(user);
                    profiles.add(user + "," + followerCount);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    advancedDataManager.moveData(0, 1, hashtag);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            twitterPlatform.close();
            anotherTwitterPlatform.close();
            try {
                dataManager.writeData(new ArrayList<>(profiles));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                loadKOLDataFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            twitterPlatform.close();
            step2.setVisible(false);
            step3.setVisible(true);
        });

        loadFileButton.setOnAction(_ -> {
            try {
                loadHashtagsFromFile(hashtagsField);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Twitter Autotake");
        primaryStage.setScene(scene);
        primaryStage.show();

        applyThresholdButton.setOnAction(_ -> {
            String thresholdText = thresholdField.getText();
            try {
                int threshold = Integer.parseInt(thresholdText);
                ObservableList<User> filteredList = FXCollections.observableArrayList();

                for (User user : userList) {
                    if (user.getNumberOfFollowers() > threshold) {
                        filteredList.add(user);
                    }
                }

                tableView.setItems(filteredList);
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid number for the threshold.");
            }
        });
        tweetMore.setOnAction(_ -> {
            copyList=FXCollections.observableArrayList(tableView.getItems());
            System.out.println(thresholdField.getText());
            List<User> displayedUsers = new ArrayList<>(tableView.getItems());
            List<String> usernames = displayedUsers.stream()
                    .map(User::getUsername) // Lấy username
                    .toList();
            try {
                new DataManagerJson<String>("src/resource/current_kol_name.json", String.class).writeData(usernames);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            AutomationTool automationTool = new SeleniumAutomationTool(20);
            automationTool.open();
            SocialMediaPlatform twitterPlatform = new TwitterPlatform(automationTool);
            twitterPlatform.login(Arrays.asList(username, email, password));
            List<KOL> kols = new ArrayList<>();
            for (String user : usernames) {
                try {
                    KOL newKol = collectData(twitterPlatform, "1", user, 3, 1, 1, 1, 1);
                    if (newKol != null) {
                        kols.add(newKol);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                new DataManagerJson<KOL>("src/resource/kol_data.json", KOL.class).writeData(kols);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //Chạy hamf GetKOLData

            try {
                loadRankedDataFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                getRankKOL();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            twitterPlatform.close();

            step3.setVisible(false);
            step4.setVisible(true);
        });

        pagerankBox.setOnAction(_ -> {
            copyList=FXCollections.observableArrayList(tableView.getItems());
            System.out.println(thresholdField.getText());
            List<User> displayedUsers = new ArrayList<>(tableView.getItems());
            List<String> usernames = displayedUsers.stream()
                    .map(User::getUsername) // Lấy username
                    .toList();
            try {
                new DataManagerJson<String>("src/resource/current_kol_name.json", String.class).writeData(usernames);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            //Chạy hàm lấy followers rồi pagerank

            AutomationTool automationTool = new SeleniumAutomationTool(20);
            automationTool.open();
            SocialMediaPlatform twitterPlatform = new TwitterPlatform(automationTool);
            twitterPlatform.login(Arrays.asList(username, email, password));
            List<KOL> kols = new ArrayList<>();
            for (String user : usernames) {
                try {
                    KOL newKol = collectData(twitterPlatform, "0", user, 3, 1, 1, 1, 1);
                    if (newKol != null) {
                        kols.add(newKol);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                new DataManagerJson<KOL>("src/resource/kol_data.json", KOL.class).writeData(kols);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                getRankKOL();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                loadRankedDataFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            step3.setVisible(false);
            step4.setVisible(true);
        });

        backBox.setOnAction(_->{
            step2.setVisible(true);
            step3.setVisible(false);
        });

        backBox1.setOnAction(_->{
            tableView.setItems(copyList);
            step3.setVisible(true);
            step4.setVisible(false);
        });

        exitButton.setOnAction(_-> Platform.exit());
    }

    private void loadHashtagsFromFile(TextArea hashtagsField) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            List<String> fileHashtags = new DataManagerJson<>(file.getAbsolutePath(),String.class).readData();

            hashtags.clear();
            hashtags.addAll(fileHashtags);

            hashtagsField.setText(String.join(", ", hashtags));
            showAlert("Success", "Hashtags loaded from file!");
        }
    }

    private void parseHashtags(String hashtagsInput) {
        String[] hashtagArray = hashtagsInput.split(",");
        for (String tag : hashtagArray) {
            hashtags.add(tag.trim().replaceAll("\"", ""));
        }
    }


    private void loadKOLDataFromFile() throws IOException {
        List<String> rawData = new DataManagerJson<String>("src/resource/data.json",String.class).readData();

        userList.clear();
        for (String data : rawData) {
            String[] parts = data.split(",");
            if (parts.length == 2) {
                String username = parts[0].trim();
                int followers = Integer.parseInt(parts[1].trim());
                userList.add(new User(username, followers));
            }
        }
    }

    private void loadRankedDataFromFile() throws IOException {
        String filePath="src/resource/ranked.json";
        List<RankedKOL> rankedData = new DataManagerJson<RankedKOL>(filePath, RankedKOL.class).readData();
        rankedList.clear();
        rankedList.addAll(rankedData);
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
