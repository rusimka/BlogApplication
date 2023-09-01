package com.example.blogpostapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.blogpostapplication.model.Tag;
import com.example.blogpostapplication.model.dto.TagDTO;
import com.example.blogpostapplication.model.exceptions.RecordNotFoundException;
import com.example.blogpostapplication.repository.TagRepository;
import com.example.blogpostapplication.service.impl.TagServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTests {

  @Mock TagRepository tagRepository;
  @InjectMocks private TagServiceImpl tagService;

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
    when(tagRepository.findByTagName(tagName)).thenReturn(Optional.empty());
    assertThrows(RecordNotFoundException.class, () -> tagService.findTagByTagName(tagName));
  }

  @Test
  void getAllTags() {
    Tag tag1 = new Tag();
    tag1.setTagName("mathematics");
    Tag tag2 = new Tag();
    tag2.setTagName("sport");

    List<Tag> tags = Arrays.asList(tag1, tag2);
    when(tagRepository.findAll()).thenReturn(tags);

    List<TagDTO> resultList = tagService.getAllTags();
    verify(tagRepository).findAll();
    assertEquals(2, resultList.size());
    TagDTO tagDTO1 = resultList.get(0);
    TagDTO tagDTO2 = resultList.get(1);
    assertEquals("mathematics", tagDTO1.getTagName());
    assertEquals("sport", tagDTO2.getTagName());
  }
}
