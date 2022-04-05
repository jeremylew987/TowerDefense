package coms309.server.Network;

import coms309.server.Schema.MessageSchema;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Message{

    public LocalDateTime date;
    public String author;
    public String message;
    public String code;

    public Message(String author, String code, String message) {
        this.date = LocalDateTime.now();
        this.author = author;
        this.code = code;
        this.message = message;
    }

    public byte[] serialize() {
        MessageSchema m =
                MessageSchema.newBuilder()
                        .setDate(this.date.toEpochSecond(ZoneOffset.ofHours(0)))
                        .setAuthor(this.author)
                        .setCode(this.code)
                        .setMessage(this.message)
                        .build();
        return m.toByteArray();
    }
}
