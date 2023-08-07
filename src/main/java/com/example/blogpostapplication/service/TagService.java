package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.Tag;

public interface TagService {

  Tag findOrCreateTagByName(String tagName);

  void addTagsToBlogPost(Long blogPostId, Tag tag);

  void deleteTagFromBlogPost(Long blogPostId, Tag tag);
}
