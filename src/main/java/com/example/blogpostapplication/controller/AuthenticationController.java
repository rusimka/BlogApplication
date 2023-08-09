package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.payload.SignupRequest;
import com.example.blogpostapplication.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;




public AuthenticationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
}

@PostMapping("/signup")
public ResponseEntity<String> registerUser(@RequestBody SignupRequest signupRequest){
    if(userRepository.existsByUsername(signupRequest.getUsername())){
        return ResponseEntity.badRequest().body("Username already taken");
    }

    User user = new User(signupRequest.getUsername(),passwordEncoder.encode(signupRequest.getPassword()),signupRequest.getDisplayName());
    userRepository.save(user);
    return ResponseEntity.ok("User registered successfully");
}
}
