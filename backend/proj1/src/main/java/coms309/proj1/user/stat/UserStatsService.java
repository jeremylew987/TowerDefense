package coms309.proj1.user.stat;

import coms309.proj1.user.User;
import coms309.proj1.user.UserRepository;
import coms309.proj1.user.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserStatsService
{

	@Autowired
	private UserStatsRepository userStatsRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private final Logger logger = LoggerFactory.getLogger(UserStatsService.class);

	public UserStats loadUserStats(String username){

		// Check that the user can be found
		Optional<User> user_opt = userRepository.findByUsername(username);
		if (user_opt.isEmpty()) {
			return null;
		}
		// Gives user default stats object if it doesn't have one already
		if (user_opt.get().getStats() == null) {
			user_opt.get().setStats(new UserStats(user_opt.get()));
			userStatsRepository.save(user_opt.get().getStats());
		}
		return user_opt.get().getStats();
	}

	public List<User> loadLeaderboard(String username, boolean friendsOnly, String sortBy, String order) {
		List<User> leaderboard;
		if (friendsOnly) {
			leaderboard = userService.getFriends(username);
		} else {
			leaderboard = userService.loadUsers();
		}

		leaderboard.sort((u1, u2) -> {
			UserStats u1Stats = u1.getStats();
			UserStats u2Stats = u2.getStats();
			int cmp;
			switch(sortBy.toLowerCase()) {

				case "level":
					if (u1Stats.getLevel() - u2Stats.getLevel() != 0) {
						cmp = u1Stats.getLevel() - u2Stats.getLevel();
					} else {
						cmp = u1Stats.getExperience() - u2Stats.getExperience();
					}
					break;

				case "wins":
					cmp = u1Stats.getWins() - u2Stats.getWins();
					break;

				case "losses":
					cmp = u1Stats.getLosses() - u2Stats.getLosses();
					break;

				case "kills":
					cmp = u1Stats.getKills() - u2Stats.getKills();
					break;

				case "username":
				default:
					cmp = u1.getUsername().compareTo(u2.getUsername());
					break;
			}

			if (cmp == 0) {
				cmp = u1.getUsername().compareTo(u2.getUsername());
			}
			return cmp;
		});

		if (order.equalsIgnoreCase("descending")) {
			Collections.reverse(leaderboard);
		} else if (order.equalsIgnoreCase("ascending")) {
			// Leave as is
		}
		return leaderboard;
	}
}
