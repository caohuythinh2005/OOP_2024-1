package pagerank;

public class KOLRank {
    private int rankIndex;  // Thứ hạng của KOL
    private String username;  // Tên KOL
    private double score;  // Giá trị PageRank của KOL

    public KOLRank(int rankIndex, String username, double score) {
        this.rankIndex = rankIndex;
        this.username = username;
        this.score = score;
    }

    public int getRankIndex() {
        return rankIndex;
    }

    public String getUsername() {
        return username;
    }

    public double getScore() {
        return score;
    }
}
