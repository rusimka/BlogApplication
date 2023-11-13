package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.TagDTO;

import java.util.List;

public interface TagService {
  Tag findTagByTagName(String tagName);

  Tag createTag(String tagName);

  List<TagDTO> getAllTags();

  Tag findOrCreateTag(String tagName);

}
