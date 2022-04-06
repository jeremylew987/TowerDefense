package coms309.server.GameLogic.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Entity {

    private Tile tile;
    private int objectId;
    private String name;
    private int range, damage, cooldown;

    public Entity(Tile tile, int objectId) throws Exception {
        this.tile = tile;
        loadObject(objectId);
    }

    /**
     * Loads object data from JSON File (objects.json)
     * @param objectId
     * @throws IOException
     * @throws ParseException
     * @throws ClassNotFoundException
     */
    public void loadObject(int objectId) throws Exception {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(getClass().getClassLoader().getResource("objects.json").getFile());
        Object obj = jsonParser.parse(reader);
        JSONArray objects = (JSONArray) obj;
        boolean found = false;
        for (int i = 0; i < objects.size(); i++) {
            JSONObject object = (JSONObject) objects.get(i);
            if ( (int) object.get("id") == objectId ) {
                this.objectId = objectId;
                this.name = (String) object.get("name");
                this.range = (int) object.get("range");
                this.cooldown = (int) object.get("cooldown");
                this.damage = (int) object.get("damage");
                return;
            }
            throw new ClassNotFoundException();
        }
    }

    public Tile getTile() {
        return this.tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getObjectId() {
        return objectId;
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
}
