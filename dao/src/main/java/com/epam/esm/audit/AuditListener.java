package com.epam.esm.audit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.time.LocalDateTime;

public class AuditListener {
    private static final Logger logger = LogManager.getRootLogger();

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyOperation(Object entity) {
        logger.info("[AUDIT] starting add/update/delete operation for entity {} at {}",
                entity, LocalDateTime.now());
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyOperation(Object entity) {
        logger.info("[AUDIT] complete add/update/delete operation for entity {} at {}",
                entity, LocalDateTime.now());
    }

    @PostLoad
    private void afterLoadingEntity(Object entity) {
        logger.info("[AUDIT] entity {} loaded at {}",
                entity, LocalDateTime.now());
    }

}
