package com.se309.tower;

import com.se309.render.TextElement;
import com.se309.schema.DataObjectSchema;
import com.se309.socket.SocketClient;

import java.io.IOException;

public class MessageReader extends Thread {

    private TextElement output;
    private SocketClient client;

    public MessageReader(SocketClient client, TextElement output) {
        this.output = output;
        this.client = client;
    }

    public void run() {
        while (true) {

            try {
                DataObjectSchema data =
                        DataObjectSchema.parseDelimitedFrom(client.getDataIn());

                if (data.hasMessage()) {
                    output.setText(output.getText() + "" + data.getMessage().getMessage() + "\n");
                }

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
