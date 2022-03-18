package coms309.proj1.usertests;

import coms309.proj1.exception.EmailNotFoundException;
import coms309.proj1.registration.token.ConfirmationToken;
import coms309.proj1.registration.token.ConfirmationTokenService;
import coms309.proj1.user.User;
import coms309.proj1.user.UserRepository;
import coms309.proj1.user.UserRole;
import coms309.proj1.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestUserService
{
	@InjectMocks
	UserService userService;

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

		User user1 = (User) userService.loadUserByUsername(username);

		assertEquals(username, user1.getUsername());
		assertEquals(email, user1.getEmail());

		// Test username does not exist
		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
		try {
			userService.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			return;
		}
		fail();
	}

	@Test
	public void loadUserByEmailTest() {
		// Test email exists
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		User user1 = (User) userService.loadUserByEmail(email);

		assertEquals(username, user1.getUsername());
		assertEquals(email, user1.getEmail());

		// Test email does not exist
		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		try {
			userService.loadUserByEmail(email);
		} catch (EmailNotFoundException e) {
			return;
		}
		fail();
	}

	@Test
	public void registerUserTest() {
//		ConfirmationToken confirmationToken =
//				mock(ConfirmationToken.class, withSettings().useConstructor
//						("11223344",
//						LocalDateTime.now(),
//						LocalDateTime.now().plusMinutes(15),
//						user)
//				);
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

		when(bCryptPasswordEncoder.encode(password)).thenReturn(encryptedPassword);

		//doNothing().when(userRepository).save(user);
		when(userRepository.save(user)).thenReturn(user);

		UUID defaultUuid = UUID.fromString("8d8b30e3-de52-4f1c-a71c-9905a8043dac");

		doNothing().when(confirmationTokenService).saveConfirmationToken(any(ConfirmationToken.class));
		try(MockedStatic<UUID> mockedUuid = mockStatic(UUID.class)) {
			mockedUuid.when(UUID::randomUUID).thenReturn(defaultUuid);
			String token = userService.registerUser(user);
			assertEquals(defaultUuid.toString(), UUID.randomUUID().toString());
		}
	}

}
