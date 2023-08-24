package com.example.blogpostapplication.security.services;

import com.example.blogpostapplication.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// UserDetails is Authentication object containing all necessary information

public class UserDetailsInterfaceImpl implements UserDetails {

  private Long userId;

  private String username;

  private String password;

  private String displayName;

  public UserDetailsInterfaceImpl(
      Long userId, String username, String password, String displayName) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.displayName = displayName;
  }

  public static UserDetailsInterfaceImpl build(User user) {
    return new UserDetailsInterfaceImpl(
        user.getUserId(), user.getUsername(), user.getPassword(), user.getDisplayName());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Long getUserId() {
    return userId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
