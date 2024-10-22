package ThanhQuy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KOLRanker {
	public static double calKOLscore(KOL kol) {
		int followersNum=kol.getFollowers().size();
		List<Tweet> Tweets=kol.getTweet();
		int tweetNum=Tweets.size();
		if(tweetNum==0) return 0;
		int interaction=0;
		for(Tweet tweet : Tweets) {
			interaction+=tweet.getComments();
			interaction+=tweet.getLikes();
			interaction+=tweet.getRetweets();
		}
		double averageInteraction=interaction/(double)tweetNum;
		return (0.5*followersNum)+(0.3*averageInteraction)+(0.2*tweetNum);
	}
	
	public static List<KOL> KOLRanks(List<KOL> kols){
		 Collections.sort(kols, new Comparator<KOL>() {
	            @Override
	            public int compare(KOL kol1, KOL kol2) {
	                double score1 = calKOLscore(kol1);
	                double score2 = calKOLscore(kol2);
	                return Double.compare(score2, score1); 
	            }
	        });
		 return kols;
	}
}
