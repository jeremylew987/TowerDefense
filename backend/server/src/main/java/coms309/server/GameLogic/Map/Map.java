package coms309.server.GameLogic.Map;

import coms309.server.GameLogic.Exceptions.AlreadyOccupiedException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class Map {

    public String name;
    public int mapId;
    public Entity[][] grid;

    public Map(int mapId) {
        loadMap(mapId);
    }

    /*
    Load map data from JSON file
     */
    public void loadMap(int mapId) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(getClass().getClassLoader().getResource("maps.json").getFile()))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray maps = (JSONArray) obj;
            for (int i = 0; i < maps.size(); i++) {
                JSONObject map = (JSONObject) maps.get(i);
                if ( (long) map.get("id") == (long) mapId ) {
                    this.mapId = mapId;
                    this.name = (String) map.get("name");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Create new object at (X, Y) of type:objectId.
     */
    public void setEntityAtPoint(int objectId, int X, int Y) throws AlreadyOccupiedException {
        if (grid[X][Y].getObjectId() < 2) {
            throw new AlreadyOccupiedException();
        } else {
            this.grid[X][Y] = new Entity(objectId, X, Y);
        }
    }

    /*
    Set each coordinate to empty cell.
     */
    public void clearMap() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = new Entity(0, i, j);
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
