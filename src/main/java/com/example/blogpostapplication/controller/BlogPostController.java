package com.example.blogpostapplication.controller;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.service.BlogPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog-posts")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Blog Post", description = "Blog Post APIs")
public class BlogPostController {

  private final BlogPostService blogPostService;

  @PostMapping
  @Operation(
      description = "Endpoint for creating blog post",
      summary = "Create Blog Post",
      responses = {
        @ApiResponse(description = "Successful creation of Blog Post", responseCode = "201"),
        @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "401")
      })
  public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPostDTO blogPostDTO) {
    return ResponseEntity.ok(blogPostService.createBlogPost(blogPostDTO));
  }

  @GetMapping
  @Operation(
      summary = "Get All Blog Posts",
      description = "Endpoint to retrieve a list of all blog posts",
      responses = {
        @ApiResponse(
            description = "Successfully returned list of blog posts",
            responseCode = "200",
            content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = BlogPostDTO.class))))
      })
  public Page<BlogPostDTO> getAllBlogPosts(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    return this.blogPostService.getAllBlogPosts(page, size);
  }

  @PatchMapping("/{blogPostId}")
  @Operation(
      summary = "Update Blog Post for given ID",
      description = "Update blog post title,or text, or both",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully updated blog post",
            content =
                @Content(
                    schema = @Schema(implementation = BlogPost.class),
                    mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Unauthorized / Invalid token"),
        @ApiResponse(responseCode = "404", description = "Not found")
      })
  public ResponseEntity<BlogPost> updateBlogPostTitleAndText(
      @PathVariable Long blogPostId, @RequestBody BlogPostDTO blogPostDTO) {
    BlogPost blogPost = blogPostService.updateBlogPost(blogPostId, blogPostDTO);
    return ResponseEntity.ok(blogPost);
  }

  @GetMapping("/{tagName}")
  @Operation(
      summary = "Get All Blog Posts By Tag Name",
      description = "Endpoint to retrieve a list of all blog posts by given tag name",
      responses = {
        @ApiResponse(
            description = "Successfully returned list of blog posts",
            responseCode = "200",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = BlogPost.class)))),
        @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "401"),
        @ApiResponse(description = "Tag not found", responseCode = "404")
      })
  public List<BlogPost> getAllBlogPostByTagName(@PathVariable String tagName) {
    return this.blogPostService.getAllBlogPostByTagName(tagName);
  }

  @PutMapping("/{blogPostId}/tags")
  @Operation(
      summary = "Add tag to blog post",
      description = "Endpoint to add tag to blog post for a given blog post id",
      responses = {
        @ApiResponse(description = "Successfully added tag to blog", responseCode = "200"),
        @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "401"),
        @ApiResponse(description = "Blog not found", responseCode = "404")
      })
  public ResponseEntity<String> addTagsToBlog(
      @PathVariable Long blogPostId, @RequestBody TagDTO tagDTO) {
    this.blogPostService.addTagsToBlogPost(blogPostId, tagDTO);
    return ResponseEntity.ok("Tags are added successfully");
  }

  @DeleteMapping("/{blogPostId}/tags")
  @Operation(
      summary = "Delete tag from blog post",
      description = "Endpoint to delete tag from blog post by given blog post id",
      responses = {
        @ApiResponse(description = "Successfully deleted tag from blog post", responseCode = "200"),
        @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "401"),
        @ApiResponse(description = "Blog not found", responseCode = "404"),
        @ApiResponse(description = "Tag not found", responseCode = "404")
      })
  public ResponseEntity<String> deleteTagFromBlog(
      @PathVariable Long blogPostId, @RequestBody TagDTO tagDTO) {
    this.blogPostService.deleteTagFromBlogPost(blogPostId, tagDTO);
    return ResponseEntity.ok("Tag is deleted from the blog post");
  }

  @GetMapping("/users/{userId}/blogPosts")
  @Operation(
      summary = "Get all blog posts for user",
      description = "Endpoint to retrieve a list of blog posts for user with given userId",
      responses = {
        @ApiResponse(
            description = "Successfully deleted tag from blog post",
            responseCode = "200",
            content =
                @Content(
                    array = @ArraySchema(schema = @Schema(implementation = BlogPostDTO.class)))),
        @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "401"),
      })
  public List<BlogPostDTO> getAllBlogPostsByUserId(@PathVariable Long userId) {
    return this.blogPostService.getAllBlogPostsByUserId(userId);
  }

  @DeleteMapping
  @Operation(
      summary = "Delete all blog posts for user",
      description = "Endpoint to delete all blog posts for the currently logged user",
      responses = {
        @ApiResponse(description = "Successfully deleted all blog posts", responseCode = "200"),
        @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "401"),
        @ApiResponse(description = "No blog posts found for this user", responseCode = "404"),
      })
  public ResponseEntity<String> deleteAllBlogPostByUserId() {
    this.blogPostService.deleteAllBlogPostsByUserId();
    return ResponseEntity.ok("All blog posts are deleted");
  }

  @DeleteMapping("/{blogPostId}")
  @Operation(
      summary = "Delete blog post by blog posts id",
      description = "Endpoint to delete blog post by blog post id",
      responses = {
        @ApiResponse(description = "Successfully deleted blog posts", responseCode = "200"),
        @ApiResponse(description = "Unauthorized / Invalid token", responseCode = "401"),
        @ApiResponse(description = "No blog posts found for this user", responseCode = "404"),
      })
  public ResponseEntity<String> deleteBlogPostByBlogPostId(@PathVariable Long blogPostId) {
    this.blogPostService.deleteBlogPostByBlogPostId(blogPostId);
    return ResponseEntity.ok("Blog post is successfully deleted");
  }
}
