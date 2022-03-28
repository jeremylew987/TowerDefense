package com.se309.tower;

/**
 * GameConfiguration.java
 *
 * This is a non-static class that is used to pass information from the Android side of the program to the LibGDX side
 */

public class GameConfiguration {

    private String socketServerAddress;
    private int socketServerPort;

    public GameConfiguration(String socketServerAddress, int socketServerPort) {
        this.socketServerAddress = socketServerAddress;
        this.socketServerPort = socketServerPort;
    }

    public String getSocketServerAddress() {
        return socketServerAddress;
    }

    public void setSocketServerAddress(String socketServerAddress) {
        this.socketServerAddress = socketServerAddress;
    }

    public int getSocketServerPort() {
        return socketServerPort;
    }

    public void setSocketServerPort(int socketServerPort) {
        this.socketServerPort = socketServerPort;
    }
}
