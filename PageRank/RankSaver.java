package pagerank;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RankSaver {

    /**
     * Lưu danh sách KOL đã xếp hạng vào file CSV.
     * @param kolRanks Danh sách các KOL và điểm rank (username, rank).
     * @param filePath Đường dẫn file CSV để lưu.
     */
    public static void saveToCSV(List<KOLRank> kolRanks, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Ghi tiêu đề cột
            writer.append("Rank,Username,Score\n");

            // Ghi từng KOL vào file
            int rank = 1; // Bắt đầu từ rank 1
            for (KOLRank kolRank : kolRanks) {
                writer.append(String.valueOf(rank))
                      .append(",")
                      .append(kolRank.username)
                      .append(",")
                      .append(String.format("%.10f", kolRank.rank)) // Điểm rank với 10 chữ số thập phân
                      .append("\n");
                rank++;
            }

            System.out.println("KOL ranks saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving to CSV: " + e.getMessage());
        }
    }
}
