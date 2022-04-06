package coms309.server.GameLogic.Map;

import coms309.server.GameLogic.Exceptions.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

import static coms309.server.GameLogic.Map.Tile.TileType.*;

public class Map {

    public String name;
    public int mapId;
    public Tile[][] grid;

    public Map(int mapId) throws Exception {
        loadMap(mapId);
    }

    /**
     * Load map data from JSON File (maps.json)
     * @param mapId
     * @throws Exception
     */
    public void loadMap(int mapId) throws IOException, ParseException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("maps.json");
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(
                new InputStreamReader(inputStream, "UTF-8"));
        JSONArray maps = (JSONArray) obj;
        for (int i = 0; i < maps.size(); i++) {
            JSONObject map = (JSONObject) maps.get(i);
            if ( (long) map.get("id") == (long) mapId ) {
                this.mapId = mapId;
                this.name = (String) map.get("name");
                break;
            }
        }
    }

    /**
     * Create entity at Tile[X][Y] of objectType=[objectId]
     * @param objectId
     * @param X
     * @param Y
     * @throws Exception
     */
    public void spawnEntity(int objectId, int X, int Y) throws Exception {
        if (grid[X][Y].getTileType() != RESTRICTED || grid[X][Y].getTileType() != PATH ) {
            throw new AlreadyOccupiedException();
        } else {
            this.grid[X][Y].createEntity(objectId);
        }
    }

    /**
     * Remove entities from all tiles
     */
    public void clearMap() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j].setEntity(null);
            }
        }
    }

    public int getMapId() {
        return this.mapId;
    }
    public String getName() {
        return name;
    }

}
