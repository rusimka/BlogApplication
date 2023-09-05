package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.exceptions.UserNotAuthenticatedException;
import com.example.blogpostapplication.security.services.UserDetailsInterfaceImpl;
import com.example.blogpostapplication.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  @Override
  public User getLoggedUser() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .filter(Authentication::isAuthenticated)
        .map(
            authentication -> {
              UserDetailsInterfaceImpl userDetails =
                  (UserDetailsInterfaceImpl) authentication.getPrincipal();
              return new User(
                  userDetails.getUserId(),
                  userDetails.getUsername(),
                  userDetails.getPassword(),
                  userDetails.getDisplayName());
            }).orElseThrow(() -> new UserNotAuthenticatedException());
  }
}
