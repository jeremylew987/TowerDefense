package coms309.server;

public class UserDetails {

    private String uid;
    private String username;
    private int level;

    public UserDetails(String uid, String username, int level) {
        this.uid = uid;
        this.username = username;
        this.level = level;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return uid + "," + username + "," + level;
    }
}
