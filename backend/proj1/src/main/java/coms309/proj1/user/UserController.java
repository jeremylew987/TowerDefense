package coms309.proj1.user;

import coms309.proj1.registration.token.ConfirmationTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
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

	@GetMapping(value={"/user/verifyUser"})
	public UserDTO verifyToken(@RequestParam("token") String token) {
		logger.info("Entered into User Controller Layer");
		return convertToDto(userService.verifyUserByToken(token));
	}

	private UserDTO convertToDto(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

}
