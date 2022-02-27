package coms309.proj1.user;

import coms309.proj1.registration.token.ConfirmationToken;
import coms309.proj1.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, UserDetailsPasswordService
{

    private final static String USERNAME_NOT_FOUND_MSG = "User %s does not exist";
    private final static String EMAIL_NOT_FOUND_MSG = "User with email %s does not exist";
    private final static String BAD_CREDENTIALS_MSG = "Username %s does not exist";


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

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
        logger.info("Retrieved " + result.toString() + " by username");
        return result.get();
    }

    public Optional<User> loadUserByEmail(String email) {
        logger.info("Entered into Service Layer");
        Optional<User> result = userRepository.findByEmail(email);
        logger.info("Retrieved " + result.toString() + " by email");
        return result;
    }


    public String registerUser(User user) {
        logger.info("Entered into Service Layer");
        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (userExists) {
            // Does this throw the exception or just log it?
            logger.info("Email is already registered to " + user.toString());
            new IllegalStateException("Email is already registered to " + user.toString());
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        logger.info ("Saved user to database");

        // TODO: Take token expire date from configuration
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        logger.info("Saved confirmation token");
        return token;
    }

    public boolean verifyUserByUsername(String username, String password) {
        logger.info("Entered into Service Layer");
        Optional<User> result = userRepository.findByUsername(username);
        if (result.isEmpty()) {
            logger.warn("Username [" + username + "] does not exist");
            return false;
        }

        logger.info ("Username [" + username + "] exists");

        if (!bCryptPasswordEncoder.matches(password, result.get().getPassword())) {
            logger.warn ("Password does not match user [" + username + "]");
            return false;
        }
        logger.info("Username [" + username + "] matches password");
        return true;
    }


    public boolean verifyUserByEmail(String email, String password) {
        logger.info("Entered into Service Layer");
        Optional<User> result = userRepository.findByEmail(email);
        if (result.isEmpty()) {
            logger.warn("User with email [" + email + "] does not exist");
            return false;
        }

        logger.info ("User with email [" + email + "] exists");

        if (!bCryptPasswordEncoder.matches(password, result.get().getPassword())) {
            logger.warn ("Password does not match email [" + email + "]");
            return false;
        }
        logger.info("User with email [" + email + "] matches password");
        return true;
    }

    public int enableUser(String email) {
        logger.info("Entered into Service Layer");
        return userRepository.enableUser(email);
    }


    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword)
    {
        logger.info("Entered into Service Layer\n");
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        ((User)user).setPassword(encodedPassword);
        logger.info ("Password set for " + ((User)user).toString());
        return user;
    }
}
