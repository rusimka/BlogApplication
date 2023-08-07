package com.example.blogpostapplication;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.exceptions.BlogPostDoesNotExists;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestServiceTest {

  @Autowired private TagRepository tagRepository;

  @Autowired private BlogPostRepository blogPostRepository;

  @Autowired private TagService tagService;

  private BlogPost blogPost;

  @BeforeEach
  public void setup() {
    blogPost = new BlogPost();
    blogPost.setBlogPostTitle("Test Blog Post");
    blogPost.setBlogPostText("This is a test blog post.");
    blogPostRepository.save(blogPost);
  }


  @Test
  public void testFindOrCreateTagByName_TagExists() {
    String tagName = "Technology";
    Tag existingTag = new Tag();
    existingTag.setTagId(1L);
    existingTag.setTagName(tagName);
    tagRepository.save(existingTag);

    Tag tag = tagService.findOrCreateTagByName(tagName);

    assertNotNull(tag);
    assertEquals(existingTag, tag);
  }

  @Test
  public void testFindOrCreateTagByName_TagDoesNotExist() {
    String tagName = "Technology";

    Tag tag = tagService.findOrCreateTagByName(tagName);

    assertNotNull(tag);
    assertEquals(tagName, tag.getTagName());
  }

  @Test
  public void testAddTagsToBlogPost_TagDoesNotExist() {
    String tagName = "Technology";
    Tag tag = new Tag();
    tag.setTagName(tagName);

    tagService.addTagsToBlogPost(blogPost.getBlogPostId(),tag);

    BlogPost updatedBlogPost =
        blogPostRepository
            .findById(blogPost.getBlogPostId())
            .orElseThrow(() -> new BlogPostDoesNotExists(blogPost.getBlogPostId()));
    assertNotNull(updatedBlogPost);
    List<Tag> tags = updatedBlogPost.getTags();
    assertNotNull(tags);
    assertEquals(1, tags.size());
    assertEquals(tagName, tags.get(0).getTagName());
  }

  @Test
  public void testAddTagsToBlogPost_TagExists() {
    String tagName = "Technology";
    Tag existingTag = new Tag();
    existingTag.setTagName(tagName);
    tagRepository.save(existingTag);

    tagService.addTagsToBlogPost(blogPost.getBlogPostId(), existingTag);

    BlogPost updatedBlogPost =
        blogPostRepository
            .findById(blogPost.getBlogPostId())
            .orElseThrow(() -> new BlogPostDoesNotExists(blogPost.getBlogPostId()));
    List<Tag> tags = updatedBlogPost.getTags();
    assertNotNull(tags);
    assertEquals(1, tags.size());
    assertEquals(tagName, tags.get(0).getTagName());
  }
}
