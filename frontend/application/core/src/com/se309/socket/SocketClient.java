package com.se309.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * SocketClient.java
 *
 * Connect to the backend game instance, and parses information going to and from.
 */

public class SocketClient {

    Socket socket;

    public SocketClient(String address, int port) {

        try {

            socket = new Socket(address, port);

        } catch (IOException e) {

            socket = null;

        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
