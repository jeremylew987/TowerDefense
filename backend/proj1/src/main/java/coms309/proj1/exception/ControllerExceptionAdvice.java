package exception;

import exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This class contains custom HTTP code exception handlers.
 * The ResponseEntity, constructed with ErrorResponse, will
 * automatically be serialized in JSON and used as the message body.
 *
 * @author jminardi
 */
@ControllerAdvice
public class ControllerExceptionAdvice
{
	/**
	 * Helper method to convert a stack trace to a string
	 */
	private String getStackTrace(Exception e) {
		// convert stack trace to String
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		e.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	/**
	 * Handles null pointer exceptions
	 */
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ErrorResponse> handleInvalidEmailException(InvalidEmailException e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.BAD_REQUEST; // 400 (Will not process request due to client error)
		// convert stack trace to String
		String stackTrace = getStackTrace(e);

		return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), stackTrace), status);
	}

	/**
	 * Handles null pointer exceptions (Test)
	 */
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointerException(Exception e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.NOT_FOUND; // 404
		// convert stack trace to String
		String stackTrace = getStackTrace(e);

		return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), stackTrace), status);
	}

	/**
	 * Fallback exception handler for all exception types
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		// TODO: Custom logic

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
		// convert stack trace to String
		String stackTrace = getStackTrace(e);

		return new ResponseEntity<>(new ErrorResponse(status, e.getMessage(), stackTrace), status);
	}
}
