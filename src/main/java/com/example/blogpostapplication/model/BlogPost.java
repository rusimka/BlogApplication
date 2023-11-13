package com.example.blogpostapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class BlogPost {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long blogPostId;

  @Column(name = "blog_post_title")
  private String blogPostTitle;

  @Column(name = "blog_post_text", length = 1024)
  private String blogPostText;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "blog_post_tags",
      joinColumns = @JoinColumn(name = "blog_post_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  @JsonIgnore
  private List<Tag> tags = new ArrayList<>();

}
