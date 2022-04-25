package coms309.server;


import coms309.server.GameLogic.GameState;
import coms309.server.Network.ConnectionHandler;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    // Server Properties
    private int maxPlayers;
    private ArrayList<Integer> moderators = new ArrayList<>();

    // Server Objects
    private ServerSocket socket;
    private GameState gamestate;
    private Thread gameThread;
    private ConnectionHandler connectionHandler;
    private Properties properties;
    public static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public Server() {
        try {
            // 1. Setup Logging
            System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");

            // 2. Load server settings from "server.properties"
            loadPropertiesFile();
            this.maxPlayers = Integer.parseInt(properties.getProperty("server.maxPlayers"));

            // 3. Establish Network Socket
            socket = new ServerSocket(Integer.parseInt(properties.getProperty("server.port")));
            this.connectionHandler = new ConnectionHandler(this);
            Server.logger.log(Level.INFO, "Server started on: " + socket.getLocalSocketAddress());

            // 4. Create Gamestate Logic
            this.gamestate = new GameState(this);
            this.gameThread = new Thread(this.gamestate);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Load server.properties file to load server settings
     * @throws IOException
     */
    private void loadPropertiesFile() throws IOException{
        // find root directory of jar file
        File jar = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        File f = new File(jar.getParent() + "\\server.properties");
        if (f.createNewFile()) {
            Server.logger.info("Created new default config: " + f.getName());
            FileWriter fw = new FileWriter(f);
            fw.write("server.port=25565\nserver.maxPlayers=4");
            fw.close();
        } else { Server.logger.info("Loading properties from: " + f.getAbsolutePath()); }

        // load properties
        this.properties = new Properties();
        InputStream i = new FileInputStream(f);
        properties.load(i);
        i.close();
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
    public Thread getGameThread() { return gameThread; }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }
    public ServerSocket getSocket() {
        return socket;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.getGameThread().start();
    }

}
