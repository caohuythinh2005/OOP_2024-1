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

            
            for (Tweet tweet : kol.getTweets()) {
            	// Thêm cạnh từ người bình luận (commenters) đến KOL
                for (String commenter : tweet.getCommenters()) {
                	// Bỏ qua nếu commenter là chính KOL
                    if (!commenter.equals(kolName)) {
                        graph.addEdge(commenter, kolName, 1.0); // Mỗi comment tăng trọng số 1
                    }
                }
                
                // Thêm cạnh từ người đăng lại (retweeter) đến KOL
                for (String retweeter : tweet.getRetweeters()) {
                	// Bỏ qua nếu retweeter là chính KOL
                    if (!retweeter.equals(kolName)) {
                        graph.addEdge(retweeter, kolName, 1.0); // Mỗi retweet tăng trọng số 1
                    }
                }
                
            }
            
        }


        return graph;
    }
}
