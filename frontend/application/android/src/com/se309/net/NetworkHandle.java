package com.se309.net;

import java.lang.reflect.Type;

/**
 * NetworkHandle.java
 *
 * This object provides an abstracted interface in which different program resources can interact with the backend/frontend network line.
 */

public class NetworkHandle {

    // Incoming objects
    private String defaultResource;
    private NetworkManager parentManager;

    // Reply switch
    protected ResponseContainer response;

    /**
     * Spawned from NetworkManager, provides an abstract interface to the networking components.
     * @param defaultResource
     */
    public NetworkHandle(String defaultResource, NetworkManager parentManager) {

        // Save local instances
        this.defaultResource = defaultResource;
        this.parentManager = parentManager;
    }

    public Object get(Type ty) throws RequestException {
        synchronized(this) {
            try {
                parentManager.SendStringGET(this, defaultResource);

                this.wait();
            } catch (InterruptedException e) {
                throw new RequestException(e.getMessage());
            }

            // If there is an error, throw it
            if (response.isError()) throw new RequestException(response.getBody(), response.getError());

            // Otherwise, extract body and deserialize
            String body = response.getBody();

            return parentManager.deserialize(body, ty);
        }
    }


    /**
     * Getter-ville
     */

    public String getDefaultResource() {
        return defaultResource;
    }

    public void setDefaultResource(String defaultResource) {
        this.defaultResource = defaultResource;
    }

    public NetworkManager getParentManager() {
        return parentManager;
    }

    public void setParentManager(NetworkManager parentManager) {
        this.parentManager = parentManager;
    }
}
