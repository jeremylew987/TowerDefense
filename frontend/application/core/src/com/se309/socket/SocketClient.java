package com.se309.socket;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Connect to the backend game instance, and parses information going to and from.
 *
 * @author Gavin Tersteeg
 */
public class SocketClient {

    private Socket socket;

    private DataOutputStream dataOut;
    private DataInputStream dataIn;

    /**
     * Creates a new socket client, and connects it to an address and a port.
     * @param address
     * @param port
     */
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


    /**
     * Returns the currently connected socket
     * @return Connected socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Sets the socket, currently not very useful
     * @param socket New socket
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * Gets the data output stream for the current socket.
     * @return Data output stream
     */
    public DataOutputStream getDataOut() {
        return dataOut;
    }

    /**
     * Gets the data input stream for the current socket.
     * @return
     */
    public DataInputStream getDataIn() {
        return dataIn;
    }

}
