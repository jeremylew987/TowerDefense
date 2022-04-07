package coms309.server.GameLogic.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tile {

    public enum TileType { PATH, RESTRICTED, GRASS, WATER,  }

    private int x, y;
    private TileType tileType;
    private Entity entity;

    public Tile(TileType tileType, int x, int y) {
        this.tileType = tileType;
        this.x = x; this.y = y;
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

    public TileType getTileType() {
        return tileType;
    }
    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Entity getEntity() {
        return entity;
    }
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Creates an entity on the tile, and checks to see if the tile is a valid type for object.
     * @param ObjectId
     * @throws Exception
     */
    public void createEntity(int ObjectId) throws Exception {
        new Entity(this, ObjectId);
    }
}
