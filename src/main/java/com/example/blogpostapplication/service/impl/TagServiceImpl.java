package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.TagService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

  private static final String EXCEPTION_TEXT = "Tag not found";
  private final TagRepository tagRepository;

  @Override
  public Tag findTagByTagName(String tagName) {
    return tagRepository
        .findByTagName(tagName)
        .orElseThrow(() -> new RecordNotFoundException(String.format(EXCEPTION_TEXT)));
  }

  @Override
  public Tag createTag(String tagName) {
    Tag tag = new Tag();
    tag.setTagName(tagName);
    return tagRepository.save(tag);
  }

  @Override
  public List<TagDTO> getAllTags() {
    List<Tag> tags = this.tagRepository.findAll();
    return tags.stream()
        .map(tag -> TagDTO.builder().tagName(tag.getTagName()).build())
        .collect(Collectors.toList());
  }
}
