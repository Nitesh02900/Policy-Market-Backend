package com.techlabs.insurance.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.techlabs.insurance.exception.UserAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {


	@Value("${app.jwt-secret}")
	private String jwtSecret ;
	
//	@Value("{app-jwt-expiration-milliseconds}") 
	private long jwtExpirationDate=604800000; 
	
	public String generateToken(Authentication authentication)
	{
		System.out.println(authentication);
		String username = authentication.getName();
		Date currentDate = new Date();
		
		Date expireDate = new Date(currentDate.getTime()+jwtExpirationDate);
		
				
		String token =Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.claim("role",authentication.getAuthorities())
				.signWith(key())
				.compact();
				
				
		return token;
		
	}
	private Key key()
	{
		System.out.println("jwtsecret"+jwtSecret);
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public String getUsername(String token)
	{
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		System.out.println("---------------->");
		System.out.println(claims.get("role"));
		String username = claims.getSubject();
		return username;
	}
	
	public boolean validationToken(String token) {
		System.out.println("token genrated by---"+token);
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parse(token);
			return true;
			
		}
		catch (MalformedJwtException ex)
		{
			throw new UserAPIException("Invalid JWT token",HttpStatus.BAD_REQUEST);
		}
		catch(ExpiredJwtException ex)
		{
			throw new UserAPIException("Expired JWT token",HttpStatus.BAD_REQUEST);
		}
		catch(UnsupportedJwtException ex)  
		 { 
		   throw new UserAPIException("Unsupported Jwt token", HttpStatus.BAD_REQUEST); 
		  } 
		catch(IllegalArgumentException ex)
		{
			throw new UserAPIException("Expired JWT token",HttpStatus.BAD_REQUEST);
		}
		catch(Exception e)
		{
			throw new UserAPIException("Invalid credentials",HttpStatus.BAD_REQUEST);
			
		}
	}
	
}
