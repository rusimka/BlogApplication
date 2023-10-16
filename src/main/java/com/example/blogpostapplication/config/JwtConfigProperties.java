package com.example.blogpostapplication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "blogpost.app.jwt")
@Data
public class JwtConfigProperties {

  private String jwtSecret;
  private int jwtExpirationMs;
}
