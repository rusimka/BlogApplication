package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.Tag;

import java.util.Optional;

public interface TagService {
  Tag findOrCreateTagByName(String tagName);
}
