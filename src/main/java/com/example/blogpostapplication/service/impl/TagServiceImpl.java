package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.TagService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

  private static final String EXCEPTION_TEXT = "Tag not found";
  private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);
  private final TagRepository tagRepository;

  @Override
  public Tag findTagByTagName(String tagName) {
    return tagRepository
        .findByTagName(tagName)
        .orElseThrow(
            () -> {
              logger.error("Tag  with name {} doesn't exists", tagName);
              return new RecordNotFoundException(String.format(EXCEPTION_TEXT));
            });
  }

  @Override
  public Tag createTag(String tagName) {
    Tag tag = new Tag();
    tag.setTagName(tagName);
    logger.info("New tag with name '{}' created", tagName);
    return tagRepository.save(tag);
  }

  @Override
  public List<TagDTO> getAllTags() {
    logger.info("Attempting to retrieve all tags");
    List<Tag> tags = this.tagRepository.findAll();
    return tags.stream()
        .map(tag -> TagDTO.builder().tagName(tag.getTagName()).build())
        .toList();
  }

  @Override
  public Tag findOrCreateTag(String tagName) {
    Optional<Tag> tagOptional = tagRepository.findByTagName(tagName);

    return tagOptional.orElseGet(
        () -> {
          Tag newTag = new Tag();
          newTag.setTagName(tagName);
          logger.info("New tag with name '{}' created", tagName);
          return tagRepository.save(newTag);
        });
  }
}
