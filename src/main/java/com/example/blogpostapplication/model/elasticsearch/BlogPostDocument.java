package com.example.blogpostapplication.model.elasticsearch;

import com.example.blogpostapplication.model.Tag;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "blogpost")
@Data
public class BlogPostDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Field(type=FieldType.Long, name = "blogPostId")
  private Long blogPostId;

  @Field(type = FieldType.Text, name = "blogPostTitle")
  private String blogPostTitle;

  @Field(type = FieldType.Text, name = "blogPostText")
  private String blogPostText;

  @Field(type = FieldType.Nested, includeInParent = true)
  private List<Tag> tags = new ArrayList<>();
}
