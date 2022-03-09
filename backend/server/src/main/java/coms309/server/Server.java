package coms309.server;

import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket socket;
    private int maxPlayers;
    private int numPlayers;
    private HttpURLConnection authServer;
    private Connection[] clients;

    public Server(int port, int maxPlayers) {

        this.maxPlayers = maxPlayers;
        this.numPlayers = 0;
        this.clients = new Connection[maxPlayers];
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server started on port: " + port);
    }

    public void writeToAll(String s) {
        for (int i = 1; i <= numPlayers; i++) {
            try {
                clients[i].writeTo(s);
            } catch (NullPointerException ex) {
                System.out.println("Player with ID: " + numPlayers + " is no longer connected.");
            }
        }
    }

    public void checkClientConnections() {
        // check if any connections have disconnected
        for (int i = 1; i <= numPlayers; i++) {
            if (!clients[i].isAlive) {
                this.writeToAll("Player #" + numPlayers + " has disconnected.\n");
                clients[i] = null;
            }
        }
    }

    public void waitForPlayers() {
        try {
            // wait until max players have connected
            while (numPlayers < maxPlayers) {
                // check if array item is already claimed
                if (clients[numPlayers] != null) {
                    numPlayers++;
                    continue;
                }

                // accept new connection
                Socket s = socket.accept();
                checkClientConnections();
                numPlayers++;
                System.out.println("Player with ID: " + numPlayers + " has connected.");
                Connection c = new Connection(s, numPlayers);
                clients[numPlayers] = c;

                // validate new connection
                if (c.validateUser()) {
                    // start new thread
                    Thread t = new Thread(c);
                    t.start();

                    // update player data to users
                    this.writeToAll("Player #" + numPlayers + " has connected.\n");
                } else {
                    numPlayers--;
                    c.writeTo("Failed to authenticate user.");
                    c.close();
                }

                // make sure all users are connected before starting, otherwise restart
                if (numPlayers == maxPlayers) {
                    checkClientConnections(); // update status
                    for (int i = 1; i < maxPlayers; i++) {
                        if (clients[i] == null) { // if array item is null
                            numPlayers = i; // set iterator to null object id to fill array fully
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
