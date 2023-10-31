package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.elasticsearch.BlogPostDocument;
import com.example.blogpostapplication.nosqlrepository.elasticsearch.BlogPostDocumentRepository;
import com.example.blogpostapplication.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    log.info("New blog post created with title: '{}'", blogPostDocument1.getBlogPostTitle());
    //        return this.blogPostRepository.save(blogPost);
    return this.blogPostDocumentRepository.save(blogPostDocument1);
  }

}
