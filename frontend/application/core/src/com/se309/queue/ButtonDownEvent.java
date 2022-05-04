package com.se309.queue;

public class ButtonDownEvent extends GameEvent {

    private int signal;

    public ButtonDownEvent(int signal) {
        super();

        this.signal = signal;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

}
