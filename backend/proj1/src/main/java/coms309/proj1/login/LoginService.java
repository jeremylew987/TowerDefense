package coms309.proj1.login;

import coms309.proj1.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<String> login(LoginRequest request) {
        try {
            UserDetails u = userService.loadUserByUsername(request.getEmail());
            if (!bCryptPasswordEncoder.matches(request.getPassword(), u.getPassword())) {
                throw new LoginException("BadPassword");
            }
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        } catch (UsernameNotFoundException e) {
            throw new LoginException("BadEmail");
        }
    }
}
