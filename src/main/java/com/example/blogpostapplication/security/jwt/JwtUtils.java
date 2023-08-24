package com.example.blogpostapplication.security.jwt;

import com.example.blogpostapplication.security.services.UserDetailsInterfaceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
Two functionalities:
1. Generate a JWT from username
2. Get username from JWT
3. Validate a jwt
 */
@Component
public class JwtUtils {

  @Value("${blogpost.app.jwtSecret}")
  private String jwtSecret;

  @Value("${blogpost.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  private static final Set<String> invalidatedTokens = new HashSet<>(); // Store invalidated tokens here


  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  public String generateJwtToken(Authentication authentication) {
    UserDetailsInterfaceImpl userPrincipal =
        (UserDetailsInterfaceImpl) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(userPrincipal.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return (String) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("sub");
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public void invalidateToken(String token) {
    invalidatedTokens.add(token); // Store the token in the invalidatedTokens set
  }

  public boolean isTokenInvalidated(String token) {
    return invalidatedTokens.contains(token); // Check if token is invalidated
  }


}
