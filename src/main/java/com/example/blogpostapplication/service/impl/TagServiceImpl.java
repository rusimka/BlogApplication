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

  //  public Optional<Tag> findTagByTagName(String tagName) {
  //    Optional<Tag> tag = tagRepository.findByTagName(tagName);
  //    if (tag.isPresent()) {
  //      return tag;
  //    } else {
  //      this.createTag(tagName);
  //    }
  //    return Optional.empty();
  //  }
  //
  //  public Tag createTag(String tagName) {
  //    Tag tag = new Tag();
  //    tag.setTagName(tagName);
  //    tagRepository.save(tag);
  //    return tag;
  //  }

  @Override
  public Tag findOrCreateTagByName(String tagName) {
    return tagRepository
        .findByTagName(tagName)
        .orElseGet(
            () -> {
              Tag newTag = new Tag();
              newTag.setTagName(tagName);
              tagRepository.save(newTag);
              return newTag;
            });
  }
}
