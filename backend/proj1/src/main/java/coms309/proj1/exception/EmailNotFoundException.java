package coms309.proj1.exception;

public class EmailNotFoundException extends RuntimeException
{
	public EmailNotFoundException() {
		super("email not registered");
	}
	public EmailNotFoundException(String message) {
		super(message);
	}
}
