package pagerank;

import java.util.*;

public class WeightedGraph {
    // Danh sách kề (outgoing edges)
    private Map<Node, Map<Node, Double>> adjList;
    // Danh sách kề ngược (incoming edges)
    private Map<Node, Map<Node, Double>> reverseAdjList;

    public WeightedGraph() {
        adjList = new HashMap<>();
        reverseAdjList = new HashMap<>();
    }

    // Thêm node vào đồ thị (nếu chưa tồn tại)
    public void addNode(Node node) {
        adjList.putIfAbsent(node, new HashMap<>());
        reverseAdjList.putIfAbsent(node, new HashMap<>());
    }

    // Thêm cạnh có trọng số giữa 2 node
    public void addEdge(Node from, Node to, double weight) {
        // Thêm vào danh sách kề (outgoing edges)
        adjList.computeIfAbsent(from, k -> new HashMap<>())
               .merge(to, weight, Double::sum);

        // Thêm vào danh sách kề ngược (incoming edges)
        reverseAdjList.computeIfAbsent(to, k -> new HashMap<>())
                      .merge(from, weight, Double::sum);

        // Đảm bảo cả hai node đều có mặt trong đồ thị
        addNode(from);
        addNode(to);
    }

    // Lấy các cạnh đi từ một node (outgoing edges)
    public Map<Node, Double> getOutgoingEdges(Node source) {
        return adjList.getOrDefault(source, Collections.emptyMap());
    }

    // Lấy các cạnh đi đến một node (incoming edges)
    public Map<Node, Double> getIncomingEdges(Node target) {
        return reverseAdjList.getOrDefault(target, Collections.emptyMap());
    }

    // Lấy tất cả các node trong đồ thị
    public Set<Node> getNodes() {
        return adjList.keySet();
    }

    @Override
    public String toString() {
        return "WeightedGraph{" +
               "adjList=" + adjList +
               ", reverseAdjList=" + reverseAdjList +
               '}';
    }
}
