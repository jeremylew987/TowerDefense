package coms309.proj1.user.stat;

import com.fasterxml.jackson.annotation.JsonView;
import coms309.proj1.Views;
import coms309.proj1.exception.GeneralResponse;
import coms309.proj1.friend.FriendRequest;
import coms309.proj1.user.User;
import coms309.proj1.user.UserController;
import coms309.proj1.user.UserDetailsImpl;
import coms309.proj1.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * End points for retrieving and modifying user stats
 */
@RestController
public class UserStatsController
{

	@Autowired
	private UserStatsService userStatsService;

	private final Logger logger = LoggerFactory.getLogger(UserStatsController.class);

	/**
	 * Retrieves an ordered list of users specified by the url parameters given. Can set
	 * to friends only, sort by name, level and wins, and by ascending or descending order.
	 *
	 * @param authentication Current user's authentication
	 * @param friendsOnly Whether to show only friends or not
	 * @param sortBy Options: Name, Level, Wins
	 * @param order Order to sort entries: Ascending or Descending
	 * @return Ordered list of users
	 */
	@GetMapping(value = {"/user/leaderboard"})
	@JsonView(Views.SummaryWithStats.class)
	public ResponseEntity<GeneralResponse> getLeaderboard(Authentication authentication,
														  @RequestParam boolean friendsOnly,
														  @RequestParam String sortBy,
														  @RequestParam String order) {

		logger.info("Entered into User Controller Layer");
		List<User> leaderboard = userStatsService.loadLeaderboard(((UserDetailsImpl)authentication.getPrincipal()).getUsername(), friendsOnly, sortBy, order);
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve leaderboard", leaderboard), HttpStatus.ACCEPTED);

	}

	/**
	 * Retrieves the current user's stats
	 * @param authentication Current user's authentication
	 * @return User stats object wrapped in general http response
	 */
	@GetMapping(value = {"/user/stats"})
	@JsonView(Views.SummaryWithStats.class)
	public ResponseEntity<GeneralResponse> getUserStats(Authentication authentication) {
		logger.info("Entered into User Controller Layer");
		UserStats userStats = userStatsService.loadUserStats(((UserDetailsImpl)authentication.getPrincipal()).getUsername());
		return new ResponseEntity<GeneralResponse>(new GeneralResponse(HttpStatus.ACCEPTED, "Retrieve current user stats", userStats), HttpStatus.ACCEPTED);
	}

}
