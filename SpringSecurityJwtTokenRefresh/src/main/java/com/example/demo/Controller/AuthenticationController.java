package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.ServicePackage.AuthenticationService;

import Dto.JwtAuthenticationResponse;
import Dto.RefreshTokenRequest;
import Dto.SignInRequest;
import Dto.SignUpRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService=authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.signup(signUpRequest));
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) throws IllegalAccessException{
		
		return ResponseEntity.ok(authenticationService.signIn(signInRequest));
		
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
		
	}
}
