package coms309.server.GameLogic.Map;

import coms309.server.Server;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Tower {

    // CONSTRUCTOR
    private Point point;
    private int typeId;
    private int ownerId;

    // FROM FILE
    private String name;
    private int range, damage, cooldown;

    public Tower(Point point, int typeId, int ownerId) {
        this.point = point;
        this.ownerId = ownerId;
        try {
            this.loadType(typeId);
        } catch (IOException ex) {
        } catch (ParseException ex) {
            Server.logger.warning("Failed to parse objects.json!");
            Server.logger.warning("Failed to initialize new tower!");
        }
    }

    /**
     * Loads tower data from JSON File (towers.json)
     * @param typeId type of tower
     * @throws IOException Failed to open file
     * @throws ParseException Failed to parse file
     */
    public void loadType(int typeId) throws IOException, ParseException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("objects.json");
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(
                new InputStreamReader(inputStream, "UTF-8"));
        inputStream.close();
        JSONArray objects = (JSONArray) obj;
        boolean found = false;
        for (int i = 0; i < objects.size(); i++) {
            JSONObject object = (JSONObject) objects.get(i);
            if ( (long) object.get("id") == (long) typeId ) {
                this.typeId = typeId;
                this.name = (String) object.get("name");
                this.range = ((Long) object.get("range")).intValue();
                this.cooldown = ((Long) object.get("cooldown")).intValue();
                this.damage = ((Long) object.get("damage")).intValue();
                break;
            }
        }
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
