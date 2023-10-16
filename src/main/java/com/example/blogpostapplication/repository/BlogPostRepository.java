package com.example.blogpostapplication.repository;

import com.example.blogpostapplication.model.BlogPost;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

  @Transactional
  @Modifying
  @Query("DELETE FROM BlogPost blogPost where blogPost.user.userId=:userId")
  void deleteBlogPostsByUserId(@Param("userId") Long userId);
}
