package socialmedia;

import automation.AutomationTool;

import java.util.List;

public interface SocialMediaPlatform {

    boolean login(List<String> credential);

    List<String> getFollowers(String userId, int scrolls);

    int getFollowersCount(String userId);

    List<String> getPostIds(String userId, int limit, int scrolls) throws InterruptedException;

    List<String> getReposters(String userId, String postId, int scrolls);

    List<String> getCommenters(String userId, String postId, int scrolls);

    boolean isAccountAvailable(String userId);

    List<String> searchUserFromHashtag(String hashtag, int limit, int scrolls);

    String getPlatformName();

    void close();
}