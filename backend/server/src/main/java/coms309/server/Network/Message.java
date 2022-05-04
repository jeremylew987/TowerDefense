package coms309.server.Network;

import com.google.protobuf.InvalidProtocolBufferException;
import coms309.server.Schema.DataObjectSchema;
import coms309.server.Schema.MessageSchema;

public class Message{

	public String author;
	public String message;
	public String code;

	/**
	 * Create message from args
	 * @param author author of message
	 * @param code code of message
	 * @param message message string
	 */
	public Message(String author, String code, String message) {
		this.author = author;
		this.code = code;
		this.message = message;
	}

	/**
	 * Create message from protobuf object
	 * @param m protobuf
	 * @throws InvalidProtocolBufferException invalid object
	 */
	public Message(MessageSchema m) throws InvalidProtocolBufferException {
		author = m.getAuthor();
		code = m.getCode().toUpperCase();
		message = m.getMessage();
	}

	/**
	 * Serialize Message object to Protobuf object
	 * @return data object
	 */
	public DataObjectSchema serialize() {
		return  DataObjectSchema.newBuilder()
				.setMessage(
						MessageSchema.newBuilder()
								.setAuthor(this.author)
								.setCode(this.code)
								.setMessage(this.message)
								.build()
				).build();
	}

	@Override
	public String toString() {
		return "[" + code + "] <" + author + "> " + message;
	}
}