package coms309.proj1.registration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailException extends ResponseStatusException {
    public EmailException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}