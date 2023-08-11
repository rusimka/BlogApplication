package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.SimplifiedBlogPostDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.BlogPostService;
import com.example.blogpostapplication.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {

  private final BlogPostRepository blogPostRepository;

  private final TagService tagService;

  @Override
  public BlogPost createBlogPost(BlogPost blogPost) {
    return this.blogPostRepository.save(blogPost);
  }

  @Override
  public List<SimplifiedBlogPostDTO> getAllBlogPosts() {
    List<BlogPost> allBlogPosts = blogPostRepository.findAll();
    return allBlogPosts.stream().map(this::mapToSimplifiedDTO).toList();
  }

  private SimplifiedBlogPostDTO mapToSimplifiedDTO(BlogPost blogPost) {
    SimplifiedBlogPostDTO simplifiedBlogPostDTO = new SimplifiedBlogPostDTO();
    simplifiedBlogPostDTO.setBlogPostTitle(blogPost.getBlogPostTitle());
    simplifiedBlogPostDTO.setBlogPostShortSummary(getShortSummary(blogPost.getBlogPostText()));
    return simplifiedBlogPostDTO;
  }

  private String getShortSummary(String blogPostText) {
    if (blogPostText.length() > 20) {
      return blogPostText.substring(0, 20) + " ...";
    } else {
      return blogPostText;
    }
  }

  @Override
  public BlogPost updateBlogPost(Long blogPostId, Map<String, String> blogPost) {
    BlogPost updatedBlogPost =
        this.blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    if (blogPost.containsKey("blogPostTitle")) {
      updatedBlogPost.setBlogPostTitle((String) blogPost.get("blogPostTitle"));
    }
    if (blogPost.containsKey("blogPostText")) {
      updatedBlogPost.setBlogPostText((String) blogPost.get("blogPostText"));
    }

    return blogPostRepository.save(updatedBlogPost);
  }

  @Override
  public List<BlogPost> getAllBlogPostByTagName(String tagName) {
    return Optional.ofNullable(tagService.findTagByTagName(tagName))
        .map(Tag::getBlogPosts)
        .orElse(Collections.emptyList());
  }

  @Override
  public void addTagsToBlogPost(Long blogPostId, Tag tag) {

    BlogPost blogPost =
        blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    Tag newTag = tagService.findTagByTagName(tag.getTagName());

    blogPost.getTags().add(newTag);
    blogPostRepository.save(blogPost);
  }

  @Override
  public void deleteTagFromBlogPost(Long blogPostId, Tag tag) {
    BlogPost blogPost =
        blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    Tag tagToDelete = tagService.findTagByTagName(tag.getTagName());

    blogPost.getTags().remove(tagToDelete);
    blogPostRepository.save(blogPost);
  }
}
