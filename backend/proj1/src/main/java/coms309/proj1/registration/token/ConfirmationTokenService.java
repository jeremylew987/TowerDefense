package coms309.proj1.registration.token;

import coms309.proj1.exception.InvalidTokenException;
import coms309.proj1.user.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final static String INVALID_TOKEN_MSG = "Token %s does not exist";

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token) throws InvalidTokenException {
        Optional<ConfirmationToken> c = confirmationTokenRepository.findByToken(token);
        if (c.isEmpty()) {
            logger.warn(String.format(INVALID_TOKEN_MSG, token));
            throw new InvalidTokenException(String.format(INVALID_TOKEN_MSG, token));
        }
        logger.info("Retrieved " + c.toString() + " by email");
        return c.get();
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, new Date()
        );
    }
}
