package coms309.proj1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This class contains custom HTTP code exception handlers.
 * The ResponseEntity, constructed with ErrorResponse, will
 * automatically be serialized in JSON and used as the message body.
 */
@ControllerAdvice
public class ControllerExceptionAdvice
{
	/**
	 * Handles email not found exception
	 */
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<GeneralResponse> handleEmailNotFoundException(EmailNotFoundException e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.NOT_FOUND; // 404 ()
		return new ResponseEntity<>(new GeneralResponse(status, e.getMessage()), status);
	}

	/**
	 * Handles username not found exception
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<GeneralResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.NOT_FOUND; // 404 ()
		return new ResponseEntity<>(new GeneralResponse(status, e.getMessage()), status);
	}

	/**
	 * Handles invalid email addresses
	 */
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<GeneralResponse> handleInvalidEmailException(InvalidEmailException e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.BAD_REQUEST; // 400 (Will not process request due to client error)
		return new ResponseEntity<>(new GeneralResponse(status, e.getMessage()), status);
	}

	/**
	 * Handles null pointer exceptions (Test)
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<GeneralResponse> handleNullPointerException(Exception e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.NOT_FOUND; // 404
		return new ResponseEntity<>(new GeneralResponse(status, e.getMessage()), status);
	}

	/**
	 * Fallback exception handler for all exception types
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<GeneralResponse> handleException(Exception e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
		return new ResponseEntity<>(new GeneralResponse(status, e.getMessage()), status);
	}
}
