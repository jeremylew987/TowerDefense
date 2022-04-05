package coms309.server;

import com.google.protobuf.BlockingService;
import com.google.protobuf.Service;
import coms309.server.GameLogic.GameState.GameState;
import coms309.server.Network.ConnectionHandler;

import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerSocket socket;
    private String address;
    private int maxPlayers;
    private GameState gamestate;
    private ConnectionHandler connectionHandler;
    public final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Server(String address, int port, int maxPlayers) {
        this.address = address;
        this.maxPlayers = maxPlayers;
        try {
            // Establish Server Socket
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionHandler = new ConnectionHandler(this);
        gamestate = new GameState(this);
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");
        logger.log(Level.INFO, "Server started on port: " + port);
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
        Server server = new Server("localhost", 25565, 4);
        server.connectionHandler.awaitNewConnections();
    }

}
