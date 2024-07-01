package com.example.demo.ServicePackage.Implementation;

import java.security.Key;
import java.sql.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.ServicePackage.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtServiceImpl implements JwtService{

//	For generating Token by taking userName (In this program user-name is the userEmail)
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24)) // generated token is valid for 1 day.
				.signWith(getSignatureKey(),SignatureAlgorithm.HS256)
				.compact();
		
	}

	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 604800000)) //refreshed token is valid for 7 days.
				.signWith(getSignatureKey(),SignatureAlgorithm.HS256)
				.compact();
		
	}
	
//	to extract username claim from token 
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
		/*Claims::getSubject here getSubject is method in Claims so it will get the subject 
		of jwt token here subject for token is the username (payload which contains emailetc..) used for tocken generation*/
		
	}
	
	
//	this extractClaim method is used to extract a particular claim 
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	
//	this method is used to generate a SignatureKey and returns a key 
	private Key getSignatureKey() {

	byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
	return Keys.hmacShaKeyFor(key);
			
	}
		
	
/*	return all claims (all feilds/data of user)from the tocken (claims means datamembers of pojo class 
	and tocken details like issued time etc... )*/
	private Claims extractAllClaims(String token) {
		
		
		return Jwts.parserBuilder().setSigningKey(getSignatureKey())
				.build().parseClaimsJws(token).getBody();
	}

	
// Token validation for the signIn user from DB	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		
		final String userName = extractUserName(token); //from token extracting the userName(email)
		// checking the email is equal in the DB of the signIn user. && calling isTokenExpired method to check token expiration time.
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
		
		}
	
	
//	this isTokenExpired will check the token expiration time before the current Date.
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date(0));
		
	}
}


