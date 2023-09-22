package com.example.blogpostapplication;

import com.example.blogpostapplication.config.JwtConfigProperties;
import com.example.blogpostapplication.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties(JwtConfigProperties.class)
@Slf4j
public class BlogPostApplication {
  public static void main(String[] args) {
    SpringApplication.run(BlogPostApplication.class, args);
    log.info("Starting the application");
  }

  @Bean
  public CommandLineRunner configureJwtUtils(JwtConfigProperties jwtConfigProperties) {
    return args -> JwtUtils.setJwtConfigProperties(jwtConfigProperties);
  }
}
