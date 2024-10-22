package ThanhQuy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	private static final Random random = new Random();
	public static KOL createRandomKOL(int index) {
		String name="KOL"+index;
		KOL kol=new KOL(name);
		int followersNum=random.nextInt(1000);
		for(int i=1;i<=followersNum;i++) {
			kol.addFollower(new User("Followers"+i));
		}
		int tweetNum=random.nextInt(500);
		for(int i=1;i<=tweetNum;i++) {
			Tweet tweet=new Tweet("Tweet No."+i+"from "+name);
			int likes=random.nextInt(10000);
			int comments=random.nextInt(10000);
			int retweets=random.nextInt(10000);
			for(int j=0;j<likes;j++) tweet.like();
			for(int j=0;j<retweets;j++) tweet.retweet();
			for(int j=0;j<comments;j++) tweet.comment();
			kol.getTweet().add(tweet);
		}
		return kol;
	}
	
	public static void writeCSVfile(List<KOL> kols, String filename) {
		try(FileWriter writer=new FileWriter(filename)){
			writer.append("KOL's name,Followers,Tweets,Likes,Comments,Retweets,Score\n");
			for(KOL kol: kols) {
				String name=kol.getUserName();
				int followers=kol.getFollowers().size();
				int tweets=kol.getTweet().size();
				int likesCount=0;
				int commentsCount=0;
				int retweetsCount=0;
				for(Tweet tweet: kol.getTweet()) {
					likesCount+=tweet.getLikes();
					commentsCount+=tweet.getComments();
					retweetsCount+=tweet.getRetweets();
				}
				double score=KOLRanker.calKOLscore(kol);
				writer.append(name)
					  .append(",")
					  .append(String.valueOf(followers))
					  .append(",")
					  .append(String.valueOf(tweets))
					  .append(",")
					  .append(String.valueOf(likesCount))
					  .append(",")
					  .append(String.valueOf(commentsCount))
					  .append(",")
					  .append(String.valueOf(retweetsCount))
					  .append(",")
					  .append(String.valueOf(score))
					  .append("\n");
			} 
			System.out.println("Write CSV file successfully!");
		    }catch(IOException e) {
				System.out.println("ERROR");
				e.printStackTrace();
			}
		}
	
	public static void main(String[] args) {
		List<KOL> kols=new ArrayList<>();
		for(int i=1;i<=200;i++) {
			KOL kol=createRandomKOL(i);
			kols.add(kol);
		}
		writeCSVfile(kols,"kol_data.csv");
		List<KOL> rankedKOLs=KOLRanker.KOLRanks(kols);
		for(int i=1;i<=10;i++) {
			KOL kol=rankedKOLs.get(i);
			double score=KOLRanker.calKOLscore(kol);
			System.out.println("TOP "+i+" : "+kol.getUserName()+" score: "+score);
		}
		
	}
}
