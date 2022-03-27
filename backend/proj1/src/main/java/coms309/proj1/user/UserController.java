package coms309.proj1.user;

import coms309.proj1.exception.GeneralResponse;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController // Tells Spring Boot that HTTP requests are handled here
public class UserController
{
	@Autowired
	UserService userService;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);


	@GetMapping(path = "/admin")
	public ResponseEntity<GeneralResponse> getAdminDetails(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user details", authentication.getPrincipal()), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/user")
	public ResponseEntity<GeneralResponse> getCurrentUser(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		logger.info(authentication.toString());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user details", authentication.getPrincipal()), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/users"})
	public ResponseEntity<GeneralResponse> getAllUsers() {
		logger.info("Entered into User Controller Layer");
		List<User> results = userService.loadUsers();
		logger.info("Records Fetched:" + results.size());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user details", results), HttpStatus.ACCEPTED);
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
