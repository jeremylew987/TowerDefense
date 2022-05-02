package coms309.server.GameLogic.Map;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void loadMap() throws IOException, ParseException {
        Map m = new Map(0);
        assertEquals(0, m.getMapId());
    }

    @Test
    void spawnEntityValid() throws IOException, ParseException {
        Map m = new Map(0);
        Tower t1 = m.spawnEntity(1,new Point(10, 10), 2);
        assertEquals(1, t1.getTypeId());
        assertEquals(2, t1.getOwnerId());
        assertEquals(new Point(10, 10), t1.getPoint());
        Tower t2 = m.spawnEntity(1,new Point(10, 10), 2);
        assertEquals(null, t2);
    }

    @Test
    void spawnEntityOnExisting() throws IOException, ParseException {
        Map m = new Map(0);
        Tower t1 = m.spawnEntity(1,new Point(10, 10), 2);
        Tower t2 = m.spawnEntity(1,new Point(10, 10), 2);
        assertEquals(null, t2);
    }

    @Test
    void spawnEntityOutOfBounds() throws IOException, ParseException {
        Map m = new Map(0);
        Tower t1 = m.spawnEntity(1,new Point(-10, -10), 2);
        assertEquals(null, t1);
    }

    @Test
    void update() {
    }

    @Test
    void calculateCollision() {
    }
}