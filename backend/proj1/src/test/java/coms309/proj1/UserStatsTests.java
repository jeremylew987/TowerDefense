//package coms309.proj1;
//
//import coms309.proj1.user.User;
//import coms309.proj1.user.UserRepository;
//import coms309.proj1.user.UserRole;
//import coms309.proj1.user.stat.UserStats;
//import coms309.proj1.user.stat.UserStatsRepository;
//import coms309.proj1.user.stat.UserStatsService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserStatsTests
//{
//
//	private static User testUser1;
//	private static User testUser2;
//
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private UserStatsService userStatsService;
//
//	@BeforeAll
//	static void setupTestUsers() {
//		testUser1 = new User("testSQL1", "testSQL1@gmail.com", "test", UserRole.USER);
//		testUser1.setEnabled(true);
//		testUser2 = new User("testSQL2", "testSQL2@gmail.com", "test", UserRole.ADMIN);
//		testUser2.setEnabled(true);
//
//		testUser1.setStats(new UserStats(testUser1));
//		testUser2.setStats(new UserStats(testUser2));
//
//		testUser1.getStats().setKills(10);
//		testUser2.getStats().setKills(5);
//
//		testUser1.getStats().setWins(10);
//		testUser2.getStats().setLosses(5);
//	}
//
//	@BeforeEach
//	void addUsers() {
//		userRepository.save(testUser1);
//		userRepository.save(testUser2);
//	}
//
//	@AfterEach
//	void removeUsers() {
//		userRepository.delete(testUser1);
//		userRepository.delete(testUser2);
//	}
//
//	@Test
//	void leaderboardSortTest() {
//
//		UserStats stats1 = userStatsService.loadUserStats(testUser1.getUsername());
//		UserStats stats2 = userStatsService.loadUserStats(testUser2.getUsername());
//		assertEquals(stats1.getKills(), 10);
//		assertEquals(stats1.getWins(), 10);
//
//		assertEquals(stats2.getKills(), 5);
//		assertEquals(stats2.getWins(), 5);
//
//	}
//
//
//
//
//
//}
