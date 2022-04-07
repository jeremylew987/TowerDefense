package com.se309.socket;

import com.google.protobuf.InvalidProtocolBufferException;
import com.se309.schema.DataObjectSchema;
import com.se309.schema.MessageSchema;

public class Message{

    public String author;
    public String message;
    public String code;

    // Create from args
    public Message(String author, String code, String message) {
        this.author = author;
        this.code = code;
        this.message = message;
    }

    // Read from serialized object
    public Message(MessageSchema m) throws InvalidProtocolBufferException {
        author = m.getAuthor();
        code = m.getCode();
        message = m.getMessage();
    }

    public DataObjectSchema serialize() {
        DataObjectSchema d =
                DataObjectSchema.newBuilder()
                        .setMessage(
                                MessageSchema.newBuilder()
                                        .setAuthor(this.author)
                                        .setCode(this.code)
                                        .setMessage(this.message)
                                        .build()
                        ).build();
        return d;
    }

    @Override
    public String toString() {
        return "[" + code + "] <" + author + "> " + message;
    }
}
