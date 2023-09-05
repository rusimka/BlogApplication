package com.example.blogpostapplication;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.BlogPostDTO;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.service.TagService;
import com.example.blogpostapplication.service.UserService;
import com.example.blogpostapplication.service.impl.BlogPostServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceImplTest {

  @InjectMocks private BlogPostServiceImpl blogPostService;

  @Mock private BlogPostRepository blogPostRepository;

  @Mock private TagService tagService;

  @Mock private UserService userService;

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

    when(blogPostRepository.findById(blogPostId)).thenReturn(Optional.of(blogPost));
    when(blogPostRepository.save(any(BlogPost.class))).thenReturn(blogPost);

    BlogPost updatedBlogPost = blogPostService.updateBlogPost(blogPostId, blogPostDTO);

    assertEquals(blogPostDTO.getBlogPostTitle(), updatedBlogPost.getBlogPostTitle());
    assertEquals(blogPostDTO.getBlogPostText(), updatedBlogPost.getBlogPostText());

    verify(blogPostRepository, times(1)).findById(blogPostId);
    verify(blogPostRepository, times(1)).save(any(BlogPost.class));
  }

  @Test
  void testAddTagsToBlogPost() {
    Long blogPostId = 1L;
    String tagName = "Tag Name";

    BlogPost blogPost = new BlogPost();
    TagDTO tagDTO = new TagDTO();
    tagDTO.setTagName(tagName);

    Tag newTag = new Tag();
    newTag.setTagName(tagName);

    when(blogPostRepository.findById(blogPostId)).thenReturn(Optional.of(blogPost));
    when(tagService.createTag(tagName)).thenReturn(newTag);

    blogPostService.addTagsToBlogPost(blogPostId, tagDTO);

    verify(blogPostRepository, times(1)).save(blogPost);
    assertTrue(blogPost.getTags().contains(newTag));
  }

  @Test
  void testDeleteTagFromBlogPost() {
    Long blogPostId = 1L;
    String tagName = "Tag Name";

    BlogPost blogPost = new BlogPost();
    TagDTO tagDTO = new TagDTO();
    tagDTO.setTagName(tagName);

    Tag tagToDelete = new Tag();
    tagToDelete.setTagName(tagName);

    when(blogPostRepository.findById(blogPostId)).thenReturn(Optional.of(blogPost));
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
  void testGetBlogPostById() {
    Long blogPostId = 1L;
    BlogPost expectedBlogPost = new BlogPost();
    expectedBlogPost.setBlogPostId(blogPostId);

    when(blogPostRepository.findById(blogPostId)).thenReturn(Optional.of(expectedBlogPost));

    BlogPost result = blogPostService.getBlogPostById(blogPostId);

    assertEquals(expectedBlogPost, result);
  }

  @Test
  void testGetBlogPostByIdNotFound() {
    Long blogPostId = 1L;

    when(blogPostRepository.findById(blogPostId)).thenReturn(Optional.empty());

    assertThrows(RecordNotFoundException.class, () -> blogPostService.getBlogPostById(blogPostId));
  }
}
