package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.elasticsearch.BlogPostDocument;
import com.example.blogpostapplication.nosqlrepository.elasticsearch.BlogPostDocumentRepository;
import com.example.blogpostapplication.service.ElasticSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ElasticSearchServiceImpl implements ElasticSearchService {

  private final BlogPostDocumentRepository blogPostDocumentRepository;

  @Override
  public BlogPostDocument createBlogPostDocument(BlogPostDocument blogPostDocument) {
    BlogPostDocument blogPostDocument1 = new BlogPostDocument();
    blogPostDocument1.setBlogPostTitle(blogPostDocument.getBlogPostTitle());
    blogPostDocument1.setBlogPostText(blogPostDocument.getBlogPostText());
    blogPostDocument1.setTags(blogPostDocument.getTags());
    log.info("New blog post created with title: '{}'", blogPostDocument1.getBlogPostTitle());
    //        return this.blogPostRepository.save(blogPost);
    return this.blogPostDocumentRepository.save(blogPostDocument1);
  }

  @Override
  public void deleteAll() {
    this.blogPostDocumentRepository.deleteAll();
  }

  @Override
  public List<BlogPostDocument> searchBlogPostByBlogPostTitle(String blogPostTitle) {
    return this.blogPostDocumentRepository.findByBlogPostTitle(blogPostTitle);
  }

  @Override
  public List<BlogPostDocument> searchBlogPostByBlogPostText(String blogPostText) {
    return this.blogPostDocumentRepository.findByBlogPostText(blogPostText);
  }

  @Override
  public List<BlogPostDocument> searchBlogPostByTag(String tag) {
    return this.blogPostDocumentRepository.findByTags(tag);
  }

  @Override
  public List<BlogPostDocument> searchBlogPostByBlogPostTitleAndBlogPostTextAndTag(
      String blogPostTitle, String blogPostText, String tag) {
    return this.blogPostDocumentRepository.findByBlogPostTitleAndBlogPostTextAndTags(
        blogPostTitle, blogPostText, tag);
  }
}
