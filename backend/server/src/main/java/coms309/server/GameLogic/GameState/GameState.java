package coms309.server.GameLogic.GameState;

import coms309.server.GameLogic.Map.Map;
import coms309.server.Server;
import coms309.server.Schema.GamestateSchema;

import java.util.logging.Level;

public class GameState {

    private Server server;
    private int status, round, difficulty;
    private Map map;

    public GameState(Server s) {
        server = s;
        status = 0;
        difficulty = 1;
        round = 1;
        this.map = new Map(1);
    }

    // GETTERS

    public int getDifficulty() {
        return difficulty;
    }
    public int getStatus() {
        return status;
    }
    public int getRound() {
        return round;
    }
    public Map getMap() {
        return map;
    }

    // SETTERS

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        GamestateSchema.Builder gs =
                GamestateSchema.newBuilder()
                        .setDifficulty(difficulty);
        server.getConnectionHandler().writeToAll(gs.build().toByteArray());
        server.logger.log(Level.INFO, "Difficulty has been set to " + difficulty);
    }
    public void setMap(int map) {
        this.map.loadMap(map);
        GamestateSchema.Builder gs =
                GamestateSchema.newBuilder()
                        .setMap(map);
        server.getConnectionHandler().writeToAll(gs.build().toByteArray());
        server.logger.log(Level.INFO, "Map has been set to " + map);
    }
    public void setStatus(int status) {
        this.status = status;
        GamestateSchema.Builder gs =
                GamestateSchema.newBuilder()
                        .setStatus(status);
        server.getConnectionHandler().writeToAll(gs.build().toByteArray());
        server.logger.log(Level.INFO, "Status has been set to " + status);
    }
    public void setRound(int round) {
        this.round = round;
        GamestateSchema.Builder gs =
                GamestateSchema.newBuilder()
                        .setRound(round);
        server.getConnectionHandler().writeToAll(gs.build().toByteArray());
        server.logger.log(Level.INFO, "Round has been set to " + round);
    }
    public byte[] serialize() {
        GamestateSchema gs =
                GamestateSchema.newBuilder()
                        .setDifficulty(difficulty)
                        .setStatus(status)
                        .setMap(map.getMapId())
                        .setRound(round)
                        .build();
        return gs.toByteArray();
    }
}
