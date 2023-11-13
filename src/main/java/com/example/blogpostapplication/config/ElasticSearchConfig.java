package com.example.blogpostapplication.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories({"com.example.blogpostapplication.nosqlrepository.elasticsearch"})
public class ElasticSearchConfig {

  @Bean
  public RestClient getRestClient() {
    return RestClient.builder(new HttpHost("localhost", 9200)).build();
  }
}
