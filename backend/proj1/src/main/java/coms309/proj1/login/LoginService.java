package coms309.proj1.login;

import coms309.proj1.exception.ErrorResponse;
import coms309.proj1.exception.IncorrectPasswordException;
import coms309.proj1.registration.RegistrationService;
import coms309.proj1.user.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;

@Service
@AllArgsConstructor
public class LoginService {

    @Autowired
    private UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    //TODO: Make a general response instead of error response
    public ResponseEntity<ErrorResponse> login(LoginRequest request) {
        logger.info("Entered into Login Service Layer");
        try {
            // TODO: Fix naming in loginrequest for frontends sake
            UserDetails u = userService.loadUserByUsername(request.getEmail());
            if (!bCryptPasswordEncoder.matches(request.getPassword(), u.getPassword())) {
                throw new IncorrectPasswordException("Password is incorrect");
            }
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.ACCEPTED, "Success"), HttpStatus.ACCEPTED);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User " + request.getEmail() + " does not exist");
        }
    }
}
