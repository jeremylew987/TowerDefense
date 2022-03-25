package coms309.server;

import coms309.server.Map.Map;

import static coms309.server.GameLogic.Difficulty.MEDIUM;
import static coms309.server.GameLogic.GameState.*;

public class GameLogic {

    public enum Difficulty{
        EASY, MEDIUM, HARD
    }

    public enum GameState {
        PAUSED, IN_ROUND, END_OF_ROUND, GAME_OVER, NOT_STARTED
    }

    private Server server;
    private Connection[] clients;
    private Difficulty difficulty;
    private GameState gameState;
    private int round;
    private Map map;

    public GameLogic(Server s, Connection[] clients) {
        server = s;
        gameState = NOT_STARTED;
        this.clients = clients;
        difficulty = MEDIUM;
        round = 1;
        this.map = new Map(1);
    }

    public GameLogic(Server s, Connection[] c, Difficulty diff, Map m) {
        server = s;
        gameState = NOT_STARTED;
        clients = c;
        difficulty = diff;
        round = 1;
        map = m;
    }

    // GETTERS

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public GameState getGameState() {
        return gameState;
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
        } else if (gameState != NOT_STARTED) {
            clients[pid].writeTo("ERR,INV_PARAM\n\r");
        } else {
            this.gameState = IN_ROUND;
            server.writeToAll("SETGL,STATE," + gameState.toString() + "\n\r");
        }
    }

    // NON-AUTHORIZED FUNCTIONS

    public void pauseGame(int pid) { // gameState = PAUSED
        if (gameState != PAUSED) {
            this.gameState = PAUSED;
            server.writeToAll("SETGL,PAUSED," + gameState.toString() + "\n\r");
        } else {
            clients[pid].writeTo("ERR,INV_PARAM\n\r");
        }
    }

    public void resumeGame(int pid) { // gameState = IN_ROUND (if PAUSED)
        if (gameState != PAUSED) {
            clients[pid].writeTo("ERR,INV_PARAM\n\r");
        } else {
            this.gameState = PAUSED;
            server.writeToAll("SETGL,IN_ROUND," + gameState.toString() + "\n\r");
        }
    }

    // GAME LOGIC FUNCTIONS

}
