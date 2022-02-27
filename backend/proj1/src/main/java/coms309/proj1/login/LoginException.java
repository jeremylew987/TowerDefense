package coms309.proj1.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoginException extends ResponseStatusException {

    public LoginException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
