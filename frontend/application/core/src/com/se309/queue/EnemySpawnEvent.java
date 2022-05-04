package com.se309.queue;

public class EnemySpawnEvent extends GameEvent {

    private int id;

    public EnemySpawnEvent(int id) {
        super();

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
