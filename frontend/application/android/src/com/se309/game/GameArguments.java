package com.se309.game;

/**
 * GameArguments.java
 *
 * This class is used to pass arguments to GameLauncher about the state of the desired game before activity is started
 * The information passed is mainly information about the server-side information for the game
 *
 * This is a static class because it seems to be a pain to non-statically pass information through an Intent
 */

public class GameArguments {

    private static String socketServerAddress;

    private static int socketServerPort;

    public static String getSocketServerAddress() {
        return socketServerAddress;
    }

    public static void setSocketServerAddress(String socketServerAddress) {
        GameArguments.socketServerAddress = socketServerAddress;
    }

    public static int getSocketServerPort() {
        return socketServerPort;
    }

    public static void setSocketServerPort(int socketServerPort) {
        GameArguments.socketServerPort = socketServerPort;
    }
}
