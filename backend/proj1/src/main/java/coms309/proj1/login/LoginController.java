package coms309.proj1.login;

import coms309.proj1.registration.RegistrationController;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        logger.info("Entered into Login Controller Layer");
        return loginService.login(request);
    }

}
