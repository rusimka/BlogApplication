package com.example.blogpostapplication.nosqlrepository.elasticsearch;

import com.example.blogpostapplication.model.elasticsearch.BlogPostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostDocumentRepository extends ElasticsearchRepository<BlogPostDocument,Long> {
}
