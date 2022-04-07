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

            c.writeMessage("1cb8af81-92d6-4abc-baf6-8348529577ca", "AUTH");

            Scanner sc = new Scanner(System.in);
            while (true) {
                String input = sc.nextLine();

                //use commands
                if (input.charAt(0) == '/') {
                    String[] cArg = input.split(" ");
                    switch (cArg[0]) {
                        case "/map":
                            c.writeNewMap(Integer.parseInt(cArg[1]));
                            break;
                    }
                } else {
                    c.writeMessage(input, "CHAT");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

