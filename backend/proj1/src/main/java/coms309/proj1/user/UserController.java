package coms309.proj1.user;

import coms309.proj1.exception.ErrorResponse;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController // Tells Spring Boot that HTTP requests are handled here
public class UserController
{
	@Autowired
	UserService userService;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);


	@GetMapping(path = "/admin")
	public ResponseEntity<ErrorResponse> getAdminDetails() {
		logger.info("Entered into User Controller Layer");
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.ACCEPTED, "getAdminDetails"), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/user")
	public ResponseEntity<ErrorResponse> getCurrentUser() {
		logger.info("Entered into User Controller Layer");
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.ACCEPTED, "getCurrentUser"), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/users"})
	public List<User> getAllUsers() {
		logger.info("Entered into User Controller Layer");
		List<User> results = userService.loadUsers();
		logger.info("Records Fetched:" + results.size());
		return results;
	}
//	@GetMapping(value={"/user/verifyLoginByUsername/{username}/{password}"})
//	public boolean verifyUsername(@PathVariable String username, @PathVariable String password) {
//		logger.info("Entered into User Controller Layer");
//		return userService.verifyUserByUsername(username, password);
//	}

//	@GetMapping(value={"/user/verifyLoginByEmail/{email}/{password}"})
//	public boolean verifyEmail(@PathVariable String email, @PathVariable String password) {
//		logger.info("Entered into User Controller Layer");
//		return userService.verifyUserByEmail(email, password);
//	}

}
