package com.se309.socket;

import com.se309.queue.EnemyAttackEvent;
import com.se309.queue.EnemySpawnEvent;
import com.se309.queue.GameStartEvent;
import com.se309.queue.PlayerListUpdateEvent;
import com.se309.queue.StatusUpdateEvent;
import com.se309.queue.TowerPlaceEvent;
import com.se309.schema.DataObjectSchema;
import com.se309.schema.gameTick;
import com.se309.tower.ResourceContext;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Connects to the socket to the backend, and continually polls for data.
 * When data is found, it is parsed using ProtoBUF, and is sent to it's respective NetworkEventListener
 *
 * @author Gavin Tersteeg
 */
public class NetworkDataHandler extends Thread {

    private ResourceContext context;
    private SocketClient client;

    /**
     * Creates a new NetworkDataHandler class
     * @param client The socket client to receive data from
     * @param context Resource context
     */
    public NetworkDataHandler(SocketClient client, ResourceContext context) {
        this.context = context;
        this.client = client;
    }

    private int count = 1;

    /**
     * Thread startup point for NetworkDataHandler
     */
    public void run() {
        while (true) {

            try {
                DataObjectSchema data =
                        DataObjectSchema.parseDelimitedFrom(client.getDataIn());


                if (data != null) {
                    //if(!data.getDataCase().equals("TICK")) System.out.println("Received: " + data.getDataCase());
                } else {
                    System.out.println("IIIITS NULLLLLL!!!!");
                    continue;
                }

                if (data.hasClients()) {
                    ArrayList<String> names = new ArrayList<>();
                    for (int i = 0; i < data.getClients().getClientsCount(); i++) {
                        names.add(data.getClients().getClients(i).getName());
                    }

                    context.getEventQueue().queue(new PlayerListUpdateEvent(names));
                }

                if (data.hasTick()) {
                    for (int i = 0; i < data.getTick().getEnemyUpdateCount(); i++) {
                        gameTick.EnemyUpdate update = data.getTick().getEnemyUpdate(i);

                        System.out.println(update.getEnemyId() + ", " + update.getHealth() + ", " + update.getAttackedBy());

                        if (update.getAttackedBy() == 0) {
                            context.getEventQueue().queue(new EnemySpawnEvent(update.getEnemyId()));
                        } else {
                            context.getEventQueue().queue(new EnemyAttackEvent(update.getEnemyId(), update.getAttackedBy(), update.getHealth() <= 0));
                        }
                    }
                }

                if (data.hasGamestate()) {
                    if (data.getGamestate().getStatus() == 1) {
                        context.getEventQueue().queue(new GameStartEvent());
                    }

                    context.getEventQueue().queue(new StatusUpdateEvent(data.getGamestate().hasHealth() ? data.getGamestate().getHealth() : -69, data.getGamestate().hasRound() ? data.getGamestate().getRound() : -69, data.getGamestate().hasBalance() ? data.getGamestate().getBalance() : -69));
                }

                if (data.hasTower()) {
                    TowerPlaceEvent event = new TowerPlaceEvent(0, 0, count++);
                    event.setX(data.getTower().getX());
                    event.setY(data.getTower().getY());

                    System.out.println("Tower: " + event.getX() + ", " + event.getY());

                    context.getEventQueue().queue(event);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
