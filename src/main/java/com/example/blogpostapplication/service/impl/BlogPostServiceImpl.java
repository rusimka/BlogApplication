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

  private final TagRepository tagRepository;

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

  //  @Override
  //  public List<BlogPost> getAllBlogPostByTagName(String tagName) {
  //    Optional<Tag> tag =
  //        this.tagRepository.findByTagName(
  //            tagName); // this is the tag needed, for this tag we need to find blogs
  //    List<BlogPost> blogPosts = new ArrayList<>();
  //
  //    if (tag.isPresent()) {
  //      List<Tag> tags = this.tagRepository.findAll();
  //      if (tags.contains(tag.get())) {
  //        blogPosts = tag.get().getBlogPosts();
  //      }
  //      return blogPosts;
  //    }
  //    return null;
  //  }

  @Override
  public List<BlogPost> getAllBlogPostByTagName(String tagName) {
    return tagRepository
        .findByTagName(tagName)
        .map(Tag::getBlogPosts)
        .orElse(Collections.emptyList());
  }

  @Override
  public void addTagsToBlogPost(Long blogPostId, Tag tag) {

    BlogPost blogPost =
        blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    // This is with using Optional functions
    //    Optional<Tag> newTag = this.findTagByTagName(tag.getTagName());
    //    if (newTag.isPresent()) {
    //      blogPost.getTags().add(newTag);
    //      blogPostRepository.save(blogPost);
    //    }
    Tag newTag = tagService.findOrCreateTagByName(tag.getTagName());

    blogPost.getTags().add(newTag);
    blogPostRepository.save(blogPost);
  }

  @Override
  public void deleteTagFromBlogPost(Long blogPostId, Tag tag) {
    BlogPost blogPost =
        blogPostRepository
            .findById(blogPostId)
            .orElseThrow(() -> new RecordNotFoundException("Blog post not found"));

    Tag tagToDelete = tagService.findOrCreateTagByName(tag.getTagName());

    blogPost.getTags().remove(tagToDelete);
    blogPostRepository.save(blogPost);

    // This is with using Optional functions
    //    Optional<Tag> tagToDelete = tagRepository.findByTagName(tag.getTagName());
    //    if (tagToDelete.isPresent()) {
    //      blogPost.getTags().remove(tagToDelete);
    //      blogPostRepository.save(blogPost);
    //    }

  }
}
