package com.example.blogpostapplication.repository;

import com.example.blogpostapplication.model.BlogPost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {}
