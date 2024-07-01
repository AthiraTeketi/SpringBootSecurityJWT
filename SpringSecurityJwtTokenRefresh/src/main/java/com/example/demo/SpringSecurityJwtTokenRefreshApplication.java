package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.RepositoryForUser.UserRepository;

@SpringBootApplication
public class SpringSecurityJwtTokenRefreshApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtTokenRefreshApplication.class, args);
	}

	public void run(String... args) {
		
		User adminAccount = userRepository.findByRole(Role.Admin);
		
		if(null == adminAccount) {
			
			User user = new User();
			
			user.setId(1);
			user.setEmail("adminrole@gmail.com");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setRole(Role.Admin);
			
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			userRepository.save(user);


		}
		
	}

}
