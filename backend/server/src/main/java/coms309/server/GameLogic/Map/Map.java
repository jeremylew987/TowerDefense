package coms309.server.GameLogic.Map;

import coms309.server.GameLogic.Exceptions.*;
import coms309.server.Schema.gameTick;
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
import java.util.PriorityQueue;
import java.util.Queue;

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
     * ArrayList to active store enemy information
     */
    private ArrayList<Enemy> enemyArray;
    /**
     * Queue to store waiting enemies
     */
    private PriorityQueue<Enemy> enemyQueue;

    public Map(int mapId) throws IOException, ParseException {
        towerArray = new ArrayList<>();
        enemyArray = new ArrayList<>();
        enemyPath = new LinkedList<>();
        enemyQueue = new PriorityQueue<Enemy>();
        loadMap(mapId);
        for (int i = 0; i < 30; i++) {
            enemyQueue.add(new Enemy(1, enemyPath.get(0), 1, 1));
        }
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
     * @param ownerId Id of user who placed tower
     * @return Tower object if successful, null otherwise
     */
    public Tower placeTower(int typeId, Point point, int ownerId) {

        if (!isValidTowerLocation(point)) {
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
        // Try to load new enemy
        if (!enemyQueue.isEmpty()) enemyArray.add(enemyQueue.remove());

        gameTick.Builder tickBuilder = gameTick.newBuilder();

        updateEnemyPositions(t, dt);

        Enemy e;
        for (Tower tower : towerArray) {
            for (int i = 0; i < enemyArray.size(); i++) {
                e = enemyArray.get(i);

                // Decrement cooldown
                if (tower.getCooldown() > 0) {
                    tower.setCooldown(tower.getCooldown() - 1);
                }

                // Check if tower can attack this balloon
                if (tower.getCooldown() <= 0 && isAttackCollision(e.getPoint(), tower)) {
                    tower.setCooldown(tower.getSpeed());

                    e.decreaseHealth(tower.getDamage());

                    // If dead, remove
                    if (e.getHealth() <= 0) {
                        enemyArray.remove(i);
                        i--;
                    }
                    else {
                        // create protobuf array for alive but damaged enemies
                        tickBuilder.addEnemyUpdate(
                                gameTick.EnemyUpdate.newBuilder()
                                        .setEnemyId(e.getId())
                                        .setHealth(e.getHealth())
                                        .build()
                        );
                    }
                    break; // Because one tower can only attack one enemy at a time
                }
            }
        }
        return tickBuilder.build();
    }

    /**
     * Update enemy positions. Params are placeholder or unneeded? IDK, todo
     * @param t
     * @param dt
     */
    public void updateEnemyPositions(double t, double dt) {
        for (Enemy e : enemyArray) {
            e.setPoint(enemyPath.get(e.getIterator() + e.getSpeed()));
        }
    }


    /**
     * Calculate if Point is in the range of Tower
     * @param point Point of target
     * @param tower Point of tower
     * @return whether there is a collision
     */
    public boolean isAttackCollision(Point point, Tower tower) {
        // Get tower x,y
        double tX = tower.getPoint().getX();
        double tY = tower.getPoint().getY();

        // Get point x,y
        double pX = point.getX();
        double pY = point.getY();

        return (Math.pow(pX - tX, 2) + Math.pow(pY - tY, 2)) < tower.getRange();
    }

    /**
     * Checks if a tower can be placed at a point
     * Assumes tower is not already in the tower array
     *
     * @param location location to place tower
     * @return true if valid location
     */
    public boolean isValidTowerLocation(Point location) {
        // Uses static size of Tower class for calculation

        // Check bounds
        if (isOutOfBounds(location)) {
            Server.logger.info("Invalid Tower Location: Out of bounds");
            return false;
        }
        // Check collisions against all other towers
        for (Tower t : this.towerArray) {
            if (isTowerCollision(location, t.getPoint())) {
                Server.logger.info("Invalid Tower Location: Collision with tower");
                return false;
            }
        }
        // Check collisions again all path points
        for (Point p : this.enemyPath) {
            if (isPathCollision(location, p)) {
                Server.logger.info("Invalid Tower Location: Collision with path");
                return false;
            }
        }
        return true;
    }

    /**
     * Calculate collision between two towers when placing
     *
     * @param p1 tower 1 position
     * @param p2 tower 2 position
     * @return true if collision
     */
    public boolean isTowerCollision(Point p1, Point p2) {
        // Find distance between tower origins
        double distX = p1.x - p2.x;
        double distY = p1.y - p2.y;
        double distZ = Math.sqrt((distX * distX) + (distY * distY));

        // check if its less than the sum of radiuses
        return distZ < (Tower.size + Tower.size);
    }

    /**
     * Calculate collision between tower and a path when placing
     *
     * @param p1 tower pos
     * @param p2 path pos
     * @return true if collision
     */
    public boolean isPathCollision(Point p1, Point p2) {
        // Find distance between tower origin and path origin
        double distX = p1.x - p2.x;
        double distY = p1.y - p2.y;
        double distZ = Math.sqrt((distX * distX) + (distY * distY));

        // check if its less than the sum of radiuses
        return distZ < (Tower.size + pathRadius);
    }

    /**
     * Calculate if point is out of bounds
     * @param point point to check
     * @return true if out of bounds
     */
    public boolean isOutOfBounds(Point point) {
        if (point.x < 0 || point.x > width) {
            return true;
        }
        if (point.y < 0 || point.y > height) {
            return true;
        }
        return false;
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
