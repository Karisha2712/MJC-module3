package com.epam.esm.dao;

public interface CertificateAndTagDao {
    void insertRelationship(long certificateId, long tagId);

    void deleteRelationship(long tagId, long certificateId);
}
