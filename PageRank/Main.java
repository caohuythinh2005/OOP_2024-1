package pagerank;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
    	//Đường dẫn file resrc và file lưu data
        String inputFilePath = "kol_data.json";
        String outputFilePath = "kol_ranks.json";

        try {
            // 1. Đọc dữ liệu từ file
            System.out.println("Loading KOL data...");
            DataManager<KOL> dataManager = new DataManagerJson<>(inputFilePath, KOL.class);
            List<KOL> kols = dataManager.readData();
            System.out.println("Loaded " + kols.size() + " KOLs successfully.");

            // 2. Xây dựng đồ thị từ danh sách KOL
            System.out.println("Building weighted graph...");
            BuildWeightedGraph builder = new BuildWeightedGraph();
            WeightedGraph graph = builder.buildGraph(kols);
            System.out.println("Graph built successfully. Number of nodes: " + graph.getNodes().size());

            // 3. Tính toán PageRank
            System.out.println("Computing PageRank...");
            WeightedPageRank pageRank = new WeightedPageRank(graph);
            Map<Node, Double> ranks = pageRank.computePageRank(100);
            System.out.println("PageRank computation completed.");

            // 4. Lọc chỉ các node là KOL và sắp xếp theo PageRank
            System.out.println("Filtering and processing KOL results...");
            List<Node> rankedNodes = pageRank.getRankedNodes();

            // Chỉ lấy các node là KOL
            List<KOLRank> kolRanks = new ArrayList<>();
            int rankIndex = 1;
            for (Node node : rankedNodes) {
                if (node.getType().equalsIgnoreCase("KOL")) { // Kiểm tra node có phải là KOL
                    kolRanks.add(new KOLRank(rankIndex++, node.getId(), ranks.get(node)));
                }
            }

            // 5. Lưu kết quả vào file JSON
            System.out.println("\nSaving PageRank results to JSON...");
            DataManager<KOLRank> rankManager = new DataManagerJson<>(outputFilePath, KOLRank.class);
            rankManager.writeData(kolRanks);
            System.out.println("PageRank results saved to " + outputFilePath);

            // 6. In kết quả ra màn hình
//            System.out.println("\nTop KOLs by PageRank:");
//            kolRanks.forEach(kol -> System.out.printf("Rank %d: %s - PageRank: %.6f%n",
//                    kol.getRankIndex(), kol.getUsername(), kol.getScore()));

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
