package coms309.proj1.usertests;

import coms309.proj1.user.User;
import coms309.proj1.user.UserDetailsImpl;
import coms309.proj1.user.UserRole;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser
{
	@Test
	public void UserTest() {
		User user = new User("James", "james@gmail.com", "1234", UserRole.USER);
		UserDetailsImpl userDetails = new UserDetailsImpl(user);
		assertEquals("James", user.getUsername());
		assertEquals("james@gmail.com", user.getEmail());
		assertEquals("1234", user.getPassword());
		assertEquals(UserRole.USER.toString(), user.getRole().toString());
		assertTrue(userDetails.isAccountNonLocked());
		assertFalse(userDetails.isEnabled());
	}
}
