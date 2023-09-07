package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.service.BlogPostService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog-posts")
@AllArgsConstructor
public class BlogPostController {

  private final BlogPostService blogPostService;

  @PostMapping
  public ResponseEntity<String> createBlogPost(@RequestBody BlogPostDTO blogPostDTO) {
    this.blogPostService.createBlogPost(blogPostDTO);
    return ResponseEntity.ok("Blog post created successfully");
  }

  @GetMapping
  public List<BlogPostDTO> getAllBlogPosts() {
    return this.blogPostService.getAllBlogPosts();
  }

  @PatchMapping("/{blogPostId}")
  public ResponseEntity<BlogPost> updateBlogPostTitleAndText(
      @PathVariable Long blogPostId, @RequestBody BlogPostDTO blogPostDTO) {
    return ResponseEntity.ok(blogPostService.updateBlogPost(blogPostId, blogPostDTO));
  }

  @GetMapping("/tags/{tagName}")
  public List<BlogPost> getAllBlogPostByTagName(@PathVariable String tagName) {
    return this.blogPostService.getAllBlogPostByTagName(tagName);
  }

  @PutMapping("/{blogPostId}/tags")
  public ResponseEntity<String> addTagsToBlog(
      @PathVariable Long blogPostId, @RequestBody TagDTO tagDTO) {
    this.blogPostService.addTagsToBlogPost(blogPostId, tagDTO);
    return ResponseEntity.ok("Tags are added successfully");
  }

  @DeleteMapping("/{blogPostId}/tags")
  public ResponseEntity<String> deleteTagFromBlog(
      @PathVariable Long blogPostId, @RequestBody TagDTO tagDTO) {
    this.blogPostService.deleteTagFromBlogPost(blogPostId, tagDTO);
    return ResponseEntity.ok("Tag is deleted from the blog post");
  }

  @GetMapping("/users/{userId}")
  public List<BlogPostDTO> getAllBlogPostsByUserId(@PathVariable Long userId) {
    return this.blogPostService.getAllBlogPostsByUserId(userId);
  }

  @DeleteMapping("/delete-all")
  public ResponseEntity<String> deleteAllBlogPostByUserId() {
    this.blogPostService.deleteAllBlogPostsByUserId();
    return ResponseEntity.ok("All blog posts are deleted");
  }

  @DeleteMapping("/{blogPostId}")
  public ResponseEntity<String> deleteBlogPostByBlogPostId(@PathVariable Long blogPostId) {
    this.blogPostService.deleteBlogPostByBlogPostId(blogPostId);
    return ResponseEntity.ok("Blog post is successfully deleted");
  }
}
