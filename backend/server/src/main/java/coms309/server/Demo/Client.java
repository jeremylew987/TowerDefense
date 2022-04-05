package coms309.server.Demo;

import coms309.server.Network.Message;
import coms309.server.Schema.DataObjectSchema;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
        try {
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() throws IOException {
        DataObjectSchema data =
                DataObjectSchema.parseDelimitedFrom(dataIn);

        Message r = new Message(data.getMessage());
        System.out.println(r.toString());
    }

    public void write(String data) throws IOException {
        Message m = new Message(
                "ben",
                "CHAT",
                data
        );
        m.serialize().writeDelimitedTo(dataOut);
        dataOut.flush();
    }

    public void authenticate() throws IOException {
        dataOut.writeChars("e31499d5-85bb-4520-bfc2-81b239c3f2f1\n");
        dataOut.flush();
    }

    public void run() {
        try {
            authenticate();
            while(true) {
                read();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
