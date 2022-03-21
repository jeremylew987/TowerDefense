package com.se309.net;

import android.content.Context;

import java.lang.reflect.Type;

/**
 * NetworkHandle.java
 *
 * This object provides an abstracted interface in which different program resources can interact with the backend/frontend network line.
 */

public class NetworkHandle {

    // Incoming objects
    private String defaultEndpoint;
    private NetworkManager parentManager;

    // Reply switch
    protected ResponseContainer response;

    /**
     * Spawned from NetworkManager, provides an abstract interface to the networking components.
     * @param defaultEndpoint the default endpoint
     */
    public NetworkHandle(String defaultEndpoint, NetworkManager parentManager) {

        // Save local instances
        this.defaultEndpoint = defaultEndpoint;
        this.parentManager = parentManager;
    }

    public void get(Type ty, NetworkResponse response) {

        System.out.println("Get function");

        parentManager.SendStringGET(ty, defaultEndpoint, response);

    }


    /**
     * Getter-ville
     */

    public String getDefaultEndpoint() {
        return defaultEndpoint;
    }

    public void setDefaultEndpoint(String defaultEndpoint) {
        this.defaultEndpoint = defaultEndpoint;
    }

    public NetworkManager getParentManager() {
        return parentManager;
    }

    public void setParentManager(NetworkManager parentManager) {
        this.parentManager = parentManager;
    }
}
