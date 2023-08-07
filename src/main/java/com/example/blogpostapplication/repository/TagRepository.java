package com.example.blogpostapplication.repository;

import com.example.blogpostapplication.model.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("SELECT t FROM Tag t WHERE t.tagName = :tagName")
    Tag findByTagName(@Param("tagName") String tagName);


}
