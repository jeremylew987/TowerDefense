package coms309.proj1;

import coms309.proj1.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
public class RootController {

	private final Logger logger = LoggerFactory.getLogger(RootController.class);

	@GetMapping
	public ResponseEntity<ErrorResponse> login() {
		logger.info("Entered into Root Controller Layer");
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.ACCEPTED, "Home Screen"), HttpStatus.ACCEPTED);
	}

}
