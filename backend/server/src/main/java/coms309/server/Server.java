package coms309.server;

import coms309.server.GameState.GameState;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket socket;
    private int maxPlayers;
    private int currPlayer;
    private HttpURLConnection authServer;
    private Connection[] clients;
    public GameState gameLogic;

    public Server(int port, int maxPlayers) {

        this.maxPlayers = maxPlayers;
        this.currPlayer = 0;
        this.clients = new Connection[maxPlayers];
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameLogic = new GameState(this, this.clients);
        System.out.println("Server started on port: " + port);
    }

    public void writeToAll(String s) {
        for (int i = 0; i <= currPlayer; i++) {
            if (clients[i] == null) {
                continue;
            }
            clients[i].writeTo(s);
        }
    }

    public void checkClientConnections() {
        // check if any connections have disconnected
        for (int i = 0; i < currPlayer; i++) {
            if (clients[i] == null) { continue; }
            if (!clients[i].isAlive) {
                clients[i] = null;
                currPlayer--;
            }
        }
    }

    public void waitForPlayers() {
        try {
            // wait until max players have connected
            while (currPlayer < maxPlayers) {
                // check if array item is already claimed
                if (clients[currPlayer] != null) {
                    currPlayer++;
                    continue;
                }

                // accept new connection
                Socket s = socket.accept();
                checkClientConnections();
                System.out.println("Player with ID:" + currPlayer + " is attempting to connect.");
                Connection c = new Connection(s, currPlayer, this);
                clients[currPlayer] = c;

                // validate new connection
                if (c.validateUser()) {
                    // start new thread
                    Thread t = new Thread(c);
                    t.start();

                    // update player data to users
                    this.writeToAll("PLAYER_JOIN," + c.userObject.toString() + "\n\r");

                    // tell new client current users
                    for (int i = 0; i < maxPlayers; i++) {
                        if (clients[i] == null || i==currPlayer) {
                            continue;
                        }
                        c.writeTo("CURR_PLAYER,"+ clients[i].userObject.toString() + "\n\r");
                    }

                } else {
                    currPlayer--;
                    c.writeTo("JOIN_FAILURE\n\r");
                    c.close();
                }

                currPlayer++;

                // make sure all users are connected before starting, otherwise restart
                if (currPlayer == maxPlayers) {
                    checkClientConnections(); // update status
                    for (int i = 1; i < maxPlayers; i++) {
                        if (clients[i] == null) { // if array item is null
                            currPlayer = i; // set iterator to null object id to fill array fully
                        }
                    }
                }
            }
            System.out.println("All players have connected, starting game.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server s = new Server(25565, 4);
        s.waitForPlayers();
    }

}
