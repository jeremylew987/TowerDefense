package coms309.proj1.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;


/**
 * This class contains data for a custom exception response
 * to be serialzed into JSON format by ExControllerAdvice.
 *
 * @author jminardi
 */
@Getter
@Setter
public class ErrorResponse
{
	// Custom timestamp format
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date timestamp;



	/**
	 * The HTTP code associated with response
	 */
	private int code;

	/**
	 * Status name associated with the response
	 */
	private String status;


	private String message;

	/**
	 * Optional object to provide a more verbose exception
	 */
	//private Object data;

	/**
	 * Determines whether to print the stack trace
	 * int http errors or not.
	 */
	private static boolean useStackTrace = false;
	private String stackTrace;

	public ErrorResponse()
	{
		timestamp = new Date();
	}

	public ErrorResponse(HttpStatus httpStatus, String message)
	{
		this();
		if (httpStatus != null) {
			this.code = httpStatus.value();
			this.status = httpStatus.name();
		}
		this.message = message;
	}

	public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace)
	{
		this(httpStatus, message);
		this.stackTrace = stackTrace;
	}
	
//	public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace, Object data)
//	{
//		this(httpStatus, message, stackTrace);
//		this.data = data;
//	}

	public static void setUseStackTrace(boolean v) {
		useStackTrace = false;
	}
	public static boolean getUseStackTrace() {
		return useStackTrace;
	}
}
