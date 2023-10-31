package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.elasticsearch.BlogPostDocument;
import com.example.blogpostapplication.service.ElasticSearchService;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog-posts-documents")
@RequiredArgsConstructor
public class ElasticSearchController {

  private final ElasticSearchService elasticSearchService;

  @PostMapping
  public ResponseEntity<BlogPostDocument> createBlogPost(
      @RequestBody BlogPostDocument blogPostDocument) {
    return ResponseEntity.ok(elasticSearchService.createBlogPostDocument(blogPostDocument));
  }

}
