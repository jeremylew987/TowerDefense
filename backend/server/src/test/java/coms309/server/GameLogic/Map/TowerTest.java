package coms309.server.GameLogic.Map;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class TowerTest {

    @Test
    void loadType() {
        Tower t = new Tower(new Point(10,10),0, 0);
        assertEquals("Melee", t.getName());
        assertEquals(20, t.getRange());
        assertEquals(1, t.getDamage());
        assertEquals(20, t.getCooldown());
        assertEquals(0, t.getTypeId());
    }
}