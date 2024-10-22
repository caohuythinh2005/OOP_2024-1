package ThanhQuy;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String userName;
	private List<Tweet> tweet;
	private List<User> followers;
	private List<User> following;
	
	public User(String userName) {
		this.userName=userName;
		this.tweet=new ArrayList<>();
		this.followers=new ArrayList<>();
		this.following=new ArrayList<>();
	}
	
	public void addFollower(User follower) {
		followers.add(follower);
	}
	
	public void follow(User user) {
		following.add(user);
		user.addFollower(this);
	}

	public String getUserName() {
		return userName;
	}

	public List<Tweet> getTweet() {
		return tweet;
	}

	public List<User> getFollowers() {
		return followers;
	}
	
	
}
