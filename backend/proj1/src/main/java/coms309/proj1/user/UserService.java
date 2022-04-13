package coms309.proj1.user;

import coms309.proj1.exception.*;
import coms309.proj1.friend.FriendRequest;
import coms309.proj1.friend.FriendRequestRepository;
import coms309.proj1.friend.Friendship;
import coms309.proj1.friend.FriendshipRepository;
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

    private final FriendshipRepository friendshipRepository;
    private final FriendRequestRepository friendRequestRepository;
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationTokenService confirmationTokenService;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> loadUsers() {
        logger.info("Entered into Service Layer");
        return userRepository.findAll();
    }

    public User loadUserById(Long id) {
        return userRepository.findByUserId(id);
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

    public User verifyUserByToken(String token) throws RuntimeException {
        logger.info("Entered into Service Layer");
        Optional<ConfirmationToken> c = confirmationTokenService.getToken(token);
        logger.info("Found " + c.orElse(null).getUser().getUsername());
        return c.orElseThrow(RuntimeException::new).getUser();
    }

    public int enableUser(String email) {
        logger.info("Entered into Service Layer");
        return userRepository.enableUser(email);
    }


// =============================== FRIEND API ================================== //

//    public Friendship addFriend(String owner_name, String friend_name) {
//        logger.info("Entered into Service Layer\n");
//        Optional<User> owner_opt = userRepository.findByUsername(owner_name);
//        Optional <User> friend_opt = userRepository.findByUsername(friend_name);
//        if (owner_opt.isEmpty() || friend_opt.isEmpty()) {
//            logger.info("User" + owner_name + " or " + friend_name + " is not found\n");
//            return null;
//        }
//        Friendship friendship = new Friendship(owner_opt.get(), friend_opt.get());
//        owner_opt.get().addFriendship(friendship);
//        friendshipRepository.save(friendship);
//        userRepository.save(owner_opt.get());
//        logger.info("Adding " + friend_name + " to " + owner_name + "'s friends list\n");
//        return friendship;
//
//    }


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


    public FriendRequest sendFriendRequest(String sender_username, String receiver_username) {
        logger.info("Entered into Service Layer\n");
        Optional<User> sender_opt = userRepository.findByUsername(sender_username);
        Optional <User> receiver_opt = userRepository.findByUsername(receiver_username);
        if (receiver_opt.isEmpty()) {
            logger.info("User" + receiver_username + " is not found\n");
            return null;
        }

        // Check that friend request doesn't already exist between these users
        if (friendRequestRepository.findFirstBySenderAndReceiver(sender_opt.get(), receiver_opt.get()).isEmpty()) {
            logger.info("Friend request from " + sender_username + " to " + receiver_username + " already exists\n");
            return null;
        }

        // Check that the user isn't already friends
        if (friendshipRepository.findFirstByOwnerAndFriend(sender_opt.get(), receiver_opt.get()).isEmpty()) {
            logger.info(sender_username + " is already friends with " + receiver_username + "\n");
            return null;
        }

        // Check that the receiver hasn't already sent a friend request. If so, automatically make them friends.
        if (friendRequestRepository.findFirstBySenderAndReceiver(receiver_opt.get(), sender_opt.get()).isPresent()) {
            logger.info(receiver_username + " has already sent a friend request to " + receiver_username + ". Making them friends.\n");
            acceptFriendRequest(receiver_username, sender_username);
            return null;
        }

        FriendRequest friendRequest = new FriendRequest(sender_opt.get(), receiver_opt.get());
        sender_opt.get().addSentFriendRequest(friendRequest);
        receiver_opt.get().addReceivedFriendRequest(friendRequest);
        friendRequestRepository.save(friendRequest);
        logger.info("Sent friend request from " +  sender_username + " to " + receiver_username + "\n");

        return friendRequest;
    }

    /**
     * @return Friendship of owner and friend if it exists. Null otherwise
     */
    public Friendship removeFriend(String owner_name, String friend_name) {
        logger.info("Entered into Service Layer\n");
        Optional<User> owner_opt = userRepository.findByUsername(owner_name);
        Optional <User> friend_opt = userRepository.findByUsername(friend_name);
        if (owner_opt.isEmpty() || friend_opt.isEmpty()) {
            logger.info("User" + owner_name + " or " + friend_name + " is not found\n");
            return null;
        }

        Optional<Friendship> owner_fs = friendshipRepository.findFirstByOwnerAndFriend(owner_opt.get(), friend_opt.get());
        Optional<Friendship> friend_fs = friendshipRepository.findFirstByOwnerAndFriend(friend_opt.get(), owner_opt.get());
        if (owner_fs.isEmpty() || friend_fs.isEmpty()) {
            logger.info(friend_name + "is not in " + owner_name + "'s friend list\n");
            return null;
        }
        logger.info("Removing " + friend_name + " from " + owner_name + "'s friends list\n");
        owner_opt.get().removeFriendship(owner_fs.get());
        friend_opt.get().removeFriendship(friend_fs.get());
        userRepository.save(owner_opt.get());
        userRepository.save(friend_opt.get());
        friendshipRepository.delete(owner_fs.get());
        friendshipRepository.delete(friend_fs.get());
        return owner_fs.get();
    }

    public Friendship acceptFriendRequest(String sender_username, String receiver_username) {
        logger.info("Entered into Service Layer\n");
        Optional<User> sender_opt = userRepository.findByUsername(sender_username);
        Optional <User> receiver_opt = userRepository.findByUsername(receiver_username);
        if (sender_opt.isEmpty() || receiver_opt.isEmpty()) {
            logger.info("User" + sender_opt + " or " + receiver_opt + " is not found\n");
            return null;
        }
        Optional<FriendRequest> friendRequest = friendRequestRepository.findFirstBySenderAndReceiver(sender_opt.get(), receiver_opt.get());
        if (friendRequest.isEmpty()) {
            logger.info("User" + sender_opt + " has not sent a friend request to " + receiver_opt + "\n");
            return null;
        }
        Friendship sender_fs = new Friendship(sender_opt.get(), receiver_opt.get());
        Friendship receiver_fs = new Friendship(receiver_opt.get(), sender_opt.get());
        friendshipRepository.save(sender_fs);
        friendshipRepository.save(receiver_fs);
        sender_opt.get().addFriendship(sender_fs);
        receiver_opt.get().addFriendship(receiver_fs);
        userRepository.save(sender_opt.get());
        userRepository.save(receiver_opt.get());

        // Delete friend request from database
        friendRequestRepository.delete(friendRequest.get());
        logger.info(sender_username + " and " + receiver_username + " are now friends\n");
        return receiver_fs;
    }

    public FriendRequest declineFriendRequest(String sender_username, String receiver_username) {
        logger.info("Entered into Service Layer\n");
        Optional<User> sender_opt = userRepository.findByUsername(sender_username);
        Optional <User> receiver_opt = userRepository.findByUsername(receiver_username);
        if (receiver_opt.isEmpty()) {
            logger.info("User" + receiver_username + " is not found\n");
            return null;
        }
        if (sender_opt.isEmpty()) {
            logger.info("User" + sender_username + " is not found\n");
            return null;
        }
        Optional<FriendRequest> friendRequest_opt = friendRequestRepository.findFirstBySenderAndReceiver(sender_opt.get(), receiver_opt.get());
        if (friendRequest_opt.isEmpty()) {
            return null;
        }
        sender_opt.get().removeSentFriendRequest(friendRequest_opt.get());
        receiver_opt.get().removeReceivedFriendRequest(friendRequest_opt.get());
        userRepository.save(sender_opt.get());
        userRepository.save(receiver_opt.get());
        friendRequestRepository.delete(friendRequest_opt.get());
        return friendRequest_opt.get();
    }

    public List<FriendRequest> getSentFriendRequests(String sender_username) {
        logger.info("Entered into Service Layer\n");
        Optional<User> sender_opt = userRepository.findByUsername(sender_username);
        if (sender_opt.isEmpty()) {
            logger.info("User" + sender_username + " is not found\n");
            return null;
        }
        return sender_opt.get().getSentFriendRequests();
    }

    public List<FriendRequest> getReceivedFriendRequests(String sender_username) {
        logger.info("Entered into Service Layer\n");
        Optional<User> sender_opt = userRepository.findByUsername(sender_username);
        if (sender_opt.isEmpty()) {
            logger.info("User" + sender_username + " is not found\n");
            return null;
        }
        return sender_opt.get().getReceivedFriendRequests();
    }

}
