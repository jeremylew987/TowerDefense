package coms309.proj1.user;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController // Tells Spring Boot that HTTP requests are handled here
public class UserController
{
	@Autowired
	UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/users")
	public List<User> getAllUsers() {
		logger.info("Entered into Controller Layer");
		List<User> results = userRepository.findAll();
		logger.info("Records Fetched:" + results.size());
		return results;
	}

}
