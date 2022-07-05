package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.exception.TagAlreadyExistsException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    private static final List<TagDto> tagDtos = new ArrayList<>();
    private static final List<Tag> tags = new ArrayList<>();
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;

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
        tagService = new TagServiceImpl(tagRepository, new TagDtoMapperImpl());
    }

    @Test
    void givenValidId_whenFindById_thenReturnsCorrespondingTag() {
        TagDto expected = tagDtos.get(1);
        Tag tag = tags.get(1);
        long id = 1L;
        Mockito.when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
        TagDto actual = tagService.retrieveSingleTag(id);
        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidId_whenFindById_thenTrowsTagNotFoundException() {
        long id = 100L;
        Mockito.when(tagRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.retrieveSingleTag(id));
    }

    @Test
    void givenAnExistingTagName_whenSaveTag_thenThrowsTagAlreadyExistsException() {
        Tag tag = tags.get(1);
        TagDto tagDto = tagDtos.get(1);
        Mockito.when(tagRepository.findByName(tagDto.getName())).thenReturn(Optional.of(tag));
        assertThrows(TagAlreadyExistsException.class, () -> tagService.saveTag(tagDto));
    }

    @Test
    void givenInvalidId_whenRemoveTag_thenThrowsTagNotFoundException() {
        long id = 100L;
        Mockito.when(tagRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.removeTag(id));
    }

    @Test
    void givenExistingPage_whenRetrievePageOfTags_thenReturnsCorrespondingPage() {
        int currentPage = 1;
        int elementsPerPage = 1;
        int totalPageNumber = 3;
        Page<TagDto> expected = new Page<>(currentPage, totalPageNumber, elementsPerPage, tagDtos);
        Mockito.when(tagRepository.count()).thenReturn(3L);
        Mockito.when(tagRepository.findAll(PageRequest.of(currentPage - 1, elementsPerPage))).thenReturn(new PageImpl<>(tags));
        Page<TagDto> actual = tagService.retrievePageOfTags(currentPage, elementsPerPage);
        assertEquals(expected, actual);
    }

    @Test
    void givenNotExistingPage_whenRetrievePageOfTags_thenThrowsPageNotFoundException() {
        int currentPage = 2;
        int elementsPerPage = 1;
        Mockito.when(tagRepository.count()).thenReturn(1L);
        assertThrows(PageNotFoundException.class, () -> tagService.retrievePageOfTags(currentPage, elementsPerPage));
    }

    @Test
    void whenRetrieveMostWidelyUsedTag_thenReturnsCorrespondingTag() {
        TagDto expected = tagDtos.get(1);
        Tag tag = tags.get(1);
        Mockito.when(tagRepository.findMostWidelyUsedTag()).thenReturn(Optional.of(tag));
        TagDto actual = tagService.retrieveMostWidelyUsedTag();
        assertEquals(expected, actual);
    }

    @Test
    void whenRetrieveMostWidelyUsedTag_thenThrowsTagNotFoundException() {
        Mockito.when(tagRepository.findMostWidelyUsedTag()).thenReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.retrieveMostWidelyUsedTag());
    }
}
