package com.epam.esm.dao;

import com.epam.esm.config.SpringJdbcConfig;
import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringJdbcConfig.class})
class CertificateDaoTest {
    private final CertificateDao certificateDao;


    @Autowired
    public CertificateDaoTest(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Test
    void ifInsertEntityReturnsValidId() {
        Certificate certificate = new Certificate();
        certificate.setTitle("title");
        certificate.setDescription("description");
        certificate.setPrice(new BigDecimal(10));
        certificate.setDuration(100);
        certificate.setCreatedDate(LocalDateTime.now());
        certificate.setLastUpdateDate(certificate.getCreatedDate());
        long id = certificateDao.insertEntity(certificate);
        assertTrue(id > 0);
    }

    @Test
    void ifReceiveEntityByIdReturnsCertificateFromDB() {
        long id = 2L;
        Certificate certificate = certificateDao.receiveEntityById(id).get();
        assertEquals(certificate.getId(), id);
    }

    @Test
    void ifReceiveEntityByIdReturnsEmptyOptional() {
        long id = 100L;
        Optional<Certificate> actual = certificateDao.receiveEntityById(id);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void ifReceiveCertificatesByCriteriaReturnsAll() {
        int expected = 3;
        List<Certificate> certificates =
                certificateDao.receiveCertificatesByCriteria(null, null, null, null);
        int actual = certificates.size();
        assertEquals(expected, actual);
    }

    @Test
    void ifReceiveCertificatesByCriteriaReturnsValid() {
        String textPart = "certificate";
        String sortBy = "title";
        String order = "DESC";
        String tagName = "tag1";
        Certificate certificate = new Certificate();
        certificate.setTitle("certificate2");
        certificate.setDescription("description2");
        certificate.setPrice(new BigDecimal(105));
        certificate.setDuration(20);
        certificate.setCreatedDate(LocalDateTime.parse("2022-04-19T13:00:00"));
        certificate.setLastUpdateDate(certificate.getCreatedDate());
        List<Certificate> actualCertificates =
                certificateDao.receiveCertificatesByCriteria(tagName, textPart, sortBy, order);
        assertEquals(certificate, actualCertificates.get(0));
    }

    @Test
    void ifUpdateCertificatesWorksCorrect() {
        Certificate certificate = new Certificate();
        certificate.setTitle("title2");
        certificateDao.updateCertificate(2, certificate);
        Certificate actual = certificateDao.receiveEntityById(2).get();
        assertEquals(certificate.getTitle(), actual.getTitle());
    }

    @Test
    void ifDeleteCertificateWorksCorrect() {
        long id = 2;
        certificateDao.deleteEntity(id);
        Optional<Certificate> actual = certificateDao.receiveEntityById(id);
        assertEquals(Optional.empty(), actual);
    }


}
