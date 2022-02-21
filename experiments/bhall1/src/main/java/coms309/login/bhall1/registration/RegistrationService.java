package coms309.login.bhall1.registration;

import coms309.login.bhall1.registration.token.ConfirmationToken;
import coms309.login.bhall1.registration.token.ConfirmationTokenService;
import coms309.login.bhall1.user.User;
import coms309.login.bhall1.user.UserRole;
import coms309.login.bhall1.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        boolean validEmail = emailValidator.test(request.getEmail());
        if (!validEmail) {
            throw new IllegalStateException("Email is not valid");
        }
        String token = userService.registerUser(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;

        //TODO: send email

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }
}
