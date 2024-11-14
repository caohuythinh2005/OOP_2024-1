package pagerank;

import java.util.*;

public class Main {
	private static final Random random = new Random();

	public static KOL createRandomKOL(int index, List<User> allUsers) {
		String name = "KOL" + index;
		KOL kol = new KOL(name);
		int followersNum = random.nextInt(1000);
		for (int i = 1; i <= followersNum; i++) {
			User follower = new User("Follower" + i);
			kol.addFollower(follower);
			allUsers.add(follower); // thêm follower vào danh sách users
		}

		int tweetNum = random.nextInt(500);
		for (int i = 1; i <= tweetNum; i++) {
			Tweet tweet = new Tweet("Tweet No." + i + " from " + name);
			int likes = random.nextInt(10000);
			int comments = random.nextInt(10000);
			int retweets = random.nextInt(10000);
			for (int j = 0; j < likes; j++) tweet.like();
			for (int j = 0; j < retweets; j++) tweet.retweet();
			for (int j = 0; j < comments; j++) tweet.comment();
			kol.getTweet().add(tweet);
		}
		return kol;
	}

	public static void main(String[] args) {
		List<User> users = new ArrayList<>();
		List<KOL> kols = new ArrayList<>(); // Danh sách chỉ chứa KOLs

		for (int i = 1; i <= 200; i++) {
			KOL kol = createRandomKOL(i, users);
			users.add(kol);
			kols.add(kol); // Thêm KOL vào danh sách riêng
		}

		// Tính toán PageRank cho tất cả users
		Map<User, Double> pageRank = PageRank.computePageRank(users);

		// Sắp xếp các KOL theo điểm PageRank giảm dần
		kols.sort((kol1, kol2) -> Double.compare(pageRank.get(kol2), pageRank.get(kol1)));

		// In ra các KOL theo thứ tự xếp hạng
		System.out.println("Xếp hạng KOL theo điểm PageRank:");
		int i = 1;
		for (KOL kol : kols) {
			System.out.println("Rank " + i + ": " + kol.getUserName() + ": " + pageRank.get(kol));
			i++;
		}
	}
}
