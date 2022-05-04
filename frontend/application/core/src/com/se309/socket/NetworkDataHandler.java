package com.se309.socket;

import com.se309.schema.DataObjectSchema;
import com.se309.tower.ResourceContext;

import java.io.IOException;

/**
 * Connects to the socket to the backend, and continually polls for data.
 * When data is found, it is parsed using ProtoBUF, and is sent to it's respective NetworkEventListener
 *
 * @author Gavin Tersteeg
 */
public class NetworkDataHandler extends Thread {

    private ResourceContext context;
    private SocketClient client;

    /**
     * Creates a new NetworkDataHandler class
     * @param client The socket client to receive data from
     * @param context Resource context
     */
    public NetworkDataHandler(SocketClient client, ResourceContext context) {
        this.context = context;
        this.client = client;
    }

    /**
     * Thread startup point for NetworkDataHandler
     */
    public void run() {
        while (true) {

            try {
                DataObjectSchema data =
                        DataObjectSchema.parseDelimitedFrom(client.getDataIn());


                System.out.println("Received: " + data.getDataCase());


            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
