package com.example.blogpostapplication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.User;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.NoBlogPostsFoundException;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.service.TagService;
import com.example.blogpostapplication.service.UserService;
import com.example.blogpostapplication.service.impl.BlogPostServiceImpl;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceImplTest {

  @InjectMocks private BlogPostServiceImpl blogPostService;

  @Mock private BlogPostRepository blogPostRepository;

  @Mock private TagService tagService;

  @Mock private UserService userService;

  @Mock private Logger logger;

  @Test
  void testCreateBlogPost() {
    BlogPostDTO blogPostDTO = new BlogPostDTO();
    blogPostDTO.setBlogPostTitle("Test Title");
    blogPostDTO.setBlogPostText("Test Text");

    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle(blogPostDTO.getBlogPostTitle());
    blogPost.setBlogPostText(blogPostDTO.getBlogPostText());

    when(blogPostRepository.save(any(BlogPost.class))).thenReturn(blogPost);

    BlogPost result = blogPostService.createBlogPost(blogPostDTO);

    assertNotNull(result);
    assertEquals(blogPostDTO.getBlogPostTitle(), result.getBlogPostTitle());
    assertEquals(blogPostDTO.getBlogPostText(), result.getBlogPostText());

    verify(blogPostRepository, times(1)).save(any());
  }

  @Test
  void getAllBlogPosts() {

    BlogPost blogPost1 = new BlogPost();
    blogPost1.setBlogPostTitle("Blog Post 1");
    blogPost1.setBlogPostText("Blog Post 1 text");

    BlogPost blogPost2 = new BlogPost();
    blogPost2.setBlogPostTitle("Blog Post 2");
    blogPost2.setBlogPostText("Blog Post 2 text");

    List<BlogPost> mockedBlogPosts = Arrays.asList(blogPost1, blogPost2);

    when(blogPostRepository.findAll()).thenReturn(mockedBlogPosts);

    List<BlogPostDTO> resultList = blogPostService.getAllBlogPosts();

    verify(blogPostRepository).findAll();
    assertEquals(2, resultList.size());

    BlogPostDTO blogPostDTO1 = resultList.get(0);
    BlogPostDTO blogPostDTO2 = resultList.get(1);
    assertEquals("Blog Post 1", blogPostDTO1.getBlogPostTitle());
    assertEquals("Blog Post 2", blogPostDTO2.getBlogPostTitle());
  }

  @Test
  void testGetAllBlogPostByTagName() {

    String tagName = "mathematics";
    Tag tag = new Tag();
    tag.setTagName(tagName);

    BlogPost blogPost1 = new BlogPost();
    BlogPost blogPost2 = new BlogPost();
    List<BlogPost> blogPosts = Arrays.asList(blogPost1, blogPost2);

    when(tagService.findTagByTagName("mathematics")).thenReturn(tag);

    tag.setBlogPosts(blogPosts);

    List<BlogPost> result = blogPostService.getAllBlogPostByTagName(tagName);

    assertEquals(blogPost1, result.get(0));
    assertEquals(blogPost2, result.get(1));
  }

  @Test
  void testUpdateBlogPost() {
    BlogPostDTO blogPostDTO = new BlogPostDTO();
    blogPostDTO.setBlogPostTitle("Updated title");
    blogPostDTO.setBlogPostText("Updated text");

    Long blogPostId = 1L;
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostId(blogPostId);
    blogPost.setBlogPostTitle("Original title");
    blogPost.setBlogPostText("Original text");
    User user = new User();
    user.setUserId(1L);
    user.getBlogPosts().add(blogPost);

    when(userService.getLoggedUser()).thenReturn(user);
    when(blogPostRepository.save(any(BlogPost.class))).thenReturn(blogPost);

    BlogPost updatedBlogPost = blogPostService.updateBlogPost(blogPostId, blogPostDTO);

    assertEquals(blogPostDTO.getBlogPostTitle(), updatedBlogPost.getBlogPostTitle());
    assertEquals(blogPostDTO.getBlogPostText(), updatedBlogPost.getBlogPostText());

    verify(blogPostRepository, times(1)).save(any(BlogPost.class));
  }

  @Test
  public void testAddTagsToBlogPost() {
    Long blogPostId = 5L;
    String tagName = "Tag Name";

    User user = new User();
    user.setUserId(1L);
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostId(blogPostId);
    user.getBlogPosts().add(blogPost);

    TagDTO tagDTO = new TagDTO();
    tagDTO.setTagName(tagName);

    Tag newTag = new Tag();
    newTag.setTagName(tagName);

    when(userService.getLoggedUser()).thenReturn(user);
    when(tagService.findOrCreateTag(tagName)).thenReturn(newTag);

    blogPostService.addTagsToBlogPost(blogPostId, tagDTO);

    assertTrue(blogPost.getTags().contains(newTag));
    verify(blogPostRepository, times(1)).save(blogPost);
  }

  @Test
  void testDeleteTagFromBlogPost() {

    Long blogPostId = 5L;
    String tagName = "Tag Name";

    User user = new User();
    user.setUserId(1L);
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostId(blogPostId);
    user.getBlogPosts().add(blogPost);

    TagDTO tagDTO = new TagDTO();
    tagDTO.setTagName(tagName);

    Tag tagToDelete = new Tag();
    tagToDelete.setTagName(tagName);

    when(userService.getLoggedUser()).thenReturn(user);
    when(tagService.findTagByTagName(tagName)).thenReturn(tagToDelete);

    blogPostService.deleteTagFromBlogPost(blogPostId, tagDTO);

    verify(blogPostRepository, times(1)).save(blogPost);
    assertFalse(blogPost.getTags().contains(tagToDelete));
  }

  @Test
  void testMapToSimplifiedDTO() {
    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle("Test Title");
    blogPost.setBlogPostText("This is a long blog post text that needs to be shortened.");

    ReflectionTestUtils.setField(blogPostService, "summaryLength", 20);

    BlogPostDTO blogPostDTO = blogPostService.mapToSimplifiedDTO(blogPost);

    assertEquals("Test Title", blogPostDTO.getBlogPostTitle());
    assertEquals("This is a long blog  ...", blogPostDTO.getBlogPostText());
  }

  @Test
  void testGetShortSummaryForShortText() {
    ReflectionTestUtils.setField(blogPostService, "summaryLength", 20);

    String shortText = "Short text.";

    String resultShort = blogPostService.getShortSummary(shortText);
    assertEquals("Short text.", resultShort);
  }

  @Test
  void testGetShortSummaryForLongText() {
    ReflectionTestUtils.setField(blogPostService, "summaryLength", 20);

    String longText = "This is a long blog post that needs to be summarized.";

    String resultLong = blogPostService.getShortSummary(longText);
    assertEquals("This is a long blog  ...", resultLong);
  }

  @Test
  public void testGetBlogPostByIdAndUser_ExistingBlogPost() {
    User user = new User();
    user.setUserId(1L);

    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostId(1L);
    when(userService.getLoggedUser()).thenReturn(user);
    user.getBlogPosts().add(blogPost);

    BlogPost result = blogPostService.getBlogPostByIdAndUser(1L);

    assertEquals(blogPost, result);
  }

  @Test
  public void testGetBlogPostByIdAndUser_NonExistingBlogPost() {
    User user = new User();
    user.setUserId(1L);

    when(userService.getLoggedUser()).thenReturn(user);

    assertThrows(
        RecordNotFoundException.class,
        () -> {
          blogPostService.getBlogPostByIdAndUser(1L);
        });
  }

  @Test
  void getAllBlogPostsByUserId() {
    Long userId = 1L;
    User user = new User();
    user.setUserId(userId);

    BlogPost blogPost1 = new BlogPost();
    blogPost1.setBlogPostTitle("Blog 1 title");
    blogPost1.setBlogPostText("Blog 1 text");

    List<BlogPost> blogPosts = new ArrayList<>();
    blogPosts.add(blogPost1);
    user.setBlogPosts(blogPosts);

    when(userService.findByUserId(userId)).thenReturn(user);

    List<BlogPostDTO> result = blogPostService.getAllBlogPostsByUserId(userId);
    assertEquals(1, result.size());
    assertEquals("Blog 1 title", result.get(0).getBlogPostTitle());
    assertEquals("Blog 1 title", result.get(0).getBlogPostTitle());

    verify(userService, times(1)).findByUserId(userId);
  }

  @Test
  void deleteAllBlogPostsByUserId() {
    User user = new User();
    user.setUserId(1L);

    BlogPost blogPost = new BlogPost();
    blogPost.setBlogPostTitle("Blog post title");
    blogPost.setBlogPostText("Blog post text");
    List<BlogPost> blogPosts = new ArrayList<>();
    blogPosts.add(blogPost);
    user.setBlogPosts(blogPosts);

    when(userService.getLoggedUser()).thenReturn(user);
    blogPostService.deleteAllBlogPostsByUserId();
    assertTrue(user.getBlogPosts().isEmpty());
    verify(blogPostRepository).deleteBlogPostsByUserId(user.getUserId());
  }

  @Test
  void deleteAllBlogPostsByUserIdIfNoBlogPostsFound() {
    User user = new User();
    user.setUserId(1L);

    user.setBlogPosts(new ArrayList<>());

    when(userService.getLoggedUser()).thenReturn(user);
    assertThrows(
        NoBlogPostsFoundException.class,
        () -> {
          blogPostService.deleteAllBlogPostsByUserId();
        });

    // Verify that the user's blog posts are still empty
    assertTrue(user.getBlogPosts().isEmpty());

    verify(blogPostRepository, never()).deleteBlogPostsByUserId(anyLong());
  }
}
