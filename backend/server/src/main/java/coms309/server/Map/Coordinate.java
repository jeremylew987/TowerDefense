package coms309.server.Map;

public class Coordinate {

    public boolean occupied;
    public int objectId;

    public Coordinate(int objectId) {
        if (objectId == 0) {
            this.occupied = false;
        } else {
            this.objectId = objectId;
            this.occupied = true;
        }

    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
}
