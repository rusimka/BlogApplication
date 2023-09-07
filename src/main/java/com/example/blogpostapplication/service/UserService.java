package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.User;

import java.util.Optional;

public interface UserService {

    User getLoggedUser();

    User findByUserId(Long userId);

}
