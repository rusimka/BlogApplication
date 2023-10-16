package com.example.blogpostapplication.security.services;

import java.util.HashSet;
import java.util.Set;

public class TokenInvalidationService {
  private static final Set<String> invalidatedTokens = new HashSet<>();

  public static void invalidateToken(String token) {
    invalidatedTokens.add(token); // Store the token in the invalidatedTokens set
  }

  public static boolean isTokenInvalidated(String token) {
    return invalidatedTokens.contains(token); // Check if token is invalidated
  }
}
