package pagerank;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePath = "kol_data.json";

        // Load dữ liệu từ file JSON
        List<KOL> kols = DataLoader.loadKOLsFromJsonFile(filePath);

        if (kols == null || kols.isEmpty()) {
            System.out.println("No KOL data found!");
            return;
        }

        // Xây dựng đồ thị
        WeightedGraph graph = BuildWeightedGraph.buildGraphFromKOLs(kols);

        // Tính toán PageRank
        WeightedPageRank pageRank = new WeightedPageRank(graph, 0.85, 100);
        Map<String, Double> ranks = pageRank.computePageRank();

        // In kết quả chỉ cho các KOL
        System.out.println("\nKOL PageRank:");
        for (KOL kol : kols) {
            System.out.println("KOL: " + kol.getUsername() + ", Rank: " + ranks.getOrDefault(kol.getUsername(), 0.0));
        }
    }
}
