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
    }

    @Test
    void spawnEntityOnExisting() throws IOException, ParseException {
        Map m = new Map(0);
        Tower t1 = m.spawnEntity(1,new Point(10, 10), 2);
        Tower t2 = m.spawnEntity(1,new Point(10, 10), 2);
        assertNull(t2);
    }

    @Test
    void spawnEntityOutOfBounds() throws IOException, ParseException {
        Map m = new Map(0);
        Tower t1 = m.spawnEntity(1,new Point(-10, -10), 2);
        assertNull(t1);
    }

    @Test
    void calculateCollisionHitCase() throws IOException, ParseException {
        Map m = new Map(0);
        Tower t1 = m.spawnEntity(1,new Point(400, 200), 2);
        Tower t2 = m.spawnEntity(0,new Point(500, 200), 2);
        boolean collEvent1 = m.calculateCollision(new Point(400, 205), t1);
        boolean collEvent2 = m.calculateCollision(new Point(500, 204), t2);
        assertTrue(collEvent1);
        assertTrue(collEvent2);
    }

    @Test
    void calculateCollisionMissCase() throws IOException, ParseException {
        Map m = new Map(0);
        Tower t1 = m.spawnEntity(1,new Point(400, 200), 2);
        Tower t2 = m.spawnEntity(0,new Point(500, 200), 2);
        boolean collEvent1 = m.calculateCollision(new Point(400, 210), t1);
        boolean collEvent2 = m.calculateCollision(new Point(500, 205), t2);
        assertFalse(collEvent1);
        assertFalse(collEvent2);
    }
}