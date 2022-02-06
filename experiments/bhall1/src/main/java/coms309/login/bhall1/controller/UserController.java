package coms309.trivia.bhall1.controller;

import coms309.login.bhall1.model.User;
import coms309.login.bhall1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users/all")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/register")
    User createUser(@RequestBody User user){
        userRepository.save(user);
        return user;
    }

    @PostMapping("/users/update/{id}")
    User updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.setPassword(updatedUser.getPassword());
            user.setUsername(updatedUser.getUsername());
            userRepository.save(user);
            return user;
        }
        return null;
    }
}
