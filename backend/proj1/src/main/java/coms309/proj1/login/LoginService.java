package coms309.proj1.login;

import coms309.proj1.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String login(LoginRequest request) {
        UserDetails u = userService.loadUserByUsername(request.getUsername());
        String encodedPassword = bCryptPasswordEncoder
                .encode(request.getPassword());
        if (u.getPassword() == encodedPassword) {
            return "Success";
        }
        return "Failure";
    }
}
