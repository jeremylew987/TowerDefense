package com.se309.socket;

public class EventBase {

    private long time;

    /**
     * Constructor for default event type
     * @param time Time of message
     */
    public EventBase(long time) {
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
