package com.example.demo.ServicePackage;

import com.example.demo.Entity.User;

import Dto.JwtAuthenticationResponse;
import Dto.RefreshTokenRequest;
import Dto.SignInRequest;
import Dto.SignUpRequest;

public interface AuthenticationService {

	User signup(SignUpRequest signUpRequest);
	JwtAuthenticationResponse signIn(SignInRequest signInRequest) throws IllegalAccessException;
	JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
