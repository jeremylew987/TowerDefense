package coms309.proj1.usertests;

import coms309.proj1.exception.EmailNotFoundException;
import coms309.proj1.registration.token.ConfirmationToken;
import coms309.proj1.registration.token.ConfirmationTokenService;
import coms309.proj1.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestUserService
{
	@InjectMocks
	UserService userService;

	@InjectMocks
	UserDetailsServiceImpl userDetailsService;

	@Mock
	UserRepository userRepository;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	ConfirmationTokenService confirmationTokenService;

	private static final String username = "James2002";
	private static final String email = "James2002@iastate.edu";
	private static final String password = "1234";
	private static final String encryptedPassword = "4321";
	private static final UserRole role = UserRole.USER;
	private static final User user = new User(username, email, password, role);


	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void loadUserByUsernameTest() {
		// Test username exists
		when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

		assertEquals(username, userDetails.getUsername());
		assertEquals(email, userDetails.getEmail());
	}

	/**
	 * Tests that the UsernameNotFoundException is thrown when a user is not found in userRepository
	 */
	@Test
	public void loadNullUserByUsernameTest() {
		// Test username does not exist
		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
		try {
			userDetailsService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void loadUserByEmailTest() {
		// Test email exists
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByEmail(email);

		assertEquals(username, userDetails.getUsername());
		assertEquals(email, userDetails.getEmail());
	}

	/**
	 * Tests that the EmailNotFoundException is thrown when a user is not found in userRepository
	 */
	@Test
	private void loadNullUserByEmailTest() {
		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		try {
			userDetailsService.loadUserByEmail(email);
		} catch (EmailNotFoundException e) {
			return;
		}
		fail();
	}

}
