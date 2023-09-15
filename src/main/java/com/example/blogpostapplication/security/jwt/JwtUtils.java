package com.example.blogpostapplication.security.jwt;

import com.example.blogpostapplication.config.JwtConfigProperties;
import com.example.blogpostapplication.security.services.UserDetailsInterfaceImpl;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

/*
Two functionalities:
1. Generate a JWT from username
2. Get username from JWT
3. Validate a jwt
 */
public class JwtUtils {

  private static final Set<String> invalidatedTokens =
      new HashSet<>(); // Store invalidated tokens here
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
  private static JwtConfigProperties jwtConfigProperties;

  private JwtUtils() {}

  public static void setJwtConfigProperties(JwtConfigProperties properties) {
    jwtConfigProperties = properties;
  }

  public static String generateJwtToken(Authentication authentication) {
    UserDetailsInterfaceImpl userPrincipal =
        (UserDetailsInterfaceImpl) authentication.getPrincipal();
    String jwtToken =  Jwts.builder()
        .setSubject(userPrincipal.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtConfigProperties.getJwtExpirationMs()))
        .signWith(SignatureAlgorithm.HS512, jwtConfigProperties.getJwtSecret())
        .compact();
    System.out.println(jwtToken);
    return jwtToken;
  }

  public static String getUserNameFromJwtToken(String token) {
    return (String)
        Jwts.parser()
            .setSigningKey(jwtConfigProperties.getJwtSecret())
            .parseClaimsJws(token)
            .getBody()
            .get("sub");
  }

  public static boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtConfigProperties.getJwtSecret()).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    }
    return false;
  }

  public static void invalidateToken(String token) {
    invalidatedTokens.add(token); // Store the token in the invalidatedTokens set
  }

  public static boolean isTokenInvalidated(String token) {
    return invalidatedTokens.contains(token); // Check if token is invalidated
  }
}
