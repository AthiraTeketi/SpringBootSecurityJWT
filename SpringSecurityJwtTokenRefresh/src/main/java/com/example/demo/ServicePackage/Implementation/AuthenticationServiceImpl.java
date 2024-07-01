package com.example.demo.ServicePackage.Implementation;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.RepositoryForUser.UserRepository;
import com.example.demo.ServicePackage.AuthenticationService;
import com.example.demo.ServicePackage.JwtService;

import Dto.JwtAuthenticationResponse;
import Dto.RefreshTokenRequest;
import Dto.SignInRequest;
import Dto.SignUpRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

    }
	
//	Accept the signUp for  user and save it in DB
	
	public User signup(SignUpRequest signUpRequest) {
		
		User userObj = new User();
		userObj.setId(signUpRequest.getId());
		userObj.setEmail(signUpRequest.getEmail());
		userObj.setFirstName(signUpRequest.getFirstName());
		userObj.setLastName(signUpRequest.getLastName());
		userObj.setRole(Role.User);
		userObj.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));


		return userRepository.save(userObj);
	}
	
	public JwtAuthenticationResponse signIn(SignInRequest signInRequest) throws IllegalAccessException {
		
		/*this will authenticate the user by email and password in DB.
		If it didn't find any email or password then it will throw error*/
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
				signInRequest.getPassword())); 
		/*to generate token after verifying the user */
		
		var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalAccessException("Invalid email or Password"));
		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
		
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt, refreshToken);
			
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		return jwtAuthenticationResponse; 
		
	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		
		String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
		User user =  userRepository.findByEmail(userEmail).orElseThrow();
		
		if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user))//if user is present in DB 
		{
			var jwt = jwtService.generateToken(user); //this will generate new token with new expiration date.
			
//			Giving Response
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt, refreshTokenRequest.getToken());
			
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			return jwtAuthenticationResponse; 
		}
		return null;
		
	}
}
