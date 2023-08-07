package com.example.blogpostapplication.service.impl;

import com.example.blogpostapplication.model.BlogPost;
import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.exceptions.BlogPostDoesNotExists;
import com.example.blogpostapplication.repository.BlogPostRepository;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.TagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final BlogPostRepository blogPostRepository;

    public TagServiceImpl(TagRepository tagRepository, BlogPostRepository blogPostRepository) {
        this.tagRepository = tagRepository;
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public Tag findOrCreateTagByName(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName);
        if (tag == null) {
            tag = new Tag();
            tag.setTagName(tagName);
            tag = tagRepository.save(tag);
        }

        return tag;
    }

    @Override
    public void addTagsToBlogPost(Long blogPostId, Tag tag) {

        List<Tag> tagsToAdd = new ArrayList<>();
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new BlogPostDoesNotExists(blogPostId));

        Tag tagName = this.findOrCreateTagByName(tag.getTagName());

        tagsToAdd.add(tagName);

        blogPost.getTags().addAll(tagsToAdd);
        blogPostRepository.save(blogPost);

    }

    @Override
    public void deleteTagFromBlogPost(Long blogPostId, Tag tag) {
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new BlogPostDoesNotExists(blogPostId));

        Tag tagToDelete = tagRepository.findByTagName(tag.getTagName());
        if (tagToDelete != null) {
            blogPost.getTags().remove(tagToDelete);
            blogPostRepository.save(blogPost);
        }
    }
}
