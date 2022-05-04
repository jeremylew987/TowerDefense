package com.se309.queue;

public class PlayerJoinEvent extends GameEvent {

    String name;

    public PlayerJoinEvent(String name) {
        super();

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
