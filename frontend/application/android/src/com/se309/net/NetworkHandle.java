package com.se309.net;

/**
 * NetworkHandle.java
 *
 * This object provides an abstracted interface in which different program resources can interact with the backend/frontend network line.
 */

public class NetworkHandle {

    // Incoming objects
    String defaultResource;
    NetworkManager parentManager;

    /**
     * Spawned from NetworkManager, provides an abstract interface to the networking components.
     * @param defaultResource
     */
    public NetworkHandle(String defaultResource, NetworkManager parentManager) {

        // Save local instances
        this.defaultResource = defaultResource;
        this.parentManager = parentManager;
    }

}
