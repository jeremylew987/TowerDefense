package coms309.login.bhall1.controller;

import coms309.login.bhall1.model.User;
import coms309.login.bhall1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users/all")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/register")
    public String createUser(@RequestBody User user){
        logger.info("Entered into Controller Layer.");
        userRepository.save(user);
        logger.info("Created user with UID " + user.getId());
        return "Created user with UID " + user.getId();
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.setPassword(updatedUser.getPassword());
            user.setUsername(updatedUser.getUsername());
            userRepository.save(user);
            logger.info("User with UID " + id + " has been updated");
            return "User with UID " + id + " has been updated";
        }
        logger.info("User with UID " + id + " not found");
        return "User with UID " + id + " not found";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            userRepository.delete(user);
            logger.info("User with UID " + id + " has been deleted from the database");
            return "User with UID " + id + " has been deleted from the database";
        }
        logger.info("User with UID " + id + " not found");
        return "User with UID " + id + " not found";
    }
}
