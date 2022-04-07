package coms309.proj1.user;

import coms309.proj1.friend.Friendship;
import coms309.proj1.exception.GeneralResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController // Tells Spring Boot that HTTP requests are handled here
public class UserController
{
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

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
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all users", results), HttpStatus.ACCEPTED);
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

	@GetMapping(value={"/user/friends/add/{friend}"})
	public ResponseEntity<GeneralResponse> addFriend(Authentication authentication, @PathVariable String friend) {
		logger.info("Entered into User Controller Layer");
		Friendship relationship =  userService.addFriend(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), friend);
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Added friend", relationship), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/user/friends/remove/{friend}"})
	public ResponseEntity<GeneralResponse> deleteFriend(Authentication authentication, @PathVariable String friend) {
		logger.info("Entered into User Controller Layer");
		Friendship relationship =  userService.removeFriend(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), friend);
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Deleted friend", relationship), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/user/friends"})
	public ResponseEntity<GeneralResponse> getFriends(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		List<User> friends =  userService.getFriends(((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all friends", friends), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/user/verifyUser"})
	public UserDTO verifyToken(@RequestParam("token") String token) {
		logger.info("Entered into User Controller Layer");
		UserDTO u;
		try {
			u = modelMapper.map(userService.verifyUserByToken(token), UserDTO.class);
		} catch (RuntimeException ex) {
			u = null;
		}
		return u;
	}

}
