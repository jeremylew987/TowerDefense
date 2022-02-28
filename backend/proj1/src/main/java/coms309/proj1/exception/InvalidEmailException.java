package coms309.proj1.exception;

public class InvalidEmailException extends RuntimeException
{
	public InvalidEmailException() {
		super("Email is not valid");
	}
	public InvalidEmailException(String message) {
		super(message);
	}
}
