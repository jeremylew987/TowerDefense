package coms309.server.GameLogic.Map;

import coms309.server.GameLogic.Exceptions.*;
import coms309.server.GameLogic.GameState;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Schema.MessageSchema;
import coms309.server.Schema.TowerSchema;
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

import static coms309.server.GamePath.getPath;

public class Map {

    private String name;
    public int mapId;

    private final int width = 2200;
    private final int height = 1080;
    /**
     * Path radius each point on the path
     * Used to calculate collision with path
     */
    private final int pathRadius = 40;

    private GameState gameState;

    private long enemyTickCounter = 0;

    /**
     * LinkedList to store path data for enemy
     */
    public ArrayList<Point> enemyPath;

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

    public Map(int mapId, GameState gs) throws IOException, ParseException {
        gameState = gs;
        towerArray = new ArrayList<>();
        enemyArray = new ArrayList<>();
        enemyPath = getPath(2);
        enemyQueue = new PriorityQueue<Enemy>();
        loadMap(mapId);
        for (int i = 0; i < 1; i++) {
            enemyQueue.add(new Enemy(i, enemyPath.get(0), 1, 1));
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
            Server.logger.info("Failed to place tower [type:" + typeId + ",("  + point.x + ", " + point.y + ")].");
            return null;
        }
        Tower newTower = new Tower(point, typeId, ownerId);
        towerArray.add(newTower);
        try {
            gameState.server.getConnectionHandler().writeToAll(
                    DataObjectSchema.newBuilder()
                            .setTower(
                                    TowerSchema.newBuilder()
                                            .setX(point.x)
                                            .setY(point.y)
                                            .setOwnerId(ownerId)
                                            .setTypeId(typeId)
                                            .build()
                            ).build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        Server.logger.info("Successfully placed tower [type:" + typeId + ",("  + point.x + ", " + point.y + ").");
        return newTower;
    }

    /**
     * Calculate the result of the game tick
     * @param t time passed?
     * @param dt delta_time time since last tick
     * @return gameTick protobuf object
     */
    public DataObjectSchema update(double t, double dt) {
        gameTick.Builder tickBuilder = gameTick.newBuilder();

        // updateEnemyPositions(t, dt);
        for (int i = 0; i < enemyArray.size(); i++) {
            Enemy e = enemyArray.get(i);
            e.setIterator(e.getIterator()+e.getSpeed());
            // Enemy hit the end and inflicts damage
            if (e.getIterator() >= enemyPath.size() - 2) {
                gameState.setHealth(gameState.getHealth() - 1); // Hardcode 1 heart per balloon
                Server.logger.info("[EnemyID=" + e.getId() + "] inflicted 1 damage");
                // Create protobuf for enemy that hit the end and made us lose life
                tickBuilder.addEnemyUpdate(
                        gameTick.EnemyUpdate.newBuilder()
                                .setEnemyId(e.getId())
                                .setHealth(e.getHealth())
                                .setDamageInflicted(1) // Hardcode 1 heart per balloon
                                .build()
                );

                if (gameState.getHealth() <= 0) {
                    gameState.setStatus(5); // Game over
                    Server.logger.info("Game over");
                }
                enemyArray.remove(i);
                i--;
            }
        }

        Enemy e;
        for (int j = 0; j  < towerArray.size(); j++) {
            Tower tower = towerArray.get(j);
            for (int i = 0; i < enemyArray.size(); i++) {
                e = enemyArray.get(i);

                // Decrement cooldown
                if (tower.getCooldown() > 0) {
                    tower.setCooldown(tower.getCooldown() - 1);
                }

                // Check if tower can attack this balloon
                if (tower.getCooldown() <= 0 && isAttackCollision(enemyPath.get(e.getIterator()), tower)) {
                    tower.setCooldown(tower.getSpeed());

                    e.decreaseHealth(tower.getDamage());

                    // Dead: Remove from array. Protobuf will send health <= 0
                    if (e.getHealth() <= 0) {
                        enemyArray.remove(i);
                        i--;
                        Server.logger.info("[EnemyID=" + e.getId() + "] eliminated by [TowerID=" + j + "].");
                    }
                    tickBuilder.addEnemyUpdate(
                            gameTick.EnemyUpdate.newBuilder()
                                    .setEnemyId(e.getId())
                                    .setHealth(e.getHealth())
                                    .setAttackedBy(j + 1) // TODO: hack. pls make tower store uid
                                    .build()
                    );
                    break; // Because one tower can only attack one enemy at a time

                }
            }
        }

        // Try to load new enemy every 50 ticks
        enemyTickCounter++;
        if ((!enemyQueue.isEmpty()) && enemyTickCounter >= 50 ) {
            Server.logger.info("Spawned enemy");
            enemyTickCounter = 0;
            Enemy enemy = enemyQueue.remove();
            enemyArray.add(enemy);
            tickBuilder.addEnemyUpdate(
                    gameTick.EnemyUpdate.newBuilder()
                            .setEnemyId(enemy.getId())
                            .setHealth(enemy.getHealth())
                            .build()
            );
        }


        return DataObjectSchema.newBuilder().setTick(tickBuilder.build()).build();
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

        boolean result = Math.sqrt((Math.pow(pX - tX, 2) + Math.pow(pY - tY, 2))) < tower.getRange();

        if (result) Server.logger.info("Collision detected Tower=(" + tX +", " + tY + "), Enemy=(" + pX + ", " + pY + ").");
        return result;
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
            Server.logger.info("Invalid Tower Location: Out of Bounds (" + location.getX() + "," + location.getY() + ").");
            return false;
        }
        // Check collisions against all other towers
        for (Tower t : this.towerArray) {
            if (isTowerCollision(location, t.getPoint())) {
                Server.logger.info("Invalid Tower Location: Collision with Tower=(" + t.getPoint().getX() + "," + t.getPoint().getY() + ") Location=(" + location.getX() + "," + location.getY() + ").");
                return false;
            }
        }
        // Check collisions again all path points
        for (Point p : this.enemyPath) {
            if (isPathCollision(location, p)) {
                Server.logger.info("Invalid Tower Location: Collision with path (" + location.getX() + "," + location.getY() + ").");
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
