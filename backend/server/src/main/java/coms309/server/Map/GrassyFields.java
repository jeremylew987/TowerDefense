package coms309.server.Map;

public class GrassyFields extends Map {

    public int mapId = 1;
    public String name = "Grassy Fields";
    public Coordinate[][] grid = new Coordinate[20][20];

    public GrassyFields() {
        this.grid[0][2].setObjectId();
    }

}
