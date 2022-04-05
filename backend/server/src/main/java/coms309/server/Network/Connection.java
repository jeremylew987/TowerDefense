package coms309.server.Network;

import coms309.server.Server;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.*;
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

    private final String authServerLocation = "http://localhost:8080"; //"http://coms-309-027.class.las.iastate.edu:8080";

    // user credentials
    private int playerId;
    private JsonObject userObject;

    public Connection(Socket socket, int id, Server server) {
        this.socket = socket;
        this.thread = thread;
        this.server = server;
        this.address = socket.getRemoteSocketAddress().toString();

        this.playerId = id;
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
    public void setServer(Server server) {
        this.server = server;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setThread(Thread thread) { this.thread = thread; }
    public Thread getThread() { return this.thread; }

    public DataInputStream getDataIn() {
        return dataIn;
    }
    public DataOutputStream getDataOut() {
        return dataOut;
    }

    public String getAddress() {return address;}
    public boolean isValidated() {
        return validated;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public int getPlayerId() {
        return playerId;
    }
    public JsonObject getUserObject() {
        return userObject;
    }
    public Socket getSocket() {
        return this.socket;
    }

    public boolean validateUser() {
        try {
            // Authenticate user with Auth server
            BufferedReader clientReader = new BufferedReader(
                    new InputStreamReader(dataIn));
            String authToken = clientReader.readLine();

            // Form HTTP Request
            String url = authServerLocation + "/user/verifyUser";
            String charset = "UTF-8";
            String query = String.format("token=%s",
                    URLEncoder.encode(authToken, charset));

            URLConnection connection = new URL(url + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);

            // Get response and parse to json object
            String response = convertStreamToString(connection.getInputStream());
            JsonReader reader = Json.createReader(new StringReader(response));

            // Save user object
            userObject = reader.readObject();
            reader.close();
            this.validated = true;
        } catch (IOException ex) {
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

    @Override
    public void run() {
        // authenticate user
        if (validateUser()) {
            Message m = new Message(
                    "Server",
                    "SUCCESS",
                    "AUTH_SUCCESS"
            );
            write(m.serialize());
            server.logger.log(
                    Level.INFO,
                    userObject.getString("username") + "(" + address + ") has connected."
            );
            server.getConnectionHandler().announcePlayers();
        } else { kill("AUTH_FAILED"); }

        while (this.isAlive) {
            try {
                Scanner scanner = new Scanner(new InputStreamReader(dataIn, "UTF-8"));
                scanner.useDelimiter("\n\r");
                scanner.nextLine();
            } catch (Exception ex) {
                kill("LOST_CONN");
            }
        }
    }
    public void write(byte[] data) {
        try {
            this.dataOut.write(data);
            this.dataOut.flush();
        } catch (IOException e) {
            kill("LOST_CONN");
        }
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