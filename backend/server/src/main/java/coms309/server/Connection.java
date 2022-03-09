package coms309.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;

public class Connection implements Runnable {

    private Socket socket;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    public boolean validated;
    public boolean isAlive;

    private String authServerLocation = "http://coms-309-027.class.las.iastate.edu:8080";
    private String authToken;

    // user credentials
    public int pid;
    public UserDetails userDetails;

    @Override
    public String toString() {
        return pid + "," + userDetails.toString();
    }

    public Connection(Socket s, int id) {
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

            /*
            // Form HTTP Request
            URL fetchUserCredentialsUrl = new URL(authServerLocation + "/users/token=" + authToken);
            HttpURLConnection fetchUserCredentials = (HttpURLConnection) fetchUserCredentialsUrl
                    .openConnection();
            fetchUserCredentials.setRequestMethod("POST");
            fetchUserCredentials.connect();

            / Read Auth Server response
            BufferedReader authReader = new BufferedReader(
                    new InputStreamReader(fetchUserCredentials.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = authReader.readLine()) != null) {
                content.append(inputLine);
            }
            authReader.close();

            // Close connection with Auth Server
            fetchUserCredentials.disconnect();

             */

            this.userDetails = new UserDetails("1f2u8","benhall",62);
            System.out.println("Player with ID: " + this.pid + ", UID: " + this.userDetails.getUid() + " has been successfully authenticated");
            this.validated = true;
        } catch (IOException ex) {
            System.out.println("Player with ID: " + this.pid + " has failed to authenticate: " + ex.getMessage());
            this.validated = false;
        }
        return validated;
    }

    @Override
    public void run() {
        while (this.isAlive) {
            if (!this.writeTo("")) {
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
        System.out.println("Player with ID: " + this.pid + " has disconnected.");
        this.isAlive = false;
        try {
            this.socket.close();
        } catch (IOException ex) {
            System.out.println("Player with ID: " + this.pid + " has disconnected before a proper connection has been formed.");
        }
    }
}