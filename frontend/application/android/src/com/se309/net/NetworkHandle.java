package com.se309.net;

import android.content.Context;

import java.lang.reflect.Type;

/**
 * This object provides an abstracted interface in which different program resources can interact with the backend/frontend network line.
 *
 * @author Gavin Tersteeg
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

    /**
     * Performs a GET request to the endpoint
     * @param ty Class type
     * @param response Response object
     */
    public void get(Type ty, NetworkResponse response) {

        parentManager.SendStringGET(ty, defaultEndpoint, response);

    }


    /**
     * Getter-ville
     */

    /**
     * Returns the default endpoint
     * @return Default endpoint
     */
    public String getDefaultEndpoint() {
        return defaultEndpoint;
    }

    /**
     * Changes the default endpoint
     * @param defaultEndpoint New default endpoint
     */
    public void setDefaultEndpoint(String defaultEndpoint) {
        this.defaultEndpoint = defaultEndpoint;
    }
}
