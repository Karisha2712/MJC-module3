package com.epam.esm.service;

import com.epam.esm.dao.CertificateAndTagDao;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.mapper.CertificateDtoMapperImpl;
import com.epam.esm.mapper.TagDtoMapperImpl;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {
    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);

    @InjectMocks
    private CertificateServiceImpl certificateService;
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private CertificateDao certificateDao;
    @Mock
    private CertificateAndTagDao certificateAndTagDao;
    @Mock
    private TagDao tagDao;

    private static final List<TagDto> tagDtos = new ArrayList<>();
    private static final List<CertificateDto> certificateDtos = new ArrayList<>();
    private static final List<Tag> tags = new ArrayList<>();
    private static final List<Certificate> certificates = new ArrayList<>();

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

        CertificateDto certificateDto1 = new CertificateDto();
        CertificateDto certificateDto2 = new CertificateDto();
        CertificateDto certificateDto3 = new CertificateDto();
        certificateDto1.setId(1L);
        certificateDto1.setTitle("title1");
        certificateDto1.setPrice(new BigDecimal(1));
        certificateDto1.setDuration(100);
        certificateDto1.setDescription("description1");
        certificateDto1.setCreatedDate("2022-04-19T11:00Z");
        certificateDto1.setLastUpdateDate("2022-04-19T11:00Z");
        certificateDto1.setTags(List.of(tagDto1));
        certificateDto2.setId(2L);
        certificateDto2.setTitle("title2");
        certificateDto2.setPrice(new BigDecimal(2));
        certificateDto2.setDuration(200);
        certificateDto2.setDescription("description2");
        certificateDto2.setCreatedDate("2022-04-19T12:00Z");
        certificateDto2.setLastUpdateDate("2022-04-19T12:00Z");
        certificateDto2.setTags(List.of(tagDto1, tagDto2));
        certificateDto3.setId(3L);
        certificateDto3.setTitle("title3");
        certificateDto3.setPrice(new BigDecimal(3));
        certificateDto3.setDuration(300);
        certificateDto3.setDescription("description3");
        certificateDto3.setCreatedDate("2022-04-19T13:00Z");
        certificateDto3.setLastUpdateDate("2022-04-19T13:00Z");
        certificateDto3.setTags(List.of(tagDto3));
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
        certificate1.setCreatedDate(LocalDateTime.parse("2022-04-19T11:00Z", formatter));
        certificate1.setLastUpdateDate(LocalDateTime.parse("2022-04-19T11:00Z", formatter));
        certificate2.setId(2L);
        certificate2.setTitle("title2");
        certificate2.setPrice(new BigDecimal(2));
        certificate2.setDuration(200);
        certificate2.setDescription("description2");
        certificate2.setCreatedDate(LocalDateTime.parse("2022-04-19T12:00Z", formatter));
        certificate2.setLastUpdateDate(LocalDateTime.parse("2022-04-19T12:00Z", formatter));
        certificate3.setId(3L);
        certificate3.setTitle("title3");
        certificate3.setPrice(new BigDecimal(3));
        certificate3.setDuration(300);
        certificate3.setDescription("description3");
        certificate3.setCreatedDate(LocalDateTime.parse("2022-04-19T13:00Z", formatter));
        certificate3.setLastUpdateDate(LocalDateTime.parse("2022-04-19T13:00Z", formatter));
        certificates.add(certificate1);
        certificates.add(certificate2);
        certificates.add(certificate3);
    }

    @BeforeEach
    void before() {
        tagService = new TagServiceImpl(tagDao, new TagDtoMapperImpl());
        certificateService = new CertificateServiceImpl(certificateDao, tagService, new TagDtoMapperImpl(),
                tagDao, new CertificateDtoMapperImpl(tagService), certificateAndTagDao);
    }

    @Test
    void ifRetrieveSingleCertificateReturnsCorrespondingCertificate() {
        CertificateDto certificateDto = certificateDtos.get(1);
        Certificate certificate = certificates.get(1);

        Mockito.when(certificateDao.receiveEntityById(2L)).thenReturn(Optional.of(certificate));
        Mockito.when(tagDao.receiveCertificateTags(2L)).thenReturn(List.of(tags.get(0), tags.get(1)));

        CertificateDto actual = certificateService.retrieveSingleCertificate(2L);
        assertEquals(certificateDto, actual);
    }

    @Test
    void ifRetrieveSingleCertificateThrowsNotFoundException() {
        Mockito.when(certificateDao.receiveEntityById(100L)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class,
                () -> certificateService.retrieveSingleCertificate(100L));
    }

    @Test
    void ifRemoveCertificateThrowsCertificateNotFoundException() {
        Mockito.when(certificateDao.receiveEntityById(100L)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class, () -> certificateService.removeCertificate(100L));
    }

    @Test
    void ifUpdateCertificateThrowsCertificateNotFoundException() {
        Mockito.when(certificateDao.receiveEntityById(100L)).thenReturn(Optional.empty());
        assertThrows(CertificateNotFoundException.class,
                () -> certificateService.updateCertificate(100L, certificateDtos.get(0)));
    }

    @Test
    void ifRetrieveCertificatesByCriteriaReturnsCorrespondingCertificates() {
        String textPart = "title";
        String sortBy = "title";
        String order = "DESC";
        String tagName = "tag1";
        List<Certificate> resultCertificates = List.of(certificates.get(1), certificates.get(0));
        List<CertificateDto> expected = List.of(certificateDtos.get(1), certificateDtos.get(0));
        Mockito.when(certificateDao.receiveCertificatesByCriteria(tagName, textPart, sortBy, order))
                .thenReturn(resultCertificates);
        Mockito.when(tagDao.receiveCertificateTags(1)).thenReturn(List.of(tags.get(0)));
        Mockito.when(tagDao.receiveCertificateTags(2)).thenReturn(List.of(tags.get(0), tags.get(1)));
        List<CertificateDto> actual =
                certificateService.retrieveCertificatesByCriteria(tagName, textPart, sortBy, order);
        assertEquals(expected, actual);
    }

    @Test
    void ifRetrieveCertificatesByCriteriaThrowsInvalidAttributeValueException() {
        String textPart = "title";
        String sortBy = "date";
        String order = "DESC";
        String tagName = "tag1";
        assertThrows(InvalidAttributeValueException.class,
                () -> certificateService.retrieveCertificatesByCriteria(tagName, textPart, sortBy, order));
    }

    @Test
    void ifRetrieveCertificatesByCriteriaReturnsAllCertificates() {
        Mockito.when(certificateDao.receiveCertificatesByCriteria(null, null, null, null))
                .thenReturn(certificates);
        Mockito.when(tagDao.receiveCertificateTags(1)).thenReturn(List.of(tags.get(0)));
        Mockito.when(tagDao.receiveCertificateTags(2)).thenReturn(List.of(tags.get(0), tags.get(1)));
        Mockito.when(tagDao.receiveCertificateTags(3)).thenReturn(List.of(tags.get(2)));
        List<CertificateDto> actual =
                certificateService.retrieveCertificatesByCriteria(null, null, null, null);
        assertEquals(certificateDtos, actual);
    }
}
