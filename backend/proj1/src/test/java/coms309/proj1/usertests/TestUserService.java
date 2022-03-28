package coms309.proj1.usertests;

import coms309.proj1.exception.EmailNotFoundException;
import coms309.proj1.registration.token.ConfirmationToken;
import coms309.proj1.registration.token.ConfirmationTokenService;
import coms309.proj1.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
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

	private static final String username = "James";
	private static final String email = "james@iastate.edu";
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

	/**
	 * Asserts that the registerUser() method returns a
	 * valid UUID token without exceptions or errors
	 */
	@Test
	public void registerUserTest() {
		// Must return Optional.empty() which means the username/email does not exist in the system
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

		when(bCryptPasswordEncoder.encode(password)).thenReturn(encryptedPassword);
		when(userRepository.save(user)).thenReturn(user);

		UUID defaultUuid = UUID.fromString("8d8b30e3-de52-4f1c-a71c-9905a8043dac");

		doNothing().when(confirmationTokenService).saveConfirmationToken(any(ConfirmationToken.class));

		// Block is required for the mocking of the static method UUID.randomUUID()
		try(MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
			mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);
			ConfirmationToken token = userService.registerUser(user);
			assertEquals(defaultUuid.toString(), token.getToken());
			//assertEquals(defaultUuid.toString(), UUID.randomUUID().toString());
		}
	}

}
