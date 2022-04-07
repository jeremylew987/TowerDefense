package coms309.proj1.usertests;


import coms309.proj1.user.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntTest
{
	private static User testUser1;
	private static User testUser2;

	@Autowired
	private UserRepository userRepository;
	@BeforeAll
	static void setupTestUsers() {
		testUser1 = new User("testSQL1", "testSQL1@gmail.com", "test", UserRole.USER);
		testUser1.setEnabled(true);
		testUser2 = new User("testSQL2", "testSQL2@gmail.com", "test", UserRole.ADMIN);
		testUser2.setEnabled(true);
	}

	@BeforeEach
	void addUsers() {
		userRepository.save(testUser1);
		userRepository.save(testUser2);
	}

	@AfterEach
	void removeUsers() {
		userRepository.delete(testUser1);
		userRepository.delete(testUser2);
	}

	@Test
	void findUserByUserIdTest() {
		Optional<User> users = userRepository.findByUserId(testUser1.getId());
		assertFalse(users.isEmpty());
		assertEquals(testUser1.getUsername(), users.get().getUsername());
		assertEquals(testUser1.getEmail(), users.get().getEmail());
		assertEquals(testUser1.getRole(), users.get().getRole());

		users = userRepository.findByUserId(testUser2.getId());
		assertFalse(users.isEmpty());
		assertEquals(testUser2.getUsername(), users.get().getUsername());
		assertEquals(testUser2.getEmail(), users.get().getEmail());
		assertEquals(testUser2.getRole(), users.get().getRole());
	}

	@Test
	void findUserByUsernameTest() {
		Optional<User> users = userRepository.findByUsername(testUser1.getUsername());
		assertFalse(users.isEmpty());
		assertEquals(testUser1.getUsername(), users.get().getUsername());
		assertEquals(testUser1.getEmail(), users.get().getEmail());
		assertEquals(testUser1.getRole(), users.get().getRole());

		users = userRepository.findByUsername(testUser2.getUsername());
		assertFalse(users.isEmpty());
		assertEquals(testUser2.getUsername(), users.get().getUsername());
		assertEquals(testUser2.getEmail(), users.get().getEmail());
		assertEquals(testUser2.getRole(), users.get().getRole());
	}





}
