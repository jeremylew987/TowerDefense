package com.se309.queue;

public class GameEvent {

    int consumeOn;

    public GameEvent() {
        consumeOn = -1;
    }

    public int getConsumeOn() {
        return consumeOn;
    }

    public void setConsumeOn(int consumeOn) {
        this.consumeOn = consumeOn;
    }
}
