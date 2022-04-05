package coms309.server.Network;

import coms309.server.Schema.DataObjectSchema;
import coms309.server.Schema.GamestateSchema;
import coms309.server.Server;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ConnectionHandler {

    private Server server;
    private Connection[] clients;
    private int playerIterator;

    public ConnectionHandler(Server server) {
        this.server = server;
        this.clients = new Connection[server.getMaxPlayers()];
        for (int i = 0; i < clients.length; i++){
            this.clients[i] = null;
        }
    }

    public void writeToAll(DataObjectSchema data) {
        for (int i = 0; i < server.getMaxPlayers(); i++) {
            if (clients[i] == null || !clients[i].isValidated() || !clients[i].isAlive()) {
                continue;
            }
            clients[i].write(data);
        }
    }

    public void updateConnectionStatus() {
        boolean playerChanged = false;
        GamestateSchema.Builder gs = GamestateSchema.newBuilder();
        for (int i = 0; i < server.getMaxPlayers(); i++) {
            if (clients[i] == null) { continue; }
            if (!clients[i].isAlive()) {
                clients[i] = null;
            }
        }
    }

    public void awaitNewConnections() {
        playerIterator = 0;
        try {
            // wait until max players have connected
            while (playerIterator < server.getMaxPlayers()) {
                updateConnectionStatus();
                boolean hasEmpty = false;
                boolean hasUnverified = false;

                // find next empty spot
                for (int i = 0; i < server.getMaxPlayers(); i++) {
                    if (clients[i] == null) { // if array item is null
                        playerIterator = i; // set iterator to null object id to fill array fully
                        hasEmpty = true;
                        break;
                    } else if (!clients[i].isValidated()) { hasUnverified = true; }
                }

                // breakthrough statement (when all users are connected)
                if (!hasEmpty) {
                    if (hasUnverified) { continue; }
                    break;
                }

                // accept new connection
                Socket s = server.getSocket().accept();
                Connection c = new Connection(s, playerIterator, server);
                clients[playerIterator] = c;
                server.logger.log(Level.INFO, c.getAddress() + " is attempting to connect.");
                c.setThread(new Thread(c));
                c.getThread().start();
            }
            server.getGamestate().setStatus(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void announcePlayers() {
        GamestateSchema.Builder gs = GamestateSchema.newBuilder();
        for (int i = 0; i < server.getMaxPlayers(); i++) {
            if (clients[i] != null && clients[i].isValidated() && clients[i].isAlive()) {
                gs.addPlayers(
                        GamestateSchema.Player.newBuilder()
                                .setPid(i)
                                .setId(clients[i].getUserObject().getInt("userId")));
            }
        }
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setGamestate(gs.build())
                        .build();
        writeToAll(d);
    }
}
