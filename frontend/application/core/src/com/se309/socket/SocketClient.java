package com.se309.socket;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * SocketClient.java
 *
 * Connect to the backend game instance, and parses information going to and from.
 */

public class SocketClient {

    private Socket socket;

    private DataOutputStream dataOut;
    private DataInputStream dataIn;

    public SocketClient(String address, int port) {

        try {

            socket = new Socket(address, port);

            System.out.println("Connected");

            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {

            System.out.println("Unable to connect");

            dataIn = null;
            dataOut = null;

            socket = null;

        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDataOut() {
        return dataOut;
    }

    public void setDataOut(DataOutputStream dataOut) {
        this.dataOut = dataOut;
    }

    public DataInputStream getDataIn() {
        return dataIn;
    }

    public void setDataIn(DataInputStream dataIn) {
        this.dataIn = dataIn;
    }
}
