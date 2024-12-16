package pagerank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedPageRank {
    private WeightedGraph graph;
    private Map<Node, Double> ranks;
    private double dampingFactor = 0.85;

    public WeightedPageRank(WeightedGraph graph) {
        this.graph = graph;
        this.ranks = new HashMap<>();
    }

    public Map<Node, Double> computePageRank(int iterations) {
        // Khởi tạo giá trị PageRank cho tất cả các node
        double initialRank = 1.0 / graph.getNodes().size();
        for (Node node : graph.getNodes()) {
            ranks.put(node, initialRank);
        }

        System.out.println("=== PageRank Computation Started ===");
        for (int i = 0; i < iterations; i++) {
            System.out.printf("Iteration %d\n", i + 1);
            Map<Node, Double> newRanks = new HashMap<>();

            for (Node node : graph.getNodes()) {
                double rankSum = 0.0;

                // Tính tổng điểm PageRank từ các node có cạnh hướng tới node hiện tại
                for (Map.Entry<Node, Double> entry : graph.getIncomingEdges(node).entrySet()) {
                    Node neighbor = entry.getKey();
                    double weight = entry.getValue();
                    double neighborRank = ranks.getOrDefault(neighbor, 0.0);
                    double outDegree = graph.getOutgoingEdges(neighbor).values().stream().mapToDouble(Double::doubleValue).sum();

                    if (outDegree > 0) {
                        rankSum += (neighborRank * weight) / outDegree;
                    } else {
                        // Phân phối đều giá trị PageRank của các node không có cạnh đi ra
                        rankSum += neighborRank / graph.getNodes().size();
                    }
                }

                // Áp dụng công thức PageRank
                double newRank = (1 - dampingFactor) + dampingFactor * rankSum;
                newRanks.put(node, newRank);
            }

            // Chuẩn hóa giá trị PageRank để tổng bằng 1
            double totalRank = newRanks.values().stream().mapToDouble(Double::doubleValue).sum();
            for (Map.Entry<Node, Double> entry : newRanks.entrySet()) {
                Node node = entry.getKey();
                double normalizedRank = entry.getValue() / totalRank;
                newRanks.put(node, normalizedRank);
            }

            ranks = newRanks;

            System.out.println("-----------------------------------");
        }
        System.out.println("=== PageRank Computation Completed ===");

        return ranks;
    }



 // Lấy giá trị PageRank của một node
    public double getPageRank(Node node) {
        return ranks.getOrDefault(node, 0.0);
    }

    // Lấy danh sách các node theo thứ tự PageRank giảm dần
    public List<Node> getRankedNodes() {
        return ranks.entrySet()
                .stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }
}
