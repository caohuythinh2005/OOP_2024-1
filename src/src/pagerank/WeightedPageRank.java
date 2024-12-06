package pagerank;

import java.util.*;

public class WeightedPageRank {
    private WeightedGraph graph;
    private double dampingFactor;
    private int iterations;

    public WeightedPageRank(WeightedGraph graph, double dampingFactor, int iterations) {
        this.graph = graph;
        this.dampingFactor = dampingFactor;
        this.iterations = iterations;
    }

    public Map<String, Double> computePageRank() {
        Set<String> nodes = graph.getNodes();
        int numNodes = nodes.size();

        // Khởi tạo giá trị PageRank ban đầu
        Map<String, Double> pageRank = new HashMap<>();
        for (String node : nodes) {
            pageRank.put(node, 1.0 / numNodes);
        }

        for (int i = 0; i < iterations; i++) {
            Map<String, Double> tempPageRank = new HashMap<>();
            double danglingSum = 0.0;

            // Tính tổng giá trị từ các node "dead ends"
            for (String node : nodes) {
                if (graph.getNeighbors(node).isEmpty()) {
                    danglingSum += pageRank.getOrDefault(node, 0.0);
                }
            }

            // Tính PageRank cho từng node
            for (String node : nodes) {
                double rank = (1 - dampingFactor) / numNodes;
                double sum = 0.0;

                for (String neighbor : nodes) {
                    Double weight = graph.getNeighbors(neighbor).get(node);
                    if (weight != null) {
                        double totalWeight = graph.getNeighbors(neighbor).values().stream().mapToDouble(Double::doubleValue).sum();
                        sum += (pageRank.getOrDefault(neighbor, 0.0) * weight / totalWeight);
                    }
                }

                rank += dampingFactor * (sum + danglingSum / numNodes);
                tempPageRank.put(node, rank);
            }

            pageRank = tempPageRank;
        }

        return pageRank;
    }
}
