package coms309.proj1.user;

import coms309.proj1.friend.FriendRelationship;
import org.springframework.beans.factory.annotation.*;
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

	@GetMapping(value={"/user", "/users"})
	public List<User> getAllUsers() {
		logger.info("Entered into User Controller Layer");
		List<User> results = userService.loadUsers();
		logger.info("Records Fetched:" + results.size());
		return results;
	}

	@GetMapping(value={"/user/verifyLoginByUsername/{username}/{password}"})
	public boolean verifyUsername(@PathVariable String username, @PathVariable String password) {
		logger.info("Entered into User Controller Layer");
		return userService.verifyUserByUsername(username, password);
	}

	@GetMapping(value={"/user/verifyLoginByEmail/{email}/{password}"})
	public boolean verifyEmail(@PathVariable String email, @PathVariable String password) {
		logger.info("Entered into User Controller Layer");
		return userService.verifyUserByEmail(email, password);
	}

	@GetMapping(value={"/user/addFriend/{owner}/{friend}"})
	public FriendRelationship addFriend(@PathVariable String owner, @PathVariable String friend) {
		logger.info("Entered into User Controller Layer");
		return userService.addFriend(owner, friend);
	}

}
