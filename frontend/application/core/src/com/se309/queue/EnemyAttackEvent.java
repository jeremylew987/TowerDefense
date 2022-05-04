package com.se309.queue;

public class EnemyAttackEvent extends GameEvent{

    private int enemyId;
    private int towerId;
    private boolean kill;

    public EnemyAttackEvent(int enemyId, int towerId, boolean kill) {
        this.enemyId = enemyId;
        this.towerId = towerId;
        this.kill = kill;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public int getTowerId() {
        return towerId;
    }

    public void setTowerId(int towerId) {
        this.towerId = towerId;
    }

    public boolean isKill() {
        return kill;
    }

    public void setKill(boolean kill) {
        this.kill = kill;
    }
}
