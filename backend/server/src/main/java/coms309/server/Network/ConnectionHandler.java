package coms309.server.Network;

import coms309.server.GameLogic.Player;
import coms309.server.Schema.ConnectedClients;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Schema.GamestateSchema;
import coms309.server.Server;
import java.io.IOException;
import java.nio.channels.*;
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

    /**
     * Write buffer to all connected and validated clients
     * @param data Wrapper Object to hold date
     */
    public void writeToAll(DataObjectSchema data) {
        for (int i = 0; i < server.getMaxPlayers(); i++) {
            if (clients[i] == null || !clients[i].isValidated() || !clients[i].isAlive()) {
                continue;
            }
            clients[i].write(data);
        }
    }

    /**
     * Get IDs of all connected clients and write buffer to all
     */
    public void announcePlayers() {
        ConnectedClients.Builder conn = ConnectedClients.newBuilder();
        for (int i = 0; i < server.getMaxPlayers(); i++) {
            if (clients[i] != null && clients[i].isValidated() && clients[i].isAlive()) {
                conn.addClients(
                        ConnectedClients.Client.newBuilder()
                                .setPid(i)
                                .setId(clients[i].getPlayer().getUserId()));
            }
        }
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setClients(conn.build())
                        .build();
        writeToAll(d);
    }

    /**
     * Update status of all connected clients.
     * Works by writing a null byte to every client socket
     * @return whether a connection has been changed
     */
    public boolean updateConnectionStatus() {
        boolean updated = false;
        for (int i = 0; i < server.getMaxPlayers(); i++) {
            if (clients[i] == null) { continue; }
            if (!clients[i].isAlive()) {
                updated = true;
                clients[i] = null;
            }
        }
        return updated;
    }

    /**
     * Get player details object by lobby PlayerId
     * @param pid lobby ID of player
     * @return player object
     */
    public Player getPlayerById(int pid) {
        return clients[pid].getPlayer();
    }

    /**
     * Wait for new socket connections and validate each connection to an User on the authentication server
     */
    public void awaitNewConnections() {
        playerIterator = 0;
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
            try {
                Socket s = server.getSocket().accept();
                Connection c = new Connection(s, playerIterator, server);
                clients[playerIterator] = c;
                Server.logger.log(Level.INFO, c.getAddress() + " is attempting to connect.");
                c.setThread(new Thread(c));
                c.getThread().start();
                break;
            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }
        }
    }

}