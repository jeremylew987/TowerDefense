package com.se309.game;

import com.badlogic.gdx.Gdx;
import com.se309.queue.ButtonDownEvent;
import com.se309.queue.ButtonEvent;
import com.se309.queue.EnemyAttackEvent;
import com.se309.queue.EnemySpawnEvent;
import com.se309.queue.GameEvent;
import com.se309.queue.PlayerListUpdateEvent;
import com.se309.queue.RedrawEvent;
import com.se309.input.TouchEvent;
import com.se309.input.TouchUpEvent;
import com.se309.queue.StatusUpdateEvent;
import com.se309.queue.TowerPlaceEvent;
import com.se309.render.Element;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;
import com.se309.scene.GameScene;
import com.se309.schema.DataObjectSchema;
import com.se309.schema.TowerSchema;
import com.se309.tower.ResourceContext;

import java.io.IOException;
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

    public void handleGameLogic() {

        // bad code :(
        if (pointer == null) {
            pointer = new TextureElement(game.towerAlphaTexture, 0, 0, 100, 100);
            pointer.setOrientation(Orientation.TopLeft);
        }

        GameEvent e;

        boolean deconstruct = false;


        advanceEnemies();

        while ((e = context.getEventQueue().dequeue()) != null) {
            if (e instanceof ButtonEvent) {
                // BUTTON EVENT HANDLER
                ButtonEvent be = (ButtonEvent) e;
                int signal = be.getSignal();
            } else if (e instanceof StatusUpdateEvent) {
                // STATUS UPDATE EVENT HANDLER
                StatusUpdateEvent sue = (StatusUpdateEvent) e;

                processor.setRound(sue.getRound());
                processor.setBalance(sue.getBalance());
                processor.setHealth(sue.getHealth());

                context.getEventQueue().queue(new RedrawEvent());

            } else if (e instanceof EnemyAttackEvent) {
                // ENEMY ATTACK EVENT HANDLER
                EnemyAttackEvent eae = (EnemyAttackEvent) e;

                Enemy attackedEnemy = null;
                Tower attackingTower = null;

                for (Element enemy : entityBag) {

                    if (enemy instanceof Enemy) {
                        Enemy en = (Enemy) enemy;

                        if (en.getId() == eae.getEnemyId()) attackedEnemy = en;
                    }
                }

                for (Element tower : entityBag) {

                    if (tower instanceof Tower) {
                        Tower tw = (Tower) tower;

                        if (tw.getId() == eae.getTowerId()) attackingTower = tw;

                    }
                }

                if (attackingTower != null && attackedEnemy != null) {
                    Vector vect1 = new Vector(0, 1);
                    Vector vect2 = new Vector(attackedEnemy.getX() - attackingTower.getX(), attackedEnemy.getY() - attackingTower.getY());


                    double angle = Math.atan2(vect2.getX(), vect2.getY());

                    angle = angle * (180 / Math.PI);
                    angle = angle + 180;

                    System.out.println("Angle: " + angle);

                    attackingTower.setRotation((float) angle);
                }

                if (eae.isKill() && attackedEnemy != null) {
                    context.getRenderer().removeElement(attackedEnemy);
                    entityBag.remove(attackedEnemy);
                }

            }else if (e instanceof ButtonDownEvent) {
                // BUTTON DOWN EVENT HANDLER
                ButtonDownEvent bde = (ButtonDownEvent) e;

                if (!pointerDown && ((ButtonDownEvent) e).getSignal() == 10) {
                    context.getRenderer().addElement(pointer);
                    pointerDown = true;
                }

            } else if (e instanceof TouchEvent) {
                // TOUCH EVENT HANDLER
                pointer.setX(((TouchEvent) e).getX() - pointer.getWidth() / 2);
                pointer.setY(((TouchEvent) e).getY() - pointer.getHeight() / 2);

                if (e instanceof TouchUpEvent && pointerDown) {
                    context.getRenderer().removeElement(pointer);

                    try {
                        DataObjectSchema.newBuilder()
                                .setTower(
                                        TowerSchema.newBuilder()
                                                .setX(((TouchEvent) e).getX())
                                                .setY(((TouchEvent) e).getY())
                                                .setTypeId(0)
                                                .build()
                                ).build().writeDelimitedTo(context.getDataOut());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    pointerDown = false;
                }
            } else if (e instanceof TowerPlaceEvent) {
                // TOWER PLACE EVENT HANDLER
                TowerPlaceEvent tpe = (TowerPlaceEvent) e;

                Tower t = new Tower(game.towerTexture, tpe.getX() - 40, tpe.getY() - 40, 80, 80, tpe.getId());

                entityBag.add(t);
                context.getRenderer().addElement(t);

            } else if (e instanceof EnemySpawnEvent) {
                // ENEMY SPAWN EVENT HANDLER
                EnemySpawnEvent ese = (EnemySpawnEvent) e;

                Enemy newEnemy = new Enemy(game.enemyTexture, 0, 0, 50, 50, ese.getId());

                entityBag.add(newEnemy);
                context.getRenderer().addElement(newEnemy);

                context.getEventQueue().queue(new RedrawEvent());

            } else if (e instanceof PlayerListUpdateEvent) {
                // PLAYER LIST UPDATE EVENT HANDLER
                PlayerListUpdateEvent pje = (PlayerListUpdateEvent) e;

                processor.setPlayers(pje.getNames());

                context.getEventQueue().queue(new RedrawEvent());

            } else if (e instanceof RedrawEvent) {
                // REDRAW EVENT HANDLER
                emptyPlayerListBag();
                generatePlayerList();

                game.balanceValue.setText("$" + processor.getBalance());
                game.roundValue.setText(processor.getRound() + "");
                game.liveValue.setText(processor.getHealth() + "");
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
