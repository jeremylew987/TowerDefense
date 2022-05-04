package com.se309.game;

import com.se309.queue.ButtonEvent;
import com.se309.queue.GameEvent;
import com.se309.queue.PlayerJoinEvent;
import com.se309.queue.RedrawEvent;
import com.se309.render.Element;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;
import com.se309.scene.LobbyScene;
import com.se309.tower.ResourceContext;

import java.util.ArrayList;

public class LobbyLogic {

    private GameLogicProcessor processor;
    private ResourceContext context;
    private LobbyScene lobby;

    private int maxPlayers = 4;
    private int difficulty = 1;

    private ArrayList<Element> playerListBag = new ArrayList<>();

    public LobbyLogic(GameLogicProcessor processor, ResourceContext context) {
        this.processor = processor;
        this.context = context;
    }

    public void handleLobbyLogic() {

        GameEvent e;

        boolean deconstruct = false;

        while ((e = context.getEventQueue().dequeue()) != null) {
            if (e instanceof ButtonEvent) {
                // BUTTON EVENT HANDLER
                ButtonEvent be = (ButtonEvent) e;
                int signal = be.getSignal();

                if (signal == 1) {
                    processor.setGameState(1);

                    context.getSceneManager().display("GAME");

                    deconstruct = true;
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
            } else if (e instanceof PlayerJoinEvent) {
                PlayerJoinEvent pje = (PlayerJoinEvent) e;

                processor.getPlayers().add(pje.getName());

                context.getEventQueue().queue(new RedrawEvent());

            } else if (e instanceof RedrawEvent) {
                emptyPlayerListBag();
                generatePlayerList();
            }
        }

        if (deconstruct) {
            emptyPlayerListBag();
            context.getEventQueue().queue(new RedrawEvent());
        }
    }

    private void generatePlayerList() {
        int y = 200;

        for (String p : processor.getPlayers()) {
            TextureElement dot = new TextureElement(lobby.dotTexture, 150, y + 35, 50, 50);
            dot.setOrientation(Orientation.TopLeft);
            playerListBag.add(dot);
            context.getRenderer().addElement(dot);

            TextElement name = new TextElement(p, 230, y);
            name.setFont(lobby.regularLarge);
            name.setOrientation(Orientation.TopLeft);
            playerListBag.add(name);
            context.getRenderer().addElement(name);

            y = y + 110;
        }
    }

    private void emptyPlayerListBag() {
        for (Element e : playerListBag) {
            context.getRenderer().removeElement(e);
        }
    }

    public LobbyScene getLobby() {
        return lobby;
    }

    public void setLobby(LobbyScene lobby) {
        this.lobby = lobby;
    }
}
