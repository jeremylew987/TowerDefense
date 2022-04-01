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
import java.util.Collection;

public class Map {

    public String name;
    public int mapId;
    public Coordinate[][] grid;

    public Map(int mapId) {
        loadMap(mapId);
    }

    public void loadMap(int mapId) {
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
            return;
        }
    }

    public void placeObject(int playerId, int objectId, int X, int Y) throws AlreadyOccupiedException, InvalidPositionException {
        if (grid[X][Y].getTower().getObjectId() < 2) {
            throw new AlreadyOccupiedException();
        } else {
            this.grid[X][Y] = new Coordinate(X, Y, objectId, playerId);
        }
    }

    public void clearCoordinate() {
    }

}
