package coms309.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void testInitialization() {
        String[] args = {};
        assertDoesNotThrow(() -> Server.main(args));
    }

}