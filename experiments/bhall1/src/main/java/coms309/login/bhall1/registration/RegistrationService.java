package coms309.login.bhall1.registration;

import coms309.login.bhall1.user.User;
import coms309.login.bhall1.user.UserRole;
import coms309.login.bhall1.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean validEmail = emailValidator.test(request.getEmail());
        if (!validEmail) {
            throw new IllegalStateException("Email is not valid");
        }
        return userService.registerUser(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }
}
