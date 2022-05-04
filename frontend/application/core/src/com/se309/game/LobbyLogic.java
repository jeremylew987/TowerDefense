package com.se309.game;

import com.se309.queue.ButtonEvent;
import com.se309.queue.GameEvent;
import com.se309.scene.LobbyScene;
import com.se309.tower.ResourceContext;

public class LobbyLogic {

    private GameLogicProcessor processor;
    private ResourceContext context;
    private LobbyScene lobby;

    private int maxPlayers = 4;
    private int difficulty = 1;

    public LobbyLogic(GameLogicProcessor processor, ResourceContext context) {
        this.processor = processor;
        this.context = context;
    }

    public void handleLobbyLogic() {

        GameEvent e;

        while ((e = context.getEventQueue().dequeue()) != null) {
            if (e instanceof ButtonEvent) {
                ButtonEvent be = (ButtonEvent) e;
                int signal = be.getSignal();

                if (signal == 1) {
                    processor.setGameState(1);

                    context.getSceneManager().display("GAME");
                } else

                if (signal == 2 || signal == 3) {
                    if (signal == 2 && maxPlayers > 2) maxPlayers--;
                    if (signal == 3 && maxPlayers < 8) maxPlayers++;

                    lobby.playerCountLabel.setText("Max Players: " + maxPlayers);
                } else

                if (signal == 4 || signal == 5) {
                    if (signal == 4 && difficulty > 0) difficulty--;
                    if (signal == 5 && difficulty < 2) difficulty++;

                    if (difficulty == 0) lobby.difficultyLabel.setText("Easy");
                    if (difficulty == 1) lobby.difficultyLabel.setText("Normal");
                    if (difficulty == 2) lobby.difficultyLabel.setText("Hard");
                }
            }
        }
    }

    public LobbyScene getLobby() {
        return lobby;
    }

    public void setLobby(LobbyScene lobby) {
        this.lobby = lobby;
    }
}
