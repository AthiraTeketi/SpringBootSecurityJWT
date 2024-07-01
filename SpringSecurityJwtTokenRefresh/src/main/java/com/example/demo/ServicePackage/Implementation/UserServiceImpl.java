package com.example.demo.ServicePackage.Implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.RepositoryForUser.UserRepository;
import com.example.demo.ServicePackage.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
	}
	
	@Override
	public  UserDetailsService userDetailsService() {
		return new UserDetailsService(){

			@Override
			public UserDetails loadUserByUsername(String username) {
			    return userRepository.findByEmail(username)
			            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
			}

			
		};
	}

}
