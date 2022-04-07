package coms309.server;


import coms309.server.GameLogic.GameState.GameState;
import coms309.server.Network.ConnectionHandler;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    // Server Properties
    private int port;
    private int maxPlayers;
    private ArrayList<Integer> moderators = new ArrayList<>();

    // Server Objects
    private ServerSocket socket;
    private GameState gamestate;
    private ConnectionHandler connectionHandler;
    public final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Server() {
        // 1. Setup Logging
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");

        // 2. Load server settings from "server.properties"
        Properties prop = new Properties();
        try {
            InputStream f = getClass().getClassLoader().getResourceAsStream("server.properties");
            prop.load(f);
            this.maxPlayers = Integer.parseInt(prop.getProperty("server.maxPlayers"));
            this.port = Integer.parseInt(prop.getProperty("server.port"));
            f.close();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Could not load server properties from file!");
            ex.printStackTrace();
            System.exit(1);
        }

        // 3. Establish Network Socket
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) { e.printStackTrace(); }
        this.connectionHandler = new ConnectionHandler(this);
        logger.log(Level.INFO, "Server started on port: " + port);

        // 4. Create Gamestate Logic
        this.gamestate = new GameState(this);
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public GameState getGamestate() {
        return gamestate;
    }
    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.connectionHandler.awaitNewConnections();
    }

}
