package coms309.server.GameLogic.Map;

import coms309.server.GameLogic.Exceptions.*;
import coms309.server.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class Map {

    private String name;
    public int mapId;

    private final int width = 800;
    private final int height = 600;

    /**
     * LinkedList to store path data for enemy
     */
    public LinkedList<Point> enemyPath;

    /**
     * ArrayList to store Tower information
     */
    private ArrayList<Tower> towerArray;

    public Map(int mapId) throws IOException, ParseException {
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
        inputStream.close();
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
    public int getMapId() {
        return this.mapId;
    }

    /**
     * Create new Tower at Point of typeId.
     * Fails if tower already exists at point or is out of bounds.
     * @param typeId
     * @param point
     * @return
     */
    public Tower spawnEntity(int typeId, Point point, int ownerId) {

        // Look for existing tower at given point
        for ( Tower t: towerArray ) {
            if (t.getPoint().equals(point)) {
                Server.logger.warning("Could not create tower: Tower already exists at this point!");
                return null;
            }
        }

        double pX = point.getX();
        double pY = point.getY();

        // Check bounds of map
        if ( (pX >= 0) && (pX <= width) && (pY >= 0) && (pY <= height) ) {
            Server.logger.warning("Could not create tower: Out of bounds position!");
            return null;
        }

        Tower newTower = new Tower(point, typeId, ownerId);
        towerArray.add(newTower);
        return newTower;
    }

    /**
     * Calculate if Point is in the range of Tower
     * @param point
     * @param tower
     * @return
     */
    public boolean calculateCollision(Point point, Tower tower) {
        // Get tower x,y
        double tX = tower.getPoint().getX();
        double tY = tower.getPoint().getY();

        // Get point x,y
        double pX = point.getX();
        double pY = point.getY();

        return (Math.pow(pX - tX, 2) + Math.pow(pY - tY, 2)) < tower.getRange();
    }

    public ArrayList<Tower> getTowerArray() {
        return towerArray;
    }
    public void setTowerArray(ArrayList<Tower> towerArray) {
        this.towerArray = towerArray;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
