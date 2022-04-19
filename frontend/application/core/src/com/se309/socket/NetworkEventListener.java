package com.se309.socket;

/**
 * This is the interface for handling network events.
 *
 * These interfaces can be bound to the NetworkDataHandler class, which will trigger when events are received from the backend to the frontend
 *
 * @author Gavin Tersteeg
 */
public interface NetworkEventListener {

    /**
     * Triggers when the network receives some sort of information from the server
     * @param e The passed event
     */
    public void OnEvent(NetworkEvent e);

}
