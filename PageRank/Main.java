package pagerank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import pagerank.RankSaver.KOLRank;

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
        List<KOLRank> kolRankList = new ArrayList<>();
        
        for (KOL kol : kols) {
        	kolRankList.add(new KOLRank(kol.getUsername(), ranks.getOrDefault(kol.getUsername(), 0.0)));
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
