package coms309.server.Demo;

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

            Client c = new Client(s);
            Thread t = new Thread(c);
            t.start();

            c.write("a5cc9232-b2e8-4851-a508-62b681a807c4", "AUTH");

            Scanner sc = new Scanner(System.in);
            while (true) {
                String input = sc.nextLine();
                c.write(input, "CHAT");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

