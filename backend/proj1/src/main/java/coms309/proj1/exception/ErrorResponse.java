package exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


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

	private int code;
	private String status;
	private String message;
	private String stackTrace;
	private Object data;

	public ErrorResponse()
	{
		timestamp = new Date();
	}

	public ErrorResponse(String message)
	{
		this();
		this.code = HttpStatus.NOT_FOUND.value();
		this.status = HttpStatus.NOT_FOUND.name();
		this.message = message;
	}

	public ErrorResponse(HttpStatus httpStatus, String message)
	{
		this();

		this.code = httpStatus.value();
		this.status = httpStatus.name();
		this.message = message;
	}

	public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace)
	{
		this(httpStatus, message);
		this.stackTrace = stackTrace;
	}
	public ErrorResponse(HttpStatus httpStatus, String message, String stackTrace, Object data)
	{
		this(httpStatus, message, stackTrace);
		this.data = data;
	}
}
