package coms309.server.GameLogic.Map;

import coms309.server.GameLogic.GameState;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void calculateCollisionHitCase() throws IOException, ParseException {
        Map m = new Map(0, null);
        Tower t = new Tower(new Point(400, 200), 2, 0);
        boolean collEvent = m.isAttackCollision(new Point(400, 200 + (t.getRange() / 2)), t);
        assertTrue(collEvent);
    }

    @Test
    void calculateCollisionMissCase() throws IOException, ParseException {
        Map m = new Map(0, null);
        Tower t = new Tower(new Point(400, 200), 2, 0);
        boolean collEvent = m.isAttackCollision(new Point(500, 200 + t.getRange()), t);
        assertFalse(collEvent);
    }

    @Test
    void testPlaceOutOfBounds() throws IOException, ParseException {
        Map m = new Map(0, null);
        boolean validPlacement = m.isValidTowerLocation(new Point(3000, 600));
        assertFalse(validPlacement);
    }

    @Test
    void testPlaceOnPath() throws IOException, ParseException {
        Map m = new Map(0, null);
        boolean validPlacement = m.isValidTowerLocation(new Point(869, 997));
        assertFalse(validPlacement);
    }

    @Test
    void testValidPlace() throws IOException, ParseException {
        Map m = new Map(0, null);
        boolean validPlacement = m.isValidTowerLocation(new Point(300, 400));
        assertTrue(validPlacement);
    }

    @Test
    void testTrueTowerCollision() throws IOException, ParseException {
        Map m = new Map(0, null);
        boolean validPlacement = m.isTowerCollision(
                new Point(300, 400),
                new Point(300, 400 + Tower.size));
        assertTrue(validPlacement);
    }

    @Test
    void testFalseTowerCollision() throws IOException, ParseException {
        Map m = new Map(0, null);
        boolean validPlacement = m.isTowerCollision(
                new Point(300, 400),
                new Point(300, 401 + Tower.size));
        assertFalse(validPlacement);
    }
}