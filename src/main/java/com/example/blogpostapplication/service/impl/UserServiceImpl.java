package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.model.exceptions.UserNotAuthenticatedException;
import com.example.blogpostapplication.model.exceptions.UserNotFoundException;
import com.example.blogpostapplication.repository.UserRepository;
import com.example.blogpostapplication.security.services.UserDetailsInterfaceImpl;
import com.example.blogpostapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final String EXCEPTION_TEXT = "User with ID %d not found";


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
            })
        .orElseThrow(() -> new UserNotAuthenticatedException());

    // treba i ovaa funkcija da ja dosredis, ne treba da frlis exception so ogled na toa so ako ne ti e logiran userot direktno
      // odi vo commence metodot
      // treba samo da proveris dokolku e avtentficiran da ti vrati user i toa e toa a mozebi i vopsto da ne proveurv as
  }

    @Override
    public User findByUserId(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(String.format(EXCEPTION_TEXT,userId)));
    }
}
