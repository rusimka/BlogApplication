package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.elasticsearch.BlogPostDocument;

import java.util.List;

public interface ElasticSearchService {

    BlogPostDocument createBlogPostDocument(BlogPostDocument blogPostDocument);

    void deleteAll();

    List<BlogPostDocument> searchBlogPostByBlogPostTitle(String blogPostTitle);

    List<BlogPostDocument> searchBlogPostByBlogPostText(String blogPostText);

    List<BlogPostDocument> searchBlogPostByTag(String tag);

    List<BlogPostDocument> searchBlogPostByBlogPostTitleAndBlogPostTextAndTag(String blogPostTitle, String blogPostText, String tag);





}
