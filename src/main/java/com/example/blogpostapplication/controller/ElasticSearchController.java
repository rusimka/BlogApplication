package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.elasticsearch.BlogPostDocument;
import com.example.blogpostapplication.service.ElasticSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @DeleteMapping
  public void deleteAllBlogPostDocuments() {
    this.elasticSearchService.deleteAll();
  }

  @GetMapping
  public List<BlogPostDocument> searchByBlogPostTitle(
      @RequestParam(name = "blogPostTitle") String blogPostTitle) {
    return elasticSearchService.searchBlogPostByBlogPostTitle(blogPostTitle);
  }

  @GetMapping("/search-by-text")
  public List<BlogPostDocument> searchByBlogPostText(
      @RequestParam(name = "blogPostText") String blogPostText) {
    return elasticSearchService.searchBlogPostByBlogPostText(blogPostText);
  }

  @GetMapping("/search-by-tags")
  public List<BlogPostDocument> searchBlogPostByTag(@RequestParam(name = "tag") String tag) {
    return elasticSearchService.searchBlogPostByTag(tag);
  }

  @GetMapping("/search")
  public List<BlogPostDocument> searchBlogPostByBlogPostTitleAndBlogPostTextAndTag(
      @RequestParam(name = "blogPostTitle") String blogPostTitle,
      @RequestParam(name = "blogPostText") String blogPostText,
      @RequestParam(name = "tag") String tag) {
    return elasticSearchService.searchBlogPostByBlogPostTitleAndBlogPostTextAndTag(
        blogPostTitle, blogPostText, tag);
  }
}
