package coms309.server.GameLogic.Map;

import coms309.server.Schema.gameTick;
import coms309.server.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Map {

    private String name;
    public int mapId;

    private final int width = 800;
    private final int height = 600;
    /**
     * Path radius each point on the path
     * Used to calculate collision with path
     */
    private final int pathRadius = 1;

    /**
     * LinkedList to store path data for enemy
     */
    public LinkedList<Point> enemyPath;

    /**
     * ArrayList to store Tower information
     */
    private ArrayList<Tower> towerArray;

    /**
     * ArrayList to store enemy information
     */
    private ArrayList<Enemy> enemyArray;

    public Map(int mapId) throws IOException, ParseException {
        loadMap(mapId);
    }

    /**
     * Load map data from JSON File (maps.json)
     * @param mapId map ID to load
     * @throws IOException if file is not found
     * @throws ParseException json parse error
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
     * @param typeId type of new entity
     * @param point location of new entity
     * @return new entity created
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
     * Calculate the result of the game tick
     * @param t time
     * @param dt delta_time
     * @return gameTick protobuf object
     */
    public gameTick update(double t, double dt) {
        gameTick.Builder tickBuilder = gameTick.newBuilder();
        for (int i = 0; i < enemyArray.size(); i++) {
            Enemy e = enemyArray.get(i);
            // 1. Update enemy positions
            e.setPoint(enemyPath.get(e.getIterator() + e.getSpeed()));

            int damageTaken = 0;
            for (Tower tower: towerArray) {
                // 2. Calculate collisions
                if (calculateCollision(e.getPoint(), tower)) {
                    e.decreaseHealth(tower.getDamage());
                    damageTaken += tower.getDamage();
                }
                // 3. calculate if dead
                if (e.getHealth() <= 0) {
                    enemyArray.remove(i);
                    i--;
                    break;
                }
            }

            // create protobuf array if enemy not dead
            if (e.getHealth() > 0 && damageTaken > 0) {
                tickBuilder.addEnemyUpdate(
                        gameTick.EnemyUpdate.newBuilder()
                                .setEnemyId(e.getId())
                                .setHealth(e.getHealth())
                                .build()
                );
            }
        }
        return tickBuilder.build();
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

    /**
     * Return all towers placed on map
     * @return
     */
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
