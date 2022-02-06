package coms309.trivia.bhall1.controller;

import coms309.trivia.bhall1.model.User;
import coms309.trivia.bhall1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("users/all")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/register/{u}/{p}")
    User createUser(@PathVariable String u, @PathVariable String p){
        User newUser = new User();
        newUser.setUsername(u);
        newUser.setPassword(p);

        userRepository.save(newUser);
        return newUser;
    }

    @PostMapping("/users/register")
    User createUser(@RequestBody User newUser){
        userRepository.save(newUser);
        return newUser;
    }
}
