package com.epam.esm.audit;

import com.epam.esm.entity.BaseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.time.LocalDateTime;

public class AuditListener {
    private static final Logger logger = LogManager.getRootLogger();

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyOperation(BaseEntity entity) {
        logger.info("[AUDIT] starting add/update/delete operation for entity {} with id {} at {}",
                entity.getClass().getSimpleName(), entity.getId(), LocalDateTime.now());
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyOperation(BaseEntity entity) {
        logger.info("[AUDIT] complete add/update/delete operation for entity {} with id {} at {}",
                entity.getClass().getSimpleName(), entity.getId(), LocalDateTime.now());
    }

    @PostLoad
    private void afterLoadingEntity(BaseEntity entity) {
        logger.info("[AUDIT] entity {} with id {} loaded at {}",
                entity.getClass().getSimpleName(), entity.getId(), LocalDateTime.now());
    }

}
