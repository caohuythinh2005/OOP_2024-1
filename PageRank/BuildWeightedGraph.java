package pagerank;
import java.util.*;

public class BuildWeightedGraph {

    private WeightedGraph graph;
    private Map<String, Node> nodeCache; // Cache lưu các Node đã tạo

    public BuildWeightedGraph() {
        this.graph = new WeightedGraph();
        this.nodeCache = new HashMap<>();
    }

    // Trả về Node đã tồn tại hoặc tạo Node mới nếu chưa tồn tại
    private Node getOrCreateNode(String username) {
        return nodeCache.computeIfAbsent(username, Node::new);
    }

    public WeightedGraph buildGraph(List<KOL> kols) {
        for (KOL kol : kols) {
            Node kolNode = getOrCreateNode(kol.getUsername()); // Node của KOL chính
            graph.addNode(kolNode);

            // Tính toán dựa trên followers
            double followerWeight = kol.getNumberOfFollowers();
            for (String follower : kol.getFollowers()) {
                Node followerNode = getOrCreateNode(follower);
                graph.addEdge(followerNode, kolNode, followerWeight);
            }

            // Xử lý từng tweet của KOL
            for (Tweet tweet : kol.getTweets()) {
                // Thêm edge từ commenter đến tweet
                for (String commenter : tweet.getCommenters()) {
                    Node commenterNode = getOrCreateNode(commenter);
                    graph.addEdge(commenterNode, kolNode, 1.0); // Commenter trỏ đến KOL
                }

                // Thêm edge từ retweeter đến tweet
                for (String retweeter : tweet.getRetweeters()) {
                    Node retweeterNode = getOrCreateNode(retweeter);
                    graph.addEdge(retweeterNode, kolNode, 1.0); // Retweeter trỏ đến KOL
                }
            }
        }

        return graph;
    }
}
