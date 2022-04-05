package coms309.server.Network;

import coms309.server.Schema.MessageSchema;

public class Message{

    public String author;
    public String message;
    public String code;

    public Message(String author, String code, String message) {
        this.author = author;
        this.code = code;
        this.message = message;
    }

    public byte[] serialize() {
        MessageSchema m =
                MessageSchema.newBuilder()
                        .setAuthor(this.author)
                        .setCode(this.code)
                        .setMessage(this.message)
                        .build();
        return m.toByteArray();
    }
}
