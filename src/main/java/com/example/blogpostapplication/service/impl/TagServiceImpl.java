package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  @Override
  public Tag findTagByTagName(String tagName) {
    return tagRepository.findByTagName(tagName).orElseGet(() -> createTag(tagName));
  }

  @Override
  public Tag createTag(String tagName) {
    Tag tag = new Tag();
    tag.setTagName(tagName);
    return tagRepository.save(tag);
  }
}
