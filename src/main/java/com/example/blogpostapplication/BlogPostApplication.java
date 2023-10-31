package com.example.blogpostapplication;

import com.example.blogpostapplication.config.JwtConfigProperties;
import com.example.blogpostapplication.security.jwt.JwtUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties(JwtConfigProperties.class)
//@EnableJpaRepositories({"com.example.blogpostapplication.repository"})
@EnableJpaRepositories(
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = ElasticsearchRepository.class))
@EnableElasticsearchRepositories(
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = JpaRepository.class))

public class BlogPostApplication {
  public static void main(String[] args) {
    SpringApplication.run(BlogPostApplication.class, args);
  }

  @Bean
  public CommandLineRunner configureJwtUtils(JwtConfigProperties jwtConfigProperties) {
    return args -> JwtUtils.setJwtConfigProperties(jwtConfigProperties);
  }
}
