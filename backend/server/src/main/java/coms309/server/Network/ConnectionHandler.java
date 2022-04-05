package coms309.server.Network;

import coms309.server.Schema.GamestateSchema;
import coms309.server.Server;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ConnectionHandler {

    public Server server;
    public Connection[] clients;
    private int playerIterator;

    public ConnectionHandler(Server server) {
        this.server = server;
        this.clients = new Connection[4];
    }

    public void writeToAll(byte[] data) {
        for (int i = 0; i <= playerIterator; i++) {
            if (clients[i] == null) {
                continue;
            }
            clients[i].write(data);
        }
    }

    public void updateConnectionStatus() {
        GamestateSchema.Builder gs = GamestateSchema.newBuilder();
        for (int i = 0; i < playerIterator; i++) {
            if (clients[i] == null) { continue; }
            if (!clients[i].isAlive) {
                clients[i] = null;
                playerIterator--;
            } else {
                gs.addPlayers(
                        GamestateSchema.Player.newBuilder()
                                .setPid(i)
                                .setId((String) clients[i].userObject.getString("id")));
            }
        }
        writeToAll(gs.build().toByteArray());
    }

    public void waitForPlayers() {
        try {
            // wait until max players have connected
            while (playerIterator < server.maxPlayers) {
                // 1. See if client spot is already filled
                if (clients[playerIterator] != null) {
                    playerIterator++;
                    continue;
                }

                // 2. Accept new connection
                Socket s = server.socket.accept();
                updateConnectionStatus();
                server.logger.log(Level.INFO, s.getRemoteSocketAddress().toString() + " is attempting to connect.");
                Connection c = new Connection(s, playerIterator, server);
                clients[playerIterator] = c;

                // 3. Validate user with authentication server
                if (c.validateUser()) {
                    // start new thread
                    Thread t = new Thread(c);
                    t.start();
                } else {
                    c.kill("AUTH_FAILED");
                    playerIterator--;
                }
                playerIterator++;

                // 4. Check if all users are connected
                if (playerIterator == server.maxPlayers) {
                    updateConnectionStatus(); // GET: Update status
                    for (int i = 1; i < server.maxPlayers; i++) {
                        if (clients[i] == null) { // if array item is null
                            playerIterator = i; // set iterator to null object id to fill array fully
                        }
                    }
                }
            }
            server.gamestate.setStatus(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
