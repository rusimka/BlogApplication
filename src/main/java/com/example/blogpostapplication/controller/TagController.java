package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.service.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagService tagService;

  @GetMapping
  public List<TagDTO> getAllTags() {
    return this.tagService.getAllTags();
  }
}
