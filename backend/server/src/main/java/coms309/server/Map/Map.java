package coms309.server.Map;

import coms309.server.Exceptions.AlreadyOccupiedException;
import coms309.server.Exceptions.InvalidPositionException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Map {

    public String name;
    public int mapId;
    public Coordinate[][] grid;
    public LinkedList<Coordinate> path;
    public String imageUrl;

    public void loadMap(String s, int mapId) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("maps.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray maps = (JSONArray) obj;
            maps.forEach( map -> parseDataAndLoad( (JSONObject) map, mapId ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void parseDataAndLoad(JSONObject map, int mip) {
        JSONObject mapObject = (JSONObject) map.get("map");
        int mapId = (int) map.get("id");
        if (mapId == mip) {
            this.mapId = mapId;
            this.name = (String) map.get("name");
            this.imageUrl = (String) map.get("imageUrl");
            return;
        }
    }

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
