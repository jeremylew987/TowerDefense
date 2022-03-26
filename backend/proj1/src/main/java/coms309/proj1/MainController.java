package coms309.proj1;

import coms309.proj1.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MainController
{

	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	@GetMapping(path = "/")
	public ResponseEntity<ErrorResponse> root_endpoint() {
		logger.info("Entered into Main Controller Layer");
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.ACCEPTED, "Root Screen"), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/home")
	public ResponseEntity<ErrorResponse> home_endpoint() {
		logger.info("Entered into Main Controller Layer");
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.ACCEPTED, "Home Screen2"), HttpStatus.ACCEPTED);
	}

}
