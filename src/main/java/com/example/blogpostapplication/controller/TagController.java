package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.service.TagService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Tag(name = "Tags", description = "Tag APIs")
public class TagController {

  private final TagService tagService;

  @GetMapping
  @Operation(
          summary = "Get All Tags",
          description = "Endpoint to retrieve a list of all tags",
          responses = {
                  @ApiResponse(
                          description = "Successfully returned list of tags",
                          responseCode = "200",
                          content =
                          @Content(
                                  array = @ArraySchema(schema = @Schema(implementation = TagDTO.class))))
          })
  public List<TagDTO> getAllTags() {
    return this.tagService.getAllTags();
  }
}
