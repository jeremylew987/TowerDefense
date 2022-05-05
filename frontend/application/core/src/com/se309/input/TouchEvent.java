package com.se309.input;

import com.se309.queue.GameEvent;

public class TouchEvent extends GameEvent {

    private int x;
    private int y;

    public TouchEvent(int x, int y) {
        super();

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
