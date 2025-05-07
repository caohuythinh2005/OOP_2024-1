package entity;

/*
Dùng khi chỉ muốn lấy các Tweet như những đối tượng độc lập
 */

public class TweetID {
    protected String username; // Người đăng tweet
    protected String id;       // ID của tweet
    public TweetID() {}
    public TweetID(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "username='" + username + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TweetID user)) {
            return false;
        }

        return this.username.equals(user.getUsername()) && this.id.equals(user.getId());
    }
}
