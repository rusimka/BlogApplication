package com.example.blogpostapplication;

import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTests {

  @InjectMocks private TagServiceImpl tagService;

  @Mock TagRepository tagRepository;

  @Test
  void findByTagNameIfTagExist() {
    String tagName = "mathematics";
    Tag actual = new Tag();
    actual.setTagName(tagName);
    when(tagRepository.findByTagName(tagName)).thenReturn(Optional.of(actual));
    Tag expected = tagService.findTagByTagName(tagName);
    assertEquals(actual, expected);
  }

  @Test
  void findTagNameIfTagDoesNotExist() {
    String tagName = "mathematics";
    Tag expected = new Tag();
    expected.setTagName(tagName);
    when(tagRepository.findByTagName(tagName)).thenReturn(Optional.empty());
    when(tagRepository.save(any(Tag.class))).thenReturn(expected);

    Tag actual = tagService.findTagByTagName(tagName);
    assertEquals(expected, actual);
  }
}
