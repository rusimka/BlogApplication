package com.example.blogpostapplication.model.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "blogpost")
@Data
public class BlogPostDocument {

  @Id
  //  @Field(type=FieldType.Long, name = "blogPostId")
  private String blogPostId;

  @Field(type = FieldType.Text, name = "blogPostTitle")
  private String blogPostTitle;

  @Field(type = FieldType.Text, name = "blogPostText")
  private String blogPostText;

  @Field(type = FieldType.Keyword)
  private List<String> tags = new ArrayList<>();
}
