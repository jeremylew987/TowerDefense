package coms309.proj1.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super("Invalid Token");
    }
    public InvalidTokenException(String message) {
        super(message);
    }
}
