package coms309.server.GameLogic;

import coms309.server.GameLogic.Map.Map;
import coms309.server.Network.Message;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Server;
import coms309.server.Schema.GamestateSchema;

import java.util.logging.Level;

public class GameState implements Runnable {

    private final Server server;

    /**
     * status of the game
     * 0 = init
     * 1 = starting
     * 2 = waiting
     * 3 = in-round
     * 4 = paused
     * 5 = game-over
     */
    private int status;

    /**
     * difficulty of the game
     * 0 = easy
     * 1 = normal
     * 2 = hard
     */
    private int difficulty;

    private int round;
    private Map map;

    // CONSTRUCTOR
    public GameState(Server s) {
        server = s;
        status = 0;
        difficulty = 1;
        round = 1;
        try {
            this.map = new Map(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setDifficulty(difficulty)
                                        .build()
                        ).build();
        server.getConnectionHandler().writeToAll(d);
        server.logger.log(Level.INFO, "Difficulty has been set to " + difficulty);
    }
    public void setMap(int map) throws Exception {
        this.map.loadMap(map);
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setMap(map)
                                        .build()
                        ).build();
        server.getConnectionHandler().writeToAll(d);
        server.getConnectionHandler().writeToAll(new Message(
                "Server",
                "CHAT",
                "Map has been set to " + map
        ).serialize());
        server.logger.log(Level.INFO, "Map has been set to " + map);
    }
    public void setStatus(int status) {
        this.status = status;
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setStatus(status)
                                        .build()
                        ).build();
        server.getConnectionHandler().writeToAll(d);
        server.logger.log(Level.INFO, "Status has been set to " + status);
    }
    public void setRound(int round) {
        this.round = round;
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setRound(round)
                                        .build()
                        ).build();
        server.getConnectionHandler().writeToAll(d);
        server.logger.log(Level.INFO, "Round has been set to " + round);
    }

    // RUN
    public void run() {
        server.getConnectionHandler().awaitNewConnections();
        Server.logger.info("All clients connected, waiting for start signal.");
        this.setStatus(2); // set to waiting

        double t = 0.0;
        final double dt = 1000;

        double currentTime = System.currentTimeMillis();
        double accumulator = 0.0;

        while (true) {
            while (status == 3) {
                double newTime = System.currentTimeMillis();
                double frameTime = newTime - currentTime;
                if (frameTime > 0.25)
                    frameTime = 0.25;
                currentTime = newTime;

                accumulator += frameTime;

                while (accumulator >= dt) {
                    // do math based on t, dt
                    map.update(t, dt);
                    accumulator -= dt;
                    t += dt;
                }

                // non-tick based
                if (server.getConnectionHandler().updateConnectionStatus())
                    server.getConnectionHandler().announcePlayers();
            }
        }
    }

    /**
     * Return protobuf wrapped gamestate object
     * @return protobuf wrapper
     */
    public DataObjectSchema serialize() {
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setDifficulty(difficulty)
                                        .setStatus(status)
                                        .setMap(map.getMapId())
                                        .setRound(round)
                                        .build()
                        ).build();
        return d;
    }
}
