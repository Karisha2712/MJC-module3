package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDao tagDao;

    private static final List<TagDto> tagDtos = new ArrayList<>();
    private static final List<Tag> tags = new ArrayList<>();

    @BeforeAll
    static void initialize() {
        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();
        TagDto tagDto3 = new TagDto();
        tagDto1.setId(1L);
        tagDto2.setId(2L);
        tagDto3.setId(3L);
        tagDto1.setName("tag1");
        tagDto2.setName("tag2");
        tagDto3.setName("tag3");
        tagDtos.add(tagDto1);
        tagDtos.add(tagDto2);
        tagDtos.add(tagDto3);

        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        tag1.setId(1L);
        tag2.setId(2L);
        tag3.setId(3L);
        tag1.setName("tag1");
        tag2.setName("tag2");
        tag3.setName("tag3");
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
    }

    @BeforeEach
    void before() {
        tagService = new TagServiceImpl(tagDao, new TagDtoMapperImpl());
    }

    @Test
    void ifRetrieveSingleTagReturnsCorrespondingTag() {
        TagDto tagDto = tagDtos.get(1);
        Tag tag = tags.get(1);
        Mockito.when(tagDao.receiveEntityById(1L)).thenReturn(Optional.of(tag));
        TagDto actual = tagService.retrieveSingleTag(1L);
        assertEquals(tagDto, actual);
    }

    @Test
    void ifRetrieveSingleTagThrowsTagNotFoundException() {
        Mockito.when(tagDao.receiveEntityById(100L)).thenReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.retrieveSingleTag(100L));
    }

    @Test
    void ifRetrieveAllTagsReturnsCorrespondingTags() {
        Mockito.when(tagDao.receiveAllTags()).thenReturn(tags);
        List<TagDto> actual = tagService.retrieveAllTags();
        assertEquals(tagDtos, actual);
    }

    @Test
    void ifRetrieveCertificateTagsReturnsCorrespondingTags() {
        Mockito.when(tagDao.receiveCertificateTags(1)).thenReturn(tags);
        List<TagDto> actual = tagService.retrieveCertificateTags(1);
        assertEquals(tagDtos, actual);
    }

    @Test
    void ifRemoveTagThrowsTagNotFoundException() {
        Mockito.when(tagDao.receiveEntityById(100L)).thenReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.removeTag(100L));
    }

    @Test
    void ifCreateTagReturnsCorrespondingIdAndTagNotExists() {
        Mockito.when(tagDao.receiveTagByName("tag1")).thenReturn(Optional.empty());
        Mockito.when(tagDao.insertEntity(tags.get(0))).thenReturn(1L);
        long actual = tagService.createTag(tagDtos.get(0));
        long expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void ifCreateTagReturnsCorrespondingIdAndTagExists() {
        Mockito.when(tagDao.receiveTagByName("tag1")).thenReturn(Optional.of(tags.get(0)));
        long actual = tagService.createTag(tagDtos.get(0));
        long expected = 1;
        assertEquals(expected, actual);
    }

}
