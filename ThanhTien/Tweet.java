package pagerank;

public class Tweet {
	private String content;
	private int likes;
	private int comments;
	private int retweets;
	
	public Tweet(String content ) {
		this.content=content;
		this.likes=0;
		this.comments=0;
		this.retweets=0;
	}
	
	public void like() {
		likes++;
	}
	
	public void comment() {
		comments++;
	}
	
	public void retweet() {
		retweets++;
	}

	public String getContent() {
		return content;
	}

	public int getLikes() {
		return likes;
	}

	public int getComments() {
		return comments;
	}

	public int getRetweets() {
		return retweets;
	}
	
	
}