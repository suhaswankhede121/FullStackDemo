package com.taskmgnt.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	 static SecretKey key=Keys.hmacShaKeyFor(JWTConstant.SECRET_KEY.getBytes());
	 
	 
	 
	 public static String generateToken(Authentication auth)
	 {
		 Collection<? extends GrantedAuthority > authorities=auth.getAuthorities();
		 String role=populatesAuthorities(authorities);
		 
		 String jwt=Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+86500000)).
				 claim("email",auth.getName()).
				 claim("authorities",role)
				 .signWith(key).
				 compact();
		    return jwt;
		  
		 
	 }
	 
	 public static String getEmailFromJwtToken(String jwt) {
		 jwt=jwt.substring(7);
		 Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		 String email=String.valueOf(claims.get("email"));
		 return email;
	 }
	 
	 
	 public static String populatesAuthorities( Collection<? extends GrantedAuthority> collection) {
		 Set<String> auths=new HashSet<>();
		 
		 for(GrantedAuthority authority:collection) {
			 auths.add(authority.getAuthority());
		 }
		 
		 return String.join(",", auths);
	 }
}
