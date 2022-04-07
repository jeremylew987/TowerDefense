package coms309.proj1.login;

import coms309.proj1.exception.GeneralResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@AllArgsConstructor
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);



    @GetMapping(path = "/login")
    public ResponseEntity<GeneralResponse> loginPage() {
        logger.info("Entered into Login Controller Layer");
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Login Screen"), HttpStatus.ACCEPTED);
    }
    @GetMapping(path = "/login/success")
    public ResponseEntity<GeneralResponse> loginResponse() {
        logger.info("Entered into Login Controller Layer");
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Login Success"), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/logout/success")
    public ResponseEntity<GeneralResponse> logoutResponse() {
        logger.info("Entered into Login Controller Layer");
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Logout Success"), HttpStatus.ACCEPTED);
    }
}
