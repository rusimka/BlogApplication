package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public User getCurrentLoggedUser() {
    return this.userService.getLoggedUser();
  }
}
