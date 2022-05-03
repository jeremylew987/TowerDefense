package com.se309.queue;

public class ButtonEvent extends GameEvent {

    private int signal;

    public ButtonEvent(int signal) {
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
