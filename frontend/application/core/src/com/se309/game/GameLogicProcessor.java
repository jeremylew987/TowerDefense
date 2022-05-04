package com.se309.game;

import com.se309.button.ButtonManager;
import com.se309.queue.ButtonEvent;
import com.se309.queue.GameEvent;
import com.se309.scene.LobbyScene;
import com.se309.tower.ResourceContext;

public class GameLogicProcessor {

    private ResourceContext context;

    private LobbyLogic lobbyLogic;

    private int gameState;

    public GameLogicProcessor(ResourceContext context) {
        this.context = context;

        gameState = 0;

        lobbyLogic = new LobbyLogic(this, context);
    }

    int count = 0;

    public void tick() {
        context.getButtonManager().process();


        if (gameState == 0) lobbyLogic.handleLobbyLogic();
    }



    public void setLobbyScene(LobbyScene lobbyScene) {
        lobbyLogic.setLobby(lobbyScene);
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
}
