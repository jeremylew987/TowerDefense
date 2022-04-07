package coms309.proj1.user;

import coms309.proj1.friend.FriendRequest;
import coms309.proj1.friend.Friendship;
import coms309.proj1.exception.GeneralResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController // Tells Spring Boot that HTTP requests are handled here
public class UserController
{
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	// =============================== BASIC USER API ================================== //
	@GetMapping(path = "/admin")
	public ResponseEntity<GeneralResponse> getAdminDetails(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user details", authentication.getPrincipal()), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/user")
	public ResponseEntity<GeneralResponse> getCurrentUser(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		logger.info(authentication.toString());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user details", authentication.getPrincipal()), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/users"})
	public ResponseEntity<GeneralResponse> getAllUsers() {
		logger.info("Entered into User Controller Layer");
		List<User> results = userService.loadUsers();
		logger.info("Records Fetched:" + results.size());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all users", results), HttpStatus.ACCEPTED);
	}

	// =============================== FRIEND API ================================== //
//	@GetMapping(value={"/user/friends/add/{friend}"})
//	public ResponseEntity<GeneralResponse> addFriend(Authentication authentication, @PathVariable String friend) {
//		logger.info("Entered into User Controller Layer");
//		Friendship relationship =  userService.addFriend(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), friend);
//		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Added friend", relationship), HttpStatus.ACCEPTED);
//	}
//
//	@GetMapping(value={"/user/friends/remove/{friend}"})
//	public ResponseEntity<GeneralResponse> deleteFriend(Authentication authentication, @PathVariable String friend) {
//		logger.info("Entered into User Controller Layer");
//		Friendship relationship =  userService.removeFriend(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), friend);
//		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Deleted friend", relationship), HttpStatus.ACCEPTED);
//	}

	@GetMapping(value={"/user/friends"})
	public ResponseEntity<GeneralResponse> getFriends(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		List<User> friends =  userService.getFriends(((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all friends", friends), HttpStatus.ACCEPTED);
	}


	// =============================== FRIEND REQUEST API ================================== //

	@GetMapping(value = {"/user/friends/add"})
	public ResponseEntity<GeneralResponse> sendFriendRequest(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		FriendRequest friendRequest = userService.sendFriendRequest(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), user);
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Sent friend request", friendRequest), HttpStatus.ACCEPTED);
	}

	@GetMapping(value = {"/user/friends/remove"})
	public ResponseEntity<GeneralResponse> removeFriend(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		FriendRequest friendRequest = userService.sendFriendRequest(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), user);
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Sent friend request", friendRequest), HttpStatus.ACCEPTED);
	}

	@GetMapping(value = {"/user/friends/decline"})
	public ResponseEntity<GeneralResponse> declineFriendRequest(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		FriendRequest friendRequest = userService.declineFriendRequest(user, ((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Decline friend request", friendRequest), HttpStatus.ACCEPTED);
	}

	@GetMapping(value = {"/user/friends/accept"})
	public ResponseEntity<GeneralResponse> acceptFriendRequest(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		Friendship friendship = userService.acceptFriendRequest(user, ((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Accept friend request", friendship), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/user/friends/sent"})
	public ResponseEntity<GeneralResponse> getSentFriendRequests(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		List<FriendRequest> sentFriendRequests =  userService.getSentFriendRequests(((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all sent friend requests", sentFriendRequests), HttpStatus.ACCEPTED);
	}

	@GetMapping(value={"/user/friends/received"})
	public ResponseEntity<GeneralResponse> getReceivedFriendRequests(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		List<FriendRequest> receivedFriendRequests =  userService.getReceivedFriendRequests(((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all received friend requests", receivedFriendRequests), HttpStatus.ACCEPTED);
	}

	// =============================== SERVER API ================================== //
	@GetMapping(value={"/user/verifyUser"})
	public UserDTO verifyToken(@RequestParam("token") String token) {
		logger.info("Entered into User Controller Layer");
		UserDTO u;
		try {
			u = modelMapper.map(userService.verifyUserByToken(token), UserDTO.class);
		} catch (RuntimeException ex) {
			u = null;
		}
		return u;
	}

}
