package coms309.server.GameLogic.Map;

import coms309.server.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class Tower {

    // CONSTRUCTOR
    private Point point;
    private int typeId;
    private int ownerId;

    // FROM FILE
    private String name;
    private int damage, cooldown;

    /**
     * Radius around the tower's origin that defines the attack range
     */
    private int range;

    /**
     * Radius around the tower's origin that defines the physical size of the tower
     * Static so it can be accessed without instantiation, but towers now can't be different sizes
     */
    public static int size = 1;

    public Tower(Point point, int typeId, int ownerId) {
        this.point.setLocation(point);
        this.ownerId = ownerId;
        try {
            this.loadType(typeId);
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Server.logger.warning("Could not find typeId in towers.json!");
        } catch (Exception ex) {
            Server.logger.warning("Failed to parse towers.json!");
        } finally {
            Server.logger.warning("Failed to initialize new tower!");
        }
    }

    /**
     * Loads tower data from JSON File (towers.json)
     * @param typeId
     * @throws IOException
     * @throws ParseException
     * @throws ClassNotFoundException
     */
    public void loadType(int typeId) throws Exception {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(getClass().getClassLoader().getResource("objects.json").getFile());
        Object obj = jsonParser.parse(reader);
        JSONArray objects = (JSONArray) obj;
        boolean found = false;
        for (int i = 0; i < objects.size(); i++) {
            JSONObject object = (JSONObject) objects.get(i);
            if ( (int) object.get("id") == typeId ) {
                this.typeId = typeId;
                this.name = (String) object.get("name");
                this.size = (int) object.get("size");
                this.range = (int) object.get("range");
                this.cooldown = (int) object.get("cooldown");
                this.damage = (int) object.get("damage");
                return;
            }
            throw new RuntimeException();
        }
        reader.close();
    }
    public int getTypeId() {
        return typeId;
    }

    public Point getPoint() {
        return this.point;
    }
    public void setPoint(Point point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getRange() {
        return range;
    }
    public void setRange(int range) {
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCooldown() {
        return cooldown;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
}
