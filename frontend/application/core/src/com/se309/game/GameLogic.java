package com.se309.game;

import com.badlogic.gdx.Gdx;
import com.se309.queue.ButtonDownEvent;
import com.se309.queue.ButtonEvent;
import com.se309.queue.EnemySpawnEvent;
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

    private TextureElement pointer = null;
    private boolean pointerDown = false;

    public GameLogic(GameLogicProcessor processor, ResourceContext context) {
        this.processor = processor;
        this.context = context;

        this.path = GamePath.getPath(0, 0, 2);
    }

    public void setGameScene(GameScene game) {
        this.game = game;
    }

    int counter = 0;

    public void handleGameLogic() {

        // bad code :(
        if (pointer == null) {
            pointer = new TextureElement(game.towerAlphaTexture, 0, 0, 100, 100);
            pointer.setOrientation(Orientation.TopLeft);
        }

        GameEvent e;

        boolean deconstruct = false;

        if (counter++ > 50) {
            context.getEventQueue().queue(new EnemySpawnEvent(1));
            counter = 0;
        }

        advanceEnemies();

        while ((e = context.getEventQueue().dequeue()) != null) {
            if (e instanceof ButtonEvent) {
                // BUTTON EVENT HANDLER
                ButtonEvent be = (ButtonEvent) e;
                int signal = be.getSignal();
            } else if (e instanceof ButtonDownEvent) {
                ButtonDownEvent bde = (ButtonDownEvent) e;

                if (!pointerDown && ((ButtonDownEvent) e).getSignal() == 10) {
                    context.getRenderer().addElement(pointer);
                    pointerDown = true;
                }

            } else if (e instanceof TouchEvent) {

                pointer.setX(((TouchEvent) e).getX() - pointer.getWidth() / 2);
                pointer.setY(((TouchEvent) e).getY() - pointer.getHeight() / 2);

                if (e instanceof TouchUpEvent && pointerDown) {
                    context.getRenderer().removeElement(pointer);
                    pointerDown = false;
                }
            }else if (e instanceof EnemySpawnEvent) {
                EnemySpawnEvent ese = (EnemySpawnEvent) e;

                Enemy newEnemy = new Enemy(game.enemyTexture, 0, 0, 50, 50, ese.getId());

                entityBag.add(newEnemy);
                context.getRenderer().addElement(newEnemy);

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
                Enemy en = (Enemy) e;
                en.setPosition(en.getPosition() + 1);

                if (en.getPosition() >= path.size()) {
                    en.setPosition(path.size() - 1);
                }

                Point pos = path.get(en.getPosition());
                en.setX(pos.getX() - en.getWidth() / 2);
                en.setY(pos.getY() - en.getHeight() / 2);
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
