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
        try {
            Message r = new Message(data.getMessage());
            System.out.println(r);
        } catch (NullPointerException ex) {
            System.out.println("Authentication Failed.");
        }
    }

    public void write(String data, String code) throws IOException {
        Message m = new Message(
                "ben",
                code,
                data
        );
        m.serialize().writeDelimitedTo(dataOut);
        dataOut.flush();
    }

    public void run() {
        try {
            while(true) {
                read();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
