package coms309.server.GameLogic.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class Entity {

    public int x, y;
    public int objectId;

    // Load from database
    public String name;
    public int range, damage, cooldown;

    public Entity(int objectId, int x, int y) {
        this.x = x; this.y = y;
        loadObject(objectId);
    }

    public void loadObject(int objectId) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(getClass().getClassLoader().getResource("towers.json").getFile()))
        {
            Object obj = jsonParser.parse(reader);
            JSONArray objects = (JSONArray) obj;
            for (int i = 0; i < objects.size(); i++) {
                JSONObject object = (JSONObject) objects.get(i);
                if ( (int) object.get("id") == objectId ) {
                    this.objectId = objectId;
                    this.name = (String) object.get("name");
                    this.range = (int) object.get("range");
                    this.cooldown = (int) object.get("cooldown");
                    this.damage = (int) object.get("damage");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getObjectId() {
        return this.objectId;
    }
}
