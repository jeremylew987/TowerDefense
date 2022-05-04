package com.se309.queue;

import java.util.ArrayList;

public class PlayerListUpdateEvent extends GameEvent {

    ArrayList<String> names;

    public PlayerListUpdateEvent(ArrayList<String> names) {
        super();

        this.names = names;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
