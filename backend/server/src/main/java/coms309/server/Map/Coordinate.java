package coms309.server.Map;

import coms309.server.Tower.Tower;

import java.util.Collection;

public class Coordinate {

    public int entityId, ownerId;
    public static int x, y;
    public Tower tower;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public Tower(int x, int y, int objectId, int ownerId) {
        this.x = x;
        this.y = y;
        this.ownerId = ownerId;
        this.tower = new Tower(objectId);
    }

}
