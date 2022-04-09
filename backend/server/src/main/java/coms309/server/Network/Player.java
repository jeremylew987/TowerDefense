package coms309.server.Network;

import coms309.server.Schema.DataObjectSchema;
import org.json.simple.JSONObject;

public class Player {

    private String username;
    private int userId, playerId;
    private int level, money;
    private Connection connection;

    public Player(Connection connection, int playerId) {
        this.connection = connection;
        this.playerId = playerId;
    }

    public Player(Connection connection, String username, int userId, int playerId) {
        this.connection = connection;
        this.username = username;
        this.userId = userId;
        this.playerId = playerId;

        level = 1;
        money = 0;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public int getMoney() {
        return money;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel(int level) { return level; }

    public DataObjectSchema serialize() {
        return null;
    }

}
