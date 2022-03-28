package coms309.proj1;

import coms309.proj1.exception.GeneralResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;

@RestController
@AllArgsConstructor
public class MainController
{

	@Autowired
	Environment environment;

	private final Logger logger = LoggerFactory.getLogger(MainController.class);

	@GetMapping(path = "/")
	public ResponseEntity<GeneralResponse> root_endpoint() {
		logger.info("Entered into Main Controller Layer");
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Root Screen"), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/home")
	public ResponseEntity<GeneralResponse> home_endpoint() {
		logger.info("Entered into Main Controller Layer");
		logger.info(environment.getProperty("java.rmi.server.hostname"));
		logger.info(environment.getProperty("java.rmi.server.hostname"));
		try {
			logger.info(InetAddress.getLocalHost().getHostAddress());
			logger.info(InetAddress.getLoopbackAddress().getHostAddress());
		}catch(Exception ignored) {

		}
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Home Screen"), HttpStatus.ACCEPTED);
	}

}
