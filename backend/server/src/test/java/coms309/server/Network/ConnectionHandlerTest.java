package coms309.server.Network;

import coms309.server.Demo.Client;
import coms309.server.GameLogic.Player;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionHandlerTest {

    @Test
    void writeToAll() throws IOException {
        Server server = new Server();
        server.getGameThread().start();

        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 25565);
        socket.connect(socketAddress);
        Client c = new Client(socket);
        c.writeMessage("1cb8af81-92d6-4abc-baf6-8348529577ca", "AUTH");
        DataObjectSchema.parseDelimitedFrom(c.dataIn);

        server.getConnectionHandler().writeToAll(
                new Message("JUNIT", "TEST", "TESTING").serialize());

        DataObjectSchema data =
                DataObjectSchema.parseDelimitedFrom(c.dataIn);

        assertEquals("JUNIT", data.getMessage().getAuthor());
        assertEquals("TEST", data.getMessage().getCode());
        assertEquals("TESTING", data.getMessage().getMessage());
    }

    @Test
    void getPlayerById() throws IOException {
        Server server = new Server();
        server.getGameThread().start();

        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 25565);
        socket.connect(socketAddress);
        Client c = new Client(socket);
        c.writeMessage("1cb8af81-92d6-4abc-baf6-8348529577ca", "AUTH");
        DataObjectSchema data =
                DataObjectSchema.parseDelimitedFrom(c.dataIn);

        Player pl = server.getConnectionHandler().getPlayerById(0);
        assertEquals("benTest1", pl.getUsername());
    }

    @Test
    void testValidation() throws IOException {
        Server server = new Server();
        server.getGameThread().start();

        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress("localhost", 25565);
        socket.connect(socketAddress);
        Client c = new Client(socket);

        c.writeMessage("1cb8af81-92d6-4abc-baf6-8348529577ca", "AUTH");

        DataObjectSchema data =
                DataObjectSchema.parseDelimitedFrom(c.dataIn);
        assertEquals("Server", data.getMessage().getAuthor());
        assertEquals("AUTH", data.getMessage().getCode());
        assertEquals("SUCCESS", data.getMessage().getMessage());
    }
}