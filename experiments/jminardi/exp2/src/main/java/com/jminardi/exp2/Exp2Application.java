package com.jminardi.exp2;


import com.jminardi.exp2.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * Hello World Application
 */
@SpringBootApplication
public class Exp2Application {

	public static void main(String[] args) {
		SpringApplication.run(Exp2Application.class, args);
	}

	@Component
	class Exp2CommandLineRunner implements CommandLineRunner {


		@Autowired
		private UserRepository userRepository;

		@Override
		public void run(String... args) throws Exception
		{
			Users user = new Users();
			user.setId(6);
			user.setUsername("SButler");
			user.setPassword("1234");
			user.setFirstName("Steve");
			user.setLastName("Butler");

			userRepository.save(user);

			Users user2 = new Users();
			user2.setId(7);
			user2.setUsername("TAnderson");
			user2.setPassword("5678");
			user2.setFirstName("Turner");
			user2.setLastName("Anderson");

			userRepository.save(user2);

		}
	}

}
