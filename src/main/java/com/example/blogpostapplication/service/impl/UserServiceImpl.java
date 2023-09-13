package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.UserRepository;
import com.example.blogpostapplication.security.services.UserDetailsInterfaceImpl;
import com.example.blogpostapplication.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

// TODO - write jUnit tests for this class
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String EXCEPTION_TEXT = "User with ID %d not found";
    private final UserRepository userRepository;

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
                  userDetails.getDisplayName(),
                  userDetails.getBlogPosts());
            }).orElse(null);
  }

    @Override
    public User findByUserId(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(String.format(EXCEPTION_TEXT,userId)));
    }
}
