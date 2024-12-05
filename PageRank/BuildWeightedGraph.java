package pagerank;

import java.util.List;

public class BuildWeightedGraph {
    public static WeightedGraph buildGraphFromKOLs(List<KOL> kols) {
        WeightedGraph graph = new WeightedGraph();

        for (KOL kol : kols) {
            String kolName = kol.getUsername();

            // Thêm cạnh từ followers đến KOL
            for (String follower : kol.getFollowers()) {
                graph.addEdge(follower, kolName, 1.0);
            }

            // Thêm cạnh từ người bình luận (commenters) đến KOL (chỉ cần thêm 1 lần)
            for (Tweet tweet : kol.getTweets()) {
                for (String commenter : tweet.getCommenters()) {
                    graph.addEdge(commenter, kolName, 1.0);
                }
            }
        }


        return graph;
    }
}
