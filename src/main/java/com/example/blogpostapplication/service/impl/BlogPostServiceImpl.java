package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.dto.*;
import com.example.blogpostapplication.model.exceptions.NoBlogPostsFoundException;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.service.BlogPostService;
import com.example.blogpostapplication.service.TagService;
import com.example.blogpostapplication.service.UserService;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogPostServiceImpl implements BlogPostService {

  private static final String EXCEPTION_TEXT = "Blog post with ID %d not found";
  private static final Logger logger = LoggerFactory.getLogger(BlogPostServiceImpl.class);
  private final BlogPostRepository blogPostRepository;
  private final TagService tagService;
  private final UserService userService;

  @Value("${blogpost.summary.length}")
  private int summaryLength;

  public BlogPostServiceImpl(
      BlogPostRepository blogPostRepository, TagService tagService, UserService userService) {
    this.blogPostRepository = blogPostRepository;
    this.tagService = tagService;
    this.userService = userService;
  }

  public BlogPost getBlogPostByIdAndUser(Long blogPostId) {
    User user = userService.getLoggedUser();
    return user.getBlogPosts().stream()
        .filter(blogPost -> blogPost.getBlogPostId().equals(blogPostId))
        .findFirst()
        .orElseThrow(
            () -> {
              logger.error("Failed to find   blog post with ID {}. ", blogPostId);
              return new RecordNotFoundException(String.format(EXCEPTION_TEXT, blogPostId));
            });
  }

  @Override
  public BlogPost createBlogPost(BlogPostDTO blogPostDTO) {
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle(blogPostDTO.getBlogPostTitle());
    blogPost.setBlogPostText(blogPostDTO.getBlogPostText());
    blogPost.setUser(userService.getLoggedUser());
    logger.info("New blog post created with title: '{}'", blogPost.getBlogPostTitle());
    return this.blogPostRepository.save(blogPost);
  }

  @Override
  public Page<BlogPostDTO> getAllBlogPosts(int page, int size) {
    logger.info("Attempting to retrieve all blog posts");
    Pageable pageable = PageRequest.of(page, size);
    Page<BlogPost> blogPostsPage = blogPostRepository.findAll(pageable);
    return blogPostsPage.map(this::mapToSimplifiedDTO);
  }

  public BlogPostDTO mapToSimplifiedDTO(BlogPost blogPost) {
    return BlogPostDTO.builder()
        .blogPostTitle(blogPost.getBlogPostTitle())
        .blogPostText(getShortSummary(blogPost.getBlogPostText()))
        .build();
  }

  public String getShortSummary(String blogPostText) {
    return blogPostText.length() > summaryLength
        ? blogPostText.substring(0, summaryLength) + " ..."
        : blogPostText;
  }

  @Override
  public BlogPost updateBlogPost(Long blogPostId, BlogPostDTO blogPostDTO) {
    BlogPost blogPostToUpdate = getBlogPostByIdAndUser(blogPostId);
    Optional.ofNullable(blogPostDTO.getBlogPostTitle())
        .ifPresent(
            title -> {
              blogPostToUpdate.setBlogPostTitle(title);
              logger.info("Updated title of blog post with ID {}: '{}'", blogPostId, title);
            });

    Optional.ofNullable(blogPostDTO.getBlogPostText())
        .ifPresent(
            text -> {
              blogPostToUpdate.setBlogPostText(text);
              logger.info("Updated text of blog post with ID {}: '{}'", blogPostId, text);
            });
    return blogPostRepository.save(blogPostToUpdate);
  }

  @Override
  public List<BlogPost> getAllBlogPostByTagName(String tagName) {
    return Optional.ofNullable(tagService.findTagByTagName(tagName))
        .map(Tag::getBlogPosts)
        .orElse(Collections.emptyList());
  }

  // I had to create the functionality 'findOrCreateBTag()', because I couldn't use the
  // findTagByTagName and
  // createTag in BlogPostController
  @Override
  public void addTagsToBlogPost(Long blogPostId, TagDTO tagDTO) {

    BlogPost blogPostToUpdate = getBlogPostByIdAndUser(blogPostId);

    Tag tag = tagService.findOrCreateTag(tagDTO.getTagName());

    blogPostToUpdate.getTags().add(tag);
    blogPostRepository.save(blogPostToUpdate);
    logger.info("Tag '{}' added to blog post with ID {}", tag.getTagName(), blogPostId);
  }

  @Override
  public void deleteTagFromBlogPost(Long blogPostId, TagDTO tagDTO) {
    BlogPost blogPostToDelete = getBlogPostByIdAndUser(blogPostId);
    Tag tagToDelete = tagService.findTagByTagName(tagDTO.getTagName());
    blogPostToDelete.getTags().remove(tagToDelete);
    blogPostRepository.save(blogPostToDelete);
    logger.info("Tag '{}' removed from blog post with ID {}", tagToDelete.getTagName(), blogPostId);
  }

  @Override
  public List<BlogPostDTO> getAllBlogPostsByUserId(Long userId) {
    logger.info("Attempting to get all blog posts for user with id {}", userId);
    return userService.findByUserId(userId).getBlogPosts().stream()
        .map(this::mapToSimplifiedDTO)
        .collect(Collectors.toList());
  }

  // TODO: Response Error Message for this function(if the user doesn't have any blog posts
  // assigned) , similar as error message for 'deleteBlogPostByBlogPostId', function.
  // TODO : Write jUnit test for this functionality
  @Override
  public void deleteAllBlogPostsByUserId() {
    User user = userService.getLoggedUser();

    if (!user.getBlogPosts().isEmpty()) {
      user.getBlogPosts()
          .forEach(
              blogPost -> {
                blogPost.setUser(null); // Remove the reference to the user
              });

      user.getBlogPosts().removeAll(user.getBlogPosts());
      blogPostRepository.deleteByUserUserId(user.getUserId());
      logger.info("All blog posts for user with ID {} have been deleted.", user.getUserId());

    } else {
      logger.error("No blog posts found for user with ID {}. Unable to delete.", user.getUserId());
      throw new NoBlogPostsFoundException(user.getUserId());
    }
  }

  // TODO : Write jUnit test for this functionality
  @Override
  public void deleteBlogPostByBlogPostId(Long blogPostId) {

    User user = userService.getLoggedUser();
    BlogPost blogPostToDelete = getBlogPostByIdAndUser(blogPostId);
    user.getBlogPosts().remove(blogPostToDelete);
    blogPostToDelete.setUser(null);
    this.blogPostRepository.deleteById(blogPostId);
    logger.info("Blog post with ID {} has been deleted.", blogPostId);
  }
}
