package coms309.proj1.registration;

import coms309.proj1.exception.GeneralResponse;
import coms309.proj1.login.LoginException;
import coms309.proj1.registration.token.ConfirmationToken;
import coms309.proj1.user.UserController;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @PostMapping
    public ResponseEntity<GeneralResponse> register(@RequestBody RegistrationRequest request) {
        logger.info("Entered into Registration Controller Layer");
        ConfirmationToken confirmationToken =  registrationService.register(request);
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Confirmation token created", confirmationToken), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<GeneralResponse> confirm(@RequestParam("token") String token) {
        logger.info("Entered into Registration Controller Layer");
        registrationService.confirmToken(token);
        return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Email verification complete"), HttpStatus.ACCEPTED);

    }
}
