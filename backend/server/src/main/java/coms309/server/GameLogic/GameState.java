package coms309.server.GameLogic;

import coms309.server.GameLogic.Map.Map;
import coms309.server.Network.Message;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Server;
import coms309.server.Schema.GamestateSchema;

import java.util.logging.Level;

public class GameState implements Runnable {

    public Server server;

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
    private int health;
    private int balance;
    private Map map;

    // CONSTRUCTOR
    public GameState(Server s) {
        server = s;
        status = 0;
        difficulty = 1;
        round = 1;
        health = 10;
        balance = 1000;
        try {
            this.map = new Map(1, this);
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
    public int getHealth() {
        return health;
    }
    public Map getMap() {
        return map;
    }
    public int getBalance() { return balance;}

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
    public void setHealth(int health) {
        this.health = health;
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setHealth(health)
                                        .build()
                        ).build();
        server.getConnectionHandler().writeToAll(d);
        server.logger.log(Level.INFO, "Health has been set to " + health);
    }
    public void setBalance(int balance) {
        this.balance = balance;
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setBalance(balance)
                                        .build()
                        ).build();
        server.getConnectionHandler().writeToAll(d);
        server.logger.log(Level.INFO, "balance has been set to " + balance);
    }

    // RUN
    public void run() {
        this.setStatus(2); // set to waiting
        server.getConnectionHandler().awaitNewConnections();
        Server.logger.info("All clients connected, waiting for start signal.");

        while (this.getStatus() != 1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long t = 0;

        long deltaTime = 0;
        long currentTime = System.currentTimeMillis();
        long lastTime = System.currentTimeMillis();
        long timePassed = 0;
        long timePerTimestep = 1000 / 50;

        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(
                                GamestateSchema.newBuilder()
                                        .setBalance(balance)
                                        .setStatus(3)
                                        .setHealth(10)
                                        .setRound(1)
                                        .build()
                        ).build();
        server.getConnectionHandler().writeToAll(d);

        while (this.getStatus() == 3) {
            currentTime = System.currentTimeMillis();
            deltaTime = currentTime - lastTime;
            // Update game logic based on time passed

            timePassed += deltaTime;

            // Update game logic once for every tick passed
            while ( timePassed >= timePerTimestep )
            {
                // Update fixed step?????
                server.getConnectionHandler().writeToAll(map.update(timePassed, deltaTime));
                timePassed -= timePerTimestep;

                // t += deltaTime; // Done outside of this while????
            }

            // non-tick based
            if (server.getConnectionHandler().updateConnectionStatus()) {
                server.getConnectionHandler().announcePlayers();
            }

            lastTime = currentTime;
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
