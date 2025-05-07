package entity;

public class RankedKOL {
    private int rankIndex;
    private String username;
    private double score;

    public RankedKOL(int rankIndex, String username, double score) {
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