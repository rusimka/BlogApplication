package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.SimplifiedBlogPostDTO;
import com.example.blogpostapplication.service.BlogPostService;
import com.example.blogpostapplication.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blog-posts")
@AllArgsConstructor
public class BlogPostController {

  private final BlogPostService blogPostService;

  private final TagService tagService;

  @PostMapping
  public ResponseEntity<String> createBlogPost(@RequestBody BlogPost blogPost) {
    this.blogPostService.createBlogPost(blogPost);
    return ResponseEntity.ok("Blog post created successfully");
  }

  @GetMapping
  public List<SimplifiedBlogPostDTO> getAllBlogPosts() {
    return this.blogPostService.getAllBlogPosts();
  }

  @PatchMapping("/{blogPostId}")
  public ResponseEntity<BlogPost> updateBlogPostTitleAndText(
      @PathVariable Long blogPostId, @RequestBody Map<String, String> blogPost) {
    return ResponseEntity.ok(blogPostService.updateBlogPost(blogPostId, blogPost));
  }

  @GetMapping("/tags/{tagName}")
  public List<BlogPost> getAllBlogPostByTagName(@PathVariable String tagName) {
    return this.blogPostService.getAllBlogPostByTagName(tagName);
  }

  @PutMapping("/{blogPostId}/tags")
  public ResponseEntity<String> addTagsToBlog(@PathVariable Long blogPostId, @RequestBody Tag tag) {
    this.blogPostService.addTagsToBlogPost(blogPostId, tag);
    return ResponseEntity.ok("Tags are added successfully");
  }

  @DeleteMapping("/{blogPostId}/tags")
  public ResponseEntity<String> deleteTagFromBlog(
      @PathVariable Long blogPostId, @RequestBody Tag tag) {
    this.blogPostService.deleteTagFromBlogPost(blogPostId, tag);
    return ResponseEntity.ok("Tag is deleted from the blog post");
  }
}
