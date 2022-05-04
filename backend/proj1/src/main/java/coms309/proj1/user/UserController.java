package coms309.proj1.user;

import com.fasterxml.jackson.annotation.JsonView;
import coms309.proj1.Views;
import coms309.proj1.friend.FriendRequest;
import coms309.proj1.friend.Friendship;
import coms309.proj1.exception.GeneralResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * End points for accessing and modifying user details, friends, and friend requests
 */
@RestController // Tells Spring Boot that HTTP requests are handled here
public class UserController
{
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	// =============================== BASIC USER API ================================== //

	/**
	 * Gets the authenticated user's details
	 * @param authentication Requires authentication and role of ADMIN
	 * @return current user details wrapped by a general http response
	 */
	@GetMapping(path = "/admin")
	@JsonView(Views.Detailed.class)
	public ResponseEntity<GeneralResponse> getAdminDetails(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user details", authentication.getPrincipal()), HttpStatus.ACCEPTED);
	}

	/**
	 * Gets the authenticated user's details
	 * @param authentication current user's authentication. requires authentication
	 * @return current user details wrapped by a general http response
	 */
	@JsonView(Views.Detailed.class)
	@GetMapping(path = "/user")
	public ResponseEntity<GeneralResponse> getCurrentUser(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		// Hack to get around lazy loading causing issues. Don't want to use eager for blanket performance hit
		UserDetails userDetailsPrincipal = (UserDetails) authentication.getPrincipal();
		UserDetails userDetails = userService.loadUserByUsername(userDetailsPrincipal.getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user details", userDetails), HttpStatus.ACCEPTED);
	}

	/**
	 * Get all users
	 * @return JSON list of all users and their details wrapped by a general http response
	 */
	@GetMapping(value={"/users"})
	@JsonView(Views.Summary.class)
	public ResponseEntity<GeneralResponse> getAllUsers() {
		logger.info("Entered into User Controller Layer");
		List<User> results = userService.loadUsers();
		logger.info("Records Fetched:" + results.size());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all users", results), HttpStatus.ACCEPTED);
	}

	// =============================== FRIEND API ================================== //

	/**
	 * Get all the current user's friends
	 * @return JSON list of all the user's friends wrapped in general http response
	 */
	@GetMapping(value={"/user/friends"})
	@JsonView(Views.SummaryWithFriends.class)
	public ResponseEntity<GeneralResponse> getFriends(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		List<User> friends =  userService.getFriends(((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all friends", friends), HttpStatus.ACCEPTED);
	}


	// =============================== FRIEND REQUEST API ================================== //

	/**
	 * Send a friend request to another user, or make them friends if there is a matching incoming friend request
	 * @param authentication current user's authentication
	 * @param user user to send friend request to
	 * @return optional friend request object created
	 */
	@GetMapping(value = {"/user/friends/add"})
	@JsonView(Views.Summary.class)
	public ResponseEntity<GeneralResponse> sendFriendRequest(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		FriendRequest friendRequest = userService.sendFriendRequest(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), user);
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Sent friend request", friendRequest), HttpStatus.ACCEPTED);
	}

	/**
	 * Remove a user from the current user's friends list
	 * @param authentication current user's authentication
	 * @param user user to remove from friends list
	 * @return optional friendship object removed
	 */
	@GetMapping(value = {"/user/friends/remove"})
	@JsonView(Views.SummaryWithFriends.class)
	public ResponseEntity<GeneralResponse> removeFriend(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		Friendship friendRequest = userService.removeFriend(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), user);
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Remove friend", friendRequest), HttpStatus.ACCEPTED);
	}

	/**
	 * Accept an incoming friend request from another user
	 * @param authentication current user's authentication
	 * @param user user to accept friend request from
	 * @return optional created friendship object
	 */
	@GetMapping(value = {"/user/friends/accept"})
	@JsonView(Views.SummaryWithFriends.class)
	public ResponseEntity<GeneralResponse> acceptFriendRequest(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		Friendship friendship = userService.acceptFriendRequest(user, ((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Accept friend request", friendship), HttpStatus.ACCEPTED);
	}

	/**
	 * Decline a friend request sent by another user
	 * @param authentication current user's authentication
	 * @param user user to decline friend request from
	 * @return optional deleted friend request object
	 */
	@GetMapping(value = {"/user/friends/decline"})
	@JsonView(Views.SummaryWithFriends.class)
	public ResponseEntity<GeneralResponse> declineFriendRequest(Authentication authentication, @RequestParam String user) {
		logger.info("Entered into User Controller Layer");
		FriendRequest friendRequest = userService.declineFriendRequest(user, ((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Decline friend request", friendRequest), HttpStatus.ACCEPTED);
	}

	/**
	 * Get all friend requests the authenticated user has sent to to other users.
	 * @param authentication current user's authentication
	 * @return JSON list of friend request objects
	 */
	@GetMapping(value={"/user/friends/sent"})
	@JsonView(Views.SummaryWithFriends.class)
	public ResponseEntity<GeneralResponse> getSentFriendRequests(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		List<FriendRequest> sentFriendRequests =  userService.getSentFriendRequests(((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve all sent friend requests", sentFriendRequests), HttpStatus.ACCEPTED);
	}

	/**
	 * Get all friend requests the authenticated user has received from other users.
	 * @param authentication current user's authentication
	 * @return JSON list of friend request objects
	 */
	@GetMapping(value={"/user/friends/received"})
	@JsonView(Views.SummaryWithFriends.class)
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
