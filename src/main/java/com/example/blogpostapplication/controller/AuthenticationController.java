package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserRepository userRepository;


public AuthenticationController(UserRepository userRepository) {
    this.userRepository = userRepository;
}

@PostMapping("/signup")
public ResponseEntity<String> registerUser(@RequestBody User user){
    if(userRepository.existsByUsername(user.getUsername())){
        return ResponseEntity.badRequest().body("Username already taken");
    }
    userRepository.save(user);
    return ResponseEntity.ok("User registered successfully");
}
}
