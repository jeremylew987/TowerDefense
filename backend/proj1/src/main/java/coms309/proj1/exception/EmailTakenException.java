package coms309.proj1.exception;

public class EmailTakenException extends RuntimeException
{
	public EmailTakenException() {
		super("Email is already registered");
	}
	public EmailTakenException(String message) {
		super(message);
	}
}
