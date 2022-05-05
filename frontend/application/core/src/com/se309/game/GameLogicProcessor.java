package com.se309.game;

import com.se309.button.ButtonManager;
import com.se309.queue.ButtonEvent;
import com.se309.queue.GameEvent;
import com.se309.scene.GameScene;
import com.se309.scene.LobbyScene;
import com.se309.tower.ResourceContext;

import java.util.ArrayList;

public class GameLogicProcessor {

    private ResourceContext context;

    private LobbyLogic lobbyLogic;
    private GameLogic gameLogic;

    private int gameState;
    private ArrayList<String> players;

    private int balance;
    private int round;
    private int health;

    public GameLogicProcessor(ResourceContext context) {
        this.context = context;

        // Init game state stuff
        gameState = 0;
        players = new ArrayList<>();

        lobbyLogic = new LobbyLogic(this, context);
        gameLogic = new GameLogic(this, context);

        // Initial values
        balance = 0;
        round = 0;
        health = 0;
    }

    int count = 0;

    public void tick() {
        context.getButtonManager().process();


        if (gameState == 0) lobbyLogic.handleLobbyLogic();
        if (gameState == 1) gameLogic.handleGameLogic();
    }

    public void setLobbyScene(LobbyScene lobbyScene) {
        lobbyLogic.setLobby(lobbyScene);
    }

    public void setGameScene(GameScene gameScene) {
        gameLogic.setGameScene(gameScene);
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
