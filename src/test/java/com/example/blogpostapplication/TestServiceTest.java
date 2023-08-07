package com.example.blogpostapplication;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.exceptions.BlogPostDoesNotExists;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestServiceTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private TagService tagService;

    @Test
    public void testFindOrCreateTagByName_TagExists() {
        String tagName = "Technology";
        Tag existingTag = new Tag();
        existingTag.setTagId(1L);
        existingTag.setTagName(tagName);

        when(tagRepository.findByTagName(tagName)).thenReturn(existingTag);

        Tag resultTag = tagService.findOrCreateTagByName(tagName);

        assertEquals(existingTag, resultTag);
    }

}
