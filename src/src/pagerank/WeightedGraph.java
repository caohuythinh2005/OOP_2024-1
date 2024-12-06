package pagerank;

import java.util.*;

public class WeightedGraph {
    // Cấu trúc lưu đồ thị: Map<node, Map<neighbor, weight>>
    Map<String, Map<String, Double>> adjList;

    public WeightedGraph() {
        adjList = new HashMap<>();
    }

    // Thêm cạnh có trọng số giữa 2 node
    public void addEdge(String from, String to, double weight) {
        adjList.computeIfAbsent(from, k -> new HashMap<>())
               .merge(to, weight, Double::sum); // Tăng trọng số nếu cạnh đã tồn tại
        // Đảm bảo node `to` cũng có mặt trong đồ thị, ngay cả khi không có cạnh đi ra
        adjList.computeIfAbsent(to, k -> new HashMap<>());
    }

    // Lấy danh sách các node liên kết với `from`
    public Map<String, Double> getNeighbors(String from) {
        return adjList.getOrDefault(from, Collections.emptyMap());
    }

    // Lấy tất cả các node trong đồ thị
    public Set<String> getNodes() {
        return adjList.keySet();
    }


}
