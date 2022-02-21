package coms309.login.bhall1.service;

import coms309.login.bhall1.model.User;
import coms309.login.bhall1.model.UserDto;
import coms309.login.bhall1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User registerNewUserAccount(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            return null;
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        return repository.save(user);
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }
}
