package coms309.server.Demo;


import com.google.protobuf.InvalidProtocolBufferException;
import coms309.server.Network.Message;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Schema.GamestateSchema;
import org.junit.internal.runners.statements.RunAfters;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;
import java.util.logging.Level;

public class Demo {
    public static void main(String[] args) {
        try {
            Socket s = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("localhost", 25565);
            s.connect(socketAddress);

            Client c = new Client(s);
            Thread t = new Thread(c);
            t.start();

            Scanner sc = new Scanner(System.in);
            while (true) {
                String input = sc.nextLine();
                c.write(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

