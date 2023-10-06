package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.payload.JwtResponse;
import com.example.blogpostapplication.model.payload.LoginRequest;
import com.example.blogpostapplication.model.payload.SignupRequest;
import com.example.blogpostapplication.repository.UserRepository;
import com.example.blogpostapplication.security.jwt.JwtUtils;
import com.example.blogpostapplication.security.services.UserDetailsInterfaceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Authenticate user", description = "APIs for register and login the user")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  @PostMapping("/signup")
  @Operation(
      description = "Endpoint for user registration",
      summary = "Register User",
      responses = {
        @ApiResponse(description = "User registered successfully", responseCode = "201"),
        @ApiResponse(description = "Username already exists", responseCode = "400")
      })
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
  @Operation(
      description = "Endpoint for user login",
      summary = "Login User",
      responses = {
        @ApiResponse(description = "User logged in successfully", responseCode = "200"),
        @ApiResponse(description = "Wrong Credentials", responseCode = "400")
      })
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = JwtUtils.generateJwtToken(authentication);

    UserDetailsInterfaceImpl userDetails = (UserDetailsInterfaceImpl) authentication.getPrincipal();

    return ResponseEntity.ok(
        new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername()));
  }

  @PostMapping("/logout")
  @Operation(
      description = "Endpoint for user logout",
      summary = "Logout user",
      responses = {
        @ApiResponse(description = "User logged in successfully", responseCode = "200"),
      })
  public ResponseEntity<String> logout(HttpServletRequest request) {
    return ResponseEntity.ok("Logout successful");
  }
}
