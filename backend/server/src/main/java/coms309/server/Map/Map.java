package coms309.server.Map;

import coms309.server.Exceptions.AlreadyOccupiedException;
import coms309.server.Exceptions.InvalidPositionException;

import java.util.LinkedList;

public abstract class Map {

    public String name;
    public int mapId;

    public Coordinate[][] grid;
    public LinkedList<Coordinate> path;
    public String imageUrl;

    public void placeObject(int objectId, int X, int Y) throws AlreadyOccupiedException, InvalidPositionException {
        if (grid[X][Y].isOccupied()) {
            throw new AlreadyOccupiedException();
        } if ( X >= this.grid.length || Y >= this.grid[0].length || X < 0 || Y < 0 ) {
            throw new InvalidPositionException();
        } else {
            grid[X][Y].setOccupied(true);
            grid[X][Y].setObjectId(objectId);
        }
    }

    public void clearCoordinate() {
        for ( int i = 0; i < grid.length; i++ ) {
            for ( int j = 0; j < grid[0].length; j++ ) {
                if (grid[i][j].getObjectId() > 1) {
                    grid[i][j] = new Coordinate(0);
                }
            }
        }
    }

}
