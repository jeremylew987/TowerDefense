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
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    public boolean validated;
    public boolean isAlive;

    private final String authServerLocation = "http://localhost:8080"; //"http://coms-309-027.class.las.iastate.edu:8080";

    // user credentials
    public int pid;
    public JsonObject userObject;

    public Connection(Socket s, int id, Server server) {
        this.socket = s;
        this.server = server;
        this.pid = id;
        this.isAlive = true;
        try {
            // Establish data in & out connection
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            JsonObject userObject = reader.readObject();
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
        try {
            Message m = new Message(
                    "Server",
                    "DISCONNECT",
                    "" + reason
            );
            this.write(m.serialize());
            this.socket.close();
        } catch (IOException e) {
            server.logger.log(Level.INFO, this.socket.getRemoteSocketAddress().toString() + " disconnected prematurely.");
        } finally {
            server.logger.log(Level.INFO, this.socket.getRemoteSocketAddress().toString() + " lost connection: DISCONNECT." + reason);
        }
        this.isAlive = false;
    }
}