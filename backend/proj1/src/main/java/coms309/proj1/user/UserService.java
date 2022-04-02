package coms309.proj1.user;

import coms309.proj1.exception.*;
import coms309.proj1.friend.FriendRelationship;
import coms309.proj1.friend.FriendRelationshipRepository;
import coms309.proj1.registration.token.ConfirmationToken;
import coms309.proj1.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final static String USERNAME_NOT_FOUND_MSG = "User %s does not exist";
    private final static String EMAIL_NOT_FOUND_MSG = "User with email %s does not exist";
    private final static String BAD_CREDENTIALS_MSG = "Username %s does not exist";

    @Autowired
    private UserRepository userRepository;


    private final FriendRelationshipRepository friendRelationshipRepository;

    private UserDetailsServiceImpl userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ConfirmationTokenService confirmationTokenService;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> loadUsers() {
        logger.info("Entered into Service Layer");
        return userRepository.findAll();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }

    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
        return userDetailsService.loadUserByEmail(email);
    }

    public ConfirmationToken  registerUser(User user) {
        logger.info("Entered into Service Layer");

        // continues if loadUser by Email & Username return not found.
        // Throws email or username taken exception if a user is returned
        try {
            UserDetails taken = userDetailsService.loadUserByEmail(user.getEmail());
            logger.warn("Email is already registered to " + taken.getUsername());
            throw new EmailTakenException(user.getEmail() + " is already registered");
        } catch(EmailNotFoundException ignored) {
            logger.info("Email is not registered");
        }
        try {
            UserDetails taken = userDetailsService.loadUserByUsername(user.getUsername());
            logger.warn("Username is already taken by " + taken.toString());
            throw new UsernameTakenException(user.getUsername() + " is already taken");
        } catch(UsernameNotFoundException ignored) {
            logger.info("Username is not taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        logger.info ("Saved user to database");

        // TODO: Take token expire date from configuration
        String token = UUID.randomUUID().toString();
        Date date = new Date();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                date,
                new Date(date.getTime() + (15 * 60 * 1000)), // 15 minutes +
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        logger.info("Saved confirmation token");
        return confirmationToken;
    }

//    public boolean verifyUserByUsername(String username, String password) {
//        logger.info("Entered into Service Layer");
//        Optional<User> result = userRepository.findByUsername(username);
//        if (result.isEmpty()) {
//            logger.warn("Username [" + username + "] does not exist");
//            return false;
//        }
//
//        logger.info ("Username [" + username + "] exists");
//
//        if (!bCryptPasswordEncoder.matches(password, result.get().getPassword())) {
//            logger.warn ("Password does not match user [" + username + "]");
//            return false;
//        }
//        logger.info("Username [" + username + "] matches password");
//        return true;
//    }


//    public boolean verifyUserByEmail(String email, String password) {
//        logger.info("Entered into Service Layer");
//        Optional<User> result = userRepository.findByEmail(email);
//        if (result.isEmpty()) {
//            logger.warn("User with email [" + email + "] does not exist");
//            return false;
//        }
//
//        logger.info ("User with email [" + email + "] exists");
//
//        if (!bCryptPasswordEncoder.matches(password, result.get().getPassword())) {
//            logger.warn ("Password does not match email [" + email + "]");
//            return false;
//        }
//        logger.info("User with email [" + email + "] matches password");
//        return true;
//    }

    public int enableUser(String email) {
        logger.info("Entered into Service Layer");
        return userRepository.enableUser(email);
    }


//    @Override
//    public UserDetails updatePassword(UserDetails user, String newPassword)
//    {
//        logger.info("Entered into Service Layer\n");
//        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
//        ((User)user).setPassword(encodedPassword);
//        logger.info ("Password set for " + ((User)user).toString());
//        return user;
//    }

    public FriendRelationship addFriend(String owner_name, String friend_name) {
        logger.info("Entered into Service Layer\n");
        Optional<User> owner_opt = userRepository.findByUsername(owner_name);
        Optional <User> friend_opt = userRepository.findByUsername(friend_name);
        if (owner_opt.isEmpty() || friend_opt.isEmpty()) {
            return null;
        }
        FriendRelationship friendRelationship = new FriendRelationship(owner_opt.get(), friend_opt.get());
        owner_opt.get().addFriendRelationship(friendRelationship);
        friendRelationshipRepository.save(friendRelationship);
        userRepository.save(owner_opt.get());
        logger.info("Adding " + friend_name + " to " + owner_name + "'s friends list\n");
        return friendRelationship;

    }

    /**
     * @return FriendRelationship of owner and friend if it exists. Null otherwise
     */
    public FriendRelationship removeFriend(String owner_name, String friend_name) {
        logger.info("Entered into Service Layer\n");
        Optional<User> owner_opt = userRepository.findByUsername(owner_name);
        Optional <User> friend_opt = userRepository.findByUsername(friend_name);
        if (owner_opt.isEmpty() || friend_opt.isEmpty()) {
            logger.info("User" + owner_name + " or " + friend_name + " is not found\n");
            return null;
        }
        for (FriendRelationship friendRelationship : owner_opt.get().getFriendRelationships()) {
            if (friendRelationship.getFriend() == friend_opt.get()) {
                logger.info("Removing " + friend_name + " from " + owner_name + "'s friends list\n");
                owner_opt.get().removeFriendRelationship(friendRelationship);
                friendRelationshipRepository.delete(friendRelationship);
                userRepository.save(owner_opt.get());
                return friendRelationship;
            }
        }
        logger.info(friend_name + "is not in " + owner_name + "'s friend list\n");
        return null;
    }

    public List<User> getFriends(String owner_name) {
        logger.info("Entered into Service Layer\n");
        Optional<User> owner_opt = userRepository.findByUsername(owner_name);
        if (owner_opt.isEmpty()) {
            logger.info("User" + owner_name + " is not found\n");
            return null;
        }
        List<User> friends = owner_opt.get().getFriends();
        logger.info("Retrieved " + friends.size() + " friend records\n");
        return owner_opt.get().getFriends();
    }

}
