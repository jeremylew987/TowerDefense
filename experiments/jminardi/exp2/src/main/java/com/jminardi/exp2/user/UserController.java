package com.jminardi.exp2.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class UserController
{
	@Autowired
	UserRepository usersRepository;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);


	@PostMapping("users/new")
	public String saveUser(Users user) {
		usersRepository.save(user);
		return "New User "+ user.getUsername() + " Saved";
	}

	@GetMapping("/users")
	public List<Users> getAllUsers() {
		logger.info("Entered into Controller Layer");
		List<Users> results = usersRepository.findAll();
		logger.info("Number of Records Fetched:" + results.size());
		return results;
	}

	@GetMapping("/users/{userId}")
	public Collection<Users> findUserById(@PathVariable("userId") int id) {
		logger.info("Entered into Controller Layer");
		Collection<Users> results = usersRepository.findById(id);
		return results;
	}

}
