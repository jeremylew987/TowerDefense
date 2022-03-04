package coms309.server;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private ServerSocket socket;
    private int maxPlayers;
    private int numPlayers;
    private String authServerLocation = "http://coms-309-027.class.las.iastate.edu:8080";
    private HttpURLConnection authServer;
    private Connection[] clients;

    public Server(int port, int maxPlayers) throws MalformedURLException {

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

    public void waitForPlayers() {
        try {
            // wait until max players have connected
            while (numPlayers < maxPlayers) {
                Socket s = socket.accept();
                numPlayers++;
                System.out.println("Player #" + numPlayers + " has connected.");
                Connection c = new Connection(s, numPlayers);
                clients[numPlayers] = c;
            }
            System.out.println("All players have connected, starting game.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Connection implements Runnable {

        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int pid;

        public Connection(Socket s, int id) {
            socket = s;
            pid = id;
            try {
                // Establish data in & out connection
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // Authenticate user with Auth server
                BufferedReader clientReader = new BufferedReader(
                        new InputStreamReader(dataIn));
                String authToken = clientReader.readLine();

                URL fetchUserCredentialsUrl =new URL(authServerLocation + "/users/token=" + authToken);
                HttpURLConnection fetchUserCredentials = (HttpURLConnection) fetchUserCredentialsUrl
                        .openConnection();
                fetchUserCredentials.setRequestMethod("POST");
                fetchUserCredentials.connect();

                // Read Auth Server response
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

                while (true) {}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server s = new Server(25565, 4);
        s.waitForPlayers();
    }

}
