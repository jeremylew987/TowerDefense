package coms309.proj1.exception;

public class InvalidEmailException extends RuntimeException
{
	public InvalidEmailException() {
		super();
	}
	public InvalidEmailException(String message) {
		super(message);
	}
}
