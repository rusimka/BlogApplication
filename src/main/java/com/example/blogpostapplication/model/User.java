package com.example.blogpostapplication.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,orphanRemoval = true)
  private List<BlogPost> blogPosts = new ArrayList<>();

  public User(String username, String password, String displayName) {
    this.username = username;
    this.password = password;
    this.displayName = displayName;
  }

  public User() {}

  public User(
      Long userId, String username, String password, String displayName, List<BlogPost> blogPosts) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.displayName = displayName;
    this.blogPosts = blogPosts;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public List<BlogPost> getBlogPosts() {
    return blogPosts;
  }

  public void setBlogPosts(List<BlogPost> blogPosts) {
    this.blogPosts = blogPosts;
  }
}
