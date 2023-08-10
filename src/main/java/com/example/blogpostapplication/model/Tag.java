package com.example.blogpostapplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
@Data
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long tagId;

  @Column(name = "tag_name")
  private String tagName;

  @ManyToMany(mappedBy = "tags")
  private List<BlogPost> blogPosts = new ArrayList<>();
}
