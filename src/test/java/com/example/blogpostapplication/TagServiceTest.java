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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagServiceTest {

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
  void testFindOrCreateTagByName() {
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
  void testAddTagsToBlogPost() {
    String tagName = "Technology";
    Tag tag = new Tag();
    tag.setTagName(tagName);

    tagService.addTagsToBlogPost(blogPost.getBlogPostId(), tag);

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
  void testDeleteTagFromBlogPost_TagExists() {
    Tag tagToDelete1 = new Tag();
    tagToDelete1.setTagName("Technology");
    tagRepository.save(tagToDelete1);

    Tag tagToDelete2 = new Tag();
    tagToDelete2.setTagName("Mathematics");
    tagRepository.save(tagToDelete2);

    tagService.deleteTagFromBlogPost(blogPost.getBlogPostId(), tagToDelete1);

    BlogPost updatedBlogPost =
        blogPostRepository
            .findById(blogPost.getBlogPostId())
            .orElseThrow(() -> new BlogPostDoesNotExists(blogPost.getBlogPostId()));

    assertFalse(updatedBlogPost.getTags().contains(tagToDelete1));
  }
}
