package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.SimplifiedBlogPostDTO;
import com.example.blogpostapplication.service.BlogPostService;
import com.example.blogpostapplication.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog-post")
public class BlogPostController {

  private final BlogPostService blogPostService;

  private final TagService tagService;

  public BlogPostController(BlogPostService blogPostService, TagService tagService) {
    this.blogPostService = blogPostService;
    this.tagService = tagService;
  }

  @PostMapping("/create-blog-post")
  public ResponseEntity<String> createBlogPost(@RequestBody BlogPost blogPost) {
    this.blogPostService.createBlogPost(blogPost);
    return ResponseEntity.ok("Blog post created successfully");
  }

  @GetMapping("/get-all-blog-posts")
  public List<SimplifiedBlogPostDTO> getAllBlogPosts(){
    return this.blogPostService.getAllBlogPosts();
}

@PutMapping("/update-title-and-text/{blogPostId}")
  public ResponseEntity<BlogPost> updateBlogPostTitleAndText(@PathVariable Long blogPostId, @RequestBody BlogPost blogPost){
    return ResponseEntity.ok(blogPostService.updateBlogPost(blogPostId,blogPost));
}

@PutMapping(value = "/add-tags/{blogPostId}")
  public ResponseEntity<String> addTagsToBlog(@PathVariable Long blogPostId, @RequestBody Tag tag){
    this.tagService.addTagsToBlogPost(blogPostId,tag);
    return ResponseEntity.ok("Tags are added successfully");
}

  @DeleteMapping("/delete-tag/{blogPostId}")
  public ResponseEntity<String> deleteTagFromBlog(@PathVariable Long blogPostId, @RequestBody Tag tag) {
    this.tagService.deleteTagFromBlogPost(blogPostId, tag);
    return ResponseEntity.ok("Tag is deleted from the blog post");
  }




}
