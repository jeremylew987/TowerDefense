package com.se309.queue;

public class StatusUpdateEvent extends GameEvent {

    private int health;
    private int round;
    private int balance;

    public StatusUpdateEvent(int health, int round, int balance) {
        super();

        this.health = health;
        this.round = round;
        this.balance = balance;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
