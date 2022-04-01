package coms309.server.GameState;

import coms309.server.Connection;
import coms309.server.Map.Map;
import coms309.server.Server;

import static coms309.server.GameState.GameState.Difficulty.MEDIUM;
import static coms309.server.GameState.GameState.GameTime.*;

public class GameState {

    public enum Difficulty{
        EASY, MEDIUM, HARD
    }

    public enum GameTime {
        PAUSED, IN_ROUND, END_OF_ROUND, GAME_OVER, NOT_STARTED
    }

    private Server server;
    private Connection[] clients;
    private Difficulty difficulty;
    private GameTime gameTime;
    private int round;
    private Map map;

    public GameState(Server s, Connection[] clients) {
        server = s;
        gameTime = NOT_STARTED;
        this.clients = clients;
        difficulty = MEDIUM;
        round = 1;
        this.map = new Map(1);
    }

    public GameState(Server s, Connection[] c, Difficulty diff, Map m) {
        server = s;
        gameTime = NOT_STARTED;
        clients = c;
        difficulty = diff;
        round = 1;
        map = m;
    }

    // GETTERS

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameTime getGameState() {
        return gameTime;
    }

    public int getRound() {
        return round;
    }

    public Map getMap() {
        return map;
    }

    // SETTERS

    // GAME STATE FUNCTIONS

    private boolean isAdmin(int pid) {
        return pid == clients[0].pid;
    }

    // PLAYER FUNCTIONS

    public void setDifficulty(int pid, String difficulty) {
        if (isAdmin(pid)) {
            this.difficulty = Difficulty.valueOf(difficulty);
        } else {
            clients[pid].writeTo("ERR,NOT_AUTH\n\r");
        }
    }

    // AUTHORIZED FUNCTIONS

    public void setMap(int pid, int m) {
        if (isAdmin(pid)) {
            //this.map = m;
            server.writeToAll("SETGL,MAP," + m + "\n\r");
        } else {
            clients[pid].writeTo("ERR,NOT_AUTH\n\r");
        }
    }

    public void startGame(int pid) { // gameState = IN_ROUND (if NOT_STARTED)
        if (!isAdmin(pid)) {
            clients[pid].writeTo("ERR,NOT_AUTH\n\r");
        } else if (gameTime != NOT_STARTED) {
            clients[pid].writeTo("ERR,INV_PARAM\n\r");
        } else {
            this.gameTime = IN_ROUND;
            server.writeToAll("SETGL,STATE," + gameTime.toString() + "\n\r");
        }
    }

    // NON-AUTHORIZED FUNCTIONS

    public void pauseGame(int pid) { // gameState = PAUSED
        if (gameTime != PAUSED) {
            this.gameTime = PAUSED;
            server.writeToAll("SETGL,PAUSED," + gameTime.toString() + "\n\r");
        } else {
            clients[pid].writeTo("ERR,INV_PARAM\n\r");
        }
    }

    public void resumeGame(int pid) { // gameState = IN_ROUND (if PAUSED)
        if (gameTime != PAUSED) {
            clients[pid].writeTo("ERR,INV_PARAM\n\r");
        } else {
            this.gameTime = PAUSED;
            server.writeToAll("SETGL,IN_ROUND," + gameTime.toString() + "\n\r");
        }
    }

    // GAME LOGIC FUNCTIONS

    public void placeObj(int pid, int objId) {

    }

}
