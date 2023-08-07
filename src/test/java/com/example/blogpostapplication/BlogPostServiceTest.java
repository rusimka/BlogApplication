package com.example.blogpostapplication;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.dto.SimplifiedBlogPostDTO;
import com.example.blogpostapplication.model.exceptions.BlogPostDoesNotExists;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.service.BlogPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class BlogPostServiceTest {

   @Autowired
    private  BlogPostService blogPostService;

   @Autowired
   private  BlogPostRepository blogPostRepository;


    @Test
  public void testCreateBlogPost() {
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle("Test Blog Post");
    blogPost.setBlogPostText("This is a test blog post.");
    this.blogPostRepository.save(blogPost);

    BlogPost retrievedBlogPost =
        blogPostRepository
            .findById(blogPost.getBlogPostId())
            .orElseThrow(() -> new BlogPostDoesNotExists(blogPost.getBlogPostId()));
    assertEquals(blogPost.getBlogPostTitle(), retrievedBlogPost.getBlogPostTitle());
    assertEquals(blogPost.getBlogPostText(), retrievedBlogPost.getBlogPostText());
  }

  @Test
  public void testGetAllBlogPosts() {
    BlogPost blogPost1 = new BlogPost();
    blogPost1.setBlogPostTitle("Blog Post 1");
    blogPost1.setBlogPostText("Blog 1 text");
    blogPostRepository.save(blogPost1);

    BlogPost blogPost2 = new BlogPost();
    blogPost2.setBlogPostTitle("Blog Post 2");
    blogPost2.setBlogPostText("Blog 2 text");
    blogPostRepository.save(blogPost2);

    List<SimplifiedBlogPostDTO> blogPosts = blogPostService.getAllBlogPosts();

    assertEquals(2, blogPosts.size());

    assertEquals("Blog Post 1", blogPosts.get(0).getBlogPostTitle());
    assertEquals("Blog 1", blogPosts.get(0).getBlogPostShortSummary());

    assertEquals("Blog Post 2", blogPosts.get(1).getBlogPostTitle());
    assertEquals("Blog 2", blogPosts.get(1).getBlogPostShortSummary());
  }

  @Test
  public void testUpdateBlogPost() {
    // Create a sample BlogPost and save it to the repository
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle("Original Title");
    blogPost.setBlogPostText("Original Text");
    blogPostRepository.save(blogPost);

    // Call the service method to update the BlogPost
    BlogPost updatedBlogPost = new BlogPost();
    updatedBlogPost.setBlogPostTitle("Updated Title");
    updatedBlogPost.setBlogPostText("Updated Text");
    BlogPost result = blogPostService.updateBlogPost(blogPost.getBlogPostId(), updatedBlogPost);

    // Assert that the returned BlogPost is the same as the one returned by the repository
    assertEquals(updatedBlogPost.getBlogPostTitle(), result.getBlogPostTitle());
    assertEquals(updatedBlogPost.getBlogPostText(), result.getBlogPostText());
  }

    @Test
    public void testUpdateNonExistentBlogPost() {
        // Attempt to update a non-existent BlogPost
        BlogPost updatedBlogPost = new BlogPost();
        updatedBlogPost.setBlogPostTitle("Updated Title");
        updatedBlogPost.setBlogPostText("Updated Text");

        // Assert that the BlogPostDoesNotExists exception is thrown
        assertThrows(BlogPostDoesNotExists.class,
                () -> blogPostService.updateBlogPost(999L, updatedBlogPost));
    }
}
