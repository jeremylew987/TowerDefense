package com.jminardi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class UserController
{
	@Autowired
	UserRepository usersRepository;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);


	@RequestMapping(method = RequestMethod.POST, path = "/users/new")
	public String saveUser(Users user) {
		usersRepository.save(user);
		return "New User "+ user.getUsername() + " Saved";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<Users> getAllUsers() {
		logger.info("Entered into Controller Layer");
		List<Users> results = usersRepository.findAll();
		logger.info("Number of Records Fetched:" + results.size());
		return results;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
	public Collection<Users> findUserById(@PathVariable("userId") int id) {
		logger.info("Entered into Controller Layer");
		Collection<Users> results = usersRepository.findById(id);
		return results;
	}

}
