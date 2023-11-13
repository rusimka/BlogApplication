package com.example.blogpostapplication.model.payload;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String displayName;

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
}
