package com.se309.tower;

/**
 * This is a non-static class that is used to pass information from the Android side of the program to the LibGDX side
 *
 * @author Gavin Tersteeg
 */

public class GameConfiguration {

    private String socketServerAddress;
    private int socketServerPort;

    /**
     * Default constructor for GameConfiguration
     * @param socketServerAddress Address to the backend server
     * @param socketServerPort Port of the backend server endpoint
     */
    public GameConfiguration(String socketServerAddress, int socketServerPort) {
        this.socketServerAddress = socketServerAddress;
        this.socketServerPort = socketServerPort;
    }

    /**
     * Returns the address of the socket server
     * @return Returns socket server address
     */
    public String getSocketServerAddress() {
        return socketServerAddress;
    }

    /**
     * Sets the address for the socket server
     * @param socketServerAddress New socket server address
     */
    public void setSocketServerAddress(String socketServerAddress) {
        this.socketServerAddress = socketServerAddress;
    }

    /**
     * Returns the socket server port number
     * @return Socket server port number
     */
    public int getSocketServerPort() {
        return socketServerPort;
    }

    /**
     * Sets the port for the socket server
     * @param socketServerPort New socket server port
     */
    public void setSocketServerPort(int socketServerPort) {
        this.socketServerPort = socketServerPort;
    }
}
