package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.payload.JwtResponse;
import com.example.blogpostapplication.model.payload.LoginRequest;
import com.example.blogpostapplication.model.payload.SignupRequest;
import com.example.blogpostapplication.repository.UserRepository;
import com.example.blogpostapplication.security.jwt.JwtUtils;
import com.example.blogpostapplication.security.services.UserDetailsInterfaceImpl;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired private final UserRepository userRepository;

  @Autowired private final PasswordEncoder passwordEncoder;

  @Autowired JwtUtils jwtUtils;

  public AuthenticationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@RequestBody SignupRequest signupRequest) {
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Username already taken");
    }

    User user =
        new User(
            signupRequest.getUsername(),
            passwordEncoder.encode(signupRequest.getPassword()),
            signupRequest.getDisplayName());
    userRepository.save(user);
    return ResponseEntity.ok("User registered successfully");
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsInterfaceImpl userDetails = (UserDetailsInterfaceImpl) authentication.getPrincipal();

    return ResponseEntity.ok(
        new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername()));
  }
}
