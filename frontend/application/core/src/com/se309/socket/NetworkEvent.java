package com.se309.socket;

/**
 * Base class for network related events. A form of this class will be passed to the NetworkEventListeners bound to the respective NetworkDataHandler when data is received from the backend.
 *
 * @author Gavin Tersteeg
 */
public class NetworkEvent {

    private long time;

    /**
     * Constructor for default event type
     * @param time Time of message
     */
    public NetworkEvent(long time) {
        this.time = time;
    }

    /**
     * Gets the time of the message
     * @return Time of message
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the time of the message
     * @param time Time of message
     */
    public void setTime(long time) {
        this.time = time;
    }
}
