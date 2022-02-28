package coms309.proj1.exception;

public class InvalidEmailException extends RuntimeException
{
	public InvalidEmailException() {
		super("invalid email");
	}
	public InvalidEmailException(String message) {
		super(message);
	}
}
