package com.example.blogpostapplication.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogPostId;

    @Column(name = "blog_post_title")
    private String blogPostTitle;

    @Column(name = "blog_post_text")
    private String blogPostText;

    @ManyToMany
    @JoinTable(
            name = "blog_post_tags",
            joinColumns = @JoinColumn(name = "blog_post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    public Long getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(Long blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getBlogPostTitle() {
        return blogPostTitle;
    }

    public void setBlogPostTitle(String blogPostTitle) {
        this.blogPostTitle = blogPostTitle;
    }

    public String getBlogPostText() {
        return blogPostText;
    }

    public void setBlogPostText(String blogPostText) {
        this.blogPostText = blogPostText;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
