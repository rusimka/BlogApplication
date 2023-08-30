package com.example.blogpostapplication.model.dto;

import lombok.Data;

@Data
public class TagDTO {

  private String tagName;

  public TagDTO(TagDTOBuilder tagDTOBuilder){
    this.tagName = tagDTOBuilder.tagName;
}

  public static class TagDTOBuilder {

    private String tagName;

    public TagDTOBuilder tagName(String tagName) {
      this.tagName = tagName;
      return this;
    }

    public String getTagName() {
      return tagName;
    }

    public TagDTO build(){
      return new TagDTO(this);
}
  }
}
