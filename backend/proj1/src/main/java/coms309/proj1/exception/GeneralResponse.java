package coms309.proj1.exception;

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
public class GeneralResponse
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
	private Object data;


	public GeneralResponse()
	{
		timestamp = new Date();
	}

	public GeneralResponse(HttpStatus httpStatus, String message)
	{
		this();
		if (httpStatus != null) {
			this.code = httpStatus.value();
			this.status = httpStatus.name();
		}
		this.message = message;
	}

	public GeneralResponse(HttpStatus httpStatus, String message, Object data)
	{
		this(httpStatus, message);
		this.data = data;
	}

}
