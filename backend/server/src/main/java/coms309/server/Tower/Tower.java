package coms309.server.Tower;

public class Tower {

    public int objectId;
    public int range, damage, cooldown;

    public void loadTowerById(int objectId) {

    }

    public Tower(int objectId) {
        this.objectId = objectId;
    }

    public int getObjectId() {
        return this.objectId;
    }
}
