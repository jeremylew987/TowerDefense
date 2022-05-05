package coms309.proj1.login;

import coms309.proj1.exception.GeneralResponse;
import coms309.proj1.user.User;
import coms309.proj1.user.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);


    /**
     * Login page returned by a get request to /login.
     * Note: To actually login, send post request to /login with username and password
     * @return "Login Screen"
     */
    @GetMapping(path = "/login")
    public ResponseEntity<GeneralResponse> loginPage() {
        logger.info("Entered into Login Controller Layer");
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Login Screen"), HttpStatus.ACCEPTED);
    }

    /**
     * Redirected here when a successful login is performed by /login post request
     * @return "Login Success"
     */
    @GetMapping(path = "/login/success")
    public ResponseEntity<GeneralResponse> loginResponse(Authentication authentication) {
        logger.info("Entered into Login Controller Layer");
        User u = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Login Success", u), HttpStatus.ACCEPTED);
    }

    /**
     * Redirected here when a successful logout is performed by /logout get request
     * @return "Logout Success"
     */
    @GetMapping(path = "/logout/success")
    public ResponseEntity<GeneralResponse> logoutResponse() {
        logger.info("Entered into Login Controller Layer");
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Logout Success"), HttpStatus.ACCEPTED);
    }
}
