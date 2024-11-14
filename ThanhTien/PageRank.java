package pagerank;

import java.util.*;

public class PageRank {
    
    private static final double DAMPING_FACTOR = 0.85;
    private static final int ITERATIONS = 100;
    private static final double THRESHOLD = 1e-6;
    
    public static Map<User, Double> computePageRank(List<User> users) {
        int n = users.size();
        Map<User, Double> pageRank = new HashMap<>();
        
        // Khởi tạo rank cho các user ban đầu
        for (User user : users) {
            pageRank.put(user, 1.0 / n);
        }
        
        // Lặp để tính toán rank
        for (int iter = 0; iter < ITERATIONS; iter++) {
            Map<User, Double> newPageRank = new HashMap<>();
            double sum = 0.0;
            
            // Tính toán giá trị mới cho mỗi user
            for (User user : users) {
                double rank = (1 - DAMPING_FACTOR) / n;
                
                for (User follower : user.getFollowers()) {
                    int outDegree = follower.getFollowing().size();
                    // outDegree để kiểm tra nếu user không có ai theo dõi hoặc không theo dõi ai, tránh phép chia 0
                    
                    // Kiểm tra outDegree để tránh chia cho 0
                    if (outDegree > 0) {
                        rank += DAMPING_FACTOR * (pageRank.get(follower) / outDegree);
                    } else {
                        // Nếu outDegree = 0, phân phối đều PageRank của follower cho tất cả user
                        rank += DAMPING_FACTOR * (pageRank.get(follower) / n);
                    }
                }
                newPageRank.put(user, rank);
                sum += rank;
            }
            
            // Chuẩn hóa để đảm bảo tổng điểm PageRank là 1
            for (User user : users) {
                newPageRank.put(user, newPageRank.get(user) / sum);
            }
            
            // Kiểm tra điều kiện hội tụ
            boolean converged = true;
            for (User user : users) {
                if (Math.abs(newPageRank.get(user) - pageRank.get(user)) > THRESHOLD) {
                    converged = false;
                    break;
                }
            }
            
            pageRank = newPageRank;
            if (converged) {
                break;
            }
        }
        
        return pageRank;
    }
}
