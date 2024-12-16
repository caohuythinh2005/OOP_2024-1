package pagerank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        // Tải dữ liệu KOL từ file JSON
        String jsonFilePath = "kol_data.json";
        List<KOL> kols = DataLoader.loadKOLsFromJsonFile(jsonFilePath);

        // Xây dựng đồ thị từ danh sách KOL
        BuildWeightedGraph builder = new BuildWeightedGraph();
        WeightedGraph graph = builder.buildGraph(kols);

        // Tính toán PageRank
        WeightedPageRank pageRank = new WeightedPageRank(graph);

        Map<Node, Double> ranks = pageRank.computePageRank(100);

        // In kết quả chỉ cho các KOL
        System.out.println("\nKOL PageRank:");
        List<KOLRank> kolRankList = new ArrayList<>();
        
        for (KOL kol : kols) {
            Node node = new Node(kol.getUsername()); // Tạo Node với username
            kolRankList.add(new KOLRank(kol.getUsername(), ranks.getOrDefault(node, 0.0)));
        }
        
        Collections.sort(kolRankList, new Comparator<KOLRank>() {
            @Override
            public int compare(KOLRank o1, KOLRank o2) {
                return Double.compare(o2.rank, o1.rank); // Sắp xếp giảm dần theo rank
            }
        });
        
        String outputFile = "kol_ranks.csv";
        RankSaver.saveToCSV(kolRankList, outputFile);
    }
}
