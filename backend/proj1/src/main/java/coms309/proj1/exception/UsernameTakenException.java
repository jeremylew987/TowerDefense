package coms309.proj1.exception;

public class UsernameTakenException extends RuntimeException
{
	public UsernameTakenException() {
		super("Username already taken");
	}
	public UsernameTakenException(String message) {
		super(message);
	}
}
