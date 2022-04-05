package coms309.server;


import coms309.server.Network.Message;
import coms309.server.Schema.DataObjectSchema;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        try {
            Socket s = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("localhost", 25565);
            s.connect(socketAddress);
            DataInputStream dataIn = new DataInputStream(s.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());

            dataOut.writeChars("e31499d5-85bb-4520-bfc2-81b239c3f2f1\n");
            dataOut.flush();

            DataObjectSchema objType =
                    DataObjectSchema.parseDelimitedFrom(dataIn);

            Scanner sc = new Scanner(System.in);
            while ( true ) {
                Message m = new Message(
                        "ben",
                        "CHAT",
                        sc.nextLine()
                );
                m.serialize().writeDelimitedTo(dataOut);
                dataOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
