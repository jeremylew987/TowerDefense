package coms309.server.Map;

public class GrassyFields extends Map {

    public int mapId = 1;
    public String name = "Grassy Fields";
    public Coordinate[][] grid = new Coordinate[128][128];

    public GrassyFields() {
        for ( int i = 0; i < grid.length; i++) {
            this.grid[i][128/2].setObjectId(1);
        }
    }

}
