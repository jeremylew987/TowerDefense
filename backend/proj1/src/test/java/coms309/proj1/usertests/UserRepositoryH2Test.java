package coms309.proj1.usertests;


import coms309.proj1.user.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@DataJpaTest
@Sql(scripts = "/create-data.sql")
@Sql(scripts = "/delete-data.sql", executionPhase = AFTER_TEST_METHOD)
public class UserRepositoryH2Test
{
	private static User testUser1;
	private static UserDetailsImpl testUser1Details;

	@Autowired
	private UserRepository userRepository;
	@BeforeAll
	static void setupTestUsers() {
		testUser1 = new User("testSQL1", "testSQL1@gmail.com", "test", UserRole.USER);
		testUser1.setEnabled(true);
		testUser1Details = new UserDetailsImpl(testUser1);
	}
	@Test
	void findUserByUserIdTest() {
		Optional<User> users = userRepository.findByUserId(testUser1.getId());
		assertFalse(users.isEmpty());
		assertEquals(testUser1.getUsername(), users.get().getUsername());
		assertEquals(testUser1.getEmail(), users.get().getEmail());
		assertEquals(testUser1.getRole(), users.get().getRole());
	}

	@Test
	void findUserByUsernameTest() {
		Optional<User> users = userRepository.findByUsername(testUser1.getUsername());
		assertFalse(users.isEmpty());
		assertEquals(testUser1.getUsername(), users.get().getUsername());
		assertEquals(testUser1.getEmail(), users.get().getEmail());
		assertEquals(testUser1.getRole(), users.get().getRole());
	}





}
