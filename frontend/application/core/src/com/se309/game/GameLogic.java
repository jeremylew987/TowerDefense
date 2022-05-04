package com.se309.game;

import com.badlogic.gdx.Gdx;
import com.se309.queue.ButtonEvent;
import com.se309.queue.GameEvent;
import com.se309.queue.PlayerJoinEvent;
import com.se309.queue.RedrawEvent;
import com.se309.queue.TouchEvent;
import com.se309.queue.TouchUpEvent;
import com.se309.render.Element;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;
import com.se309.scene.GameScene;
import com.se309.tower.ResourceContext;

import java.util.ArrayList;

public class GameLogic {

    private GameLogicProcessor processor;
    private ResourceContext context;
    private GameScene game;

    private ArrayList<Point> path;

    private ArrayList<Element> playerListBag = new ArrayList<>();

    private ArrayList<Element> entityBag = new ArrayList<>();

    public GameLogic(GameLogicProcessor processor, ResourceContext context) {
        this.processor = processor;
        this.context = context;

        this.path = GamePath.getPath(0, 0, 3);
    }

    public void setGameScene(GameScene game) {
        this.game = game;
    }

    public void handleGameLogic() {
        GameEvent e;

        boolean deconstruct = false;

        while ((e = context.getEventQueue().dequeue()) != null) {
            if (e instanceof ButtonEvent) {
                // BUTTON EVENT HANDLER
                ButtonEvent be = (ButtonEvent) e;
                int signal = be.getSignal();
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

    public void advanceEnemies() {
        for (Element e : entityBag) {
            if (e instanceof Enemy) {

            }
        }
    }

    private void generatePlayerList() {
        int y = 50;

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        for (String p : processor.getPlayers()) {
            TextureElement dot = new TextureElement(game.dotTexture, width - 330, y + 35, 50, 50);
            dot.setOrientation(Orientation.TopLeft);
            playerListBag.add(dot);
            context.getRenderer().addElement(dot);

            TextElement name = new TextElement(p, width - 250, y);
            name.setFont(game.regularLarge);
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

    private void emptyEntityBag() {
        for (Element e : entityBag) {
            context.getRenderer().removeElement(e);
        }
    }
}
