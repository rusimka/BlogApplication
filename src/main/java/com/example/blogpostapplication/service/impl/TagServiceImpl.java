package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;
  private final BlogPostRepository blogPostRepository;

  @Override
  public Tag findTagByTagName(String tagName) {
    return tagRepository.findByTagName(tagName).orElseGet(() -> createTag(tagName));
  }

  @Override
  public Tag createTag(String tagName) {
    Tag tag = new Tag();
    tag.setTagName(tagName);
    tagRepository.save(tag);
    return tag;
  }
}
