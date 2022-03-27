package coms309.proj1.user;

import coms309.proj1.exception.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{

	private final static String USERNAME_NOT_FOUND_MSG = "User %s does not exist";
	private final static String EMAIL_NOT_FOUND_MSG = "User with email %s does not exist";
	private final static String BAD_CREDENTIALS_MSG = "Username %s does not exist";

	@Autowired
	private UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	public List<User> loadUsers() {
		logger.info("Entered into Service Layer");
		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Entered into Service Layer");
		Optional<User> result = userRepository.findByUsername(username);

		if (result.isEmpty()) {
			logger.warn("User [" + username + "] not found");
			throw new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND_MSG, username));
		}
		User user = result.get();
		logger.info("Retrieved " + user.toString() + " by username");
		return new UserDetailsImpl(user);
	}

	public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
		logger.info("Entered into Service Layer");
		Optional<User> result = userRepository.findByEmail(email);

		if (result.isEmpty()) {
			logger.warn(String.format(EMAIL_NOT_FOUND_MSG, email));
			throw new EmailNotFoundException(String.format(EMAIL_NOT_FOUND_MSG, email));
		}
		User user = result.get();
		logger.info("Retrieved " + user.toString() + " by email");
		return new UserDetailsImpl(user);
	}
}