package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.exception.PageNotFoundException;
import com.epam.esm.filter.CertificatesFilter;
import com.epam.esm.mapper.CertificateDtoMapper;
import com.epam.esm.mapper.CertificateDtoMapperImpl;
import com.epam.esm.mapper.TagDtoMapper;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.pagination.Page;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {CertificateDtoMapperImpl.class, TagDtoMapperImpl.class})
@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {
    @InjectMocks
    private CertificateServiceImpl certificateService;
    @Mock
    private CertificateRepository certificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Autowired
    private CertificateDtoMapper certificateDtoMapper;
    @Autowired
    private TagDtoMapper tagDtoMapper;

    private static final List<CertificateDto> certificateDtos = new ArrayList<>();
    private static final List<Certificate> certificates = new ArrayList<>();
    private static final List<Tag> tags = new ArrayList<>();

    @BeforeAll
    static void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TagDto tagDto1 = new TagDto();
        TagDto tagDto2 = new TagDto();
        TagDto tagDto3 = new TagDto();
        tagDto1.setId(1L);
        tagDto2.setId(2L);
        tagDto3.setId(3L);
        tagDto1.setName("tag1");
        tagDto2.setName("tag2");
        tagDto3.setName("tag3");

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

        CertificateDto certificateDto1 = new CertificateDto();
        CertificateDto certificateDto2 = new CertificateDto();
        CertificateDto certificateDto3 = new CertificateDto();
        certificateDto1.setId(1L);
        certificateDto1.setTitle("title1");
        certificateDto1.setPrice(new BigDecimal(1));
        certificateDto1.setDuration(100);
        certificateDto1.setDescription("description1");
        certificateDto1.setCreatedDate("2022-04-19T11:00:00Z");
        certificateDto1.setLastUpdateDate("2022-04-19T11:00:00Z");
        certificateDto2.setId(2L);
        certificateDto2.setTitle("title2");
        certificateDto2.setPrice(new BigDecimal(2));
        certificateDto2.setDuration(200);
        certificateDto2.setDescription("description2");
        certificateDto2.setCreatedDate("2022-04-19T12:00:00Z");
        certificateDto2.setLastUpdateDate("2022-04-19T12:00:00Z");
        certificateDto3.setId(3L);
        certificateDto3.setTitle("title3");
        certificateDto3.setPrice(new BigDecimal(3));
        certificateDto3.setDuration(300);
        certificateDto3.setDescription("description3");
        certificateDto3.setCreatedDate("2022-04-19T13:00:00Z");
        certificateDto3.setLastUpdateDate("2022-04-19T13:00:00Z");
        certificateDto1.setTags(List.of(tagDto1, tagDto2));
        certificateDto2.setTags(List.of(tagDto2, tagDto3));
        certificateDto3.setTags(List.of(tagDto1, tagDto3));
        certificateDtos.add(certificateDto1);
        certificateDtos.add(certificateDto2);
        certificateDtos.add(certificateDto3);

        Certificate certificate1 = new Certificate();
        Certificate certificate2 = new Certificate();
        Certificate certificate3 = new Certificate();
        certificate1.setId(1L);
        certificate1.setTitle("title1");
        certificate1.setPrice(new BigDecimal(1));
        certificate1.setDuration(100);
        certificate1.setDescription("description1");
        certificate1.setCreatedDate(LocalDateTime.parse("2022-04-19T11:00:00Z", formatter));
        certificate1.setLastUpdateDate(LocalDateTime.parse("2022-04-19T11:00:00Z", formatter));
        certificate2.setId(2L);
        certificate2.setTitle("title2");
        certificate2.setPrice(new BigDecimal(2));
        certificate2.setDuration(200);
        certificate2.setDescription("description2");
        certificate2.setCreatedDate(LocalDateTime.parse("2022-04-19T12:00:00Z", formatter));
        certificate2.setLastUpdateDate(LocalDateTime.parse("2022-04-19T12:00:00Z", formatter));
        certificate3.setId(3L);
        certificate3.setTitle("title3");
        certificate3.setPrice(new BigDecimal(3));
        certificate3.setDuration(300);
        certificate3.setDescription("description3");
        certificate3.setCreatedDate(LocalDateTime.parse("2022-04-19T13:00:00Z", formatter));
        certificate3.setLastUpdateDate(LocalDateTime.parse("2022-04-19T13:00:00Z", formatter));
        certificate1.setTags(List.of(tag1, tag2));
        certificate2.setTags(List.of(tag2, tag3));
        certificate3.setTags(List.of(tag1, tag3));
        certificates.add(certificate1);
        certificates.add(certificate2);
        certificates.add(certificate3);
    }

    @BeforeEach
    void before() {
        certificateService = new CertificateServiceImpl(certificateRepository,
                tagRepository, certificateDtoMapper, tagDtoMapper);
    }

    @Test
    void givenValidId_whenRetrieveSingleCertificate_thenReturnsCorrespondingCertificate() {
        CertificateDto expected = certificateDtos.get(1);
        Certificate certificate = certificates.get(1);
        long id = 2L;
        Mockito.when(certificateRepository.findById(id)).thenReturn(Optional.of(certificate));
        CertificateDto actual = certificateService.retrieveSingleCertificate(id);
        assertEquals(expected, actual);
    }

    @Test
    void givenInvalidId_whenRetrieveSingleCertificate_thenThrowsCertificateNotFoundException() {
        long id = 100L;
        Mockito.when(certificateRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> certificateService.retrieveSingleCertificate(id));
    }

    @Test
    void givenInvalidId_whenRemoveCertificate_thenThrowsCertificateNotFoundException() {
        long id = 100L;
        Mockito.when(certificateRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> certificateService.removeCertificate(id));
    }

    @Test
    void givenInvalidId_whenEditCertificate_thenThrowsCertificateNotFoundException() {
        long id = 100L;
        Mockito.when(certificateRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class,
                () -> certificateService.editCertificate(id, certificateDtos.get(0)));
    }

    @Test
    void givenInvalidFilter_whenRetrievePageOfCertificatesFoundWithFilter_thenThrowsInvalidAttributeValueException() {
        String sortBy = "description";
        String textPart = "";
        Set<String> tagNames = Collections.emptySet();
        CertificatesFilter.Order order = CertificatesFilter.Order.ASC;
        CertificatesFilter filter = new CertificatesFilter(sortBy, order, textPart, tagNames);
        int currentPage = 1;
        int elementsPerPage = 2;
        assertThrows(InvalidAttributeValueException.class,
                () -> certificateService.retrievePageOfCertificatesFoundWithFilter(filter, currentPage, elementsPerPage));
    }

    @Test
    void givenNotExistingPage_whenRetrievePageOfCertificatesFoundWithFilter_thenThrowsPageNotFoundException() {
        String sortBy = "id";
        String textPart = "";
        Set<String> tagNames = Collections.emptySet();
        CertificatesFilter.Order order = CertificatesFilter.Order.ASC;
        CertificatesFilter filter = new CertificatesFilter(sortBy, order, textPart, tagNames);
        int currentPage = 2;
        int elementsPerPage = 3;
        Mockito.when(certificateRepository.countFilteredElements(filter)).thenReturn(1L);
        assertThrows(PageNotFoundException.class,
                () -> certificateService.retrievePageOfCertificatesFoundWithFilter(filter, currentPage, elementsPerPage));
    }

    @Test
    void givenFilterWithTags_whenRetrievePageOfCertificatesFoundWithFilter_thenReturnsCorrespondingPage() {
        String sortBy = null;
        String textPart = "";
        Set<String> tagNames = Set.of(tags.get(0).getName());
        List<CertificateDto> filteredCertificateDtos = List.of(certificateDtos.get(0), certificateDtos.get(2));
        List<Certificate> filteredCertificates = List.of(certificates.get(0), certificates.get(2));
        CertificatesFilter.Order order = CertificatesFilter.Order.ASC;
        CertificatesFilter filter = new CertificatesFilter(sortBy, order, textPart, tagNames);
        int currentPage = 1;
        int elementsPerPage = 2;
        int totalPageNumber = 1;
        Page<CertificateDto> expected = new Page<>(currentPage, totalPageNumber, elementsPerPage, filteredCertificateDtos);
        Mockito.when(certificateRepository.countFilteredElements(filter)).thenReturn(2L);
        Mockito.when(certificateRepository
                .findCertificatesWithFilter(filter, currentPage, elementsPerPage)).thenReturn(filteredCertificates);
        Page<CertificateDto> actual = certificateService
                .retrievePageOfCertificatesFoundWithFilter(filter, currentPage, elementsPerPage);
        assertEquals(expected, actual);
    }

    @Test
    void givenFilterWithSorting_whenRetrievePageOfCertificatesFoundWithFilter_thenReturnsCorrespondingPage() {
        String sortBy = "title";
        String textPart = "";
        Set<String> tagNames = Collections.emptySet();
        Collections.reverse(certificateDtos);
        Collections.reverse(certificates);
        CertificatesFilter.Order order = CertificatesFilter.Order.DESC;
        CertificatesFilter filter = new CertificatesFilter(sortBy, order, textPart, tagNames);
        int currentPage = 1;
        int elementsPerPage = 3;
        int totalPageNumber = 1;
        Page<CertificateDto> expected = new Page<>(currentPage, totalPageNumber, elementsPerPage, certificateDtos);
        Mockito.when(certificateRepository.countFilteredElements(filter)).thenReturn(3L);
        Mockito.when(certificateRepository
                .findCertificatesWithFilter(filter, currentPage, elementsPerPage)).thenReturn(certificates);
        Page<CertificateDto> actual = certificateService
                .retrievePageOfCertificatesFoundWithFilter(filter, currentPage, elementsPerPage);
        assertEquals(expected, actual);
    }

}