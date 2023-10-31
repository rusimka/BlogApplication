package com.example.blogpostapplication.service;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.elasticsearch.BlogPostDocument;

import java.util.List;

public interface ElasticSearchService {

    BlogPostDocument createBlogPostDocument(BlogPostDocument blogPostDocument);


}
