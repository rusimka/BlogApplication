package com.example.blogpostapplication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "blogpost.app")
@Data
public class JwtConfigProperties {

  private String jwtSecret;
  private int jwtExpirationMs;
}
