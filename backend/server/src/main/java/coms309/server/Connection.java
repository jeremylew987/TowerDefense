package coms309.server;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Connection implements Runnable {

    private Server server;
    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    public boolean validated;
    public boolean isAlive;

    private String authServerLocation = "http://localhost:8080";//"http://coms-309-027.class.las.iastate.edu:8080";
    private String authToken;

    // user credentials
    public int pid;
    public JsonObject userObject;

    public Connection(Socket s, int id, Server server) {
        this.server = server;
        this.socket = s;
        this.pid = id;
        this.authServerLocation = authServerLocation;
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
            this.writeTo("AWAIT_TOKEN\0");
            // Authenticate user with Auth server
            BufferedReader clientReader = new BufferedReader(
                    new InputStreamReader(dataIn));
            this.authToken = clientReader.readLine();

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

            System.out.println("Player with ID:" + this.pid + " has been successfully authenticated");
            this.validated = true;
        } catch (IOException ex) {
            System.out.println("Player with ID:" + this.pid + " has failed to authenticate: " + ex.getMessage());
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
                String parsedOutput[] = scanner.nextLine().split(",",2);
                switch (parsedOutput[0])
                {
                    case "SAY":
                        this.server.writeToAll("[" + userObject.getString("username") + "]: " + parsedOutput[1] + "\n\r");
                        break;
                    case "SETGL":
                        switch (parsedOutput[1]) {
                            case "DIFF":
                                this.server.gameLogic.setDifficulty(this.pid,parsedOutput[2]);
                                break;
                            case "MAP":
                                this.server.gameLogic.setMap(this.pid, Integer.parseInt(parsedOutput[2]));
                                break;
                            case "PAUSE":
                                this.server.gameLogic.pauseGame(this.pid);
                                break;
                            case "RESUME":
                                this.server.gameLogic.resumeGame(this.pid);
                            default:
                                break;
                        }
                    default:
                        break;
                }
            } catch (Exception ex) {
                this.close();
            }
        }
    }

    public boolean writeTo(String s) {
        try {
            this.dataOut.writeUTF(s);
            this.dataOut.flush();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public void close() {
        System.out.println("Player with ID:" + this.pid + " has disconnected.");
        server.writeToAll("PLAYER_LEFT," + pid + "\n\r");
        this.isAlive = false;
        try {
            this.socket.close();
        } catch (IOException ex) {
            System.out.println("Player with ID:" + this.pid + " has disconnected before a proper connection has been formed.");
        }
    }
}