package coms309.server.Network;

import coms309.server.GameLogic.Player;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Schema.GamestateSchema;
import coms309.server.Schema.TowerSchema;
import coms309.server.Server;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;

public class Connection implements Runnable {
    private Server server;
    private Socket socket;
    private String address;
    private Thread thread;

    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    private boolean validated;
    private boolean isAlive;

    private final String authServerLocation = "http://coms-309-027.class.las.iastate.edu:8080";
    private Player player;

    public Connection(Socket socket, int id, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.address = socket.getRemoteSocketAddress().toString();

        this.player = new Player(this, id);
        this.isAlive = true;
        this.validated = false;

        // Establish data in & out connection
        dataIn = new DataInputStream(socket.getInputStream());
        dataOut = new DataOutputStream(socket.getOutputStream());
    }

    public Server getServer() {
        return server;
    }
    public Socket getSocket() {
        return this.socket;
    }

    public DataInputStream getDataIn() {
        return dataIn;
    }
    public DataOutputStream getDataOut() {
        return dataOut;
    }

    public void setAddress() { this.address = this.socket.getRemoteSocketAddress().toString(); }
    public String getAddress() {return address;}


    public boolean isValidated() {
        return validated;
    }

    /**
     * Validates the provided token with the authentication server.
     * Initializes the Player object with JSON response.
     * @return validation status
     */
    public boolean validateUser() {
        try {
            // 1. Read Authentication Token from Client
            DataObjectSchema data =
                    DataObjectSchema.parseDelimitedFrom(dataIn);

            if (!data.hasMessage()) { throw new IOException("Did not receive proper authentication token."); }
            String authToken = data.getMessage().getMessage();

            // 2. Form HTTP Request to Authentication Server
            String url = authServerLocation + "/user/verifyUser";
            String charset = "UTF-8";
            String query = String.format("token=%s",
                    URLEncoder.encode(authToken, charset));

            // 3. Send HTTP Request to Authentication Server
            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);

            // 4. Read and parse HTTP Response
            String response = convertStreamToString(connection.getInputStream());
            JsonReader reader = Json.createReader(new StringReader(response));
            JsonObject userObject = reader.readObject();
            reader.close();

            // 5. Deserialize UserObject to class
            this.player.setUsername(userObject.getString("username"));
            this.player.setUserId(userObject.getInt("userId"));
            this.validated = true;
        } catch (IOException ex) {
            server.logger.log(Level.INFO, address + " auth failed.");
            this.validated = false;
        }
        return validated;
    }

    /**
     * Convert HTTP Response to String
     * @param is InputStream
     * @return string output
     */
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public Player getPlayer() { return this.player; }
    public void setPlayer(Player player) { this.player = player; }

    @Override
    public void run() {
        // authenticate user
        if (validateUser()) {
            write(new Message(
                    "Server",
                    "AUTH",
                    "SUCCESS"
            ).serialize());

            server.getConnectionHandler().writeToAll(new Message(
                    "Server",
                    "CHAT",
                    player.getUsername() + " has joined the server!"
            ).serialize());

            server.logger.info(player.getUsername() + "(" + address + ") has connected.");
            server.getConnectionHandler().announcePlayers();
        } else { kill("AUTH_FAILED"); }

        while (this.isAlive) {
            try {
                read();
            } catch (Exception ex) {
                kill("LOST_CONNECTION");
                break;
            }
        }
    }

    /**
     * Attempt to write Protobuf Object to data stream
     * @param data protobuf data
     */
    public void write(DataObjectSchema data) {
        try {
            data.writeDelimitedTo(dataOut);
        } catch (IOException e) {
            kill("LOST_CONNECTION");
        }
    }

    /**
     * Read protobuf object from data stream and attempt to parse object
     * @throws IOException failed to read
     */
    public void read() throws IOException {
        DataObjectSchema data =
                DataObjectSchema.parseDelimitedFrom(dataIn);

        switch (data.getDataCase()) {
            case TOWER: {
                TowerSchema tower = data.getTower();
                server.getGamestate().getMap().spawnEntity(
                        tower.getTypeId(),
                        new Point(
                                tower.getX(),
                                tower.getY()
                        ),
                        tower.getOwnerId()
                );
                break;
            }
            case GAMESTATE: {
                GamestateSchema g = data.getGamestate();
                if (g.hasMap()) {
                    try {
                        server.getGamestate().setMap(g.getMap());
                    } catch (Exception e) {
                        Server.logger.warning("Failed to load new map: " + e.getMessage());
                        write(new Message(
                                "Server",
                                "ERR",
                                "Failed to load map"
                        ).serialize());
                    }
                }
                if (g.hasDifficulty()) {
                    server.getGamestate().setDifficulty(g.getDifficulty());
                }
                if (g.hasStatus()) {
                    server.getGamestate().setStatus(g.getStatus());
                }
                break;
            }
            case MESSAGE: {
                Message m = new Message(data.getMessage());

                // Check if message username matches auth username
                if (!player.getUsername().equals(m.author)) {
                    server.logger.info("Message author and client details do not match!");
                    m.author = player.getUsername();
                }

                // Relay if chat message
                if (m.code.equals("CHAT")) {
                    server.getConnectionHandler().writeToAll(m.serialize());
                }

                server.logger.log(Level.INFO, m.toString());
                break;
            }
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Disconnect client and announce to lobby
     * @param reason for disconnect
     */
    public void kill(String reason) {
        this.isAlive = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            server.logger.log(Level.INFO, address + " disconnected without warning.");
        }
        server.logger.log(Level.INFO, address + " lost connection: DISCONNECT." + reason);
        server.getConnectionHandler().announcePlayers();
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public Thread getThread() { return this.thread; }
}