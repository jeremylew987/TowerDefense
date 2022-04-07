package coms309.server.Network;

import com.google.protobuf.ByteString;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Schema.GamestateSchema;
import coms309.server.Schema.MessageSchema;
import coms309.server.Server;
import org.json.simple.JSONObject;
import org.junit.runner.Request;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.logging.Level;

public class Connection implements Runnable {

    private Server server;
    private Socket socket;
    private Thread thread;

    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private boolean validated;
    private boolean isAlive;
    private String address;

    private final String authServerLocation = "http://localhost:8080";//"http://coms-309-027.class.las.iastate.edu:8080";
    private Player player;

    public Connection(Socket socket, int id, Server server) {
        this.socket = socket;
        this.thread = thread;
        this.server = server;
        this.address = socket.getRemoteSocketAddress().toString();

        this.player = new Player(this, id);
        this.isAlive = true;
        this.validated = false;

        try {
            // Establish data in & out connection
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }
    public Socket getSocket() {
        return this.socket;
    }
    public Thread getThread() { return this.thread; }
    public void setThread(Thread thread) { this.thread = thread; }

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

            server.logger.log(Level.INFO, "Authentication query for token=" + query);

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
            server.logger.log(Level.INFO, address + " disconnected without authorizing.");
            this.validated = false;
        } catch (NullPointerException ex ) {
            server.logger.log(Level.INFO, address + " disconnected without authorizing.");
            this.validated = false;
        }
        return validated;
    }
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
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

            server.logger.log(
                    Level.INFO,
                    player.getUsername() + "(" + address + ") has connected."
            );
            server.getConnectionHandler().announcePlayers();
        } else { kill("AUTH_FAILED"); }

        while (this.isAlive) {
            try {
                read();
            } catch (Exception ex) {
                kill("LOST_CONNECTION");
            }
        }
    }

    public void write(DataObjectSchema data) {
        try {
            data.writeDelimitedTo(dataOut);
        } catch (IOException e) {
            kill("LOST_CONNECTION");
        }
    }
    public void read() throws IOException {
        DataObjectSchema data =
                DataObjectSchema.parseDelimitedFrom(dataIn);

        switch (data.getDataCase()) {
            case ENTITY:
                // Receive commands for entities
                break;
            case GAMESTATE:

                GamestateSchema g = data.getGamestate();
                if (g.hasMap()) {
                    try {
                        server.getGamestate().setMap(g.getMap());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (g.hasDifficulty()) {
                    server.getGamestate().setDifficulty(g.getDifficulty());
                }
                if (g.hasStatus()) {
                    server.getGamestate().setStatus(g.getStatus());
                }
                break;
            case MESSAGE:
                Message m = new Message(data.getMessage());

                // Check if message username matches auth username
                if (!player.getUsername().equals(m.author)) {
                    server.logger.log(Level.WARNING, "Message author and client details do not match!");
                    m.author = player.getUsername();
                }

                if (m.code.equals("CHAT")) {
                    server.getConnectionHandler().writeToAll(m.serialize()); // relay chat to users
                }
                server.logger.log(Level.INFO, m.toString());
                break;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }
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
}